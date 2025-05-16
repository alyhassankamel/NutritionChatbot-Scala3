object Diet {
  import scala.io.StdIn.readLine
  import User._
  import scala.io.Source
  import java.io.{File, FileWriter, PrintWriter}

  var dietUsageCount: Int = 0
  private val csvDirPath = "C:\\Personal Data\\Uni\\Y2\\Y2S2\\More\\The Underdogs_Nutrition Chatbot_Source\\Project\\chatbot\\src\\main\\resources\\"
  private val csvPath = s"${csvDirPath}diet_plans.csv"

  // Ensure resources directory exists
  private val resourcesDir = new File(csvDirPath)
  if (!resourcesDir.exists()) resourcesDir.mkdirs()

  case class DietPlan(
      calorieRangeMin: Int,
      calorieRangeMax: Int,
      purpose: String,
      dietType: String,
      mealPlan: String
  )

  // Create CSV file with diet plans if it doesn't exist
  private def createDietPlansCSV(): Unit = {
    if (!new File(csvPath).exists()) {
      val pw = new PrintWriter(new FileWriter(csvPath))
      try {
        // Header
        pw.println("min_calories,max_calories,purpose,diet_type,meal_plan")

        // Standard diets - Extreme Weight Loss (1500-1800)
        pw.println(
          """1500,1800,Extreme Weight Loss,Standard,"Breakfast: Oatmeal with berries and nuts (300 cal)
Alternative: Protein smoothie with banana and peanut butter

Lunch: Grilled chicken salad with vinaigrette (400 cal)
Alternative: Tuna wrap with vegetables

Snack: Greek yogurt with honey (150 cal)
Alternative: Cottage cheese with fruit

Dinner: Baked fish with steamed vegetables (450 cal)
Alternative: Grilled chicken with quinoa

Snack: Apple with peanut butter (200 cal)
Alternative: Protein bar with almonds""""
        )

        // Mediterranean diets - Extreme Weight Loss (1500-1800)
        pw.println(
          """1500,1800,Extreme Weight Loss,Mediterranean,"Breakfast: Greek yogurt with honey and nuts (300 cal)
Alternative: Whole grain toast with avocado and eggs

Lunch: Grilled chicken with olive oil and lemon (400 cal)
Alternative: Falafel wrap with hummus

Snack: Olives and feta cheese (150 cal)
Alternative: Hummus with vegetables

Dinner: Grilled fish with roasted vegetables (450 cal)
Alternative: Mediterranean chicken with couscous

Snack: Fresh fruit with nuts (200 cal)
Alternative: Greek yogurt with honey""""
        )

        // Vegetarian diets - Extreme Weight Loss (1500-1800)
        pw.println(
          """1500,1800,Extreme Weight Loss,Vegetarian,"Breakfast: Chia pudding with fruit (300 cal)
Alternative: Tofu scramble with vegetables

Lunch: Quinoa salad with chickpeas (400 cal)
Alternative: Lentil and vegetable soup

Snack: Hummus with veggies (150 cal)
Alternative: Greek yogurt with granola

Dinner: Lentil soup with spinach (450 cal)
Alternative: Vegetable stir-fry with tofu

Snack: Edamame (200 cal)
Alternative: Mixed nuts and seeds""""
        )

        // Vegan diets - Extreme Weight Loss (1500-1800)
        pw.println(
          """1500,1800,Extreme Weight Loss,Vegan,"Breakfast: Tofu scramble with vegetables (300 cal)
Alternative: Vegan protein smoothie bowl

Lunch: Buddha bowl with quinoa (400 cal)
Alternative: Vegan wrap with tempeh

Snack: Roasted chickpeas (150 cal)
Alternative: Vegan protein bar

Dinner: Vegan curry with brown rice (450 cal)
Alternative: Vegan stir-fry with tofu

Snack: Mixed nuts and seeds (200 cal)
Alternative: Vegan yogurt with granola""""
        )

        // Low-Carb diets - Extreme Weight Loss (1500-1800)
        pw.println(
          """1500,1800,Extreme Weight Loss,Low-Carb,"Breakfast: Scrambled eggs with avocado (300 cal)
Alternative: Protein pancakes with berries

Lunch: Grilled chicken salad (400 cal)
Alternative: Tuna salad with vegetables

Snack: Cheese and nuts (150 cal)
Alternative: Hard-boiled eggs

Dinner: Baked salmon with vegetables (450 cal)
Alternative: Grilled steak with asparagus

Snack: Greek yogurt with berries (200 cal)
Alternative: Cottage cheese with nuts""""
        )

        // Standard diets - Weight Loss (1800-2100)
        pw.println(
          """1800,2100,Weight Loss,Standard,"Breakfast: Scrambled eggs with whole wheat toast (400 cal)
Alternative: Protein smoothie with oats

Lunch: Turkey wrap with vegetables (500 cal)
Alternative: Chicken and rice bowl

Snack: Apple with peanut butter (200 cal)
Alternative: Greek yogurt with granola

Dinner: Grilled salmon with quinoa (550 cal)
Alternative: Lean beef with sweet potato

Snack: Cottage cheese with berries (150 cal)
Alternative: Protein shake""""
        )

        // Mediterranean diets - Weight Loss (1800-2100)
        pw.println(
          """1800,2100,Weight Loss,Mediterranean,"Breakfast: Greek yogurt with pomegranate (400 cal)
Alternative: Mediterranean omelet

Lunch: Grilled chicken with pita bread (500 cal)
Alternative: Falafel plate with hummus

Snack: Hummus with veggies (200 cal)
Alternative: Olives and cheese

Dinner: Grilled fish with lemon and herbs (550 cal)
Alternative: Mediterranean chicken with vegetables

Snack: Fresh figs with cheese (150 cal)
Alternative: Greek yogurt with honey""""
        )

        // Vegetarian diets - Weight Loss (1800-2100)
        pw.println(
          """1800,2100,Weight Loss,Vegetarian,"Breakfast: Vegetable omelette (400 cal)
Alternative: Tofu scramble with vegetables

Lunch: Lentil and vegetable soup (500 cal)
Alternative: Quinoa bowl with vegetables

Snack: Greek yogurt with honey (200 cal)
Alternative: Hummus with pita

Dinner: Stuffed bell peppers (550 cal)
Alternative: Vegetable curry with rice

Snack: Mixed nuts (150 cal)
Alternative: Protein shake""""
        )

        // Vegan diets - Weight Loss (1800-2100)
        pw.println(
          """1800,2100,Weight Loss,Vegan,"Breakfast: Tofu scramble with veggies (400 cal)
Alternative: Vegan protein smoothie

Lunch: Vegan wrap with tempeh (500 cal)
Alternative: Buddha bowl with quinoa

Snack: Fresh fruit salad (200 cal)
Alternative: Vegan protein bar

Dinner: Vegan curry with brown rice (550 cal)
Alternative: Vegan stir-fry with tofu

Snack: Vegan protein shake (150 cal)
Alternative: Mixed nuts and seeds""""
        )

        // Low-Carb diets - Weight Loss (1800-2100)
        pw.println(
          """1800,2100,Weight Loss,Low-Carb,"Breakfast: Avocado and egg toast (400 cal)
Alternative: Protein pancakes

Lunch: Chicken Caesar salad (500 cal)
Alternative: Tuna salad with avocado

Snack: Hard-boiled eggs (200 cal)
Alternative: Cheese and nuts

Dinner: Grilled steak with vegetables (550 cal)
Alternative: Baked salmon with asparagus

Snack: Cottage cheese (150 cal)
Alternative: Greek yogurt with berries""""
        )

        // Standard diets - Moderate Weight Loss/Maintenance (2100-2400)
        pw.println(
          """2100,2400,Moderate Weight Loss/Maintenance,Standard,"Breakfast: Protein smoothie with banana (450 cal)
Lunch: Chicken stir-fry with brown rice (600 cal)
Snack: Trail mix (250 cal)
Dinner: Lean steak with sweet potato (650 cal)
Snack: Protein shake (200 cal)""""
        )

        // Mediterranean diets - Moderate Weight Loss/Maintenance (2100-2400)
        pw.println(
          """2100,2400,Moderate Weight Loss/Maintenance,Mediterranean,"Breakfast: Greek yogurt with nuts and honey (450 cal)
Lunch: Chicken kebab with pita bread (600 cal)
Snack: Olives and cheese (250 cal)
Dinner: Grilled steak with roasted vegetables (650 cal)
Snack: Fresh fruit salad (200 cal)""""
        )

        // Vegetarian diets - Moderate Weight Loss/Maintenance (2100-2400)
        pw.println(
          """2100,2400,Moderate Weight Loss/Maintenance,Vegetarian,"Breakfast: Vegetable frittata (450 cal)
Lunch: Mushroom risotto (600 cal)
Snack: Mixed nuts (250 cal)
Dinner: Portobello mushroom steaks (650 cal)
Snack: Greek yogurt with berries (200 cal)""""
        )

        // Vegan diets - Moderate Weight Loss/Maintenance (2100-2400)
        pw.println(
          """2100,2400,Moderate Weight Loss/Maintenance,Vegan,"Breakfast: Vegan protein smoothie (450 cal)
Lunch: Buddha bowl with tempeh (600 cal)
Snack: Vegan protein bar (250 cal)
Dinner: Vegan stir-fry with tofu (650 cal)
Snack: Mixed nuts and seeds (200 cal)""""
        )

        // Low-Carb diets - Moderate Weight Loss/Maintenance (2100-2400)
        pw.println(
          """2100,2400,Moderate Weight Loss/Maintenance,Low-Carb,"Breakfast: Protein pancakes (450 cal)
Lunch: Chicken stir-fry with vegetables (600 cal)
Snack: Almonds and cheese (250 cal)
Dinner: Grilled salmon with asparagus (650 cal)
Snack: Greek yogurt with nuts (200 cal)""""
        )

        // Standard diets - Maintenance (2400-2700)
        pw.println(
          """2400,2700,Maintenance,Standard,"Breakfast: Avocado toast with eggs (500 cal)
Lunch: Tuna salad sandwich (600 cal)
Snack: Protein bar (300 cal)
Dinner: Pork chops with mashed potatoes (700 cal)
Snack: Dark chocolate with nuts (250 cal)""""
        )

        // Mediterranean diets - Maintenance (2400-2700)
        pw.println(
          """2400,2700,Maintenance,Mediterranean,"Breakfast: Greek yogurt with fresh fruit (500 cal)
Lunch: Tuna salad with pita bread (600 cal)
Snack: Hummus with veggies (300 cal)
Dinner: Grilled pork with Mediterranean vegetables (700 cal)
Snack: Fresh fruit with nuts (250 cal)""""
        )

        // Vegetarian diets - Maintenance (2400-2700)
        pw.println(
          """2400,2700,Maintenance,Vegetarian,"Breakfast: Vegetable omelette with cheese (500 cal)
Lunch: Quinoa bowl with vegetables (600 cal)
Snack: Greek yogurt with granola (300 cal)
Dinner: Vegetable lasagna (700 cal)
Snack: Mixed nuts and dried fruit (250 cal)""""
        )

        // Vegan diets - Maintenance (2400-2700)
        pw.println(
          """2400,2700,Maintenance,Vegan,"Breakfast: Vegan breakfast burrito (500 cal)
Lunch: Vegan sushi rolls (600 cal)
Snack: Vegan protein bar (300 cal)
Dinner: Vegan lasagna (700 cal)
Snack: Vegan chocolate (250 cal)""""
        )

        // Low-Carb diets - Maintenance (2400-2700)
        pw.println(
          """2400,2700,Maintenance,Low-Carb,"Breakfast: Bacon and eggs (500 cal)
Lunch: Chicken salad with avocado (600 cal)
Snack: Cheese and nuts (300 cal)
Dinner: Grilled steak with vegetables (700 cal)
Snack: Greek yogurt with berries (250 cal)""""
        )

        // Standard diets - Lean Bulk (2700-3000)
        pw.println(
          """2700,3000,Lean Bulk,Standard,"Breakfast: Protein pancakes with syrup (600 cal)
Lunch: Beef burrito bowl (700 cal)
Snack: Hard-boiled eggs (200 cal)
Dinner: Grilled chicken with pasta (800 cal)
Snack: Casein protein shake (300 cal)""""
        )

        // Mediterranean diets - Lean Bulk (2700-3000)
        pw.println(
          """2700,3000,Lean Bulk,Mediterranean,"Breakfast: Greek yogurt with granola (600 cal)
Lunch: Beef kebab with pita bread (700 cal)
Snack: Olives and cheese (200 cal)
Dinner: Grilled chicken with Mediterranean vegetables (800 cal)
Snack: Fresh fruit with nuts (300 cal)""""
        )

        // Vegetarian diets - Lean Bulk (2700-3000)
        pw.println(
          """2700,3000,Lean Bulk,Vegetarian,"Breakfast: Protein smoothie with Greek yogurt (600 cal)
Lunch: Quinoa bowl with vegetables (700 cal)
Snack: Mixed nuts and seeds (200 cal)
Dinner: Vegetable curry with rice (800 cal)
Snack: Greek yogurt with honey (300 cal)""""
        )

        // Vegan diets - Lean Bulk (2700-3000)
        pw.println(
          """2700,3000,Lean Bulk,Vegan,"Breakfast: Vegan protein pancakes (600 cal)
Lunch: Vegan burrito bowl (700 cal)
Snack: Vegan protein balls (200 cal)
Dinner: Vegan pasta with lentil meatballs (800 cal)
Snack: Vegan protein shake (300 cal)""""
        )

        // Low-Carb diets - Lean Bulk (2700-3000)
        pw.println(
          """2700,3000,Lean Bulk,Low-Carb,"Breakfast: Protein pancakes with bacon (600 cal)
Lunch: Beef bowl with vegetables (700 cal)
Snack: Hard-boiled eggs (200 cal)
Dinner: Grilled chicken with vegetables (800 cal)
Snack: Protein shake (300 cal)""""
        )

        // Standard diets - Moderate Bulk (3000-3300)
        pw.println(
          """3000,3300,Moderate Bulk,Standard,"Breakfast: 4 eggs with bacon and toast (700 cal)
Lunch: Chicken parmesan with spaghetti (800 cal)
Snack: Peanut butter sandwich (400 cal)
Dinner: Ribeye steak with baked potato (900 cal)
Snack: Mass gainer shake (500 cal)""""
        )

        // Mediterranean diets - Moderate Bulk (3000-3300)
        pw.println(
          """3000,3300,Moderate Bulk,Mediterranean,"Breakfast: Greek yogurt with honey and nuts (700 cal)
Lunch: Chicken parmesan with pita bread (800 cal)
Snack: Hummus with veggies (400 cal)
Dinner: Grilled steak with Mediterranean vegetables (900 cal)
Snack: Fresh fruit with nuts (500 cal)""""
        )

        // Vegetarian diets - Moderate Bulk (3000-3300)
        pw.println(
          """3000,3300,Moderate Bulk,Vegetarian,"Breakfast: Protein smoothie with Greek yogurt (700 cal)
Lunch: Vegetable curry with rice (800 cal)
Snack: Mixed nuts and seeds (400 cal)
Dinner: Vegetable lasagna (900 cal)
Snack: Greek yogurt with granola (500 cal)""""
        )

        // Vegan diets - Moderate Bulk (3000-3300)
        pw.println(
          """3000,3300,Moderate Bulk,Vegan,"Breakfast: Vegan protein waffles (700 cal)
Lunch: Vegan parmesan with spaghetti (800 cal)
Snack: Vegan peanut butter sandwich (400 cal)
Dinner: Vegan steak with baked potato (900 cal)
Snack: Vegan mass gainer shake (500 cal)""""
        )

        // Low-Carb diets - Moderate Bulk (3000-3300)
        pw.println(
          """3000,3300,Moderate Bulk,Low-Carb,"Breakfast: Bacon and eggs with avocado (700 cal)
Lunch: Chicken parmesan with vegetables (800 cal)
Snack: Cheese and nuts (400 cal)
Dinner: Ribeye steak with vegetables (900 cal)
Snack: Protein shake (500 cal)""""
        )

        // Standard diets - High Calorie (3300+)
        pw.println(
          """3300,9999,High Calorie,Standard,"Breakfast: Steak and eggs with hash browns (800 cal)
Lunch: Double cheeseburger with fries (900 cal)
Snack: Nuts and dried fruit (500 cal)
Dinner: BBQ ribs with mac and cheese (1000 cal)
Snack: Whole milk with peanut butter (600 cal)""""
        )

        // Mediterranean diets - High Calorie (3300+)
        pw.println(
          """3300,9999,High Calorie,Mediterranean,"Breakfast: Greek yogurt with granola and nuts (800 cal)
Lunch: Grilled chicken with pita bread (900 cal)
Snack: Hummus with veggies (500 cal)
Dinner: Grilled steak with Mediterranean vegetables (1000 cal)
Snack: Fresh fruit with nuts (600 cal)""""
        )

        // Vegetarian diets - High Calorie (3300+)
        pw.println(
          """3300,9999,High Calorie,Vegetarian,"Breakfast: Protein smoothie with Greek yogurt (800 cal)
Lunch: Vegetable curry with rice (900 cal)
Snack: Mixed nuts and seeds (500 cal)
Dinner: Vegetable lasagna (1000 cal)
Snack: Greek yogurt with granola (600 cal)""""
        )

        // Vegan diets - High Calorie (3300+)
        pw.println(
          """3300,9999,High Calorie,Vegan,"Breakfast: Vegan protein waffles (800 cal)
Lunch: Vegan parmesan with spaghetti (900 cal)
Snack: Vegan peanut butter sandwich (500 cal)
Dinner: Vegan steak with baked potato (1000 cal)
Snack: Vegan mass gainer shake (600 cal)""""
        )

        // Low-Carb diets - High Calorie (3300+)
        pw.println(
          """3300,9999,High Calorie,Low-Carb,"Breakfast: Bacon and eggs with avocado (800 cal)
Lunch: Chicken parmesan with vegetables (900 cal)
Snack: Cheese and nuts (500 cal)
Dinner: Ribeye steak with vegetables (1000 cal)
Snack: Protein shake (600 cal)""""
        )
      } finally {
        pw.close()
      }
    }
  }

  // Initialize the CSV file when the object is loaded
  createDietPlansCSV()

  // Load diet plans from CSV
  private def loadDietPlansFromCSV(): List[DietPlan] = {
    try {
      val source = Source.fromFile(csvPath)
      try {
        // Skip header line and parse data
        val plans = source
          .getLines()
          .drop(1)
          .map { line =>
            val parts = parseDietPlanCSVLine(line)
            if (parts.length >= 5) {
              DietPlan(
                parts(0).toInt,
                parts(1).toInt,
                parts(2),
                parts(3),
                parts(4)
              )
            } else {
              // Fallback if CSV format is incorrect
              DietPlan(
                1500,
                1800,
                "Extreme Weight Loss",
                "Standard",
                "Please check the CSV file format. Unable to load meal plan."
              )
            }
          }
          .toList

        plans
      } finally {
        source.close()
      }
    } catch {
      case e: Exception =>
        println(s"Error loading diet plans from CSV: ${e.getMessage}")
        // Return a default diet plan if CSV can't be read
        List(
          DietPlan(
            1500,
            1800,
            "Extreme Weight Loss",
            "Standard",
            "Error loading meal plans. Please check the CSV file."
          )
        )
    }
  }

  // Parse CSV line considering that meal plans contain commas and newlines
  private def parseDietPlanCSVLine(line: String): Array[String] = {
    val result = new scala.collection.mutable.ArrayBuffer[String]
    var currentField = new StringBuilder
    var inQuotes = false

    for (c <- line) {
      if (c == '"') {
        inQuotes = !inQuotes
      } else if (c == ',' && !inQuotes) {
        result += currentField.toString
        currentField = new StringBuilder
      } else {
        currentField.append(c)
      }
    }

    // Add the last field
    result += currentField.toString

    // Make sure all line breaks in the meal plan (last field) are properly handled
    if (result.length >= 5) {
      // Process the meal plan (field 4) to replace escaped newlines
      val mealPlan = result(4)

      // Replace all variants of newlines with actual newlines
      val cleanedMealPlan = mealPlan
        .replace("\\n", "\n")
        .replace("\\r\\n", "\n")
        .replace("\\r", "\n")

      result(4) = cleanedMealPlan
    }

    result.toArray
  }

//i tried to make this one better, i tried to make something like prefered and non pref foods, made the user choose type
//of diet but it kept on having errors so i left the old version and will send the new version
//garabo feha ento keda

  def diet(): Unit = {
    dietUsageCount += 1

    def getInput(message: String): String = {
      print(message)
      readLine()
    }

    def handleDislikedFoods(plan: String): String = {
      println("\nDo you dislike any foods in this plan? (yes/no)")
      val response = readLine().toLowerCase()

      if (response == "yes") {
        println("Please list the foods you don't like (separated by commas):")
        val dislikedFoods = readLine().toLowerCase().split(",").map(_.trim)

        var modifiedPlan = plan
        dislikedFoods.foreach { food =>
          val alternatives = food match {
            case f
                if f.contains("fish") || f
                  .contains("tuna") || f.contains("salmon") =>
              "\n- Instead of fish: Grilled chicken breast, tofu, or tempeh"
            case f if f.contains("egg") =>
              "\n- Instead of eggs: Tofu scramble, protein pancakes, or tempeh"
            case f if f.contains("milk") || f.contains("yogurt") =>
              "\n- Instead of dairy: Almond milk, oat milk, soy milk, or plant-based yogurt"
            case f if f.contains("cheese") =>
              "\n- Instead of cheese: Avocado, nut butter, or vegan cheese"
            case f
                if f.contains("meat") || f
                  .contains("chicken") || f.contains("beef") =>
              "\n- Instead of meat: Tofu, tempeh, seitan, or plant-based meat alternatives"
            case f if f.contains("nuts") =>
              "\n- Instead of nuts: Seeds (pumpkin, sunflower, chia) or roasted beans"
            case f if f.contains("olive") =>
              "\n- Instead of olives: Roasted vegetables or hummus"
            case f if f.contains("bean") || f.contains("chickpea") =>
              "\n- Instead of beans: Lentils, quinoa, or other grains"
            case _ =>
              s"\n- Alternative for $food: Please consult with a nutritionist for specific alternatives"
          }
          modifiedPlan += alternatives
        }
        modifiedPlan
      } else {
        plan
      }
    }

    def getUserData(): Option[(Int, String, Double, Double)] = {
      // Try to use stored user data first
      (User.age, User.name, User.weight, User.height) match {
        case (Some(a), Some(n), Some(w), Some(h)) =>
          println(s"Using your stored data, $n:")
          Some((a, "M", w, h)) // Defaulting to male for now
        case _ =>
          try {
            val age = getInput("Enter your age: ").toInt
            val gender = getInput(
              "Enter your gender (M/F): "
            ).trim.toUpperCase // parse here
            val weight = getInput("Enter your weight in kg: ").toDouble
            val height = getInput("Enter your height in cm: ").toDouble
            if (age <= 0 || weight <= 0 || height <= 0) None
            else {
              // Update user profile with the entered data
              User.updateProfile(
                age = Some(age),
                weight = Some(weight),
                height = Some(height)
              )
              Some((age, gender, weight, height))
            }
          } catch {
            case _: NumberFormatException =>
              println("Error: Please enter valid numbers")
              None
          }
      }
    }
    /*
    def getActivityFactor(): Double = {
      println("\n[Activity Levels]")
      println("1. Sedentary (little exercise)")
      println("2. Lightly active (1-3 days/week)")
      println("3. Moderately active (3-5 days/week)")
      println("4. Very active (6-7 days/week)")
      println("5. Super active (twice daily)")
      getInput("Your choice (1-5): ").toInt match {
        case 1 => 1.2
        case 2 => 1.375
        case 3 => 1.55
        case 4 => 1.725
        case 5 => 1.9
        case _ =>
          println("Invalid option. Defaulting to sedentary.")
          1.2
      }
    }*/

    def getActivityFactor(): Double = {
      println("\n[Activity Levels]")
      println("1. Sedentary (little exercise)")
      println("2. Lightly active (1-3 days/week)")
      println("3. Moderately active (3-5 days/week)")
      println("4. Very active (6-7 days/week)")
      println("5. Super active (twice daily)")

      var activityFactor = 0.0
      var validSelection = false

      while (!validSelection) {
        print("\nYour choice (1-5 or enter activity level): ")
        val input = readLine().trim().toLowerCase()

        activityFactor = input match {
          case "1" | "sedentary"                      => 1.2
          case "2" | "lightly active" | "lightly"     => 1.375
          case "3" | "moderately active" | "moderate" => 1.55
          case "4" | "very active" | "active"         => 1.725
          case "5" | "super active" | "super"         => 1.9
          case _ =>
            println("Invalid option. Please try again.")
            0.0
        }

        if (activityFactor != 0.0) {
          validSelection = true
        }
      }

      activityFactor
    }
    def calculateCalories(
        age: Int,
        gender: String,
        weight: Double,
        height: Double
    ): Double = {
      // Convert height to meters if in cm
      val heightM = if (height > 100) height / 100.0 else height

      val bmr = gender match {
        case "M" => 10 * weight + 6.25 * (heightM * 100) - 5 * age + 5
        case "F" => 10 * weight + 6.25 * (heightM * 100) - 5 * age - 161
        case _ =>
          println("Unknown gender, using male formula by default.")
          10 * weight + 6.25 * (heightM * 100) - 5 * age + 5
      }
      bmr * getActivityFactor()
    }
    println("Diet Advisor")
    getUserData() match {
      case Some((age, gender, weight, height)) =>
        val calories = calculateCalories(age, gender, weight, height)
        val (adjustedCalories, _) = target(calories)
        val dietType = selectDietPlan()

        // Comprehensive hardcoded solution for all diet types and calorie ranges
        var foundPlan = true

        // Prepare a calorie category description
        val purpose = if (adjustedCalories < 1800) {
          "Extreme Weight Loss"
        } else if (adjustedCalories < 2100) {
          "Weight Loss"
        } else if (adjustedCalories < 2400) {
          "Moderate Weight Loss/Maintenance"
        } else if (adjustedCalories < 2700) {
          "Maintenance"
        } else if (adjustedCalories < 3000) {
          "Lean Bulk"
        } else if (adjustedCalories < 3300) {
          "Moderate Bulk"
        } else {
          "High Calorie"
        }

        println(s"Calorie Target: $adjustedCalories")
        println(s"Purpose: $purpose")
        println("Meal Plan:")

        // Display appropriate hardcoded meal plan based on diet type and calorie range
        val mealPlan = (dietType, purpose) match {
          // Standard diet plans for each calorie range
          case ("Standard", "Extreme Weight Loss") =>
            """Breakfast: Oatmeal with berries and nuts (300 cal)
Alternative: Protein smoothie with banana and peanut butter

Lunch: Grilled chicken salad with vinaigrette (400 cal)
Alternative: Tuna wrap with vegetables

Snack: Greek yogurt with honey (150 cal)
Alternative: Cottage cheese with fruit

Dinner: Baked fish with steamed vegetables (450 cal)
Alternative: Grilled chicken with quinoa

Snack: Apple with peanut butter (200 cal)
Alternative: Protein bar with almonds"""

          case ("Standard", "Weight Loss") =>
            """Breakfast: Scrambled eggs with whole wheat toast (400 cal)
Alternative: Protein smoothie with oats

Lunch: Turkey wrap with vegetables (500 cal)
Alternative: Chicken and rice bowl

Snack: Apple with peanut butter (200 cal)
Alternative: Greek yogurt with granola

Dinner: Grilled salmon with quinoa (550 cal)
Alternative: Lean beef with sweet potato

Snack: Cottage cheese with berries (150 cal)
Alternative: Protein shake"""

          case ("Standard", "Moderate Weight Loss/Maintenance") =>
            """Breakfast: Protein smoothie with banana (450 cal)
Lunch: Chicken stir-fry with brown rice (600 cal)
Snack: Trail mix (250 cal)
Dinner: Lean steak with sweet potato (650 cal)
Snack: Protein shake (200 cal)"""

          case ("Standard", "Maintenance") =>
            """Breakfast: Avocado toast with eggs (500 cal)
Lunch: Tuna salad sandwich (600 cal)
Snack: Protein bar (300 cal)
Dinner: Pork chops with mashed potatoes (700 cal)
Snack: Dark chocolate with nuts (250 cal)"""

          case ("Standard", "Lean Bulk") =>
            """Breakfast: Protein pancakes with syrup (600 cal)
Lunch: Beef burrito bowl (700 cal)
Snack: Hard-boiled eggs (200 cal)
Dinner: Grilled chicken with pasta (800 cal)
Snack: Casein protein shake (300 cal)"""

          case ("Standard", "Moderate Bulk") =>
            """Breakfast: 4 eggs with bacon and toast (700 cal)
Lunch: Chicken parmesan with spaghetti (800 cal)
Snack: Peanut butter sandwich (400 cal)
Dinner: Ribeye steak with baked potato (900 cal)
Snack: Mass gainer shake (500 cal)"""

          case ("Standard", "High Calorie") =>
            """Breakfast: Steak and eggs with hash browns (800 cal)
Lunch: Double cheeseburger with fries (900 cal)
Snack: Nuts and dried fruit (500 cal)
Dinner: BBQ ribs with mac and cheese (1000 cal)
Snack: Whole milk with peanut butter (600 cal)"""

          // Mediterranean diet plans
          case ("Mediterranean", "Extreme Weight Loss") =>
            """Breakfast: Greek yogurt with honey and nuts (300 cal)
Alternative: Whole grain toast with avocado and eggs

Lunch: Grilled chicken with olive oil and lemon (400 cal)
Alternative: Falafel wrap with hummus

Snack: Olives and feta cheese (150 cal)
Alternative: Hummus with vegetables

Dinner: Grilled fish with roasted vegetables (450 cal)
Alternative: Mediterranean chicken with couscous

Snack: Fresh fruit with nuts (200 cal)
Alternative: Greek yogurt with honey"""

          case ("Mediterranean", "Weight Loss") =>
            """Breakfast: Greek yogurt with pomegranate (400 cal)
Alternative: Mediterranean omelet

Lunch: Grilled chicken with pita bread (500 cal)
Alternative: Falafel plate with hummus

Snack: Hummus with veggies (200 cal)
Alternative: Olives and cheese

Dinner: Grilled fish with lemon and herbs (550 cal)
Alternative: Mediterranean chicken with vegetables

Snack: Fresh figs with cheese (150 cal)
Alternative: Greek yogurt with honey"""

          case ("Mediterranean", "Moderate Weight Loss/Maintenance") =>
            """Breakfast: Greek yogurt with nuts and honey (450 cal)
Lunch: Chicken kebab with pita bread (600 cal)
Snack: Olives and cheese (250 cal)
Dinner: Grilled steak with roasted vegetables (650 cal)
Snack: Fresh fruit salad (200 cal)"""

          case ("Mediterranean", "Maintenance") =>
            """Breakfast: Greek yogurt with fresh fruit (500 cal)
Lunch: Tuna salad with pita bread (600 cal)
Snack: Hummus with veggies (300 cal)
Dinner: Grilled pork with Mediterranean vegetables (700 cal)
Snack: Fresh fruit with nuts (250 cal)"""

          case ("Mediterranean", "Lean Bulk") =>
            """Breakfast: Greek yogurt with granola (600 cal)
Lunch: Beef kebab with pita bread (700 cal)
Snack: Olives and cheese (200 cal)
Dinner: Grilled chicken with Mediterranean vegetables (800 cal)
Snack: Fresh fruit with nuts (300 cal)"""

          case ("Mediterranean", "Moderate Bulk") =>
            """Breakfast: Greek yogurt with honey and nuts (700 cal)
Lunch: Chicken parmesan with pita bread (800 cal)
Snack: Hummus with veggies (400 cal)
Dinner: Grilled steak with Mediterranean vegetables (900 cal)
Snack: Fresh fruit with nuts (500 cal)"""

          case ("Mediterranean", "High Calorie") =>
            """Breakfast: Greek yogurt with granola and nuts (800 cal)
Lunch: Grilled chicken with pita bread (900 cal)
Snack: Hummus with veggies (500 cal)
Dinner: Grilled steak with Mediterranean vegetables (1000 cal)
Snack: Fresh fruit with nuts (600 cal)"""

          // Vegetarian diet plans
          case ("Vegetarian", "Extreme Weight Loss") =>
            """Breakfast: Chia pudding with fruit (300 cal)
Alternative: Tofu scramble with vegetables

Lunch: Quinoa salad with chickpeas (400 cal)
Alternative: Lentil and vegetable soup

Snack: Hummus with veggies (150 cal)
Alternative: Greek yogurt with granola

Dinner: Lentil soup with spinach (450 cal)
Alternative: Vegetable stir-fry with tofu

Snack: Edamame (200 cal)
Alternative: Mixed nuts and seeds"""

          case ("Vegetarian", "Weight Loss") =>
            """Breakfast: Vegetable omelette (400 cal)
Alternative: Tofu scramble with vegetables

Lunch: Lentil and vegetable soup (500 cal)
Alternative: Quinoa bowl with vegetables

Snack: Greek yogurt with honey (200 cal)
Alternative: Hummus with pita

Dinner: Stuffed bell peppers (550 cal)
Alternative: Vegetable curry with rice

Snack: Mixed nuts (150 cal)
Alternative: Protein shake"""

          case ("Vegetarian", "Moderate Weight Loss/Maintenance") =>
            """Breakfast: Vegetable frittata (450 cal)
Lunch: Mushroom risotto (600 cal)
Snack: Mixed nuts (250 cal)
Dinner: Portobello mushroom steaks (650 cal)
Snack: Greek yogurt with berries (200 cal)"""

          case ("Vegetarian", "Maintenance") =>
            """Breakfast: Vegetable omelette with cheese (500 cal)
Lunch: Quinoa bowl with vegetables (600 cal)
Snack: Greek yogurt with granola (300 cal)
Dinner: Vegetable lasagna (700 cal)
Snack: Mixed nuts and dried fruit (250 cal)"""

          case ("Vegetarian", "Lean Bulk") =>
            """Breakfast: Protein smoothie with Greek yogurt (600 cal)
Lunch: Quinoa bowl with vegetables (700 cal)
Snack: Mixed nuts and seeds (200 cal)
Dinner: Vegetable curry with rice (800 cal)
Snack: Greek yogurt with honey (300 cal)"""

          case ("Vegetarian", "Moderate Bulk") =>
            """Breakfast: Protein smoothie with Greek yogurt (700 cal)
Lunch: Vegetable curry with rice (800 cal)
Snack: Mixed nuts and seeds (400 cal)
Dinner: Vegetable lasagna (900 cal)
Snack: Greek yogurt with granola (500 cal)"""

          case ("Vegetarian", "High Calorie") =>
            """Breakfast: Protein smoothie with Greek yogurt (800 cal)
Lunch: Vegetable curry with rice (900 cal)
Snack: Mixed nuts and seeds (500 cal)
Dinner: Vegetable lasagna (1000 cal)
Snack: Greek yogurt with granola (600 cal)"""

          // Vegan diet plans
          case ("Vegan", "Extreme Weight Loss") =>
            """Breakfast: Tofu scramble with vegetables (300 cal)
Alternative: Vegan protein smoothie bowl

Lunch: Buddha bowl with quinoa (400 cal)
Alternative: Vegan wrap with tempeh

Snack: Roasted chickpeas (150 cal)
Alternative: Vegan protein bar

Dinner: Vegan curry with brown rice (450 cal)
Alternative: Vegan stir-fry with tofu

Snack: Mixed nuts and seeds (200 cal)
Alternative: Vegan yogurt with granola"""

          case ("Vegan", "Weight Loss") =>
            """Breakfast: Tofu scramble with veggies (400 cal)
Alternative: Vegan protein smoothie

Lunch: Vegan wrap with tempeh (500 cal)
Alternative: Buddha bowl with quinoa

Snack: Fresh fruit salad (200 cal)
Alternative: Vegan protein bar

Dinner: Vegan curry with brown rice (550 cal)
Alternative: Vegan stir-fry with tofu

Snack: Vegan protein shake (150 cal)
Alternative: Mixed nuts and seeds"""

          case ("Vegan", "Moderate Weight Loss/Maintenance") =>
            """Breakfast: Vegan protein smoothie (450 cal)
Lunch: Buddha bowl with tempeh (600 cal)
Snack: Vegan protein bar (250 cal)
Dinner: Vegan stir-fry with tofu (650 cal)
Snack: Mixed nuts and seeds (200 cal)"""

          case ("Vegan", "Maintenance") =>
            """Breakfast: Vegan breakfast burrito (500 cal)
Lunch: Vegan sushi rolls (600 cal)
Snack: Vegan protein bar (300 cal)
Dinner: Vegan lasagna (700 cal)
Snack: Vegan chocolate (250 cal)"""

          case ("Vegan", "Lean Bulk") =>
            """Breakfast: Vegan protein pancakes (600 cal)
Lunch: Vegan burrito bowl (700 cal)
Snack: Vegan protein balls (200 cal)
Dinner: Vegan pasta with lentil meatballs (800 cal)
Snack: Vegan protein shake (300 cal)"""

          case ("Vegan", "Moderate Bulk") =>
            """Breakfast: Vegan protein waffles (700 cal)
Lunch: Vegan parmesan with spaghetti (800 cal)
Snack: Vegan peanut butter sandwich (400 cal)
Dinner: Vegan steak with baked potato (900 cal)
Snack: Vegan mass gainer shake (500 cal)"""

          case ("Vegan", "High Calorie") =>
            """Breakfast: Vegan protein waffles (800 cal)
Lunch: Vegan parmesan with spaghetti (900 cal)
Snack: Vegan peanut butter sandwich (500 cal)
Dinner: Vegan steak with baked potato (1000 cal)
Snack: Vegan mass gainer shake (600 cal)"""

          // Low-Carb diet plans
          case ("Low-Carb", "Extreme Weight Loss") =>
            """Breakfast: Scrambled eggs with avocado (300 cal)
Alternative: Protein pancakes with berries

Lunch: Grilled chicken salad (400 cal)
Alternative: Tuna salad with vegetables

Snack: Cheese and nuts (150 cal)
Alternative: Hard-boiled eggs

Dinner: Baked salmon with vegetables (450 cal)
Alternative: Grilled steak with asparagus

Snack: Greek yogurt with berries (200 cal)
Alternative: Cottage cheese with nuts"""

          case ("Low-Carb", "Weight Loss") =>
            """Breakfast: Avocado and egg toast (400 cal)
Alternative: Protein pancakes

Lunch: Chicken Caesar salad (500 cal)
Alternative: Tuna salad with avocado

Snack: Hard-boiled eggs (200 cal)
Alternative: Cheese and nuts

Dinner: Grilled steak with vegetables (550 cal)
Alternative: Baked salmon with asparagus

Snack: Cottage cheese (150 cal)
Alternative: Greek yogurt with berries"""

          case ("Low-Carb", "Moderate Weight Loss/Maintenance") =>
            """Breakfast: Protein pancakes (450 cal)
Lunch: Chicken stir-fry with vegetables (600 cal)
Snack: Almonds and cheese (250 cal)
Dinner: Grilled salmon with asparagus (650 cal)
Snack: Greek yogurt with nuts (200 cal)"""

          case ("Low-Carb", "Maintenance") =>
            """Breakfast: Bacon and eggs (500 cal)
Lunch: Chicken salad with avocado (600 cal)
Snack: Cheese and nuts (300 cal)
Dinner: Grilled steak with vegetables (700 cal)
Snack: Greek yogurt with berries (250 cal)"""

          case ("Low-Carb", "Lean Bulk") =>
            """Breakfast: Protein pancakes with bacon (600 cal)
Lunch: Beef bowl with vegetables (700 cal)
Snack: Hard-boiled eggs (200 cal)
Dinner: Grilled chicken with vegetables (800 cal)
Snack: Protein shake (300 cal)"""

          case ("Low-Carb", "Moderate Bulk") =>
            """Breakfast: Bacon and eggs with avocado (700 cal)
Lunch: Chicken parmesan with vegetables (800 cal)
Snack: Cheese and nuts (400 cal)
Dinner: Ribeye steak with vegetables (900 cal)
Snack: Protein shake (500 cal)"""

          case ("Low-Carb", "High Calorie") =>
            """Breakfast: Bacon and eggs with avocado (800 cal)
Lunch: Chicken parmesan with vegetables (900 cal)
Snack: Cheese and nuts (500 cal)
Dinner: Ribeye steak with vegetables (1000 cal)
Snack: Protein shake (600 cal)"""

          case _ =>
            foundPlan = false
            "No suitable meal plan found for your requirements."
        }

        if (foundPlan) {
          // Print each line of the meal plan
          mealPlan.split("\n").foreach(line => println(line))

          // Handle disliked foods
          val finalPlan = handleDislikedFoods(mealPlan)
          if (finalPlan != mealPlan) {
            println("\nModified plan with alternatives:")
            finalPlan.split("\n").foreach(line => println(line))
          }
        } else {
          // Fallback to the original approach if no hardcoded plan matches
          val dietPlans = loadDietPlansFromCSV()
          val matchingPlans = dietPlans.filter(plan =>
            plan.calorieRangeMin <= adjustedCalories &&
              plan.calorieRangeMax > adjustedCalories &&
              plan.dietType == dietType
          )

          matchingPlans.headOption.orElse {
            dietPlans
              .filter(_.dietType == dietType)
              .sortBy(plan =>
                Math.abs(
                  plan.calorieRangeMin + plan.calorieRangeMax - 2 * adjustedCalories
                )
              )
              .headOption
          } match {
            case Some(plan) =>
              println(s"Calorie Target: $adjustedCalories")
              println(s"Purpose: ${plan.purpose}")
              println("Meal Plan:")
              plan.mealPlan.split("\n").foreach(line => println(line))

              val finalPlan = handleDislikedFoods(plan.mealPlan)
              if (finalPlan != plan.mealPlan) {
                println("\nModified plan with alternatives:")
                finalPlan.split("\n").foreach(line => println(line))
              }

            case None =>
              println("No suitable meal plan found for your requirements.")
          }
        }

      case None =>
        println("Invalid input. Exiting.")
    }
  }
  def target(calories: Double): (Double, String) = {
    println(
      "What is your goal? (Maintain, Weight loss, Extreme weight loss, Bulk)"
    )
    val targetString = scala.io.StdIn.readLine().toLowerCase().trim()
    val result = targetString match {
      case "maintain" => calories
      case "weight loss" =>
        if ((calories - 200) < 1500) calories else (calories - 200)
      case "extreme weight loss" =>
        if ((calories - 800) < 1500) calories else (calories - 800)
      case "bulk" => calories + 500
      case _      => calories
    }
    (result, targetString)
  }
  def selectDietPlan(): String = {
    println("\nAvailable Diet Plans:")
    println("1. Standard")
    println("2. Mediterranean")
    println("3. Vegetarian")
    println("4. Vegan")
    println("5. Low-Carb")

    var selectedDiet = ""
    var validSelection = false

    while (!validSelection) {
      print("\nPlease select your preferred diet plan (1-5): ")
      val input = readLine().trim()

      selectedDiet = input match {
        case "1" | "standard"              => "Standard"
        case "2" | "mediterranean"         => "Mediterranean"
        case "3" | "vegetarian"            => "Vegetarian"
        case "4" | "vegan"                 => "Vegan"
        case "5" | "low-carb" | "low carb" => "Low-Carb"
        case _                             => ""
      }

      if (selectedDiet.nonEmpty) {
        validSelection = true
      } else {
        println(
          "Invalid selection. Please choose a number between the listed options."
        )
      }
    }

    selectedDiet
  }
  def suggestDiet(calories: Double): (String, String) = {
    val dietType = selectDietPlan()
    val dietPlans = loadDietPlansFromCSV()

    // Find the appropriate plan based on calorie range and diet type
    val matchingPlans = dietPlans.filter(plan =>
      plan.calorieRangeMin <= calories &&
        plan.calorieRangeMax > calories &&
        plan.dietType == dietType
    )

    // Helper to clean the meal plan text
    def cleanMealPlan(plan: String): String = {
      // Replace escaped newlines with actual newlines
      // Sometimes they might appear as \\n, \n, or \r\n
      plan.replace("\\n", "\n").replace("\\r", "\r")
    }

    matchingPlans.headOption match {
      case Some(plan) =>
        (plan.purpose, cleanMealPlan(plan.mealPlan))
      case None =>
        // Find the closest plan if no exact match
        val closestPlan = dietPlans
          .filter(_.dietType == dietType)
          .minBy(plan =>
            math.abs(plan.calorieRangeMin + plan.calorieRangeMax - 2 * calories)
          )
        (closestPlan.purpose, cleanMealPlan(closestPlan.mealPlan))
    }
  }
}
