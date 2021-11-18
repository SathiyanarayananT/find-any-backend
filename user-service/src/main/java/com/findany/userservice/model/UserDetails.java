package com.findany.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private Instant createdAt;
    private Address address;
    private String password;
    @Enumerated(EnumType.STRING)
    private List<RoleType> roles;
    @Enumerated(EnumType.STRING)
    private Provider provider;
}
