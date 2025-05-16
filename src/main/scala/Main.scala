import scala.io.StdIn.readLine
import scala.util.Random
import Facts.fact
import Diet.diet
import BMI.calculateBMI
import FFMI.calculateFFMI
import Quiz.quiz
import Analytics.analytics
import Preferences.preferences
import User._
import Jokes.jokes
import Tips.tip
import Motivations.motivate
import CommandParser._
import ConversationManager._
import InputValidator._
import WorkoutPlanner.startWorkoutPlanner
import SmallTalk.smallTalk

@main def run(): Unit = {
  println("Welcome to the Nutrition Chatbot!")

  // Collect user data
  println("\nLet's get to know you better!")

  val name = getValidInput[String](
    "What's your name? ",
    validateName,
    "Please enter a valid name."
  )
  User.updateProfile(name = Some(name))

  val age = getValidInput[Int](
    "How old are you? ",
    input => validateAge(input.filter(_.isDigit)),
    "Please enter a valid age between 1 and 120."
  )
  User.updateProfile(age = Some(age))

  val height = getValidInput[Double](
    "What's your height? (in meters or feet) ",
    input => validateHeight(input.replaceAll("[^0-9.]", "")),
    "Please enter a valid height (0.5-3.0m or 1.5-10.0ft)."
  )
  User.updateProfile(height = Some(height))

  val weight = getValidInput[Double](
    "What's your weight? (in kg or lbs) ",
    input => validateWeight(input.replaceAll("[^0-9.]", "")),
    "Please enter a valid weight (20-300kg or 45-660lbs)."
  )
  User.updateProfile(weight = Some(weight))

  val gender = getValidInput[String](
    "What's your gender? (M/F) ",
    input =>
      validateGender(input.trim.toLowerCase match {
        case "m" | "male" | "man" | "boy"      => "M"
        case "f" | "female" | "woman" | "girl" => "F"
        case other                             => other
      }),
    "Please enter M for male or F for female."
  )
  User.updateProfile(gender = Some(gender))

  val bodyFat = getValidInput[Double](
    "What's your body fat percentage? (0-100) ",
    input => validateBodyFat(input.replaceAll("[^0-9.]", "")),
    "Please enter a valid body fat percentage between 0 and 100."
  )
  User.updateProfile(bodyFatPercentage = Some(bodyFat))

  val isImperial = getValidInput[Boolean](
    "Do you use imperial units (lbs/inches)? (yes/no) ",
    input => validateUnitPreference(input.trim.toLowerCase),
    "Please enter yes or no."
  )
  User.updateProfile(isImperial = Some(isImperial))

  println(
    "\nGreat! I'll remember your information to give you more personalized advice."
  )
  println("You can ask me about:")
  println("- Fitness facts")
  println("- BMI calculations")
  println("- FFMI calculations")
  println("- Diet and nutrition")
  println("- Fitness quizzes")
  println("- Personal analytics")
  println("- Workout Planner")
  println("- Healthy tips")
  println("- Motivational quotes")
  println("- Settings and preferences")

  println("\nType 'exit' to end our conversation")

  var continue = true

  while (continue) do {
    print("\nWhat would you like to know? ")
    val input = readLine()

    CommandParser.parseInput(input) match {

      case Some("greeting") =>
        val response = CommandParser.getRandomGreeting
        println(response)
        logInteraction(input, response)

      case Some("smallTalkTriggers") =>
        val response = smallTalk(input)
        println(response)
        logInteraction(input, response)

      case Some("thanks") =>
        val responses = List(
          "You're welcome! Always happy to help.",
          "Anytime! That's what I'm here for.",
          "No problem at all! Is there anything else you'd like to know?",
          "Glad I could help! Let me know if you need anything else.",
          "My pleasure! I enjoy helping with your fitness journey.",
          "Happy to be of service! What else can I assist with today?",
          "It's what I do! Anything else on your mind?",
          "You got it! Feel free to ask about anything else."
        )
        val response = responses(Random.nextInt(responses.size))
        println(response)
        logInteraction(input, response)

      case Some("tips") =>
        val response = tip()
        println(response)
        logInteraction(input, response)

      case Some("capabilities") =>
        val userName = User.name.getOrElse("").trim
        val greeting = if (userName.nonEmpty) s"$userName, here" else "Here"
        val response = s"""$greeting's what I can help with:
            |
            |Fact - Know some fun nutrition facts!
            |Nutrition - Healthy eating tips, meal plans
            |Calculations - Track body mass index, and fat free mass index
            |Quiz - Have a challenging nutrition test!
            |Workout Planner - Plan and track your workouts
            |Small Talk - Chat casually about whatever's on your mind
            |
            |What would you like to focus on today?""".stripMargin
        println(response)
        logInteraction(input, response)

      case Some("fact") =>
        val response = s"\nHere's an interesting fitness fact:\n${fact()}"
        println(response)
        logInteraction(input, response)

      case Some("funny") =>
        val response = jokes()
        println(response)
        logInteraction(input, response)

      case Some("reaction") =>
        val response = "Fascinating right?"
        println(response)
        logInteraction(input, response)

      case Some("bmi") =>
        println("\nLet's calculate your Body Mass Index!")
        println("This will help you understand your body composition.")
        BMI.bmiUsageCount +=1
        val bmi = calculateBMI()
        val response = if (bmi > 0) {
          val category =
            if (bmi < 18.5) "Underweight"
            else if (bmi <= 24.9) "Normal weight"
            else if (bmi <= 29.9) "Overweight"
            else if (bmi <= 34.9) "Obese"
            else "Extremely Obese"
          s"Your Body Mass Index (BMI) is: ${"%.1f".format(bmi)}\nCategory: $category"
        } else {
          "I couldn't calculate your BMI. Please make sure you've entered your height and weight correctly."
        }
        println(response)
        logInteraction(input, response)

      case Some("ffmi") =>
        println("\nLet's calculate your Fat-Free Mass Index!")
        println("This measures your muscle mass relative to your height.")
        FFMI.ffmiUsageCount += 1
        val ffmi = calculateFFMI()
        val response = if (ffmi > 0) {
          val category =
            if (ffmi < 18.0) "Below Average"
            else if (ffmi <= 20.0) "Average"
            else if (ffmi <= 22.0) "Above Average"
            else if (ffmi <= 24.0) "Excellent"
            else if (ffmi <= 26.0) "Superior"
            else "Exceptional"
          s"Your Fat-Free Mass Index (FFMI) is: ${"%.1f".format(ffmi)}\nCategory: $category"
        } else {
          "I couldn't calculate your FFMI. Please make sure you've entered your height, weight, and body fat percentage correctly."
        }
        println(response)
        logInteraction(input, response)

      case Some("diet") =>
        println("\nLet's talk about nutrition!")
        println("I can help you understand healthy eating habits.")
        diet()
        logInteraction(input, "Diet plan provided")

      case Some("preferences") =>
        println("\nLet's set up your fitness preferences!")
        println("This will help me give you more personalized advice.")
        preferences()
        logInteraction(input, "Preferences updated")

      case Some("quiz") =>
        println("\nLet's test your fitness knowledge!")
        println("I'll ask you some questions about fitness and nutrition.")
        val score = quiz()
        val totalQuestions = 21
        val percentage = f"${score * 100.0 / totalQuestions}%.1f"
        val response =
          s"\nYou answered $score out of $totalQuestions questions correctly!\nYour score: $percentage%"
        println(response)
        if (score >= 8) {
          println("You're a fitness expert!")
        } else if (score >= 5) {
          println("Not bad! You're on your way to becoming a fitness expert.")
        } else {
          println(
            "Let's keep practicing! Here are some fitness facts to help you improve:"
          )
          println(fact())
        }
        logInteraction(input, response)

      case Some("analytics") =>
        println("\nLet's check your fitness analytics!")
        println("I'll show you your progress and key metrics.")
        analytics()
        logInteraction(input, "Analytics displayed")

      case Some("workout") =>
        println("\nLet's plan your workout!")
        startWorkoutPlanner()
        logInteraction(input, "Workout planner started")

      case Some("clear_history") =>
        println(
          "Are you sure you want to clear the conversation history? Type 'yes' to confirm or anything else to cancel."
        )
        val confirm = readLine().trim.toLowerCase
        if (confirm == "yes") {
          ConversationManager.clearHistory()
          val response = "Conversation history has been cleared."
          println(response)
          logInteraction(input, response)
        } else {
          val response = "History clearing cancelled."
          println(response)
          logInteraction(input, response)
        }

      case Some("motivation") =>
        println("\nLet's talk about motivation!")
        println("I'll give you some tips to help you stay motivated.")
        val response = Motivations.motivate()
        println(response)
        logInteraction(input, response)

      case Some("view_history") =>
        val history = ConversationManager.viewHistory()
        println("\nConversation History:")
        println("-------------------")
        println(history)
        println("-------------------")
        logInteraction(input, "History displayed")

      case Some("exit") =>
        println(
          "Are you sure you want to exit? Type 'yes' to confirm or anything else to continue."
        )
        val confirm = readLine().trim.toLowerCase
        if (confirm == "yes") {
          val response = "Goodbye! Have a great day!"
          println(response)
          logInteraction(input, response)
          continue = false
        } else {
          val response = "Great! Let's continue. How can I help you?"
          println(response)
          logInteraction(input, response)
        }

      case Some(command) =>
        val response =
          s"I'm not sure what you mean by '$command'. Here are some examples:\n- Tell me a fitness fact\n- Calculate my BMI\n- Give me diet tips"
        println(response)
        logInteraction(input, response)

      case None =>
        val response =
          "I'm not sure what you mean. Could you please rephrase?\nHere are some examples:\n- Tell me a fact\n- Calculate my BMI\n- What should I eat?"
        println(response)
        logInteraction(input, response)

    }
  }
}
