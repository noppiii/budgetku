package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.BudgetSubtypeNotFoundException;
import com.budgetku.backend.exception.BudgetTypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
import com.budgetku.backend.mapper.BudgetMapper;
import com.budgetku.backend.model.BudgetType;
import com.budgetku.backend.payload.request.budget.BudgetTypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetTypeResponse;
import com.budgetku.backend.repository.BudgetTypeRepository;
import com.budgetku.backend.service.BudgetTypeService;
import com.budgetku.backend.validator.BudgetValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetTypeServiceImpl implements BudgetTypeService {

    private final BudgetValidator budgetValidator;
    private final BudgetTypeRepository budgetTypeRepository;
    private final BudgetMapper budgetMapper;

    @Override
    public BudgetTypeResponse createBudgetType(BudgetTypeRequest budgetTypeRequest) throws BudgetTypeAlreadyExistsException {
        budgetValidator.checkForExistingBudgetType(budgetTypeRequest, budgetTypeRepository);
        BudgetType budgetType = budgetMapper.toEntity(budgetTypeRequest);
        budgetTypeRepository.save(budgetType);
        BudgetTypeResponse savedBudgetTypeResponse = budgetMapper.toDTO(budgetType);
        savedBudgetTypeResponse.setCorrelationId(budgetTypeRequest.getCorrelationId());
        return savedBudgetTypeResponse;
    }

    @Override
    public void deleteBudgetType(UUID id) throws BudgetTypeNotFoundException {
        if (!budgetTypeRepository.existsById(id)) {
            throw new BudgetTypeNotFoundException(id);
        }

        budgetTypeRepository.deleteById(id);
    }

    @Override
    public BudgetTypeResponse updateBudgetType(BudgetTypeRequest budgetTypeRequest) throws BudgetTypeNotFoundException, BudgetTypeAlreadyExistsException, BudgetSubtypeNotFoundException {
        findById(budgetTypeRequest.getId());
        budgetValidator.checkForExistingBudgetTypeUpdate(budgetTypeRequest, budgetTypeRepository);
        BudgetType budgetType = budgetMapper.toEntity(budgetTypeRequest);
        BudgetTypeResponse savedBudgetTypeResponse = budgetMapper.toDTO(budgetTypeRepository.save(budgetType));
        return savedBudgetTypeResponse;
    }

    @Transactional
    public BudgetType findById(UUID id) throws BudgetTypeNotFoundException {
        Optional<BudgetType> budgetType = budgetTypeRepository.findById(id);

        if (budgetType.isPresent()) {
            return budgetType.get();
        }

        throw new BudgetTypeNotFoundException(id);
    }
}
