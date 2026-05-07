package com.bis.business.service;

import com.bis.data.entity.Expense;
import com.bis.data.entity.User;
import com.bis.data.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CsvImportService — Business Layer.
 * Uses a temporary directory for real CSV files and mocks for the data layer.
 */
@ExtendWith(MockitoExtension.class)
class CsvImportServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ExpenseRepository expenseRepository;

    private CsvImportService importService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        importService = new CsvImportService(userService, expenseRepository);
    }

    // -------------------------------------------------------------------------
    // Happy path
    // -------------------------------------------------------------------------

    @Test
    void importData_validFile_importsAllRows() throws IOException {
        File csv = writeFile("valid.csv",
                "username,password,date,value,category\n" +
                "alice,pass1,2024-01-15,50.00,Food\n" +
                "bob,pass2,2024-01-16,120.50,Transport\n"
        );

        User alice = User.builder().username("alice").build();
        User bob   = User.builder().username("bob").build();
        when(userService.createUser("alice", "pass1")).thenReturn(alice);
        when(userService.createUser("bob",   "pass2")).thenReturn(bob);
        when(expenseRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ImportResult result = importService.importData(csv);

        assertThat(result.getSuccessCount()).isEqualTo(2);
        assertThat(result.getFailureCount()).isEqualTo(0);
        verify(expenseRepository, times(2)).save(any(Expense.class));
    }

    @Test
    void importData_blankCategoryOptional_setsNull() throws IOException {
        File csv = writeFile("no_category.csv",
                "username,password,date,value,category\n" +
                "alice,pass,2024-03-01,30.00,\n"
        );

        User alice = User.builder().username("alice").build();
        when(userService.createUser(eq("alice"), any())).thenReturn(alice);

        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);
        when(expenseRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        importService.importData(csv);

        assertThat(captor.getValue().getCategory()).isNull();
    }

    // -------------------------------------------------------------------------
    // Invalid format
    // -------------------------------------------------------------------------

    @Test
    void importData_wrongHeader_throwsInvalidFormatException() throws IOException {
        File csv = writeFile("bad_header.csv",
                "name,pass,date,amount,cat\n" +
                "alice,pass,2024-01-01,10.00,Food\n"
        );

        assertThatThrownBy(() -> importService.importData(csv))
                .isInstanceOf(InvalidFormatException.class)
                .hasMessageContaining("Invalid CSV header");
    }

    @Test
    void importData_emptyFile_throwsInvalidFormatException() throws IOException {
        File csv = writeFile("empty.csv", "");

        assertThatThrownBy(() -> importService.importData(csv))
                .isInstanceOf(InvalidFormatException.class)
                .hasMessageContaining("empty");
    }

    @Test
    void importData_nullFile_throwsInvalidFormatException() {
        assertThatThrownBy(() -> importService.importData(null))
                .isInstanceOf(InvalidFormatException.class);
    }

    // -------------------------------------------------------------------------
    // Row-level validation
    // -------------------------------------------------------------------------

    @Test
    void importData_negativeValue_skipsRow() throws IOException {
        File csv = writeFile("negative.csv",
                "username,password,date,value,category\n" +
                "alice,pass,-2024-01-01,-5.00,Food\n"
        );

        ImportResult result = importService.importData(csv);

        assertThat(result.getSuccessCount()).isEqualTo(0);
        assertThat(result.getFailureCount()).isEqualTo(1);
    }

    @Test
    void importData_badDate_skipsRow() throws IOException {
        File csv = writeFile("bad_date.csv",
                "username,password,date,value,category\n" +
                "alice,pass,not-a-date,10.00,Food\n"
        );

        ImportResult result = importService.importData(csv);

        assertThat(result.getFailureCount()).isEqualTo(1);
    }

    @Test
    void importData_mixedValidAndInvalid_countsCorrectly() throws IOException {
        File csv = writeFile("mixed.csv",
                "username,password,date,value,category\n" +
                "alice,pass,2024-01-01,10.00,Food\n" +
                "bob,pass,bad-date,20.00,Fun\n" +
                "carol,pass,2024-02-01,30.00,\n"
        );

        User alice = User.builder().username("alice").build();
        User carol = User.builder().username("carol").build();
        when(userService.createUser(eq("alice"), any())).thenReturn(alice);
        when(userService.createUser(eq("carol"), any())).thenReturn(carol);
        when(expenseRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ImportResult result = importService.importData(csv);

        assertThat(result.getSuccessCount()).isEqualTo(2);
        assertThat(result.getFailureCount()).isEqualTo(1);
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private File writeFile(String name, String content) throws IOException {
        Path path = tempDir.resolve(name);
        Files.writeString(path, content);
        return path.toFile();
    }
}
