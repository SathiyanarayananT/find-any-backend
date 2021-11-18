package com.findany.authservice.repository;

import com.findany.authservice.entity.UserSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecretsRepository extends JpaRepository<UserSecret, Integer> {

    Optional<UserSecret> findByEmail(String email);

    UserSecret getByEmail(String email);

}
