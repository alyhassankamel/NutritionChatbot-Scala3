import scala.util.Random
import scala.io.Source
import java.io.File
import java.io.{FileWriter, PrintWriter}
import java.time.{LocalTime, LocalDate}
import User._

object SmallTalk {
  var smallTalkUsageCount: Int = 0
  private var lastResponseIndex: Int =
    -1 // Track last response to avoid repetition

  private val csvDirPath = "C:\\Personal Data\\Uni\\Y2\\Y2S2\\More\\The Underdogs_Nutrition Chatbot_Source\\Project\\chatbot\\src\\main\\resources\\"
  private val csvPath = s"${csvDirPath}smalltalk.csv"

  // Ensure resources directory exists
  private val resourcesDir = new File(csvDirPath)
  if (!resourcesDir.exists()) resourcesDir.mkdirs()

  // Create CSV file with small talk responses if it doesn't exist
  private def createSmallTalkCSV(): Unit = {
    if (!new File(csvPath).exists()) {
      val pw = new PrintWriter(new FileWriter(csvPath))
      try {
        // Write header with categories
        pw.println("category,response")

        // General responses
        List(
          "general,I'm here for you! What's on your mind?",
          "general,I'm just doing my digital thing! 😊",
          "general,Always ready to chat! How are you doing?",
          "general,Just chilling in the code world. Want to talk about fitness?",
          "general,I'm here and listening. Let's talk!",
          "general,I'm all ears... or circuits 😄",
          "general,You caught me in a good mood. Let's chat about health!",
          "general,I'm online and ready to vibe with you!",
          "general,Great to hear from you! What do you want to talk about?",
          "general,I'm your fitness companion! How can I help you today?",
          "general,Always here to discuss nutrition and exercise!",
          "general,Ready to help with your wellness journey!",
          "general,I enjoy our conversations about health and fitness.",
          "general,Fitness is my passion! What's yours?",

          // Morning responses
          "morning,Good morning! Ready for a healthy day ahead?",
          "morning,Morning! Have you had a nutritious breakfast yet?",
          "morning,Rise and shine! Let's plan your workout for today.",
          "morning,Good morning! A great day for some exercise, don't you think?",
          "morning,Morning! Remember to hydrate as you start your day.",

          // Afternoon responses
          "afternoon,Good afternoon! How's your energy level today?",
          "afternoon,Afternoon! Have you taken a movement break yet?",
          "afternoon,Hope your day is going well! Need any fitness motivation?",
          "afternoon,Afternoon slump? Let's talk about healthy snacks to boost your energy!",
          "afternoon,Good afternoon! Remember to stay hydrated throughout the day.",

          // Evening responses
          "evening,Good evening! How was your activity level today?",
          "evening,Evening! Planning a nutritious dinner?",
          "evening,Wind-down time! Have you thought about some gentle stretching?",
          "evening,Good evening! Remember that good sleep is essential for recovery.",
          "evening,Evening check-in: How did your health goals go today?",

          // Feeling responses
          "feeling,I'm functioning at optimal performance! How are you feeling today?",
          "feeling,I'm programmed to be helpful and positive! How about you?",
          "feeling,I'm having a great day in the digital world! How's your day going?",
          "feeling,I'm feeling energetic and ready to assist with your fitness goals!",
          "feeling,I'm always in a good mood when I get to talk about health and wellness!",

          // Busy responses
          "busy,I'm helping fitness enthusiasts like you! What can I assist you with?",
          "busy,Just analyzing some health data. What's on your mind?",
          "busy,I'm learning more about nutrition so I can be more helpful to you!",
          "busy,I'm never too busy to chat about fitness and wellbeing!",
          "busy,Just updating my knowledge on the latest fitness trends. What can I help you with?",

          // Personalized responses (placeholder format for name insertion)
          "personal,I'm here to help with your fitness journey, %s! What would you like to talk about?",
          "personal,Great to see you, %s! How's your fitness routine going?",
          "personal,%s, I was just thinking about our last conversation. Any new health goals?",
          "personal,Hello, %s! Ready to chat about nutrition or exercise?",
          "personal,%s, it's always a pleasure to help with your wellness questions!"
        ).foreach(pw.println)
      } finally {
        pw.close()
      }
    }
  }

  // Initialize the CSV file when the object is loaded
  createSmallTalkCSV()

  // Data structure to hold responses by category
  private case class SmallTalkEntry(category: String, response: String)

  // Load small talk responses from CSV
  private def loadSmallTalkFromCSV(): Map[String, List[String]] = {
    try {
      val source = Source.fromFile(csvPath)
      try {
        val lines = source.getLines().toList

        // Skip header row
        val dataLines =
          if (lines.nonEmpty && lines.head.contains("category")) lines.tail
          else lines

        // Parse each line into a SmallTalkEntry
        val entries = dataLines.map { line =>
          val parts = line.split(",", 2)
          if (parts.length == 2) {
            SmallTalkEntry(parts(0).trim, parts(1).trim)
          } else {
            SmallTalkEntry("general", line.trim)
          }
        }

        // Group entries by category
        entries.groupBy(_.category).map { case (category, entries) =>
          category -> entries.map(_.response)
        }
      } finally {
        source.close()
      }
    } catch {
      case e: Exception =>
        println(s"Error loading small talk responses from CSV: ${e.getMessage}")
        Map(
          "general" -> List("I'm here to chat with you anytime!")
        ) // Fallback response
    }
  }

  def smallTalk(input: String = ""): String = {
    smallTalkUsageCount += 1
    val responses = loadSmallTalkFromCSV()
    val userName = User.name.getOrElse("").trim

    // Determine appropriate category based on time or input
    val category = determineCategory(input)

    // Get responses for the category, fallback to general if category not found
    val categoryResponses = responses.getOrElse(
      category,
      responses.getOrElse("general", List("I'm here to chat with you!"))
    )

    // Avoid repetition by ensuring we don't get the same response twice in a row
    val availableIndices = Range(0, categoryResponses.length)
      .filterNot(_ == lastResponseIndex)
      .toList
    val randomIndex = if (availableIndices.nonEmpty) {
      availableIndices(Random.nextInt(availableIndices.length))
    } else {
      Random.nextInt(categoryResponses.length)
    }

    lastResponseIndex = randomIndex
    val response = categoryResponses(randomIndex)

    // Format personalized responses with user's name if available
    if (category == "personal" && userName.nonEmpty) {
      response.format(userName)
    } else {
      response
    }
  }

  // Helper method to determine appropriate response category
  private def determineCategory(input: String): String = {
    val lowerInput = input.toLowerCase

    // Check for specific context in the input
    if (
      lowerInput.contains("how are you") || lowerInput.contains("how r u") ||
      lowerInput
        .contains("how do you feel") || lowerInput.contains("how are u feeling")
    ) {
      return "feeling"
    } else if (
      lowerInput.contains("what are you doing") || lowerInput.contains(
        "what r u doing"
      ) ||
      lowerInput.contains("are you busy") || lowerInput.contains("r u busy")
    ) {
      return "busy"
    }

    // Use personalized responses occasionally
    val userName = User.name.getOrElse("").trim
    if (userName.nonEmpty && Random.nextInt(3) == 0) { // 1/3 chance
      return "personal"
    }

    // Time-based responses
    val hour = LocalTime.now().getHour
    if (hour >= 5 && hour < 12) {
      "morning"
    } else if (hour >= 12 && hour < 18) {
      "afternoon"
    } else {
      "evening"
    }
  }
}
