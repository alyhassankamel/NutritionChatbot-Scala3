import scala.io.StdIn.readLine
import Facts.fact
import User._
import Motivations.motivate
import BMI.calculateBMI
import FFMI.calculateFFMI
import InputValidator._
import WorkoutPlanner.startWorkoutPlanner
import Jokes.jokes
import Diet.dietUsageCount
import SmallTalk.smallTalk
import Tips.healthyTipsUsageCount

object Preferences {
  private case class MenuOption(
      id: String,
      description: String,
      action: () => Unit
  )

  private val menuOptions = List(
    MenuOption("1", "Set/Update Personal Information", updateUserInfo),
    MenuOption("2", "View Current Information", viewUserInfo),
    MenuOption("3", "Reset All Information", resetUserInfo),
    MenuOption("4", "View Usage Statistics", showUsageStatistics),
    MenuOption("Q", "Quit to Main Menu", () => {})
  )

  private val fuzzyMatchMap = Map(
    "1" -> "1",
    "set" -> "1",
    "update" -> "1",
    "personal" -> "1",
    "info" -> "1",
    "2" -> "2",
    "view" -> "2",
    "current" -> "2",
    "information" -> "2",
    "3" -> "3",
    "reset" -> "3",
    "clear" -> "3",
    "delete" -> "3",
    "4" -> "4",
    "usage" -> "4",
    "statistics" -> "4",
    "stats" -> "4",
    "q" -> "Q",
    "quit" -> "Q",
    "exit" -> "Q",
    "back" -> "Q",
    "menu" -> "Q"
  )

  private def getValidatedInput[T](
      prompt: String,
      parse: String => Option[T],
      errorMsg: String
  ): Option[T] = {
    print(prompt)
    val input = readLine()
    parse(input) match {
      case Some(value) => Some(value)
      case None =>
        println(errorMsg)
        None
    }
  }

  private def getNumericInput(
      prompt: String,
      isDouble: Boolean = false
  ): Option[Double] = {
    getValidatedInput(
      prompt,
      input => {
        val cleaned = input.replaceAll("[^0-9.]", "")
        if (isDouble) cleaned.toDoubleOption
        else cleaned.toIntOption.map(_.toDouble)
      },
      "Please enter a valid number."
    )
  }

  private def getGenderInput(prompt: String): Option[String] = {
    getValidatedInput(
      prompt,
      input => {
        val gender = input.trim.toLowerCase match {
          case "m" | "male" | "man" | "boy"      => "M"
          case "f" | "female" | "woman" | "girl" => "F"
          case _                                 => ""
        }
        if (gender.nonEmpty) Some(gender) else None
      },
      "Please enter a valid gender (M/F)."
    )
  }

  private def getBooleanInput(prompt: String): Option[Boolean] = {
    getValidatedInput(
      prompt,
      input => {
        val normalized = input.trim.toLowerCase
        if (normalized == "yes" || normalized == "y") Some(true)
        else if (normalized == "no" || normalized == "n") Some(false)
        else None
      },
      "Please answer yes or no."
    )
  }

  def preferences(): Unit = {
    println("\nUser Preferences and Information")
    menuOptions.foreach(opt => println(s"${opt.id}. ${opt.description}"))

    var continue = true
    while (continue) {
      print("\nChoose an option: ")
      val rawInput = readLine().trim.toLowerCase

      fuzzyMatchMap.collectFirst {
        case (key, value) if key == rawInput || rawInput.contains(key) => value
      } match {
        case Some("Q") => continue = false
        case Some(id) =>
          menuOptions.find(_.id == id).foreach(_.action())
        case None =>
          println(
            "Invalid choice. Please try again. Valid options are 1-4 or Q to quit."
          )
      }
    }
  }

