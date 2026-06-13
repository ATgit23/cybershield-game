package com.cybershield;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PasswordMode {

    private Scene scene;
    private GameManager gameManager;

    public PasswordMode(GameManager gameManager, Runnable returnToMenu) {
        this.gameManager = gameManager;

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #11998e, #38ef7d);");

        Label title = new Label("Password Strength Checker");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setTextFill(Color.WHITE);

        Label instructions = new Label("Type a password below to test its strength:");
        instructions.setTextFill(Color.WHITE);
        instructions.setFont(Font.font("Arial", 14));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter a password...");
        passwordField.setMaxWidth(350);
        passwordField.setFont(Font.font("Arial", 16));

        TextField visibleField = new TextField();
        visibleField.setMaxWidth(350);
        visibleField.setFont(Font.font("Arial", 16));
        visibleField.setVisible(false);
        visibleField.setManaged(false);

        CheckBox showPassword = new CheckBox("Show Password");
        showPassword.setTextFill(Color.WHITE);
        showPassword.setOnAction(e -> {
            if (showPassword.isSelected()) {
                visibleField.setText(passwordField.getText());
                visibleField.setVisible(true);
                visibleField.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);
            } else {
                passwordField.setText(visibleField.getText());
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                visibleField.setVisible(false);
                visibleField.setManaged(false);
            }
        });

        ProgressBar strengthBar = new ProgressBar(0);
        strengthBar.setPrefWidth(350);
        strengthBar.setPrefHeight(20);

        Label strengthLabel = new Label("Strength: Enter a password");
        strengthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        strengthLabel.setTextFill(Color.WHITE);

        Label tipsLabel = new Label("");
        tipsLabel.setTextFill(Color.LIGHTYELLOW);
        tipsLabel.setWrapText(true);
        tipsLabel.setMaxWidth(400);

        passwordField.textProperty().addListener((obs, old, text) -> {
            analyzePassword(text, strengthBar, strengthLabel, tipsLabel);
        });

        visibleField.textProperty().addListener((obs, old, text) -> {
            analyzePassword(text, strengthBar, strengthLabel, tipsLabel);
        });

        Button backBtn = new Button("Back to Menu");
        backBtn.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-weight: bold;");
        backBtn.setOnAction(e -> returnToMenu.run());

        root.getChildren().addAll(
                title,
                instructions,
                passwordField,
                visibleField,
                showPassword,
                strengthBar,
                strengthLabel,
                tipsLabel,
                backBtn
        );

        scene = new Scene(root, 600, 500);
    }

    private void analyzePassword(String pwd, ProgressBar bar, Label label, Label tips) {
        int score = 0;
        StringBuilder feedback = new StringBuilder("Tips: ");

        if (pwd.length() >= 8) {
            score++;
        } else {
            feedback.append("Use 8+ chars. ");
        }

        if (pwd.length() >= 12) {
            score++;
        } else {
            feedback.append("12+ chars is better. ");
        }

        if (pwd.matches(".*[A-Z].*")) {
            score++;
        } else {
            feedback.append("Add uppercase letters. ");
        }

        if (pwd.matches(".*[a-z].*")) {
            score++;
        } else {
            feedback.append("Add lowercase letters. ");
        }

        if (pwd.matches(".*[0-9].*")) {
            score++;
        } else {
            feedback.append("Add numbers. ");
        }

        if (pwd.matches(".*[!@#$%^&*()_+\\-=\\[\\]\\{\\}].*")) {
            score++;
        } else {
            feedback.append("Add symbols such as !@#. ");
        }

        double strength = score / 6.0;
        bar.setProgress(strength);

        String levelText;
        String colorCode;

        if (score == 0) {
            levelText = "Very Weak";
            colorCode = "#f44336";
        } else if (score == 1) {
            levelText = "Weak";
            colorCode = "#FF9800";
        } else if (score == 2) {
            levelText = "Fair";
            colorCode = "#FFC107";
        } else if (score == 3) {
            levelText = "Good";
            colorCode = "#8BC34A";
        } else if (score == 4) {
            levelText = "Strong";
            colorCode = "#4CAF50";
        } else {
            levelText = "Very Strong";
            colorCode = "#00BCD4";
        }

        label.setText("Strength: " + levelText);
        bar.setStyle("-fx-accent: " + colorCode + ";");

        if (score >= 5) {
            tips.setText("Excellent password!");
        } else {
            tips.setText(feedback.toString());
        }
    }

    public Scene getScene() {
        return scene;
    }
}