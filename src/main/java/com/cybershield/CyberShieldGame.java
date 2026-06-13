package com.cybershield;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CyberShieldGame extends Application {
    private Stage primaryStage;
    private GameManager gameManager;
    private Scene mainMenuScene;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.gameManager = new GameManager();

        primaryStage.setTitle("🛡️ CyberShield - Cybersecurity Awareness Game");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        showMainMenu();
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> gameManager.shutdown());
    }

    private void showMainMenu() {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea, #764ba2);");

        Label title = new Label("🛡️ CyberShield");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Cybersecurity Awareness Game");
        subtitle.setFont(Font.font("Arial", 18));
        subtitle.setTextFill(Color.LIGHTYELLOW);

        HBox nameBox = new HBox(10);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Enter your name:");
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("Arial", 14));

        TextField nameField = new TextField();
        nameField.setPromptText("Your name");
        nameField.setMaxWidth(250);

        nameBox.getChildren().addAll(nameLabel, nameField);

        VBox modesBox = new VBox(15);
        modesBox.setAlignment(Pos.CENTER);

        Button quizBtn = createModeButton("🧠 Quiz Mode", "#667eea");
        quizBtn.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "Player" : nameField.getText();
            gameManager.setPlayerName(name);
            gameManager.resetScore();
            showGameMode(new QuizMode(gameManager, this::showMainMenu).getScene());
        });

        Button phishingBtn = createModeButton("📧 Phishing Detection", "#FF6B6B");
        phishingBtn.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "Player" : nameField.getText();
            gameManager.setPlayerName(name);
            gameManager.resetScore();
            showGameMode(new PhishingMode(gameManager, this::showMainMenu).getScene());
        });

        Button passwordBtn = createModeButton("🔐 Password Strength", "#11998e");
        passwordBtn.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "Player" : nameField.getText();
            gameManager.setPlayerName(name);
            gameManager.resetScore();
            showGameMode(new PasswordMode(gameManager, this::showMainMenu).getScene());
        });

        Button leaderboardBtn = createModeButton("🏆 Leaderboard", "#f093fb");
        leaderboardBtn.setOnAction(e ->
                showGameMode(new LeaderboardView(gameManager, this::showMainMenu).getScene())
        );

        Button exitBtn = createModeButton("❌ Exit Game", "#333333");
        exitBtn.setOnAction(e -> {
            gameManager.shutdown();
            primaryStage.close();
        });

        modesBox.getChildren().addAll(quizBtn, phishingBtn, passwordBtn, leaderboardBtn, exitBtn);

        root.getChildren().addAll(title, subtitle, new Separator(), nameBox, new Separator(), modesBox);

        mainMenuScene = new Scene(root, 800, 700);
        primaryStage.setScene(mainMenuScene);
    }

    private Button createModeButton(String text, String color) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                "-fx-padding: 15; -fx-font-size: 16;");
        btn.setPrefWidth(300);
        return btn;
    }

    private void showGameMode(Scene gameScene) {
        primaryStage.setScene(gameScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}