package com.budgetku.backend.model;

import com.budgetku.backend.model.audit.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
@Data
public class FailedPayment extends AbstractEntity {

    private String clientSecret;

    private int retryAttempts;

    private LocalDateTime lastAttemptTime;

    private boolean retryable;

}