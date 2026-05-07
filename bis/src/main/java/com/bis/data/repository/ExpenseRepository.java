package com.bis.data.repository;

import com.bis.data.entity.Expense;
import com.bis.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DATA LAYER — Spring Data JPA Repository
 * Handles all database operations for the Expense entity.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /** Retrieves all expenses owned by a given user. */
    List<Expense> findByUser(User user);

    /** Used by CsvImportService to check duplicates if needed in future iterations. */
    boolean existsByUserAndDateAndValue(User user, java.time.LocalDate date, java.math.BigDecimal value);
}
