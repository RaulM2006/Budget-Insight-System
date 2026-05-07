package com.bis.presentation.controller;

import com.bis.business.service.ExpenseService;
import com.bis.data.entity.Expense;
import com.bis.data.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PRESENTATION LAYER — DashboardController
 *
 * Mediates between DashboardWindow (View) and ExpenseService (Business Layer).
 * Ensures the view only ever receives data belonging to the logged-in user.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final ExpenseService expenseService;

    /**
     * Loads expenses for the given user and returns them to the view.
     * Data isolation is enforced here — only this user's expenses are returned.
     */
    public List<Expense> loadExpenses(User user) {
        return expenseService.getExpensesForUser(user);
    }
}