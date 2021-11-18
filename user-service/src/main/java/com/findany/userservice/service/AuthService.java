package com.findany.userservice.service;

import com.findany.userservice.feignclient.AuthServiceClient;
import com.findany.userservice.model.UserSecretDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthServiceClient authServiceClient;

    public void addUserSecret(UserSecretDto userSecretDto) {
        ResponseEntity<UserSecretDto> userSecretResponse = authServiceClient.addUserSecret(userSecretDto);
        if (!userSecretResponse.getStatusCode().is2xxSuccessful()) {
            String message = String.format("unable to add user secret, contact administrator. %s",
                    userSecretResponse.getStatusCode());
            throw new RuntimeException(message);
        }
    }
}
