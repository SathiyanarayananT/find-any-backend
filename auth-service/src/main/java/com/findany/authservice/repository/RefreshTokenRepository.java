package com.findany.authservice.repository;

import com.findany.authservice.entity.RefreshToken;
import com.findany.authservice.entity.UserSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Optional<RefreshToken> getByUserSecret(UserSecret userSecret);

    Integer deleteByUserSecret(UserSecret userSecret);

}
