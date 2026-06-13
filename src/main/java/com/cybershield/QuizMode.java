package com.cybershield;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizMode {
    private Scene scene;
    private GameManager gameManager;
    private int currentQuestion = 0;
    private int correctAnswers = 0;
    private List<String[]> questions = new ArrayList<>();
    private Label scoreLabel;
    private Label questionLabel;
    private ToggleGroup optionsGroup;

    public QuizMode(GameManager gameManager, Runnable returnToMenu) {
        this.gameManager = gameManager;
        loadQuestions();

        VBox root = createMainLayout(returnToMenu);
        scene = new Scene(root, 700, 500);
    }

    private void loadQuestions() {
        try {
            ResultSet rs = gameManager.getDatabase().getQuestionsByCategory("general");
            while (rs.next() && questions.size() < 5) {
                String[] question = {
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct"),
                        rs.getString("explanation")
                };
                questions.add(question);
            }
        } catch (SQLException e) {
            System.err.println("Error loading questions: " + e.getMessage());
        }
    }

    private VBox createMainLayout(Runnable returnToMenu) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea, #764ba2);");
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("🧠 Cybersecurity Quiz");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.WHITE);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        scoreLabel.setTextFill(Color.LIGHTYELLOW);

        questionLabel = new Label("");
        questionLabel.setFont(Font.font("Arial", 16));
        questionLabel.setTextFill(Color.WHITE);
        questionLabel.setWrapText(true);

        optionsGroup = new ToggleGroup();

        RadioButton optA = new RadioButton();
        RadioButton optB = new RadioButton();
        RadioButton optC = new RadioButton();
        RadioButton optD = new RadioButton();

        optA.setToggleGroup(optionsGroup);
        optB.setToggleGroup(optionsGroup);
        optC.setToggleGroup(optionsGroup);
        optD.setToggleGroup(optionsGroup);

        optA.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        optB.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        optC.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        optD.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        Button submitBtn = new Button("Submit Answer");
        submitBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        submitBtn.setOnAction(e -> checkAnswer(optA, optB, optC, optD, submitBtn));

        Button backBtn = new Button("🏠 Back to Menu");
        backBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        backBtn.setOnAction(e -> returnToMenu.run());

        root.getChildren().addAll(
                title, scoreLabel, new Separator(),
                questionLabel,
                optA, optB, optC, optD,
                submitBtn, backBtn
        );

        showQuestion(optA, optB, optC, optD, submitBtn);
        return root;
    }

    private void showQuestion(RadioButton a, RadioButton b, RadioButton c, RadioButton d, Button btn) {
        if (currentQuestion >= questions.size()) {
            showFinalScore();
            return;
        }

        String[] q = questions.get(currentQuestion);
        questionLabel.setText((currentQuestion + 1) + ". " + q[0]);
        a.setText("A) " + q[1]);
        b.setText("B) " + q[2]);
        c.setText("C) " + q[3]);
        d.setText("D) " + q[4]);
        optionsGroup.selectToggle(null);
        btn.setDisable(false);
    }

    private void checkAnswer(RadioButton a, RadioButton b, RadioButton c, RadioButton d, Button btn) {
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        if (selected == null) {
            showAlert("Please select an answer!");
            return;
        }

        String[] q = questions.get(currentQuestion);
        char correctAnswer = q[5].charAt(0);

        if (selected.getText().startsWith(String.valueOf(correctAnswer))) {
            gameManager.addScore(100);
            correctAnswers++;
            showAlert("✅ Correct! " + q[6]);
        } else {
            gameManager.subtractScore(20);
            showAlert("❌ Wrong! " + q[6]);
        }

        scoreLabel.setText("Score: " + gameManager.getTotalScore());
        currentQuestion++;
        btn.setDisable(true);

        new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(() ->
                    showQuestion(a, b, c, d, btn)
            );
        }).start();
    }

    private void showFinalScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Complete!");
        alert.setHeaderText("🎉 Results");
        alert.setContentText(String.format(
                "Correct Answers: %d / %d\nFinal Score: %d",
                correctAnswers, questions.size(), gameManager.getTotalScore()
        ));
        alert.showAndWait();
        gameManager.addToLeaderboard();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Answer");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Scene getScene() { return scene; }
}