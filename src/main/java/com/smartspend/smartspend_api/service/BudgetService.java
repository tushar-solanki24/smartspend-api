package com.smartspend.smartspend_api.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.smartspend.smartspend_api.dto.BudgetRequest;
import com.smartspend.smartspend_api.entity.Budget;
import com.smartspend.smartspend_api.entity.User;
import com.smartspend.smartspend_api.repository.BudgetRepository;
import com.smartspend.smartspend_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public Budget addBudget(@NonNull Long userId, @NonNull BudgetRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Budget budget = new Budget();
        budget.setAmount(request.getAmount());
        budget.setCategory(request.getCategory());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());
        budget.setUser(user);

        return budgetRepository.save(budget);
    }

    public Budget editBudget(@NonNull Long userId, @NonNull Long budgetId, @NonNull BudgetRequest request) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        if (!budget.getUser().getId().equals(userId)) {
            throw new RuntimeException("Budget does not belong to user");
        }

        budget.setAmount(request.getAmount());
        budget.setCategory(request.getCategory());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        return budgetRepository.save(budget);
    }

    public void deleteBudget(@NonNull Long userId, @NonNull Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        if (!budget.getUser().getId().equals(userId)) {
            throw new RuntimeException("Budget does not belong to user");
        }

        budgetRepository.delete(budget);
    }

    public List<Budget> getBudgetsByUser(Long userId) {
        return budgetRepository.findByUserId(userId);
    }
}