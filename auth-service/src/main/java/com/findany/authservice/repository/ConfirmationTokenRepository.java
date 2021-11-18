package com.findany.authservice.repository;

import com.findany.authservice.entity.ConfirmationToken;
import com.findany.authservice.entity.UserSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);

//    @Query("FROM ConfirmationToken c WHERE c.userSecret.id = :userId")
//    ConfirmationToken getByUserId(int userId);
    ConfirmationToken getByUserSecret(UserSecret userSecret);
}
