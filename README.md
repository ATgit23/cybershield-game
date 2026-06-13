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

---

## 🔧 Technology Stack

| Component | Technology |
|-----------|-----------|
| **Language** | Java 17 |
| **UI Framework** | JavaFX 17 |
| **Database** | SQLite 3 (JDBC) |
| **Build Tool** | Maven 3.8+ |
| **Version Control** | Git |

---

## 📚 Educational Content

This game teaches:

✅ **Phishing Attack Recognition**
- Fake email identification
- Domain spoofing detection
- Social engineering tactics

✅ **Strong Password Creation**
- Password complexity requirements
- Character types (uppercase, lowercase, numbers, symbols)
- Password length recommendations

✅ **Cybersecurity Best Practices**
- Two-Factor Authentication (2FA)
- Virtual Private Networks (VPNs)
- Regular password updates
- Email security

---

## 🎯 How to Play

### Step 1: Enter Your Name
Type your name in the input field on the main menu

### Step 2: Choose a Game Mode
🧠 Quiz Mode          → Answer 5 cybersecurity questions

📧 Phishing Detection → Identify 3 phishing emails

🔐 Password Strength  → Analyze password quality

🏆 Leaderboard       → View top scores

### Step 3: Play & Earn Points
Quiz Mode:        +100 for correct, -20 for wrong

Phishing Mode:    +150 for correct, -30 for wrong

Password Mode:    +50 for excellent passwords

### Step 4: Check Leaderboard
View your score and compete with other players

---

## 💾 Database

The game uses **SQLite** for persistent data storage:

```sql
-- Questions Table
CREATE TABLE questions (
  id INT PRIMARY KEY,
  question_text TEXT,
  option_a TEXT, option_b TEXT, option_c TEXT, option_d TEXT,
  correct TEXT,
  category TEXT,
  explanation TEXT
);

-- Users Table (Leaderboard)
CREATE TABLE users (
  id INT PRIMARY KEY,
  name TEXT UNIQUE,
  email TEXT,
  high_score INT,
  created_at TIMESTAMP
);
```

Database file: `cybershield.db` (auto-created in project root)

---

## 🎨 User Interface

The game features:
- 🎨 **Gradient backgrounds** for each mode
- 🎯 **Colorful buttons** with emoji icons
- 📊 **Real-time feedback** with visual indicators
- ⌚ **Auto-advancing questions** with timed feedback
- 📈 **Progress tracking** with score display

---

## 🏫 Educational Use Cases

This game is perfect for:
- 🏫 **Schools & Colleges** → Cybersecurity awareness training
- 🏢 **Companies** → Employee security training
- 👨‍💻 **Individuals** → Self-paced learning
- 📖 **Security Courses** → Practical learning tool

---

## 🚀 Future Enhancements

Potential features to add:
- [ ] Sound effects & background music
- [ ] More game modes (password cracking, encryption)
- [ ] Web version using Spring Boot + React
- [ ] Multiplayer leaderboard (cloud database)
- [ ] Difficulty levels (Easy, Medium, Hard)
- [ ] Certificate generation after completing all modes
- [ ] Export scores as PDF report

---

## 📜 License

This project is **open source** and available for educational purposes.

---

## 👨‍💻 Author

Created as an educational project to teach **cybersecurity awareness** in an interactive and engaging way.

---

## 📞 Support

### Issues or Bugs?
- Open an **Issue** on GitHub
- Describe the problem and steps to reproduce

### Want to Contribute?
- Fork the repository
- Create a new branch
- Make your changes
- Submit a Pull Request

---

## 🎓 Learning Resources

- [JavaFX Documentation](https://openjfx.io/openjfx-docs/)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Cybersecurity Basics](https://www.cisa.gov/cybersecurity-basics)
- [Password Security Best Practices](https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html)

---

## 🎉 Ready to Learn Cybersecurity?

**Download, play, and secure your digital world!** 🛡️

```bash
git clone https://github.com/YOUR_USERNAME/cybershield-game.git
cd cybershield-game
mvn javafx:run
```

---

**Happy Gaming & Learning!** 🚀