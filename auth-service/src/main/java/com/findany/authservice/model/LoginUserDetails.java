package com.findany.authservice.model;

import com.findany.authservice.entity.UserSecret;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginUserDetails implements UserDetails {

    private static final long serialVersionUID = -586654147225147541L;

    private String username;
    private String password;
    private List<GrantedAuthority> roles;
    private boolean active;

    public LoginUserDetails() {
    }

    public LoginUserDetails(UserSecret userSecret) {
        this.username = userSecret.getEmail();
        this.password = userSecret.getPassword();
        this.active = userSecret.isActive();
        this.roles = userSecret.getRoles().stream()
                .map(roleType -> roleType.name())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}
