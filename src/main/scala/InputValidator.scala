object InputValidator {
  case class ValidationError(message: String, suggestion: String)

  def validateName(name: String): Option[String] = {
    if (name.trim.nonEmpty) Some(name.trim)
    else None
  }

  def validateAge(ageInput: String): Option[Int] = {
    val age = ageInput.filter(_.isDigit).toIntOption
    age match {
      case Some(a) if a > 0 && a < 120 => Some(a)
      case _                           => None
    }
  }

  def validateHeight(heightInput: String): Option[Double] = {
    val height = heightInput.replaceAll("[^0-9.]", "").toDoubleOption
    height match {
      case Some(h) if h > 0.5 && h < 3.0 => Some(h) // Assuming meters
      case Some(h) if h > 1.5 && h < 10.0 =>
        Some(h / 3.28084) // Convert feet to meters
      case _ => None
    }
  }

  def validateWeight(weightInput: String): Option[Double] = {
    val weight = weightInput.replaceAll("[^0-9.]", "").toDoubleOption
    weight match {
      case Some(w) if w > 20 && w < 300 => Some(w) // Assuming kg
      case Some(w) if w > 45 && w < 660 =>
        Some(w / 2.20462) // Convert lbs to kg
      case _ => None
    }
  }

  def validateGender(genderInput: String): Option[String] = {
    val gender = genderInput.trim.toLowerCase
    gender match {
      case "m" | "male" | "man" | "boy"      => Some("M")
      case "f" | "female" | "woman" | "girl" => Some("F")
      case _                                 => None
    }
  }

  def validateBodyFat(bodyFatInput: String): Option[Double] = {
    val bodyFat = bodyFatInput.replaceAll("[^0-9.]", "").toDoubleOption
    bodyFat match {
      case Some(bf) if bf >= 0 && bf <= 100 => Some(bf)
      case _                                => None
    }
  }

  def validateUnitPreference(preferenceInput: String): Option[Boolean] = {
    val preference = preferenceInput.trim.toLowerCase
    preference match {
      case "yes" | "y" | "true" => Some(true)
      case "no" | "n" | "false" => Some(false)
      case _                    => None
    }
  }

  def getValidInput[T](
      prompt: String,
      validator: String => Option[T],
      errorMsg: String
  ): T = {
    var input: Option[T] = None
    while (input.isEmpty) {
      print(prompt)
      val userInput = scala.io.StdIn.readLine()
      input = validator(userInput)
      if (input.isEmpty) {
        if (userInput.trim.isEmpty) {
          println("Input cannot be empty. " + errorMsg)
        } else {
          println(errorMsg)
        }
      }
    }
    input.get
  }

  def getValidOptionalInput[T](
      prompt: String,
      validator: String => Option[T],
      errorMsg: String
  ): Option[T] = {
    print(prompt)
    val userInput = scala.io.StdIn.readLine()
    if (userInput.trim.isEmpty) {
      None
    } else {
      val input = validator(userInput)
      if (input.isEmpty) {
        println(errorMsg)
        None
      } else {
        input
      }
    }
  }

  def handleError(error: ValidationError): Unit = {
    println(s"\nError: ${error.message}")
    println(s"Suggestion: ${error.suggestion}")
  }
}
