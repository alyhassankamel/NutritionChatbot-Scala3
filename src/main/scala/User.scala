object User {
  case class UserProfile(
      name: Option[String] = None,
      age: Option[Int] = None,
      height: Option[Double] = None,
      weight: Option[Double] = None,
      bodyFatPercentage: Option[Double] = None,
      gender: Option[String] = None,
      isImperial: Boolean = false
  )

  var profile = UserProfile()

  def updateProfile(
      name: Option[String] = None,
      age: Option[Int] = None,
      height: Option[Double] = None,
      weight: Option[Double] = None,
      bodyFatPercentage: Option[Double] = None,
      gender: Option[String] = None,
      isImperial: Option[Boolean] = None
  ): Unit = {
    profile = UserProfile(
      name.orElse(profile.name),
      age.orElse(profile.age),
      height.orElse(profile.height),
      weight.orElse(profile.weight),
      bodyFatPercentage.orElse(profile.bodyFatPercentage),
      gender.orElse(profile.gender),
      isImperial.getOrElse(profile.isImperial)
    )
  }

  def resetUserData(): Unit = {
    profile = UserProfile()
  }

  def name: Option[String] = profile.name
  def age: Option[Int] = profile.age
  def height: Option[Double] = profile.height
  def weight: Option[Double] = profile.weight
  def bodyFatPercentage: Option[Double] = profile.bodyFatPercentage
  def gender: Option[String] = profile.gender
  def isImperial: Boolean = profile.isImperial
}
