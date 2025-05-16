import scala.io.StdIn.readLine
import scala.io.Source
import java.io.{File, FileWriter, PrintWriter}

object Quiz {
  var quizUsageCount: Int = 0
  var score: Int = 0 // At object level for Analytics access
  var questionsAnswered: Int = 0 // Track number of questions answered
  private val csvDirPath = "C:\\Personal Data\\Uni\\Y2\\Y2S2\\More\\The Underdogs_Nutrition Chatbot_Source\\Project\\chatbot\\src\\main\\resources\\"
  private val csvPath = s"${csvDirPath}quiz_questions.csv"

  private case class Question(
      question: String,
      options: List[String],
      correctAnswer: String
  )

  // Ensure resources directory exists
  private val resourcesDir = new File(csvDirPath)
  if (!resourcesDir.exists()) resourcesDir.mkdirs()

  // Create CSV file with questions if it doesn't exist
  private def createQuestionsCSV(): Unit = {
    if (!new File(csvPath).exists()) {
      val pw = new PrintWriter(new FileWriter(csvPath))
      try {
        // Format: question|option1|option2|option3|correctAnswer
        val questionsData = List(
          "What's the main function of carbs?|A) Build muscle|B) Provide energy|C) Help digestion|B",
          "Best source of omega-3?|A) Olive oil|B) Salmon|C) Whole milk|B",
          "What helps bowel regularity?|A) Protein|B) Fiber|C) Fats|B",
          "Which is a complete protein?|A) Beans|B) Chicken|C) Rice|B",
          "Healthiest fat type?|A) Saturated|B) Trans|C) Unsaturated|C",
          "Main mineral in bones?|A) Iron|B) Calcium|C) Zinc|B",
          "Powerful antioxidant vitamin?|A) C|B) D|C) B12|A",
          "Probiotics mainly help?|A) Gut health|B) Muscle growth|C) Energy|A",
          "Daily water intake?|A) 2-4 cups|B) 6-8 cups|C) 10-12 cups|B",
          "What's the recommended daily protein intake for an average adult?|A) 0.8g per kg of body weight|B) 1.2g per kg of body weight|C) 1.6g per kg of body weight|A",
          "Which exercise is best for improving cardiovascular health?|A) Weightlifting|B) Running|C) Yoga|B",
          "What's the primary source of energy during high-intensity exercise?|A) Fat|B) Carbohydrates|C) Protein|B",
          "Which macronutrient has the highest thermic effect?|A) Carbohydrates|B) Fats|C) Proteins|C",
          "What's the recommended weekly amount of moderate-intensity aerobic activity?|A) 75 minutes|B) 150 minutes|C) 300 minutes|B",
          "Which mineral is essential for muscle contraction?|A) Magnesium|B) Potassium|C) Sodium|A",
          "What's the ideal ratio of macronutrients for muscle building?|A) 40% carbs, 40% protein, 20% fat|B) 50% carbs, 30% protein, 20% fat|C) 30% carbs, 50% protein, 20% fat|B",
          "Which hormone is primarily responsible for muscle growth?|A) Insulin|B) Testosterone|C) Cortisol|B",
          "What's the minimum recommended sleep duration for optimal recovery?|A) 6 hours|B) 7-9 hours|C) 10 hours|B",
          "Which type of exercise is best for improving flexibility?|A) Strength training|B) Stretching|C) Cardio|B",
          "What's the recommended daily fiber intake for adults?|A) 15-20g|B) 25-30g|C) 35-40g|B"
        )

        // Write each question as a line in the CSV
        questionsData.foreach(pw.println)
      } finally {
        pw.close()
      }
    }
  }

  // Initialize the CSV file when the object is loaded
  createQuestionsCSV()

  // Load questions from CSV
  private def loadQuestionsFromCSV(): List[Question] = {
    try {
      val source = Source.fromFile(csvPath)
      try {
        source
          .getLines()
          .map { line =>
            val parts = line.split("\\|", -1)
            if (parts.length >= 5) {
              Question(
                parts(0),
                List(parts(1), parts(2), parts(3)),
                parts(4)
              )
            } else {
              // Fallback if CSV format is incorrect
              Question(
                "Which vitamin is known as the sunshine vitamin?",
                List("A) Vitamin A", "B) Vitamin D", "C) Vitamin C"),
                "B"
              )
            }
          }
          .toList
      } finally {
        source.close()
      }
    } catch {
      case e: Exception =>
        println(s"Error loading questions from CSV: ${e.getMessage}")
        // Return default question if CSV can't be read
        List(
          Question(
            "Which vitamin is known as the sunshine vitamin?",
            List("A) Vitamin A", "B) Vitamin D", "C) Vitamin C"),
            "B"
          )
        )
    }
  }

