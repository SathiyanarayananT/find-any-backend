package com.findany.productfeedservice.feignclient;

import com.findany.productfeedservice.model.AuthToken;
import com.findany.productfeedservice.model.LoginCredentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceClient {

    @PostMapping("/find-any/auth/login")
    ResponseEntity<AuthToken> login(@RequestBody LoginCredentials loginCredentials);

}
