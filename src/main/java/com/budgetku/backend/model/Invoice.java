package com.budgetku.backend.model;

import com.budgetku.backend.model.audit.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;

@Entity
@Audited
@Getter
@Setter
public class Invoice extends AbstractEntity {

    @Column(nullable = false)
    private LocalDate dateOfEmission;

    @Column(nullable = false)
    private String description;

    @NotAudited
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "invoice")
    @JoinColumn(name = "movement_id")
    private Movement movement;

    private String fileKey;

    private String stripeReceiptUrl;
}