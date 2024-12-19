package com.budgetku.backend.model;

import com.budgetku.backend.model.audit.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Audited
@Getter
@Setter
public class BudgetSubtype extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private Double availableFunds;

    @Column(nullable = false)
    private String description;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "budget_type_id")
    private BudgetType budgetType;
}
