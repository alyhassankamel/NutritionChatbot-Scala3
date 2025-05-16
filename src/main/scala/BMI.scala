import User._

object BMI {
  var bmiUsageCount: Int = 0
  
  def calculateBMI(): Double = {
    
    val weight = User.profile.weight.getOrElse(0.0)
    val height = User.profile.height.getOrElse(0.0)
    
    if (height <= 0 || weight <= 0) {
      0.0
    } else {
      val isImperial: Boolean = User.isImperial
      if (isImperial) {
        val weightKg: Double = weight * 0.453592
        val heightM: Double = height * 0.0254
        weightKg / (heightM * heightM)
      } else {
        weight / (height * height)
      }
    }
  }
}
