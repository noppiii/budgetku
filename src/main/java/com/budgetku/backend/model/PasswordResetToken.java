package com.budgetku.backend.model;

import com.budgetku.backend.model.audit.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDateTime;

@Entity
@Data
@Audited
public class PasswordResetToken extends AbstractEntity {

    private String token;

    private LocalDateTime expiryDate;

    @NotAudited
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User users;
}