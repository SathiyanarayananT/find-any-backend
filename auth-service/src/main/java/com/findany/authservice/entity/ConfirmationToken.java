package com.findany.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(targetEntity = UserSecret.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserSecret userSecret;
    @NotNull
    private String confirmationToken;
    @NotNull
    private Instant createdAt;

    public ConfirmationToken(UserSecret userSecret) {
        this.userSecret = userSecret;
        createdAt = Instant.now();
        confirmationToken = UUID.randomUUID().toString();
    }

}
