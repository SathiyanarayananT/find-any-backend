package com.findany.userservice.service;

import com.findany.userservice.entity.User;
import com.findany.userservice.model.RoleType;
import com.findany.userservice.model.UserDetails;
import com.findany.userservice.model.UserSecretDto;
import com.findany.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User saveUserDetails(UserDetails userDetails) {
        User user = User.builder()
                .email(userDetails.getEmail())
                .firstname(userDetails.getFirstname())
                .lastname(userDetails.getLastname())
                .phone(userDetails.getPhone())
                .createdAt(Instant.now())
                .address(userDetails.getAddress())
                .build();
        user = userRepository.save(user);
        UserSecretDto userSecretDto = UserSecretDto.builder()
                .id(user.getId())
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .active(false)
                .roles(Arrays.asList(RoleType.ROLE_CONSUMER, RoleType.ROLE_PRODUCER))
                .provider(userDetails.getProvider())
                .build();
        updateUserAuthInfoAsync(userSecretDto);
        return user;
    }

    @Async
    private void updateUserAuthInfoAsync(UserSecretDto userSecretDto) {
        authService.addUserSecret(userSecretDto);
    }

    public User getUser(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found.");
        }
        return userOptional.get();
    }

}
