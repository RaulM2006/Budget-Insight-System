package com.bis.presentation.view;

import com.bis.presentation.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * PRESENTATION LAYER — LoginWindow (Boundary / View)
 *
 * Displays the login form and delegates actions to LoginController.
 * Matches UC-2: Authenticate User and the Login Sequence Diagram.
 *
 * Responsibilities:
 *  - Render username/password fields and a Login button
 *  - Show error messages on failed login
 *  - Delegate all business logic to LoginController — no logic here
 */
public class LoginWindow {

    private final LoginController controller;
    private final Stage stage;

    private TextField usernameField;
    private PasswordField passwordField;
    private Label errorLabel;

    public LoginWindow(LoginController controller) {
        this.controller = controller;
        this.stage = new Stage();
        controller.setView(this);
        buildUI();
    }

    // -------------------------------------------------------------------------
    // Public API (called by LoginController)
    // -------------------------------------------------------------------------

    public void show() {
        stage.show();
    }

    public void hide() {
        stage.hide();
    }

    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
        errorLabel.setVisible(false);
    }

    // -------------------------------------------------------------------------
    // UI construction
    // -------------------------------------------------------------------------

    private void buildUI() {
        stage.setTitle("Budget Insight System — Login");
        stage.setResizable(false);

        Label titleLabel = new Label("Budget Insight System");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefWidth(260);

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(260);
        loginButton.setDefaultButton(true);

        // Delegate to controller on button click or Enter key
        loginButton.setOnAction(e -> onLoginClicked());
        passwordField.setOnAction(e -> onLoginClicked());

        VBox root = new VBox(10,
                titleLabel,
                new Separator(),
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                errorLabel,
                loginButton
        );
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(24));

        stage.setScene(new Scene(root, 320, 280));
    }

    private void onLoginClicked() {
        errorLabel.setVisible(false);
        controller.handleLogin(usernameField.getText(), passwordField.getText());
    }
}
