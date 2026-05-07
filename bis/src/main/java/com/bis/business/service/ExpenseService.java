package com.bis.business.service;

import com.bis.data.entity.Expense;
import com.bis.data.entity.User;
import com.bis.data.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BUSINESS LAYER — ExpenseService
 *
 * Handles all expense-related business logic.
 * Enforces data isolation: a user can only access their own expenses.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    /**
     * Returns all expenses belonging to the given user only.
     * This enforces the personal-use data isolation requirement.
     */
    @Transactional(readOnly = true)
    public List<Expense> getExpensesForUser(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        log.info("Loaded {} expense(s) for user '{}'.", expenses.size(), user.getUsername());
        return expenses;
    }
}