  // Helper to clean input (adapted from your parseInput)
  def cleanInput(input: String): String = {
    input
      .toLowerCase()
      .replaceAll("[.,!?]", "")
      .trim
  }

  // Extract answer text from option (e.g., "Vitamin D" from "B) Vitamin D")
  def extractAnswerText(option: String): String = {
    option.trim.dropWhile(_ != ')').drop(2).trim
  }

  // Parse input to match A, B, C, full answer, or keywords
  def parseInput(
      input: String,
      options: List[String],
      correctLetter: String
  ): Option[String] = {
    val cleanedInput = cleanInput(input)

    // Check for quit command
    if (cleanedInput == "q" || cleanedInput == "quit" || cleanedInput == "exit") {
      Some("Q") 
    } else {
      // Define patterns for this question
      val correctOption = options
        .find(_.startsWith(correctLetter + ")"))
        .map(extractAnswerText)
        .getOrElse("")
      val correctKeywords = correctOption.toLowerCase.split("\\s+").toSet

      // Patterns: (letter, possible inputs)
      val commandPatterns: List[(String, Set[String])] = options.map { option =>
        val letter = option.takeWhile(_ != ')').trim
        val answerText = extractAnswerText(option)
        val keywords = answerText.toLowerCase.split("\\s+").toSet
        (letter, Set(letter.toLowerCase, cleanInput(answerText)) ++ keywords)
      }

      // Find matching option by direct comparison to the cleaned input
      val matchedOption = commandPatterns.find {
        case (_, patterns) => 
          patterns.exists { pattern => 
            cleanedInput == pattern || 
            // Handle when user types just the answer text without the letter prefix
            (pattern.length > 1 && 
             pattern.matches("[a-zA-Z]\\).*").equals(false) && 
             cleanedInput == pattern)
          }
      }
      
      if (matchedOption.isDefined) {
        matchedOption.map(_._1)
      } else {
        // Check if input contains any answer text directly
        val containsOption = commandPatterns.find {
          case (_, patterns) =>
            patterns.exists(pattern => 
              pattern.length > 2 && cleanedInput.contains(pattern)
            )
        }
        
        if (containsOption.isDefined) {
          containsOption.map(_._1)
        } else {
          // Additional keyword check for correct answer (for partial matches)
          if (cleanedInput.split("\\s+").exists(keyword => correctKeywords.contains(keyword))) {
            Some(correctLetter)
          } else {
            None
          }
        }
      }
    }
  }

  def quiz(): Int = {
    quizUsageCount += 1
    score = 0 // Reset score at the start
    questionsAnswered = 0 // Reset questions answered counter
    val questions = loadQuestionsFromCSV()

    println("\nNutrition Quiz")
    println("Test your nutrition knowledge with multiple-choice questions.")
    println(
      "Answer with A, B, C, the full answer, or a keyword (e.g., 'Vitamin D'). Type Q or quit to exit the quiz.\n"
    )

    var shouldContinue = true

    for (question <- questions if shouldContinue) {
      println(question.question)
      question.options.foreach(println)

      print("Your answer: ")
      val answer = readLine().trim

      parseInput(answer, question.options, question.correctAnswer) match {
        case Some("Q") =>
          println("\nQuiz terminated early.")
          println(
            s"Final score: $score/$questionsAnswered (${f"${score * 100.0 / questionsAnswered}%.1f"}%)"
          )
          shouldContinue = false
        case Some(ans) if ans == question.correctAnswer =>
          println("Correct!\n")
          score += 1
          questionsAnswered += 1
        case Some(ans) =>
          val correctOption = question.options
            .find(_.startsWith(question.correctAnswer + ")"))
            .map(extractAnswerText)
            .getOrElse(question.correctAnswer)
          println(
            s"Wrong! Correct answer was ${question.correctAnswer}: $correctOption\n"
          )
          questionsAnswered += 1
        case None =>
          val correctOption = question.options
            .find(_.startsWith(question.correctAnswer + ")"))
            .map(extractAnswerText)
            .getOrElse(question.correctAnswer)
          println(
            s"Invalid input! Please enter A, B, C, the full answer, or a keyword. Correct answer was ${question.correctAnswer}: $correctOption\n"
          )
          questionsAnswered += 1
      }
    }

    if (shouldContinue) {
      println(
        s"Your score: $score/$questionsAnswered (${f"${score * 100.0 / questionsAnswered}%.1f"}%)"
      )
    }
    score
  }
}
