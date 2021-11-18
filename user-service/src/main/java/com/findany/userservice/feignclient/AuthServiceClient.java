package com.findany.userservice.feignclient;

import com.findany.userservice.model.UserSecretDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceClient {

    @PostMapping("/find-any/auth/user-secret")
    ResponseEntity<UserSecretDto> addUserSecret(@RequestBody UserSecretDto userSecretDto);

}
