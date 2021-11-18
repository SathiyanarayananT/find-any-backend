package com.findany.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredentials implements Serializable {

    private static final long serialVersionUID = -8220618031810610091L;
    
    private String username;
    private String password;
}
