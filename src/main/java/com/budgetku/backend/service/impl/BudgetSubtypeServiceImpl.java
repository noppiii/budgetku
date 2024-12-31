package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.BudgetExceededException;
import com.budgetku.backend.exception.BudgetSubtypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetSubtypeNotFoundException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
import com.budgetku.backend.mapper.BudgetMapper;
import com.budgetku.backend.model.BudgetSubtype;
import com.budgetku.backend.model.BudgetType;
import com.budgetku.backend.payload.request.budget.BudgetSubtypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetSubtypeResponse;
import com.budgetku.backend.repository.BudgetSubtypeRepository;
import com.budgetku.backend.service.BudgetSubtypeService;
import com.budgetku.backend.service.BudgetTypeService;
import com.budgetku.backend.util.BudgetUtils;
import com.budgetku.backend.validator.BudgetValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetSubtypeServiceImpl implements BudgetSubtypeService {

    private final BudgetTypeService budgetTypeService;
    private final BudgetValidator budgetValidator;
    private final BudgetSubtypeRepository budgetSubtypeRepository;
    private final BudgetUtils budgetUtils;
    private final BudgetMapper budgetMapper;

    @Override
    @Transactional
    public BudgetSubtypeResponse addSubtypeToBudget(BudgetSubtypeRequest budgetSubtypeRequest) throws BudgetTypeNotFoundException, BudgetSubtypeAlreadyExistsException, BudgetSubtypeNotFoundException, BudgetExceededException {
        BudgetType budgetType = budgetTypeService.findBudgetTypeEntityById(budgetSubtypeRequest.getBudgetTypeId());
        budgetValidator.checkForExistingBudgetSubtype(budgetSubtypeRequest, budgetSubtypeRepository);
        budgetUtils.checkBudgetExceeded(budgetType, budgetSubtypeRequest, budgetSubtypeRepository, null);
        BudgetSubtype budgetSubtype = budgetMapper.toEntity(budgetSubtypeRequest);
        budgetSubtype.setBudgetType(budgetType);
        budgetSubtypeRepository.save(budgetSubtype);
        BudgetSubtypeResponse savedBudgetSubtypeResponse = budgetMapper.toDTO(budgetSubtype);
        savedBudgetSubtypeResponse.setCorrelationId(budgetSubtypeRequest.getCorrelationId());
        return savedBudgetSubtypeResponse;
    }

    @Override
    public BudgetSubtypeResponse updateBudgetSubtype(BudgetSubtypeRequest budgetSubtypeRequest) throws BudgetSubtypeNotFoundException, BudgetSubtypeAlreadyExistsException, BudgetExceededException {
        BudgetSubtype existingBudgetSubtype = findById(budgetSubtypeRequest.getId());
        budgetUtils.checkBudgetExceeded(existingBudgetSubtype.getBudgetType(), budgetSubtypeRequest, budgetSubtypeRepository, existingBudgetSubtype);
        budgetValidator.checkForExistingBudgetSubtypeUpdate(budgetSubtypeRequest, budgetSubtypeRepository);
        BudgetSubtype budgetSubtype = budgetMapper.toEntity(budgetSubtypeRequest);
        BudgetSubtypeResponse savedBudgetSubtypeResponse = budgetMapper.toDTO(budgetSubtypeRepository.save(budgetSubtype));
        return savedBudgetSubtypeResponse;
    }

    @Transactional
    public BudgetSubtype findById(UUID id) throws BudgetSubtypeNotFoundException {
        Optional<BudgetSubtype> budgetSubtype = budgetSubtypeRepository.findById(id);

        if (budgetSubtype.isPresent()) {
            return budgetSubtype.get();
        }

        throw new BudgetSubtypeNotFoundException(id);
    }
}
