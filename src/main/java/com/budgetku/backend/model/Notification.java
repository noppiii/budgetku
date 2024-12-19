package com.budgetku.backend.model;

import com.budgetku.backend.model.audit.AbstractEntity;
import com.budgetku.backend.model.enumType.NotificationStatus;
import com.budgetku.backend.model.enumType.NotificationType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Audited
@Data
@EntityListeners(AuditingEntityListener.class)
public class Notification extends AbstractEntity {

    private String sender;

    private String recipient;

    private String subject;

    private String body;

    private LocalDateTime sentDate;

    private int retryCount = 0;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private String fileKey;
}
