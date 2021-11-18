package com.findany.authservice.service;

import com.findany.authservice.constants.AppConstants;
import com.findany.authservice.entity.ConfirmationToken;
import com.findany.authservice.entity.UserSecret;
import com.findany.authservice.repository.ConfirmationTokenRepository;
import com.findany.authservice.repository.UserSecretsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class EmailService {

    @Autowired
    private UserSecretsRepository userSecretsRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(UserSecret userSecret, ConfirmationToken confirmationToken) {
        SimpleMailMessage confirmationMail = new SimpleMailMessage();
        confirmationMail.setTo(userSecret.getEmail());
        confirmationMail.setSubject("Complete Registration!");
        confirmationMail.setFrom(AppConstants.SENDER_EMAIL);
        confirmationMail.setText("To confirm your account, please click here : "
                + "http://localhost:8080/find-any/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());
        javaMailSender.send(confirmationMail);
    }

    public String confirmAccount(String confirmationToken) {
        Optional<ConfirmationToken> tokenOptional = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (!tokenOptional.isPresent()) {
            throw new RuntimeException("Confirmation Token invalid");
        }
        UserSecret userSecret = userSecretsRepository.getByEmail(tokenOptional.get().getUserSecret().getEmail());
        userSecret.setActive(true);
        userSecretsRepository.save(userSecret);
        return AppConstants.ACCOUNT_VERIFIED;
    }

}
