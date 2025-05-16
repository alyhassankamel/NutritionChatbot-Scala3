import User._

object FFMI {
  var ffmiUsageCount: Int = 0
  def calculateFFMI(): Double = {

    val weight = User.profile.weight.getOrElse(0.0)
    val height = User.profile.height.getOrElse(0.0)
    val bodyFatPercentage = User.profile.bodyFatPercentage.getOrElse(0.0)

    if (
      height <= 0 || weight <= 0 || bodyFatPercentage < 0 || bodyFatPercentage > 100
    ) {
      0.0
    } else {
      val isImperial: Boolean = User.isImperial
      val (weightKg, heightM) = if (isImperial) {
        (weight * 0.453592, height * 0.0254)
      } else {
        (weight, height)
      }

      val fatFreeMass: Double = weightKg * (1 - (bodyFatPercentage / 100))
      fatFreeMass / (heightM * heightM)
    }
  }
}
