package com.findany.authservice.service;

import com.findany.authservice.constants.AppConstants;
import com.findany.authservice.entity.ConfirmationToken;
import com.findany.authservice.entity.UserSecret;
import com.findany.authservice.model.LoginUserDetails;
import com.findany.authservice.model.RoleType;
import com.findany.authservice.model.UserSecretDto;
import com.findany.authservice.repository.ConfirmationTokenRepository;
import com.findany.authservice.repository.UserSecretsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private UserSecretsRepository userSecretsRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecret userSecret = userSecretsRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return new LoginUserDetails(userSecret);
    }

    public UserSecret addUserSecret(UserSecretDto userSecretDto) {
        Optional<UserSecret> userSecretOptional = userSecretsRepository.findByEmail(userSecretDto.getEmail());
        ConfirmationToken confirmationToken = null;
        UserSecret userSecret = null;
        if (!userSecretOptional.isPresent()) {
            userSecret = UserSecret.builder()
                    .id(userSecretDto.getId())
                    .email(userSecretDto.getEmail())
                    .password(passwordEncoder.encode(userSecretDto.getPassword()))
                    .active(false)
                    .roles(Arrays.asList(RoleType.ROLE_CONSUMER, RoleType.ROLE_PRODUCER))
                    .provider(userSecretDto.getProvider())
                    .build();
            userSecret = userSecretsRepository.save(userSecret);
            confirmationToken = new ConfirmationToken(userSecret);
            confirmationToken = confirmationTokenRepository.save(confirmationToken);
        } else {
            userSecret = userSecretOptional.get();
            confirmationToken = confirmationTokenRepository.getByUserSecret(userSecret);
        }
        emailService.sendEmail(userSecret, confirmationToken);
        return userSecret;
    }

}
