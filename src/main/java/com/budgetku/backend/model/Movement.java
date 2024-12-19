package com.budgetku.backend.model;

import com.budgetku.backend.model.audit.AbstractEntity;
import com.budgetku.backend.model.enumType.MovementStatus;
import com.budgetku.backend.model.enumType.MovementType;
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
public class Movement extends AbstractEntity {

    @Column(nullable = false)
    private String documentNumber;

    @Column(nullable = false)
    private LocalDate dateOfEmission;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double valueWithoutIva;

    private Double ivaValue;

    private Double ivaRate;

    private Double totalValue;

    @Enumerated(EnumType.STRING)
    private MovementStatus status;

    @NotAudited
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @NotAudited
    @JoinColumn(name = "invoice_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Invoice invoice;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "budget_subtype_id")
    private BudgetSubtype budgetSubtype;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "budget_type_id")
    private BudgetType budgetType;

    @NotAudited
    @ManyToOne
    private Supplier supplier;
}