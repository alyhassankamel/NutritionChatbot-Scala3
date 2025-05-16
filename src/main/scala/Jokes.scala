import scala.util.Random
import scala.io.StdIn.{readLine, readDouble}
import scala.io.Source
import java.io.File

object Jokes {
  var jokeUsageCount: Int = 0
  private val csvDirPath = "C:\\Personal Data\\Uni\\Y2\\Y2S2\\More\\The Underdogs_Nutrition Chatbot_Source\\Project\\chatbot\\src\\main\\resources\\"
  private val csvPath = s"${csvDirPath}jokes.csv"

  // Ensure resources directory exists
  private val resourcesDir = new File(csvDirPath)
  if (!resourcesDir.exists()) resourcesDir.mkdirs()

  // Create CSV file with jokes if it doesn't exist
  private def createJokesCSV(): Unit = {
    if (!new File(csvPath).exists()) {
      import java.io.{FileWriter, PrintWriter}
      val pw = new PrintWriter(new FileWriter(csvPath))
      try {
        // Write all jokes to CSV
        val jokesData = List(
          // Nutrition-themed jokes
          "Why did the tomato turn red? Because it saw the salad dressing!",
          "Why don't eggs tell jokes? They'd crack each other up!",
          "What did the grape say when it got stepped on? Nothing, it just let out a little wine!",
          "Why did the banana go to the doctor? Because it wasn't peeling well!",
          "What do you call cheese that isn't yours? Nacho cheese!",
          "Why did the broccoli go to the gym? To get more vitamin 'BEEF'!",
          "What's a vampire's favorite fruit? A blood orange!",
          "Why did the cookie go to the doctor? Because it was feeling crummy!",
          "What did the mama corn say to the baby corn? Where's your pop corn?",
          "Why did the gym close down? It just didn't work out!",
          // General food jokes
          "I told my wife she was drawing her eyebrows too high. She looked surprised.",
          "Did you hear about the restaurant on the moon? Great food, no atmosphere!",
          "What do you call a fake noodle? An impasta!",
          "How does a penguin build its house? Igloos it together!",
          "Why don't skeletons fight each other? They don't have the guts!",
          "I'm reading a book about anti-gravity. It's impossible to put down!",
          "What's brown and sticky? A stick!",
          "Why did the scarecrow win an award? Because he was outstanding in his field!",
          "How do you organize a space party? You planet!",
          "You are so fat that when you go to a restaurant, the menu comes to you!",
          "What's the best thing about Switzerland? I don't know, but the flag is a big plus!"
        )

        // Write each joke as a line in the CSV
        jokesData.foreach(joke => pw.println(joke))
      } finally {
        pw.close()
      }
    }
  }

  // Initialize the CSV file when the object is loaded
  createJokesCSV()


  private def loadJokesFromCSV(): List[String] = {
    try {
      val source = Source.fromFile(csvPath)
      try {
        source.getLines().toList
      } finally {
        source.close()
      }
    } catch {
      case e: Exception =>
        println(s"Error loading jokes from CSV: ${e.getMessage}")
        List(
          "Why did the programmer quit his job? He didn't get arrays!"
        ) // Fallback joke
    }
  }

  def jokes(): String = {
    jokeUsageCount += 1
    val jokes = loadJokesFromCSV()

    val randomIndex = Random.nextInt(jokes.length)
    jokes(randomIndex)
  }
}
