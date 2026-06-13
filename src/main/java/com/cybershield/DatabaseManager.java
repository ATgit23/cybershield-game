package com.cybershield;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:cybershield.db";
    private Connection connection;

    public DatabaseManager() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
            seedData();
            System.out.println("✅ Database connected successfully.");
        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
        }
    }

    private void createTables() throws SQLException {
        String questionsTable = """
            CREATE TABLE IF NOT EXISTS questions (
                id            INTEGER PRIMARY KEY AUTOINCREMENT,
                question_text TEXT    NOT NULL,
                option_a      TEXT    NOT NULL,
                option_b      TEXT    NOT NULL,
                option_c      TEXT    NOT NULL,
                option_d      TEXT    NOT NULL,
                correct       TEXT    NOT NULL,
                category      TEXT    NOT NULL,
                explanation   TEXT
            );
        """;

        String usersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id         INTEGER PRIMARY KEY AUTOINCREMENT,
                name       TEXT    NOT NULL UNIQUE,
                email      TEXT,
                high_score INTEGER DEFAULT 0,
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(questionsTable);
            stmt.execute(usersTable);
        }
    }

    private void seedData() throws SQLException {
        String checkSQL = "SELECT COUNT(*) FROM questions";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(checkSQL)) {
            if (rs.getInt(1) > 0) return;
        }

        String insertSQL = """
            INSERT INTO questions
            (question_text, option_a, option_b, option_c, option_d, correct, category, explanation)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        Object[][] questions = {
                {
                        "You receive an email asking for your password. What should you do?",
                        "Click the link immediately",
                        "Ignore and delete it",
                        "Report it as phishing",
                        "Reply with your password",
                        "C",
                        "general",
                        "Never click suspicious links. Always report phishing emails to your IT department."
                },
                {
                        "Which is the strongest password?",
                        "123456",
                        "qwerty",
                        "MyP@ssw0rd!2024",
                        "password123",
                        "C",
                        "general",
                        "Strong passwords use uppercase, lowercase, numbers, and special characters (8+ chars)."
                },
                {
                        "What does 2FA stand for?",
                        "Two File Authentication",
                        "Two-Factor Authentication",
                        "Two Firewall Access",
                        "Trusted File Access",
                        "B",
                        "general",
                        "2FA adds an extra security layer requiring a second verification step beyond your password."
                },
                {
                        "What is a VPN?",
                        "A virus protection tool",
                        "Virtual Private Network - encrypts your internet connection",
                        "Very Personal Network",
                        "Verified Private Number",
                        "B",
                        "general",
                        "A VPN masks your IP and encrypts data, protecting your privacy on public WiFi."
                },
                {
                        "How often should you change your password?",
                        "Never, same password forever",
                        "Every 10 years",
                        "Every 3-6 months or if compromised",
                        "Only when your boss asks",
                        "C",
                        "general",
                        "Change passwords regularly to reduce risk. Immediately if you suspect compromise."
                }
        };

        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            for (Object[] row : questions) {
                for (int i = 0; i < row.length; i++) {
                    ps.setObject(i + 1, row[i]);
                }
                ps.executeUpdate();
            }
        }
    }

    public ResultSet getQuestionsByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM questions WHERE category = ? ORDER BY RANDOM()";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, category);
        return ps.executeQuery();
    }

    public List<ScoreEntry> getLeaderboard() {
        List<ScoreEntry> entries = new ArrayList<>();
        String sql = "SELECT name, high_score FROM users ORDER BY high_score DESC LIMIT 10";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entries.add(new ScoreEntry(rs.getString("name"), rs.getInt("high_score")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
        }
        return entries;
    }

    public void saveHighScore(String name, int score) {
        String sql = "INSERT OR REPLACE INTO users (name, high_score) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, score);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving score: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}