package com.smartspend.smartspend_api.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.smartspend.smartspend_api.dto.ExpenseRequest;
import com.smartspend.smartspend_api.entity.Expense;
import com.smartspend.smartspend_api.entity.User;
import com.smartspend.smartspend_api.repository.ExpenseRepository;
import com.smartspend.smartspend_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final GeminiService geminiService;

    public Expense addExpense(@NonNull Long userId, @NonNull ExpenseRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setDescription(request.getDescription());
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public void deleteExpense(@NonNull Long userId, @NonNull Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(userId)) {
            throw new RuntimeException("Expense does not belong to user");
        }

        expenseRepository.delete(expense);
    }

    public String analyzeExpenses(@NonNull Long userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return geminiService.analyzeExpenses(expenses);
    }
}