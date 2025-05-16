import scala.util.Random
import scala.io.StdIn.{readLine, readDouble}
import scala.io.Source
import java.io.File


object Facts {
  var factUsageCount: Int = 0
  private val csvDirPath = "C:\\Personal Data\\Uni\\Y2\\Y2S2\\More\\The Underdogs_Nutrition Chatbot_Source\\Project\\chatbot\\src\\main\\resources\\"
  private val csvPath = s"${csvDirPath}facts.csv"

  // Ensure resources directory exists
  private val resourcesDir = new File(csvDirPath)
  if (!resourcesDir.exists()) resourcesDir.mkdirs()

  // Create CSV file with facts if it doesn't exist
  private def createFactsCSV(): Unit = {
    if (!new File(csvPath).exists()) {
      import java.io.{FileWriter, PrintWriter}
      val pw = new PrintWriter(new FileWriter(csvPath))
      try {
        // Write all facts to CSV
        val factsData = List(
          // General Facts
          "Carrots are a good source of beta-carotene, which is converted to vitamin A in the body.",
          "Avocados are high in healthy monounsaturated fats.",
          "Spinach is rich in iron and folate.",
          "Oats are a great source of soluble fiber which helps lower cholesterol.",
          "Greek yogurt contains more protein than regular yogurt.",
          "Almonds are packed with vitamin E and magnesium.",
          "Blueberries are rich in antioxidants.",
          // Calories Facts
          "1 gram of fat provides 9 calories, while 1 gram of protein or carbohydrate provides 4 calories.",
          "Your body burns calories even at rest — this is called your Basal Metabolic Rate (BMR).",
          "Muscle burns more calories at rest than fat does.",
          "Cutting 500 calories per day can lead to approximately 0.5 kg of fat loss per week.",
          "Calories from whole foods are metabolized differently than calories from processed foods.",
          "Liquid calories, like soda or juice, are easy to consume in large amounts without feeling full.",
          "Not all calories are equal — the thermic effect of food varies by macronutrient.",
          // Working Out Facts
          "Strength training increases muscle mass, which helps burn more calories throughout the day.",
          "High-Intensity Interval Training (HIIT) can burn more calories in a shorter amount of time than steady-state cardio.",
          "Consistent workouts improve insulin sensitivity and metabolic health.",
          "Post-workout, your body continues to burn calories during recovery — this is called the 'afterburn effect' or EPOC.",
          "Exercise boosts endorphins, which can improve mood and reduce stress.",
          "Lifting weights doesn't make you bulky — it helps build lean muscle and improve body composition.",
          "Stretching regularly can improve flexibility and prevent workout-related injuries.",
          "Hydration is key — even mild dehydration can reduce exercise performance."
        )

        // Write each fact as a line in the CSV
        factsData.foreach(fact => pw.println(fact))
      } finally {
        pw.close()
      }
    }
  }

  // Initialize the CSV file when the object is loaded
  createFactsCSV()

  // Load facts from CSV
  private def loadFactsFromCSV(): List[String] = {
    try {
      val source = Source.fromFile(csvPath)
      try {
        source.getLines().toList
      } finally {
        source.close()
      }
    } catch {
      case e: Exception =>
        println(s"Error loading facts from CSV: ${e.getMessage}")
        List(
          "Did you know that exercise helps improve your mood?"
        ) // Fallback fact
    }
  }

  def fact(): String = {
    factUsageCount += 1
    val facts = loadFactsFromCSV()

    val randomIndex = Random.nextInt(facts.length)
    facts(randomIndex)
  }
}
