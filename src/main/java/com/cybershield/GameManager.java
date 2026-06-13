package com.cybershield;

import java.util.List;

public class GameManager {
    private int totalScore = 0;
    private String playerName = "";
    private DatabaseManager dbManager;

    public GameManager() {
        this.dbManager = new DatabaseManager();
    }

    public void addScore(int points) {
        totalScore += points;
        System.out.println("➕ Points added: " + points + " | Total: " + totalScore);
    }

    public void subtractScore(int points) {
        totalScore = Math.max(0, totalScore - points);
        System.out.println("➖ Points subtracted: " + points + " | Total: " + totalScore);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void resetScore() {
        totalScore = 0;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void addToLeaderboard() {
        dbManager.saveHighScore(playerName, totalScore);
    }

    public List<ScoreEntry> getLeaderboard() {
        return dbManager.getLeaderboard();
    }

    public DatabaseManager getDatabase() {
        return dbManager;
    }

    public void shutdown() {
        dbManager.closeConnection();
    }
}