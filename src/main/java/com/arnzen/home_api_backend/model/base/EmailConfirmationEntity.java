package com.arnzen.home_api_backend.model.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfirmationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    private String emailConfirmationCode;   // Hashed email confirmation code.
    private LocalDateTime createdAtDateTime;
    private LocalDateTime expiresAtDateTime;
    private LocalDateTime confirmedAtDateTime;
    private int incorrectAttempts;
    private boolean isActive;
}
