# 🥗 Nutrition Chatbot – Scala 3 Terminal Assistant

![Scala Version](https://img.shields.io/badge/Scala-3.x-red)
![Build Tool](https://img.shields.io/badge/Build-sbt-blue)
![Platform](https://img.shields.io/badge/Platform-Terminal-informational)
![License](https://img.shields.io/badge/License-MIT-green)

A comprehensive, interactive Scala 3 chatbot that delivers personalized fitness and nutrition advice, BMI & FFMI tracking, quizzes, motivational content, and more — right from your terminal.

---

## 🧠 Overview

**Nutrition Chatbot** is a powerful command-line assistant built to support users in their health and fitness journey. It provides personalized advice, intelligent calculations, and engaging content to keep users motivated and informed.

---

## ✨ Features

- **User Profile Management** – Collects & stores user info (name, age, height, weight, gender, body fat %, preferences)
- **BMI & FFMI Calculation** – Analyzes body metrics and provides health classifications
- **Personalized Nutrition Tips** – Tailored dietary suggestions based on user data
- **Interactive Fitness Quiz** – Test your knowledge with a fun, educational quiz
- **Progress Analytics** – Track your metrics over time
- **Workout Planner** – Plan and manage workouts aligned with your goals
- **Motivational Quotes** – Get inspired to stay consistent
- **Fitness Facts & Jokes** – Fun, engaging content to keep it interesting
- **Natural Language Commands** – Talk to the chatbot in simple English
- **Conversation History** – Keeps logs of your previous interactions
- **Unit Conversion Support** – Metric and imperial measurement systems

---

## 🛠 Prerequisites

- Java JDK 11 or higher  
- [sbt](https://www.scala-sbt.org/) 1.5.0 or higher

---

## 🚀 Installation

```bash
git clone https://github.com/yourusername/nutrition-chatbot.git
cd nutrition-chatbot
sbt compile


## 🧾 Running the Application
sbt run

## 🧭 Usage Guide
🧍 Initial Setup
On first launch, the chatbot will request:

Name

Age

Height (meters or feet)

Weight (kg or lbs)

Gender (M/F)

Body fat percentage

Preferred measurement system (imperial or metric)

💬 Available Commands
You can interact naturally. Some supported inputs include:

Tell me a fitness fact

Calculate my BMI

Calculate my FFMI

I want to take a quiz

Show me my analytics

Help me plan a workout

Tell me about nutrition

I need motivation

Show my conversation history

Clear my history

Update my preferences

Tell me a joke

What can you do?

Exit


## 🧱 Project Structure

src/
├── Main.scala                 # Main loop
├── User.scala                 # User profile logic
├── BMI.scala                  # BMI calculations
├── FFMI.scala                 # FFMI calculations
├── Diet.scala                 # Nutrition tips
├── Facts.scala                # Fitness facts
├── Quiz.scala                 # Fitness quiz
├── Analytics.scala            # Tracking progress
├── WorkoutPlanner.scala       # Workout planning
├── Preferences.scala          # User preferences
├── Jokes.scala                # Fitness jokes
├── Tips.scala                 # Health tips
├── Motivations.scala          # Motivational quotes
├── CommandParser.scala        # NLP-style command handling
├── ConversationManager.scala  # History logging
├── InputValidator.scala       # Input validation
├── SmallTalk.scala            # Friendly interactions

##🧪 Development & Testing

sbt compile      # Compile the project
sbt run          # Run the chatbot
sbt console      # Start Scala 3 REPL


## 📜 License
This project is licensed under the MIT License – see the LICENSE file for details.

## 🙌 Contributing
Contributions, bug reports, and feature suggestions are welcome. Please open an issue or submit a pull request.

## ⭐️ Star This Project
If you find this helpful, give it a ⭐️ to help others discover it!
