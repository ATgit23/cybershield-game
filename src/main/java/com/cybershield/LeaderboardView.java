package com.cybershield;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class LeaderboardView {
    private Scene scene;

    public LeaderboardView(GameManager gameManager, Runnable returnToMenu) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f093fb, #f5576c);");

        Label title = new Label("🏆 Leaderboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        VBox leaderboardBox = new VBox(10);
        leaderboardBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-padding: 20; -fx-border-radius: 10;");
        leaderboardBox.setMaxWidth(500);

        List<ScoreEntry> entries = gameManager.getLeaderboard();

        if (entries.isEmpty()) {
            Label noData = new Label("No scores yet. Play a game first!");
            noData.setTextFill(Color.WHITE);
            leaderboardBox.getChildren().add(noData);
        } else {
            String[] medals = {"🥇", "🥈", "🥉"};
            for (int i = 0; i < entries.size(); i++) {
                ScoreEntry entry = entries.get(i);
                String medal = i < 3 ? medals[i] : (i + 1) + ".";

                Label line = new Label(medal + " " + entry.getName() + ": " + entry.getScore() + " pts");
                line.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                line.setTextFill(Color.WHITE);
                leaderboardBox.getChildren().add(line);
            }
        }

        Label currentScore = new Label("Your current session score: " + gameManager.getTotalScore());
        currentScore.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        currentScore.setTextFill(Color.LIGHTYELLOW);

        Button backBtn = new Button("🏠 Back to Menu");
        backBtn.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        backBtn.setPrefWidth(150);
        backBtn.setOnAction(e -> returnToMenu.run());

        root.getChildren().addAll(title, leaderboardBox, currentScore, backBtn);
        scene = new Scene(root, 600, 600);
    }

    public Scene getScene() { return scene; }
}