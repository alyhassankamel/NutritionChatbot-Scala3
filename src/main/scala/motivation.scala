import scala.util.Random
import scala.io.StdIn.{readLine, readDouble}
import scala.io.Source
import java.io.File


object Motivations {
  var motivationUsageCount: Int = 0

  private val csvDirPath = "C:\\Personal Data\\Uni\\Y2\\Y2S2\\More\\The Underdogs_Nutrition Chatbot_Source\\Project\\chatbot\\src\\main\\resources\\"
  private val csvPath = s"${csvDirPath}motivations.csv"

  // Ensure resources directory exists
  private val resourcesDir = new File(csvDirPath)
  if (!resourcesDir.exists()) resourcesDir.mkdirs()

  // Create CSV file with motivations if it doesn't exist
  private def createMotivationsCSV(): Unit = {
    if (!new File(csvPath).exists()) {
      import java.io.{FileWriter, PrintWriter}
      val pw = new PrintWriter(new FileWriter(csvPath))
      try {
        // Write all motivations to CSV
        val motivationsData = List(
          "Believe in yourself and all that you are.",
          "Success doesn’t just find you. You have to go out and get it.",
          "Don't stop when you're tired. Stop when you're done.",
          "The harder you work for something, the greater you'll feel when you achieve it.",
          "Dream it. Wish it. Do it.",
          "Success is not for the lazy.",
          "You are your only limit.",
          "The only way to do great work is to love what you do.",
          "Opportunities don’t happen, you create them.",
          "Don't watch the clock; do what it does. Keep going.",
          "Hard work beats talent when talent doesn’t work hard.",
          "The future depends on what you do today.",
          "It always seems impossible until it's done.",
          "The key to success is to focus on goals, not obstacles.",
          "Believe you can and you're halfway there.",
          "Your limitation—it’s only your imagination.",
          "Push yourself because no one else is going to do it for you.",
          "Great things never come from comfort zones.",
          "Dream it. Believe it. Achieve it.",
          "Everything you need is already inside you."
        )

        // Write each motivation as a line in the CSV
        motivationsData.foreach(motivation => pw.println(motivation))
      } finally {
        pw.close()
      }
    }
  }

  // Initialize the CSV file when the object is loaded
  createMotivationsCSV()

  // Load motivations from CSV
  private def loadMotivationsFromCSV(): List[String] = {
    try {
      val source = Source.fromFile(csvPath)
      try {
        source.getLines().toList // No header, so no .tail needed
      } finally {
        source.close()
      }
    } catch {
      case e: Exception =>
        println(s"Error loading motivations from CSV: ${e.getMessage}")
        List("Keep pushing forward!") // Fallback motivation
    }
  }

  def motivate(): String = {
    motivationUsageCount += 1
    val motivations = loadMotivationsFromCSV()

    val randomIndex = Random.nextInt(motivations.length)
    motivations(randomIndex)
  }
}
