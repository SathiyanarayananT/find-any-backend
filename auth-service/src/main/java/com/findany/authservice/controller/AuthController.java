package com.findany.authservice.controller;

import com.findany.authservice.config.JwtTokenUtil;
import com.findany.authservice.constants.AppConstants;
import com.findany.authservice.entity.RefreshToken;
import com.findany.authservice.entity.UserSecret;
import com.findany.authservice.model.*;
import com.findany.authservice.repository.UserSecretsRepository;
import com.findany.authservice.service.EmailService;
import com.findany.authservice.service.LoginUserDetailsService;
import com.findany.authservice.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserSecretsRepository userSecretsRepository;

    @PostMapping("/login")
    public AuthTokenResponse validateLoginCredentials(@RequestBody LoginCredentials loginCredentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(),
                loginCredentials.getPassword()));
        UserDetails userDetails = loginUserDetailsService.loadUserByUsername(loginCredentials.getUsername());
        String authToken = jwtTokenUtil.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userSecretsRepository.getByEmail(userDetails.getUsername()).getId());
        return AuthTokenResponse.builder()
                .username(userDetails.getUsername())
                .accessToken(authToken)
                .refreshToken(refreshToken.getRefreshToken())
                .tokenType(AppConstants.BEARER)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PostMapping("/refreshtoken")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserSecret)
                .map(user -> {
                    String token = jwtTokenUtil.generateToken(loginUserDetailsService.loadUserByUsername(user.getEmail()));
                    return new RefreshTokenResponse(token, refreshToken);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @PostMapping("/user-secret")
    public UserSecret addUserSecret(@RequestBody UserSecretDto userSecretDto) {
        return loginUserDetailsService.addUserSecret(userSecretDto);
    }

    @GetMapping("/confirm-account")
    public String confirmAccount(@RequestParam(name = "token") String confirmationToken) {
        return emailService.confirmAccount(confirmationToken);
    }

}

