package com.findany.authservice.service;

import com.findany.authservice.constants.AppConstants;
import com.findany.authservice.entity.RefreshToken;
import com.findany.authservice.repository.RefreshTokenRepository;
import com.findany.authservice.repository.UserSecretsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.sql.Ref;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserSecretsRepository userSecretsRepository;

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public RefreshToken createRefreshToken(int userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserSecret(userSecretsRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(AppConstants.JWT_REFRESH_TOKEN_VALIDITY));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.getByUserSecret(userSecretsRepository.findById(userId).get());
        if(refreshTokenOptional.isPresent()) {
            refreshTokenRepository.deleteById(refreshTokenOptional.get().getId());
        }
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(int userId) {
        return refreshTokenRepository.deleteByUserSecret(userSecretsRepository.findById(userId).get());
    }
}
