package com.findany.productfeedservice.service;

import com.findany.productfeedservice.feignclient.AuthServiceClient;
import com.findany.productfeedservice.model.AuthToken;
import com.findany.productfeedservice.model.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private LoginCredentials loginCredentials;

    public String getAccessToken() {
        ResponseEntity<AuthToken> authTokenResponse = authServiceClient.login(loginCredentials);
        if (!authTokenResponse.getStatusCode().is2xxSuccessful()) {
            String message = String.format("unable to get access token for service account, %s",
                    authTokenResponse.getStatusCode());
            throw new RuntimeException(message);
        }
        return authTokenResponse.getBody().getAccessToken();
    }

}