  private def updateUserInfo(): Unit = {
    println("\nLet's update your information!")

    // Name
    val name = getValidOptionalInput[String](
      "What's your name? (leave blank to skip) ",
      validateName,
      "Please enter a valid name."
    )
    if (name.isDefined) User.updateProfile(name = name)

    // Age
    val age = getValidOptionalInput[Int](
      "How old are you? (leave blank to skip) ",
      input => validateAge(input.filter(_.isDigit)),
      "Please enter a valid age between 1 and 120."
    )
    if (age.isDefined) User.updateProfile(age = age)

    // Height
    val height = getValidOptionalInput[Double](
      "What's your height? (in meters or feet, leave blank to skip) ",
      input => validateHeight(input.replaceAll("[^0-9.]", "")),
      "Please enter a valid height (0.5-3.0m or 1.5-10.0ft)."
    )
    if (height.isDefined) User.updateProfile(height = height)

    // Weight
    val weight = getValidOptionalInput[Double](
      "What's your weight? (in kg or lbs, leave blank to skip) ",
      input => validateWeight(input.replaceAll("[^0-9.]", "")),
      "Please enter a valid weight (20-300kg or 45-660lbs)."
    )
    if (weight.isDefined) User.updateProfile(weight = weight)

    // Gender
    val gender = getValidOptionalInput[String](
      "What's your gender? (M/F, leave blank to skip) ",
      input => validateGender(input.trim.toLowerCase),
      "Please enter M for male or F for female."
    )
    if (gender.isDefined) User.updateProfile(gender = gender)

    // Body Fat
    val bodyFat = getValidOptionalInput[Double](
      "What's your body fat percentage? (0-100, leave blank to skip) ",
      input => validateBodyFat(input.replaceAll("[^0-9.]", "")),
      "Please enter a valid body fat percentage between 0 and 100."
    )
    if (bodyFat.isDefined) User.updateProfile(bodyFatPercentage = bodyFat)

    // Unit System
    val isImperial = getValidOptionalInput[Boolean](
      "Do you use imperial units (lbs/inches)? (yes/no, leave blank to skip) ",
      input => validateUnitPreference(input.trim.toLowerCase),
      "Please enter yes or no."
    )
    if (isImperial.isDefined) User.updateProfile(isImperial = isImperial)

    println("\nProfile updated successfully!")
  }

  private def viewUserInfo(): Unit = {
    println("\nCurrent User Information:")
    val info = List(
      "Name" -> User.name,
      "Age" -> User.age,
      "Weight" -> User.weight,
      "Height" -> User.height,
      "Body Fat Percentage" -> User.bodyFatPercentage,
      "Unit System" -> Some(if (User.isImperial) "Imperial" else "Metric")
    )

    info.foreach { case (label, value) =>
      println(s"$label: ${value.map(_.toString).getOrElse("Not set")}")
    }
  }

  private def resetUserInfo(): Unit = {
    val confirmReset = getValidOptionalInput[Boolean](
      "Are you sure you want to reset all information? (yes/no): ",
      input => validateUnitPreference(input.trim.toLowerCase),
      "Please enter yes or no."
    )

    if (confirmReset.getOrElse(false)) {
      User.profile = UserProfile()
      println("\nAll user information has been reset.")
    } else {
      println("\nReset cancelled.")
    }
  }

  private def showUsageStatistics(): Unit = {
    val usageList = List(
      ("Facts", Facts.factUsageCount),
      ("Diet", Diet.dietUsageCount),
      ("BMI", BMI.bmiUsageCount),
      ("Quiz", Quiz.quizUsageCount),
      ("FFMI", FFMI.ffmiUsageCount),
      ("Motivation", Motivations.motivationUsageCount),
      ("Workout", WorkoutPlanner.workoutUsageCount),
      ("Jokes", Jokes.jokeUsageCount),
      ("Small Talk", SmallTalk.smallTalkUsageCount),
      ("Tips", Tips.healthyTipsUsageCount)
    ).sortBy(-_._2)

    println("\nUsage Statistics:")
    if (usageList.exists(_._2 > 0)) {
      usageList.take(3).zipWithIndex.foreach {
        case ((function, count), index) =>
          println(s"${index + 1}. $function: $count uses")
      }
    } else {
      println("No usage statistics available yet.")
    }
  }
}
