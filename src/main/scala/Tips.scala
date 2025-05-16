import scala.util.Random
import scala.io.StdIn.{readLine, readDouble}
import scala.io.Source
import java.io.File

object Tips {
  var healthyTipsUsageCount: Int = 0
  private val csvDirPath = "C:\\Personal Data\\Uni\\Y2\\Y2S2\\More\\The Underdogs_Nutrition Chatbot_Source\\Project\\chatbot\\src\\main\\resources\\"
  private val csvPath = s"${csvDirPath}tips.csv"

  // Ensure resources directory exists
  private val resourcesDir = new File(csvDirPath)
  if (!resourcesDir.exists()) resourcesDir.mkdirs()

  // Create CSV file with tips if it doesn't exist
  private def createTipsCSV(): Unit = {
    if (!new File(csvPath).exists()) {
      import java.io.{FileWriter, PrintWriter}
      val pw = new PrintWriter(new FileWriter(csvPath))
      try {
        // Write all tips to CSV
        val tipsData = List(
          "Drink plenty of water every day to stay hydrated.",
          "Include fruits and vegetables in every meal.",
          "Limit processed and sugary foods.",
          "Get at least 7-8 hours of sleep per night.",
          "Exercise for at least 30 minutes most days of the week.",
          "Take breaks from screens to rest your eyes and mind.",
          "Eat mindfully and avoid overeating.",
          "Don't skip breakfast—it boosts your metabolism.",
          "Choose whole grains over refined carbs.",
          "Reduce salt intake to maintain healthy blood pressure.",
          "Practice stress-reducing techniques like meditation.",
          "Avoid drinking too many sugary drinks or sodas.",
          "Keep healthy snacks like nuts or fruits on hand.",
          "Get regular health checkups.",
          "Cook more meals at home instead of eating out.",
          "Stand and stretch every hour during work or study.",
          "Limit red meat and choose lean protein sources.",
          "Chew your food slowly to help digestion.",
          "Stay consistent with your healthy routines.",
          "Take time for self-care and mental wellness."
        )

        // Write each tip as a line in the CSV
        tipsData.foreach(tip => pw.println(tip))
      } finally {
        pw.close()
      }
    }
  }

  // Initialize the CSV file when the object is loaded
  createTipsCSV()

  // Load tips from CSV
  private def loadTipsFromCSV(): List[String] = {
    try {
      val source = Source.fromFile(csvPath)
      try {
        source.getLines().toList
      } finally {
        source.close()
      }
    } catch {
      case e: Exception =>
        println(s"Error loading tips from CSV: ${e.getMessage}")
        List(
          "Drink plenty of water every day to stay hydrated."
        ) // Fallback tip
    }
  }

  def tip(): String = {
    healthyTipsUsageCount += 1
    val tips = loadTipsFromCSV()

    val randomIndex = Random.nextInt(tips.length)
    tips(randomIndex)
  }
}
