import Diet.diet
import BMI.calculateBMI
import Quiz.quiz
import FFMI.calculateFFMI

object Analytics {
  private case class AnalysisResult(
      value: Double,
      category: String,
      message: String
  )

  private def getBMIAnalysis(bmi: Double): AnalysisResult = bmi match {
    case b if b < 18.5 =>
      AnalysisResult(
        b,
        "Underweight",
        "Consider consulting a doctor to ensure you're at a healthy weight."
      )
    case b if b <= 24.9 =>
      AnalysisResult(b, "Normal", "Great job maintaining a healthy weight!")
    case b if b <= 29.9 =>
      AnalysisResult(
        b,
        "Overweight",
        "You might benefit from a balanced diet and exercise."
      )
    case b if b <= 34.9 =>
      AnalysisResult(
        b,
        "Obese",
        "It's recommended to consult a healthcare provider for guidance."
      )
    case b =>
      AnalysisResult(
        b,
        "Extremely Obese",
        "Please seek medical advice for support."
      )
  }

  private def getFFMIAnalysis(ffmi: Double): AnalysisResult = ffmi match {
    case f if f < 18.0 =>
      AnalysisResult(
        f,
        "Below Average",
        "You might consider increasing muscle mass through strength training."
      )
    case f if f <= 20.0 =>
      AnalysisResult(f, "Average", "You have a typical level of muscularity.")
    case f if f <= 22.0 =>
      AnalysisResult(
        f,
        "Above Average",
        "You have a good level of muscle mass!"
      )
    case f if f <= 24.0 =>
      AnalysisResult(f, "Excellent", "Your muscularity is impressive!")
    case f if f <= 26.0 =>
      AnalysisResult(
        f,
        "Superior",
        "You have an elite level of muscle mass, likely at a competitive level."
      )
    case f =>
      AnalysisResult(
        f,
        "Exceptional",
        "Your muscularity is extraordinary, possibly indicating professional bodybuilding."
      )
  }

  private def getQuizAnalysis(score: Int): AnalysisResult = {
    val totalQuestions = 21
    val percentage = (score * 100.0 / totalQuestions)

    val (category, message) = score match {
      case s if s >= 18 => ("Expert", "Your knowledge is impressive.")
      case s if s >= 12 =>
        ("Advanced", "Keep learning to become a fitness expert.")
      case s if s >= 6 =>
        ("Intermediate", "Keep practicing to improve your fitness knowledge.")
      case _ => ("Beginner", "Keep studying to improve your fitness knowledge.")
    }

    AnalysisResult(percentage, category, message)
  }

  def analytics(): Unit = {
    // BMI analytics
    val bmi = calculateBMI()
    if (bmi > 0) {
      println("\nBMI Analysis:")
      val bmiResult = getBMIAnalysis(bmi)
      println(
        s"Your BMI is ${bmiResult.value}. You are ${bmiResult.category}. ${bmiResult.message}"
      )
    } else {
      println("\nNo BMI data available.")
    }

    // FFMI analytics
    val ffmi = calculateFFMI()
    if (ffmi > 0) {
      println("\nFFMI Analysis:")
      val ffmiResult = getFFMIAnalysis(ffmi)
      println(
        s"Your FFMI is ${ffmiResult.value}. You are ${ffmiResult.category}. ${ffmiResult.message}"
      )
    } else {
      println("\nNo FFMI data available.")
    }

    // Quiz analytics
    if (Quiz.quizUsageCount > 0) {
      println("\nQuiz Analysis:")
      val quizResult = getQuizAnalysis(Quiz.score)
      println(
        s"Your quiz score: ${Quiz.score}/20 (${f"${quizResult.value}%.1f"}%)"
      )
      println(s"You're a ${quizResult.category}! ${quizResult.message}")
    } else {
      println("\nNo quiz data available.")
    }
  }

  def dietAnalytics(baseCalories: Double): Unit = {
    try {
      val (targetCalories, targetString) = Diet.target(baseCalories)
      val (purpose, suggestion) = Diet.suggestDiet(targetCalories)
      println(s"\nDiet Analysis:")
      println(s"Goal: $targetString")
      println(s"Calorie Target: $targetCalories")
      println(s"Recommended Plan: $purpose")
      println(s"\nMeal Plan:\n$suggestion")
    } catch {
      case e: Exception =>
        println("\nError analyzing diet data:")
        println("Please ensure all required user data is available.")
    }
  }
}
