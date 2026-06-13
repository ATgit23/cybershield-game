package com.cybershield;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class PhishingMode {
    private Scene scene;
    private GameManager gameManager;
    private List<PhishingEmail> emails = new ArrayList<>();
    private int currentEmailIndex = 0;
    private int correctIdentifications = 0;

    private Label senderLabel;
    private Label subjectLabel;
    private TextArea contentArea;
    private Label titleLabel;
    private Label scoreLabel;
    private Button phishingBtn;
    private Button legitimateBtn;
    private VBox feedbackBox;

    public PhishingMode(GameManager gameManager, Runnable returnToMenu) {
        this.gameManager = gameManager;
        loadEmails();

        VBox root = createLayout(returnToMenu);
        scene = new Scene(root, 800, 600);
    }

    private void loadEmails() {
        emails.add(new PhishingEmail(
                "noreply@amaz0n.com",
                "URGENT: Verify Your Account Immediately!",
                "Dear Customer,\n\nYour Amazon account has been flagged for security reasons.\n" +
                        "Click here to verify your identity: http://amaz0n-verify.xyz\n\n" +
                        "Do this immediately to avoid account suspension!",
                true,
                "❌ PHISHING: Notice the fake domain 'amaz0n.com' (with zero). Real Amazon uses 'amazon.com'"
        ));

        emails.add(new PhishingEmail(
                "security@yourbank.com",
                "Suspicious Activity Detected",
                "Hello,\n\nWe detected unusual login attempts on your account.\n" +
                        "Please verify your banking details here: www.yourbank.com/security\n\n" +
                        "This is a secure link from our official domain.",
                false,
                "✅ LEGITIMATE: Comes from official bank domain and uses HTTPS"
        ));

        emails.add(new PhishingEmail(
                "paypal-alert@updates-paypal.net",
                "Confirm Payment Information",
                "PayPal Alert:\n\nWe need to confirm your payment method.\n" +
                        "Click: http://paypal-verify.com/update\n\nYour account will be suspended in 24 hours!",
                true,
                "❌ PHISHING: Uses urgency tactic and suspicious domain 'updates-paypal.net'"
        ));
    }

    private VBox createLayout(Runnable returnToMenu) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6B6B, #FF8E72);");

        titleLabel = new Label("📧 Email 1 of " + emails.size());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        scoreLabel.setTextFill(Color.LIGHTYELLOW);

        senderLabel = new Label("From: ");
        senderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        senderLabel.setTextFill(Color.WHITE);

        subjectLabel = new Label("Subject: ");
        subjectLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        subjectLabel.setTextFill(Color.WHITE);

        contentArea = new TextArea();
        contentArea.setWrapText(true);
        contentArea.setPrefHeight(150);
        contentArea.setEditable(false);
        contentArea.setStyle("-fx-control-inner-background: #333; -fx-text-fill: white;");

        phishingBtn = new Button("🚨 This is Phishing!");
        phishingBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        phishingBtn.setPrefWidth(150);
        phishingBtn.setOnAction(e -> checkAnswer(true));

        legitimateBtn = new Button("✅ This is Legitimate");
        legitimateBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        legitimateBtn.setPrefWidth(150);
        legitimateBtn.setOnAction(e -> checkAnswer(false));

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(phishingBtn, legitimateBtn);

        feedbackBox = new VBox(10);
        feedbackBox.setVisible(false);

        Button backBtn = new Button("🏠 Back to Menu");
        backBtn.setStyle("-fx-background-color: #333; -fx-text-fill: white;");
        backBtn.setOnAction(e -> returnToMenu.run());

        root.getChildren().addAll(
                titleLabel, scoreLabel, new Separator(),
                senderLabel, subjectLabel,
                contentArea,
                buttonBox, feedbackBox,
                backBtn
        );

        showCurrentEmail();
        return root;
    }

    private void showCurrentEmail() {
        if (currentEmailIndex >= emails.size()) {
            showFinalResults();
            return;
        }

        PhishingEmail email = emails.get(currentEmailIndex);
        senderLabel.setText("From: " + email.getSender());
        subjectLabel.setText("Subject: " + email.getSubject());
        contentArea.setText(email.getContent());

        feedbackBox.getChildren().clear();
        feedbackBox.setVisible(false);
        phishingBtn.setDisable(false);
        legitimateBtn.setDisable(false);

        titleLabel.setText("📧 Email " + (currentEmailIndex + 1) + " of " + emails.size());
    }

    private void checkAnswer(boolean userSaysPhishing) {
        PhishingEmail email = emails.get(currentEmailIndex);
        boolean correct = (userSaysPhishing == email.isPhishing());

        if (correct) {
            gameManager.addScore(150);
            correctIdentifications++;
            showInlineFeedback("✅ Correct! " + email.getExplanation(), "#4CAF50");
        } else {
            gameManager.subtractScore(30);
            showInlineFeedback("❌ Wrong! " + email.getExplanation(), "#f44336");
        }

        scoreLabel.setText("Score: " + gameManager.getTotalScore());
        phishingBtn.setDisable(true);
        legitimateBtn.setDisable(true);

        new Thread(() -> {
            try { Thread.sleep(2500); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(() -> {
                currentEmailIndex++;
                showCurrentEmail();
            });
        }).start();
    }

    private void showInlineFeedback(String message, String color) {
        Label feedback = new Label(message);
        feedback.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        feedback.setStyle("-fx-background-color: " + color + "; -fx-padding: 10; -fx-background-radius: 8;");
        feedback.setTextFill(Color.WHITE);
        feedback.setWrapText(true);

        feedbackBox.getChildren().setAll(feedback);
        feedbackBox.setVisible(true);
    }

    private void showFinalResults() {
        double pct = (double) correctIdentifications / emails.size() * 100;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Phishing Mode Complete");
        alert.setHeaderText("🎉 Results");
        alert.setContentText(String.format(
                "Correctly identified: %d / %d (%.1f%%)\nTotal Score: %d",
                correctIdentifications, emails.size(), pct, gameManager.getTotalScore()
        ));
        alert.showAndWait();
        gameManager.addToLeaderboard();
    }

    public Scene getScene() { return scene; }

    private static class PhishingEmail {
        private String sender, subject, content, explanation;
        private boolean isPhishing;

        public PhishingEmail(String sender, String subject, String content, boolean isPhishing, String explanation) {
            this.sender = sender;
            this.subject = subject;
            this.content = content;
            this.isPhishing = isPhishing;
            this.explanation = explanation;
        }

        public String getSender() { return sender; }
        public String getSubject() { return subject; }
        public String getContent() { return content; }
        public boolean isPhishing() { return isPhishing; }
        public String getExplanation() { return explanation; }
    }
}