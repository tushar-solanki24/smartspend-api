package com.smartspend.smartspend_api.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartspend.smartspend_api.dto.BudgetRequest;
import com.smartspend.smartspend_api.entity.Budget;
import com.smartspend.smartspend_api.entity.User;
import com.smartspend.smartspend_api.repository.UserRepository;
import com.smartspend.smartspend_api.service.BudgetService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // ← add this!
public class BudgetController {

    private final BudgetService budgetService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Budget> addBudget(@RequestBody @NonNull BudgetRequest request) {
        Long userId = getCurrentUserId();
        Budget budget = budgetService.addBudget(userId, request);
        return ResponseEntity.ok(budget);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets() {
        Long userId = getCurrentUserId();
        List<Budget> budgets = budgetService.getBudgetsByUser(userId);
        return ResponseEntity.ok(budgets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> editBudget(@PathVariable @NonNull Long id,
            @RequestBody @NonNull BudgetRequest request) {
        Long userId = getCurrentUserId();
        Budget budget = budgetService.editBudget(userId, id, request);
        return ResponseEntity.ok(budget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable @NonNull Long id) {
        Long userId = getCurrentUserId();
        budgetService.deleteBudget(userId, id);
        return ResponseEntity.noContent().build();
    }

    private @NonNull Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return Objects.requireNonNull(user.getId(), "User ID must not be null");
    }
}
