package com.budgetku.backend.repository;

import com.budgetku.backend.model.BudgetSubtype;
import com.budgetku.backend.model.BudgetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BudgetSubtypeRepository extends JpaRepository<BudgetSubtype, UUID> {

    Optional<BudgetSubtype> findByName(String name);

    List<BudgetSubtype> findByBudgetType(BudgetType budgetType);
}
