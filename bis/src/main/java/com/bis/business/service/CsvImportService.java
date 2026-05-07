package com.bis.business.service;

import com.bis.data.entity.Expense;
import com.bis.data.entity.User;
import com.bis.data.repository.ExpenseRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * BUSINESS LAYER — CsvImportService
 *
 * Implements F1: Import Functionality.
 *
 * Expected CSV format (with header row):
 *   username,password,date,value,category
 *
 * Rules enforced by this layer:
 *  - Header row must match exactly (InvalidFormatException if not)
 *  - Date must be parseable as yyyy-MM-dd
 *  - Value must be a positive number
 *  - Category is optional (may be blank)
 *  - If a user already exists, their password is NOT updated (idempotent)
 *  - Each valid row creates the user (if new) and saves one Expense
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CsvImportService {

    static final String[] EXPECTED_HEADER = {"username", "password", "date", "value", "category"};
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final UserService userService;
    private final ExpenseRepository expenseRepository;

    /**
     * Parses and imports a CSV file, creating users and expenses.
     *
     * @param csvFile the CSV file to import
     * @return an ImportResult describing success/failure counts
     * @throws InvalidFormatException if the file header is wrong or unreadable
     */
    @Transactional
    public ImportResult importData(File csvFile) {
        if (csvFile == null || !csvFile.exists()) {
            throw new InvalidFormatException("File not found: " + (csvFile != null ? csvFile.getPath() : "null"));
        }

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            validateHeader(reader);
            return processRows(reader);
        } catch (IOException | CsvValidationException e) {
            throw new InvalidFormatException("Could not read file: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private void validateHeader(CSVReader reader) throws CsvValidationException, IOException {
        String[] header = reader.readNext();
        if (header == null) {
            throw new InvalidFormatException("CSV file is empty.");
        }
        for (int i = 0; i < EXPECTED_HEADER.length; i++) {
            if (i >= header.length || !EXPECTED_HEADER[i].equalsIgnoreCase(header[i].trim())) {
                throw new InvalidFormatException(
                        "Invalid CSV header. Expected: username,password,date,value,category");
            }
        }
    }

    private ImportResult processRows(CSVReader reader) throws IOException, CsvValidationException {
        int success = 0;
        int failures = 0;
        String[] row;

        while ((row = reader.readNext()) != null) {
            if (isBlankRow(row)) continue;

            try {
                processRow(row);
                success++;
            } catch (Exception e) {
                failures++;
                log.warn("Skipping invalid row {}: {}", success + failures, e.getMessage());
            }
        }

        String summary = String.format("Import complete: %d record(s) imported, %d skipped.", success, failures);
        log.info(summary);
        return new ImportResult(success, failures, summary);
    }

    private void processRow(String[] cols) {
        if (cols.length < 4) {
            throw new IllegalArgumentException("Row has too few columns.");
        }

        String username = cols[0].trim();
        String password = cols[1].trim();
        String dateStr  = cols[2].trim();
        String valueStr = cols[3].trim();
        String category = cols.length > 4 ? cols[4].trim() : null;

        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be empty.");
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format (expected yyyy-MM-dd): " + dateStr);
        }

        BigDecimal value;
        try {
            value = new BigDecimal(valueStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value: " + valueStr);
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expense value must be positive, got: " + valueStr);
        }

        // Delegate user creation to UserService (Business Layer)
        User user = userService.createUser(username, password);

        // Build and persist expense (Data Layer via repository)
        Expense expense = Expense.builder()
                .user(user)
                .date(date)
                .value(value)
                .category(category == null || category.isEmpty() ? null : category)
                .build();

        expenseRepository.save(expense);
    }

    private boolean isBlankRow(String[] row) {
        for (String cell : row) {
            if (cell != null && !cell.isBlank()) return false;
        }
        return true;
    }
}
