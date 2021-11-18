package com.findany.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSecretDto {

    private int id;
    private String email;
    private String password;
    private boolean active;
    private List<RoleType> roles;
    private Provider provider;

}
