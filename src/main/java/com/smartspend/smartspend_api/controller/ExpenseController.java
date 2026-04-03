package com.smartspend.smartspend_api.controller;

import com.smartspend.smartspend_api.dto.ExpenseRequest;
import com.smartspend.smartspend_api.entity.Expense;
import com.smartspend.smartspend_api.entity.User;
import com.smartspend.smartspend_api.repository.UserRepository;
import com.smartspend.smartspend_api.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // ← add this!
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody @NonNull ExpenseRequest request) {
        Long userId = getCurrentUserId();
        Expense expense = expenseService.addExpense(userId, request);
        return ResponseEntity.ok(expense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses() {
        Long userId = getCurrentUserId();
        List<Expense> expenses = expenseService.getExpensesByUser(userId);
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable @NonNull Long id) {
        Long userId = getCurrentUserId();
        expenseService.deleteExpense(userId, id);
        return ResponseEntity.noContent().build();
    }

    private @NonNull Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return Objects.requireNonNull(user.getId(), "User ID must not be null");
    }

    @GetMapping("/analyze")
    public ResponseEntity<String> analyzeExpenses() {
        Long userId = getCurrentUserId();
        String advice = expenseService.analyzeExpenses(userId);
        return ResponseEntity.ok(advice);
    }
}