package com.bis.presentation.view;

import com.bis.DashboardWindowContext;
import com.bis.business.service.ImportResult;
import com.bis.business.service.InvalidFormatException;
import com.bis.data.entity.Expense;
import com.bis.data.entity.User;
import com.bis.presentation.controller.DashboardController;
import com.bis.presentation.controller.ImportController;
import com.bis.presentation.controller.LoginController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * PRESENTATION LAYER — DashboardWindow (Boundary / View)
 *
 * The main window after successful login.
 * Shows only the logged-in user's own expenses (data isolation enforced via DashboardController).
 */
public class DashboardWindow {

    private final User user;
    private final LoginController loginController;
    private final DashboardController dashboardController;
    private final ImportController importController;

    private final Stage stage;
    private TableView<Expense> expenseTable;
    private Label statusLabel;

    public DashboardWindow(User user, LoginController loginController) {
        this.user = user;
        this.loginController = loginController;
        this.dashboardController = DashboardWindowContext.getDashboardController();
        this.importController = DashboardWindowContext.getImportController();
        this.stage = new Stage();
        buildUI();
        // Load this user's expenses immediately on open
        loadExpenses();
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void show() {
        stage.show();
    }

    public void hide() {
        stage.hide();
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private void loadExpenses() {
        if (dashboardController == null) return;
        List<Expense> expenses = dashboardController.loadExpenses(user);
        expenseTable.setItems(FXCollections.observableArrayList(expenses));
        if (!expenses.isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText(expenses.size() + " expense(s) loaded.");
        }
    }

    // -------------------------------------------------------------------------
    // UI construction
    // -------------------------------------------------------------------------

    private void buildUI() {
        stage.setTitle("Budget Insight System — Dashboard");
        stage.setWidth(720);
        stage.setHeight(500);

        Label welcomeLabel = new Label("Welcome, " + user.getUsername());
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 15));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> onLogout());

        HBox topBar = new HBox(10, welcomeLabel, new Pane(), logoutButton);
        HBox.setHgrow(topBar.getChildren().get(1), Priority.ALWAYS);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 16, 10, 16));
        topBar.setStyle("-fx-border-color: #cccccc; -fx-border-width: 0 0 1 0;");

        expenseTable = buildExpenseTable();

        Button importButton = new Button("Import CSV…");
        importButton.setOnAction(e -> onImportCsv());

        statusLabel = new Label("Your expenses are shown above.");
        statusLabel.setWrapText(true);

        HBox importBar = new HBox(10, importButton, statusLabel);
        importBar.setAlignment(Pos.CENTER_LEFT);
        importBar.setPadding(new Insets(10, 16, 10, 16));

        VBox root = new VBox(topBar, expenseTable, new Separator(), importBar);
        VBox.setVgrow(expenseTable, Priority.ALWAYS);

        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(e -> loginController.handleLogout());
    }

    @SuppressWarnings("unchecked")
    private TableView<Expense> buildExpenseTable() {
        TableView<Expense> table = new TableView<>();
        table.setPlaceholder(new Label("No expenses found. Import a CSV file to populate data."));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Expense, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDate().toString()));

        TableColumn<Expense, String> valueCol = new TableColumn<>("Amount");
        valueCol.setCellValueFactory(c -> new SimpleStringProperty(
                String.format("%.2f", c.getValue().getValue())));

        TableColumn<Expense, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(c -> {
            String cat = c.getValue().getCategory();
            return new SimpleStringProperty(cat != null ? cat : "—");
        });

        table.getColumns().addAll(dateCol, valueCol, categoryCol);
        return table;
    }

    // -------------------------------------------------------------------------
    // Event handlers
    // -------------------------------------------------------------------------

    private void onImportCsv() {
        if (importController == null) {
            statusLabel.setText("Import service not available.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select CSV File");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = chooser.showOpenDialog(stage);
        if (file == null) return;

        try {
            ImportResult result = importController.handleImport(file);
            statusLabel.setStyle(result.hasFailures() ? "-fx-text-fill: orange;" : "-fx-text-fill: green;");
            statusLabel.setText(result.getSummary());
            // Reload only this user's expenses after import
            loadExpenses();
        } catch (InvalidFormatException e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Import failed: " + e.getMessage());
        }
    }

    private void onLogout() {
        stage.hide();
        loginController.handleLogout();
    }
}