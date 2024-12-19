package com.budgetku.backend.model;

import com.budgetku.backend.model.audit.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;

@Entity
@Audited
@Getter
@Setter
public class BudgetType extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private Double availableFunds;

    @Column(nullable = false)
    private String description;

    @NotAudited
    @OneToMany(mappedBy = "budgetType", cascade = CascadeType.ALL)
    private List<BudgetSubtype> subtypes;
}