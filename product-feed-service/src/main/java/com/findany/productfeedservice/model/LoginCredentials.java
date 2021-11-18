package com.findany.productfeedservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LoginCredentials {

    @Value("${findany.service.username}")
    private String username;
    @Value("${findany.service.password}")
    private String password;

}
