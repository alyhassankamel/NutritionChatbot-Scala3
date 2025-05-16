# Nutrition Chatbot

A comprehensive, interactive nutrition and fitness assistant built with Scala 3.

## Overview

The Nutrition Chatbot is an intelligent command-line application designed to help users on their fitness journey. It provides personalized advice, calculations, and information about nutrition, fitness, and health. The chatbot collects user data to offer tailored recommendations and maintains conversation history for a seamless experience.

## Features

- **User Profile Management**: Collects and stores user information (name, age, height, weight, gender, body fat percentage)
- **BMI Calculation**: Calculates Body Mass Index and provides category classification
- **FFMI Calculation**: Calculates Fat-Free Mass Index to measure muscle mass relative to height
- **Diet & Nutrition Advice**: Offers personalized nutrition guidance
- **Fitness Facts**: Provides interesting facts about fitness and nutrition
- **Interactive Quiz**: Tests users' knowledge about fitness and nutrition
- **Analytics**: Tracks user progress and key metrics
- **Workout Planner**: Helps users plan and track their workouts
- **Preferences**: Allows users to set fitness preferences for personalized advice
- **Motivational Quotes**: Delivers motivation to keep users inspired
- **Conversation History**: Maintains a log of interactions
- **Unit Conversion**: Supports both metric and imperial measurement systems

## Prerequisites

- Java JDK 11 or higher
- sbt (Scala Build Tool) 1.5.0 or higher

## Installation

1. Clone this repository:
   ```
   git clone https://github.com/yourusername/nutrition-chatbot.git
   cd nutrition-chatbot
   ```

2. Build the project:
   ```
   sbt compile
   ```

## Running the Application

To start the chatbot:

```
sbt run
```

## Usage Guide

1. **Initial Setup**:
   - When you first run the application, the chatbot will ask for your personal information:
     - Name
     - Age
     - Height (in meters or feet)
     - Weight (in kg or lbs)
     - Gender (M/F)
     - Body fat percentage
     - Preferred measurement system (imperial or metric)

2. **Available Commands**:
   - After setup, you can interact with the chatbot using natural language. Here are some examples:
     - `Tell me a fitness fact` - Get an interesting fitness fact
     - `Calculate my BMI` - Calculate your Body Mass Index
     - `Calculate my FFMI` - Calculate your Fat-Free Mass Index
     - `I want to take a quiz` - Test your fitness knowledge
     - `Show me my analytics` - View your fitness analytics
     - `Help me plan a workout` - Start the workout planner
     - `Tell me about nutrition` - Get diet and nutrition advice
     - `I need motivation` - Get a motivational quote
     - `Show my conversation history` - View past interactions
     - `Clear my history` - Delete conversation history
     - `Update my preferences` - Change your fitness preferences
     - `Tell me a joke` - Hear a fitness-related joke
     - `What can you do?` - See all available capabilities
     - `Exit` - End the conversation

3. **Conversation Flow**:
   - The chatbot uses natural language processing to understand your requests
   - It will respond with relevant information and may ask follow-up questions
   - Your interactions are logged to provide a personalized experience

## Development

This project is built with Scala 3 using the sbt build system.

### Project Structure

- `Main.scala`: Entry point and main conversation loop
- Supporting modules:
  - `User.scala`: User profile management
  - `BMI.scala`: BMI calculation functionality
  - `FFMI.scala`: FFMI calculation functionality
  - `Diet.scala`: Nutrition advice
  - `Facts.scala`: Fitness facts database
  - `Quiz.scala`: Interactive quiz functionality
  - `Analytics.scala`: User progress tracking
  - `WorkoutPlanner.scala`: Workout planning functionality
  - `Preferences.scala`: User preference management
  - `Jokes.scala`: Fitness-related jokes
  - `Tips.scala`: Health and fitness tips
  - `Motivations.scala`: Motivational quotes
  - `CommandParser.scala`: Natural language processing
  - `ConversationManager.scala`: Conversation history management
  - `InputValidator.scala`: User input validation
  - `SmallTalk.scala`: Casual conversation handling

### Building and Testing

- Compile the code: `sbt compile`
- Run the application: `sbt run`
- Start a Scala 3 REPL: `sbt console`


