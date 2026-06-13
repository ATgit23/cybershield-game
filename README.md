# 🛡️ CyberShield — Cybersecurity Awareness Game

A JavaFX-based desktop game teaching cybersecurity best practices through interactive
quizzes, phishing detection, password challenges, and real-world scenarios.

## 🎮 Game Modes

- **🧠 Quiz Mode**: Timed multiple-choice cybersecurity questions
- **📧 Phishing Detection**: Identify real vs. fake emails
- **🔐 Password Strength**: Analyze password security
- **🏆 Leaderboard**: Track and compete for high scores

## 🚀 Quick Start

### Prerequisites
- Java JDK 17+
- Maven 3.8+

### Run the Game
```bash
mvn javafx:run
```

### Build as JAR
```bash
mvn clean package
java -jar target/cybershield-game-1.0.0.jar
```

## 📂 Project Structure
cybershield-game/

├── src/main/java/com/cybershield/

│   ├── CyberShieldGame.java      (Main entry point)

│   ├── GameManager.java          (Score & player management)

│   ├── DatabaseManager.java      (SQLite database)

│   ├── QuizMode.java             (Quiz gameplay)

│   ├── PhishingMode.java         (Phishing detection)

│   ├── PasswordMode.java         (Password strength)

│   ├── LeaderboardView.java      (Scores display)

│   └── ScoreEntry.java           (Data model)

│

├── src/main/resources/

│   ├── styles/

│   ├── images/

│   └── db/

│

├── pom.xml

├── README.md

└── .gitignore
## 🔧 Tech Stack
- **Language**: Java 17
- **UI Framework**: JavaFX 17
- **Database**: SQLite (JDBC)
- **Build Tool**: Maven

## 📝 Features
✅ Interactive quizzes on cybersecurity  
✅ Real phishing email detection  
✅ Password strength analyzer  
✅ Score tracking and leaderboard  
✅ Educational explanations  
✅ User-friendly GUI with animations

## 🎓 Educational Content
- Phishing attack identification
- Strong password creation
- Cybersecurity best practices
- Social engineering awareness

## 👨‍💻 Author
Created for cybersecurity awareness training in schools and companies.

---

**Ready to secure your digital world? Play CyberShield!** 🛡️