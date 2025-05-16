import scala.io.StdIn.readLine
import scala.util.boundary, boundary.break
import scala.io.Source
import java.io.{File, FileWriter, PrintWriter}

object WorkoutPlanner {
  var workoutUsageCount: Int = 0

  // CSV file paths
  private val csvDirPath =
    "C:\\Personal Data\\Uni\\Y2\\Y2S2\\More\\The Underdogs_Nutrition Chatbot_Source\\Project\\chatbot\\src\\main\\resources\\"
  private val exercisesCSVPath = s"$csvDirPath\\workout_exercises.csv"
  private val warmUpsCSVPath = s"$csvDirPath\\workout_warmups.csv"
  private val muscleGroupsCSVPath = s"$csvDirPath\\workout_muscle_groups.csv"
  private val workoutSplitsCSVPath = s"$csvDirPath\\workout_splits.csv"
  private val workoutCategoriesCSVPath = s"$csvDirPath\\workout_categories.csv"

  // Ensure resources directory exists
  private val resourcesDir = new File(csvDirPath)
  if (!resourcesDir.exists()) resourcesDir.mkdirs()

  case class Exercise(
      name: String,
      description: String,
      difficulty: String,
      equipment: String,
      sets: String,
      reps: String,
      progression: String,
      variations: List[String],
      tutorialLink: String =
        "" // Default empty string for backward compatibility
  )

  case class WarmUp(
      name: String,
      duration: String,
      description: String
  )

  case class MuscleGroup(
      name: String,
      exercises: List[Exercise],
      warmUps: List[WarmUp]
  )

  case class WorkoutSplit(
      name: String,
      description: String,
      muscleGroups: List[MuscleGroup],
      frequency: String,
      notes: String,
      progressionGuidelines: String
  )

  case class WorkoutCategory(
      name: String,
      description: String,
      splits: List[WorkoutSplit]
  )

  // Define the workout data before using it in CSV creation
  private val bodybuildingSplits = List(
    WorkoutSplit(
      "Push/Pull/Legs (PPL)",
      "A popular split that groups exercises by movement patterns",
      List(
        MuscleGroup(
          "Push Day Template",
          List(
            Exercise(
              "Main Push Workout",
              "Primary pushing movements for chest, shoulders, and triceps",
              "Beginner",
              "Barbell/Dumbbells",
              "4-5",
              "8-12",
              "Add 5lbs every 2 weeks when you can complete all sets with good form",
              List(
                "Template 1: Bench Press + Overhead Press + Tricep Extensions",
                "Template 2: Incline Press + Lateral Raises + Close Grip Press",
                "Template 3: Dumbbell Press + Arnold Press + Dips"
              )
            ),
            Exercise(
              "Alternative Push Workout 1",
              "Focus on upper chest and shoulders",
              "Intermediate",
              "Dumbbells",
              "3-4",
              "10-12",
              "Increase weight when you can complete 12 reps with good form",
              List(
                "Incline Dumbbell Press + Front Raises + Tricep Pushdowns",
                "Smith Machine Press + Cable Lateral Raises + Skull Crushers",
                "Decline Push-ups + Military Press + Rope Extensions"
              )
            ),
            Exercise(
              "Alternative Push Workout 2",
              "Emphasis on shoulder development",
              "Intermediate",
              "Mixed Equipment",
              "4",
              "8-12",
              "Progressive overload with focus on form",
              List(
                "Overhead Press + Incline Bench + Tricep Dips",
                "Lateral Raises + Flat Bench + Cable Extensions",
                "Arnold Press + Decline Bench + Overhead Extensions"
              )
            ),
            Exercise(
              "Alternative Push Workout 3",
              "Power and strength focused",
              "Advanced",
              "Barbell",
              "5",
              "5-8",
              "Focus on heavy weights and explosive movements",
              List(
                "Push Press + Close Grip Bench + JM Press",
                "Floor Press + Strict Press + Tate Press",
                "Board Press + Bradford Press + Rolling Extensions"
              )
            )
          ),
          List(
            WarmUp(
              "Light Cardio",
              "5-10 minutes",
              "Treadmill or stationary bike"
            ),
            WarmUp(
              "Dynamic Stretching",
              "5 minutes",
              "Arm circles, shoulder rolls"
            ),
            WarmUp("Light Sets", "2 sets", "50% of working weight for 10 reps")
          )
        ),
        MuscleGroup(
          "Pull Day Template",
          List(
            Exercise(
              "Main Pull Workout",
              "Primary pulling movements for back and biceps",
              "Beginner",
              "Barbell/Dumbbells",
              "4-5",
              "8-12",
              "Add 5lbs every 2 weeks when you can complete all sets",
              List(
                "Template 1: Barbell Rows + Pull-ups + Bicep Curls",
                "Template 2: Deadlifts + Lat Pulldowns + Hammer Curls",
                "Template 3: T-Bar Rows + Chin-ups + Preacher Curls"
              )
            ),
            Exercise(
              "Alternative Pull Workout 1",
              "Focus on back width",
              "Intermediate",
              "Cable Machine",
              "3-4",
              "10-12",
              "Focus on mind-muscle connection",
              List(
                "Wide Grip Pulldowns + Rack Pulls + Incline Curls",
                "Straight Arm Pulldowns + Meadows Rows + Spider Curls",
                "Reverse Grip Pulldowns + Single Arm Rows + Concentration Curls"
              )
            ),
            Exercise(
              "Alternative Pull Workout 2",
              "Emphasis on back thickness",
              "Intermediate",
              "Barbell",
              "4",
              "8-12",
              "Focus on full range of motion",
              List(
                "Pendlay Rows + Face Pulls + Cross Body Curls",
                "Bent Over Rows + Reverse Flyes + Drag Curls",
                "Chest Supported Rows + Band Pull Aparts + Zottman Curls"
              )
            ),
            Exercise(
              "Alternative Pull Workout 3",
              "Power and strength focused",
              "Advanced",
              "Mixed Equipment",
              "5",
              "5-8",
              "Focus on compound movements",
              List(
                "Power Cleans + Weighted Pull-ups + Barbell Curls",
                "Snatch Grip Deadlifts + Rope Climbs + Cheat Curls",
                "Rack Pulls + Muscle Ups + 21s"
              )
            )
          ),
          List(
            WarmUp(
              "Shoulder Mobility",
              "5 minutes",
              "Arm circles, band pull-aparts"
            ),
            WarmUp("Light Sets", "2 sets", "50% of working weight for 10 reps")
          )
        ),
        MuscleGroup(
          "Legs Day Template",
          List(
            Exercise(
              "Main Legs Workout",
              "Primary leg movements for quadriceps, hamstrings, and calves",
              "Beginner",
              "Barbell/Dumbbells",
              "4-5",
              "8-12",
              "Add 5lbs every 2 weeks when you can complete all sets",
              List(
                "Template 1: Squats + Romanian Deadlifts + Calf Raises",
                "Template 2: Leg Press + Leg Curls + Standing Calves",
                "Template 3: Front Squats + Good Mornings + Seated Calves"
              )
            ),
            Exercise(
              "Alternative Legs Workout 1",
              "Focus on quadriceps",
              "Intermediate",
              "Machine Based",
              "3-4",
              "10-12",
              "Focus on time under tension",
              List(
                "Hack Squats + Leg Extensions + Walking Lunges",
                "Bulgarian Split Squats + Step Ups + Sissy Squats",
                "Goblet Squats + Reverse Lunges + Leg Press"
              )
            ),
            Exercise(
              "Alternative Legs Workout 2",
              "Emphasis on hamstrings",
              "Intermediate",
              "Mixed Equipment",
              "4",
              "8-12",
              "Focus on mind-muscle connection",
              List(
                "Romanian Deadlifts + Leg Curls + Hip Thrusts",
                "Sumo Deadlifts + Glute Ham Raises + Back Extensions",
                "Single Leg Deadlifts + Nordic Curls + Cable Pull Throughs"
              )
            ),
            Exercise(
              "Alternative Legs Workout 3",
              "Power and strength focused",
              "Advanced",
              "Barbell",
              "5",
              "5-8",
              "Focus on explosive movements",
              List(
                "Box Squats + Power Cleans + Jump Squats",
                "Pause Squats + Snatch Grip Deadlifts + Broad Jumps",
                "Front Squats + Clean Pulls + Depth Jumps"
              )
            )
          ),
          List(
            WarmUp("Hip Mobility", "5 minutes", "Hip circles, leg swings"),
            WarmUp("Light Sets", "2 sets", "50% of working weight for 10 reps")
          )
        )
      ),
      "6 days per week (2 cycles)",
      "Great for beginners and intermediate lifters. Allows for high frequency training.",
      """
      |Progression Guidelines:
      |1. Start with 3 sets of each exercise
      |2. Add one set every 2 weeks until you reach 5 sets
      |3. Increase weight when you can complete all reps with good form
      |4. Deload every 4-6 weeks (reduce weight by 20% for one week)
      |5. Track your progress in a workout log
      |6. Rotate between templates and alternatives every 4-6 weeks
      |7. Choose templates based on your experience level
      |8. Ensure proper form before increasing weight
      """.stripMargin
    ),
    WorkoutSplit(
      "Specialized Strength",
      "Focus on maximal strength development",
      List(
        MuscleGroup(
          "Power Lifts",
          List(
            Exercise(
              "Squat",
              "Maximal strength development",
              "Advanced",
              "Barbell",
              "5",
              "3-5",
              "Follow 5/3/1 progression",
              List(
                "Low Bar Squat (powerlifting style)",
                "High Bar Squat (olympic style)",
                "Front Squat (quad focus)"
              )
            ),
            Exercise(
              "Deadlift",
              "Posterior chain development",
              "Advanced",
              "Barbell",
              "5",
              "3-5",
              "Follow 5/3/1 progression",
              List(
                "Conventional Deadlift",
                "Sumo Deadlift",
                "Romanian Deadlift"
              )
            )
          ),
          List(
            WarmUp(
              "Mobility Work",
              "10 minutes",
              "Hip mobility, ankle mobility"
            ),
            WarmUp(
              "Light Sets",
              "3 sets",
              "Progressive loading up to working weight"
            )
          )
        )
      ),
      "4 days per week",
      "Advanced split focusing on maximal strength development",
      """
      |Progression Guidelines:
      |1. Follow 5/3/1 programming
      |2. Track all lifts in a training log
      |3. Deload every 4 weeks
      |4. Focus on form and technique
      |5. Use percentage-based training
      """.stripMargin
    ),
    WorkoutSplit(
      "Upper/Lower Split",
      "Balanced split focusing on upper and lower body development",
      List(
        MuscleGroup(
          "Upper Body",
          List(
            Exercise(
              "Barbell Rows",
              "Compound back exercise",
              "Intermediate",
              "Barbell",
              "4",
              "8-12",
              "Add 5lbs every 2 weeks when you can complete all sets",
              List(
                "Pendlay Rows (explosive variation)",
                "T-Bar Rows (more stable)",
                "Dumbbell Rows (unilateral focus)"
              )
            ),
            Exercise(
              "Overhead Press",
              "Compound shoulder exercise",
              "Intermediate",
              "Barbell",
              "4",
              "8-12",
              "Add 5lbs every 2 weeks when you can complete all sets",
              List(
                "Seated Overhead Press",
                "Push Press",
                "Arnold Press"
              )
            )
          ),
          List(
            WarmUp(
              "Shoulder Mobility",
              "5 minutes",
              "Arm circles, band pull-aparts"
            ),
            WarmUp("Light Sets", "2 sets", "50% of working weight for 10 reps")
          )
        ),
        MuscleGroup(
          "Lower Body",
          List(
            Exercise(
              "Back Squat",
              "Compound leg exercise",
              "Intermediate",
              "Barbell",
              "4",
              "8-12",
              "Add 5lbs every 2 weeks when you can complete all sets",
              List(
                "Front Squat",
                "Bulgarian Split Squat",
                "Goblet Squat"
              )
            ),
            Exercise(
              "Romanian Deadlift",
              "Posterior chain exercise",
              "Intermediate",
              "Barbell",
              "4",
              "8-12",
              "Add 5lbs every 2 weeks when you can complete all sets",
              List(
                "Conventional Deadlift",
                "Sumo Deadlift",
                "Single Leg RDL"
              )
            )
          ),
          List(
            WarmUp("Hip Mobility", "5 minutes", "Hip circles, leg swings"),
            WarmUp("Light Sets", "2 sets", "50% of working weight for 10 reps")
          )
        )
      ),
      "4 days per week",
      "Great for balanced development and recovery",
      """
      |Progression Guidelines:
      |1. Start with 3 sets of each exercise
      |2. Add one set every 2 weeks until you reach 5 sets
      |3. Increase weight when you can complete all reps with good form
      |4. Deload every 4-6 weeks
      |5. Track your progress in a workout log
      """.stripMargin
    )
  )

  private val calisthenicsWorkouts = List(
    WorkoutSplit(
      "Basic Calisthenics",
      "Foundation movements for bodyweight training",
      List(
        MuscleGroup(
          "Upper Body",
          List(
            Exercise(
              "Push-ups",
              "Basic pushing movement",
              "Beginner",
              "Bodyweight",
              "3-4",
              "10-15",
              "Progress to harder variations when you can do 15 reps with good form",
              List(
                "Diamond Push-ups (tricep focus)",
                "Wide Push-ups (chest focus)",
                "Decline Push-ups (upper chest focus)",
                "Pike Push-ups (shoulder focus)"
              )
            ),
            Exercise(
              "Pull-ups",
              "Basic pulling movement",
              "Intermediate",
              "Bodyweight",
              "3-4",
              "8-12",
              "Add reps until you can do 12, then progress to harder variations",
              List(
                "Chin-ups (bicep focus)",
                "Wide Grip Pull-ups (back width focus)",
                "Close Grip Pull-ups (back thickness focus)"
              )
            )
          ),
          List(
            WarmUp(
              "Joint Mobility",
              "5 minutes",
              "Shoulder circles, wrist mobility"
            ),
            WarmUp("Light Cardio", "5 minutes", "Jumping jacks, high knees"),
            WarmUp("Scapular Pull-ups", "2 sets", "10 reps to warm up back")
          )
        )
      ),
      "3-4 days per week",
      "Great for building functional strength and body control.",
      """
      |Progression Guidelines:
      |1. Master basic movements before progressing
      |2. Focus on form and full range of motion
      |3. Increase reps before moving to harder variations
      |4. Rest 2-3 minutes between sets
      |5. Track progress in a workout log
      """.stripMargin
    ),
    WorkoutSplit(
      "Weighted Calisthenics",
      "Advanced bodyweight training with added resistance",
      List(
        MuscleGroup(
          "Strength Focus",
          List(
            Exercise(
              "Weighted Pull-ups",
              "Advanced pulling movement with added weight",
              "Advanced",
              "Weight Belt/Dumbbell",
              "4-5",
              "5-8",
              "Add 2.5-5lbs when you can complete all sets with good form",
              List(
                "Weighted Chin-ups (bicep focus)",
                "Weighted Wide Pull-ups (back width)",
                "Weighted L-Sit Pull-ups (core integration)"
              )
            ),
            Exercise(
              "Weighted Dips",
              "Advanced pushing movement with added weight",
              "Advanced",
              "Weight Belt/Dumbbell",
              "4-5",
              "5-8",
              "Add 2.5-5lbs when you can complete all sets with good form",
              List(
                "Weighted Ring Dips (stability focus)",
                "Weighted Bulgarian Dips (chest focus)",
                "Weighted Korean Dips (shoulder focus)"
              )
            )
          ),
          List(
            WarmUp(
              "Joint Mobility",
              "5 minutes",
              "Shoulder circles, wrist mobility"
            ),
            WarmUp("Light Sets", "2 sets", "Bodyweight variations for 5 reps"),
            WarmUp(
              "Weight Progression",
              "3 sets",
              "Gradually add weight up to working sets"
            )
          )
        )
      ),
      "3-4 days per week",
      "Focus on recovery and skill development between sessions",
      """
      |Progression Guidelines:
      |1. Start with 3 sets of each exercise
      |2. Master bodyweight variations before adding weight
      |3. Begin with 2.5-5lbs additional weight
      |4. Increase by 2.5lbs when you can complete all sets and reps
      |5. Focus on perfect form over additional weight
      """.stripMargin
    )
  )

  private val martialArtsWorkouts = List(
    WorkoutSplit(
      "Grappling Arts",
      "Training for wrestling, BJJ, and Sambo",
      List(
        MuscleGroup(
          "Technical Training",
          List(
            Exercise(
              "Drilling",
              "Basic grappling techniques",
              "Beginner",
              "Partner",
              "3-4",
              "5 minutes",
              "Increase complexity and speed",
              List(
                "Takedown Drills",
                "Guard Passing",
                "Submission Chains"
              )
            ),
            Exercise(
              "Live Training",
              "Application of techniques",
              "Intermediate",
              "Partner",
              "4-5",
              "5 minutes",
              "Increase intensity and resistance",
              List(
                "Positional Sparring",
                "Submission Only",
                "Full Rolling"
              )
            )
          ),
          List(
            WarmUp(
              "Joint Mobility",
              "5 minutes",
              "Hip mobility, shoulder mobility"
            ),
            WarmUp(
              "Dynamic Stretching",
              "5 minutes",
              "Hip circles, arm circles"
            ),
            WarmUp("Light Drilling", "2 rounds", "Basic movement patterns")
          )
        )
      ),
      "3-5 days per week",
      "Balance technical training with sparring and recovery",
      """
      |Progression Guidelines:
      |1. Focus on technique before intensity
      |2. Increase training volume gradually
      |3. Train with partners of varying skill levels
      |4. Log techniques and positions to identify gaps
      |5. Complement with strength and conditioning
      """.stripMargin
    ),
    WorkoutSplit(
      "Weapons Training",
      "Training for FMA, Kenjutsu, and other weapons arts",
      List(
        MuscleGroup(
          "Technical Training",
          List(
            Exercise(
              "Basic Strikes",
              "Fundamental weapon techniques",
              "Beginner",
              "Training Weapon",
              "3-4",
              "5 minutes",
              "Increase speed and precision",
              List(
                "Single Stick Drills",
                "Double Stick Drills",
                "Sword Cuts"
              )
            ),
            Exercise(
              "Partner Drills",
              "Application of techniques",
              "Intermediate",
              "Training Weapon",
              "4-5",
              "5 minutes",
              "Increase complexity and speed",
              List(
                "Sinawali Patterns",
                "Sparring Drills",
                "Counter Techniques"
              )
            )
          ),
          List(
            WarmUp(
              "Joint Mobility",
              "5 minutes",
              "Wrist mobility, shoulder mobility"
            ),
            WarmUp(
              "Dynamic Stretching",
              "5 minutes",
              "Arm circles, hip rotations"
            ),
            WarmUp("Light Drilling", "2 rounds", "Basic movement patterns")
          )
        )
      ),
      "3-4 days per week",
      "Focus on precision, control, and tactical awareness",
      """
      |Progression Guidelines:
      |1. Master basic strikes and blocks
      |2. Increase speed while maintaining control
      |3. Add complexity with combinations and counters
      |4. Integrate footwork with weapon techniques
      |5. Progress to controlled sparring
      """.stripMargin
    )
  )

  private val workoutCategories = List(
    WorkoutCategory(
      "Bodybuilding",
      "Focused on muscle hypertrophy and strength",
      bodybuildingSplits
    ),
    WorkoutCategory(
      "Calisthenics",
      "Bodyweight training for strength and skill",
      calisthenicsWorkouts
    ),
    WorkoutCategory(
      "Martial Arts",
      "Combat sports and self-defense training",
      martialArtsWorkouts
    )
  )

  // Methods to create and load CSV files

  // Create CSV files with workout data if they don't exist
  private def createWorkoutCSVs(): Unit = {
    createExercisesCSV()
    createWarmUpsCSV()
    createMuscleGroupsCSV()
    createWorkoutSplitsCSV()
    createWorkoutCategoriesCSV()
  }

  // Initialize the CSV files when the object is loaded
  createWorkoutCSVs()

  // Create the exercises CSV file
  private def createExercisesCSV(): Unit = {
    if (!new File(exercisesCSVPath).exists()) {
      val pw = new PrintWriter(new FileWriter(exercisesCSVPath))
      try {
        // Header: id,name,description,difficulty,equipment,sets,reps,progression,variations,tutorialLink
        pw.println(
          "id,name,description,difficulty,equipment,sets,reps,progression,variations,tutorialLink"
        )

        // We'll use a sequential ID to reference exercises in other files
        var id = 1

        // Function to write exercise data
        def writeExercise(exercise: Exercise): Unit = {
          // Clean and format fields for CSV
          val cleanName = exercise.name.replace("\"", "\"\"")
          val cleanDesc = exercise.description.replace("\"", "\"\"")
          val cleanDifficulty = exercise.difficulty.replace("\"", "\"\"")
          val cleanEquipment = exercise.equipment.replace("\"", "\"\"")
          val cleanSets = exercise.sets.replace("\"", "\"\"")
          val cleanReps = exercise.reps.replace("\"", "\"\"")
          val cleanProgression = exercise.progression.replace("\"", "\"\"")

          // Format variations as a single string with | separator
          // Ensure each variation is properly escaped
          val cleanVariations = exercise.variations
            .map(v => v.replace("\"", "\"\"").replace("|", "\\|"))
            .mkString("|")

          val cleanTutorialLink = exercise.tutorialLink.replace("\"", "\"\"")

          pw.println(
            s"""$id,"$cleanName","$cleanDesc","$cleanDifficulty","$cleanEquipment","$cleanSets","$cleanReps","$cleanProgression","$cleanVariations","$cleanTutorialLink""""
          )
          id += 1
        }

        // Write all exercises from all workout categories
        List(
          bodybuildingSplits,
          calisthenicsWorkouts,
          martialArtsWorkouts
        ).flatten.foreach { split =>
          split.muscleGroups.foreach { group =>
            group.exercises.foreach(writeExercise)
          }
        }
      } finally {
        pw.close()
      }
    }
  }

  // Create the warm-ups CSV file
  private def createWarmUpsCSV(): Unit = {
    if (!new File(warmUpsCSVPath).exists()) {
      val pw = new PrintWriter(new FileWriter(warmUpsCSVPath))
      try {
        // Header: id,name,duration,description
        pw.println("id,name,duration,description")

        // We'll use a sequential ID to reference warm-ups in other files
        var id = 1

        // Function to write warm-up data
        def writeWarmUp(warmUp: WarmUp): Unit = {
          pw.println(
            s"""$id,"${warmUp.name}","${warmUp.duration}","${warmUp.description}""""
          )
          id += 1
        }

        // Write all warm-ups from bodybuildingSplits
        bodybuildingSplits.foreach { split =>
          split.muscleGroups.foreach { group =>
            group.warmUps.foreach(writeWarmUp)
          }
        }

        // Write all warm-ups from calisthenicsWorkouts
        calisthenicsWorkouts.foreach { split =>
          split.muscleGroups.foreach { group =>
            group.warmUps.foreach(writeWarmUp)
          }
        }

        // Write all warm-ups from martialArtsWorkouts
        martialArtsWorkouts.foreach { split =>
          split.muscleGroups.foreach { group =>
            group.warmUps.foreach(writeWarmUp)
          }
        }
      } finally {
        pw.close()
      }
    }
  }

  // Create the muscle groups CSV file
  private def createMuscleGroupsCSV(): Unit = {
    if (!new File(muscleGroupsCSVPath).exists()) {
      val pw = new PrintWriter(new FileWriter(muscleGroupsCSVPath))
      try {
        // Header: id,name,exercise_ids,warmup_ids,split_id
        pw.println("id,name,exercise_ids,warmup_ids,split_id")

        // We'll use a sequential ID to reference muscle groups in other files
        var id = 1
        var splitId = 1

        // Maps to store exercise and warmup IDs
        val exerciseMap = scala.collection.mutable.Map[Exercise, Int]()
        val warmUpMap = scala.collection.mutable.Map[WarmUp, Int]()

        // Initialize maps with IDs
        var exerciseId = 1
        var warmUpId = 1

        // Add all exercises from all workout categories
        List(
          bodybuildingSplits,
          calisthenicsWorkouts,
          martialArtsWorkouts
        ).flatten.foreach { split =>
          split.muscleGroups.foreach { group =>
            group.exercises.foreach { exercise =>
              exerciseMap.put(exercise, exerciseId)
              exerciseId += 1
            }
            group.warmUps.foreach { warmUp =>
              warmUpMap.put(warmUp, warmUpId)
              warmUpId += 1
            }
          }
        }

        // Function to write muscle group data
        def writeMuscleGroup(group: MuscleGroup, splitId: Int): Unit = {
          // Get exercise IDs for this group
          val exerciseIds = group.exercises.map(exerciseMap(_)).mkString("|")

          // Get warmup IDs for this group
          val warmUpIds = group.warmUps.map(warmUpMap(_)).mkString("|")

          pw.println(
            s"""$id,"${group.name}","$exerciseIds","$warmUpIds",$splitId"""
          )
          id += 1
        }

        // Write all muscle groups
        List(
          bodybuildingSplits,
          calisthenicsWorkouts,
          martialArtsWorkouts
        ).flatten.foreach { split =>
          split.muscleGroups.foreach { group =>
            writeMuscleGroup(group, splitId)
          }
          splitId += 1
        }
      } finally {
        pw.close()
      }
    }
  }

  // Create the workout splits CSV file
  private def createWorkoutSplitsCSV(): Unit = {
    if (!new File(workoutSplitsCSVPath).exists()) {
      val pw = new PrintWriter(new FileWriter(workoutSplitsCSVPath))
      try {
        // Header: id,name,description,frequency,notes,progression_guidelines,category_id
        pw.println(
          "id,name,description,frequency,notes,progression_guidelines,category_id"
        )

        // We'll use a sequential ID to reference splits in other files
        var id = 1

        // Function to write split data
        def writeSplit(split: WorkoutSplit, categoryId: Int): Unit = {
          // Format multiline strings for CSV
          val cleanDescription =
            split.description.replace("\n", "\\n").replace("\"", "\"\"")
          val cleanNotes =
            split.notes.replace("\n", "\\n").replace("\"", "\"\"")

          // Ensure progression guidelines are properly formatted for CSV
          val cleanProgressionGuidelines = split.progressionGuidelines.trim
            .replace("\n", "\\n")
            .replace("\r", "")
            .replace("\"", "\"\"")

          pw.println(
            s"""$id,"${split.name}","$cleanDescription","${split.frequency}","$cleanNotes","$cleanProgressionGuidelines",$categoryId"""
          )
          id += 1
        }

        // Write all splits from bodybuilding category (ID 1)
        bodybuildingSplits.foreach(writeSplit(_, 1))

        // Write all splits from calisthenics category (ID 2)
        calisthenicsWorkouts.foreach(writeSplit(_, 2))

        // Write all splits from martial arts category (ID 3)
        martialArtsWorkouts.foreach(writeSplit(_, 3))
      } finally {
        pw.close()
      }
    }
  }

  // Create the workout categories CSV file
  private def createWorkoutCategoriesCSV(): Unit = {
    if (!new File(workoutCategoriesCSVPath).exists()) {
      val pw = new PrintWriter(new FileWriter(workoutCategoriesCSVPath))
      try {
        // Header: id,name,description,split_ids
        pw.println("id,name,description,split_ids")

        // Calculate split IDs
        val bodybuildingSplitIds =
          (1 to bodybuildingSplits.length).mkString("|")
        val calisthenicsSplitIds =
          ((bodybuildingSplits.length + 1) to (bodybuildingSplits.length + calisthenicsWorkouts.length))
            .mkString("|")
        val martialArtsSplitIds =
          ((bodybuildingSplits.length + calisthenicsWorkouts.length + 1) to (bodybuildingSplits.length + calisthenicsWorkouts.length + martialArtsWorkouts.length))
            .mkString("|")

        // Write each category
        pw.println(
          s"""1,"Bodybuilding","Focused on muscle hypertrophy and strength","$bodybuildingSplitIds""""
        )
        pw.println(
          s"""2,"Calisthenics","Bodyweight training for strength and skill","$calisthenicsSplitIds""""
        )
        pw.println(
          s"""3,"Martial Arts","Combat sports and self-defense training","$martialArtsSplitIds""""
        )
      } finally {
        pw.close()
      }
    }
  }

  // Methods to load data from CSV files

  // Parse CSV line considering that some fields may contain commas inside quotes
  private def parseCSVLine(line: String): Array[String] = {
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

    // Process all fields to handle escaped characters and normalize newlines
    val processedResult = result.map { field =>
      // Replace all variants of newlines
      field.replace("\\n", "\n").replace("\\r\\n", "\n").replace("\\r", "\n")
    }

    processedResult.toArray
  }

  // Load all workout data from CSV files
  private def loadWorkoutDataFromCSV(): List[WorkoutCategory] = {
    try {
      // Load exercises
      val exercisesMap = loadExercisesFromCSV()

      // Load warm-ups
      val warmUpsMap = loadWarmUpsFromCSV()

      // Load muscle groups
      val muscleGroupsMap = loadMuscleGroupsFromCSV(exercisesMap, warmUpsMap)

      // Load workout splits
      val splitsMap = loadWorkoutSplitsFromCSV(muscleGroupsMap)

      // Load workout categories
      loadWorkoutCategoriesFromCSV(splitsMap)
    } catch {
      case e: Exception =>
        println(s"Error loading workout data from CSV: ${e.getMessage}")
        // If loading fails, return the hardcoded data
        List(
          WorkoutCategory(
            "Bodybuilding",
            "Focused on muscle hypertrophy and strength",
            bodybuildingSplits
          ),
          WorkoutCategory(
            "Calisthenics",
            "Bodyweight training for strength and skill",
            calisthenicsWorkouts
          ),
          WorkoutCategory(
            "Martial Arts",
            "Combat sports and self-defense training",
            martialArtsWorkouts
          )
        )
    }
  }

  // Load exercises from CSV
  private def loadExercisesFromCSV(): Map[Int, Exercise] = {
    if (!new File(exercisesCSVPath).exists()) {
      return Map.empty
    }

    val source = Source.fromFile(exercisesCSVPath)
    try {
      source
        .getLines()
        .drop(1)
        .zipWithIndex
        .map { case (line, index) =>
          val parts = parseCSVLine(line)
          val id = parts(0).toInt

          // Process variations field to ensure it splits correctly
          val rawVariations = parts(8).trim
          val variations = if (rawVariations.isEmpty) {
            List.empty
          } else {
            // Handle variations that might contain pipe characters
            rawVariations.split("\\|").map(_.trim).filter(_.nonEmpty).toList
          }

          val exercise = Exercise(
            name = parts(1).trim,
            description = parts(2).trim,
            difficulty = parts(3).trim,
            equipment = parts(4).trim,
            sets = parts(5).trim,
            reps = parts(6).trim,
            progression = parts(7).trim,
            variations = variations,
            tutorialLink = if (parts.length > 9) parts(9).trim else ""
          )
          id -> exercise
        }
        .toMap
    } finally {
      source.close()
    }
  }

  // Load warm-ups from CSV
  private def loadWarmUpsFromCSV(): Map[Int, WarmUp] = {
    if (!new File(warmUpsCSVPath).exists()) {
      return Map.empty
    }

    val source = Source.fromFile(warmUpsCSVPath)
    try {
      source
        .getLines()
        .drop(1)
        .zipWithIndex
        .map { case (line, index) =>
          val parts = parseCSVLine(line)
          val id = parts(0).toInt
          val warmUp = WarmUp(
            name = parts(1),
            duration = parts(2),
            description = parts(3)
          )
          id -> warmUp
        }
        .toMap
    } finally {
      source.close()
    }
  }

  // Load muscle groups from CSV
  private def loadMuscleGroupsFromCSV(
      exercisesMap: Map[Int, Exercise],
      warmUpsMap: Map[Int, WarmUp]
  ): Map[Int, MuscleGroup] = {
    if (!new File(muscleGroupsCSVPath).exists()) {
      return Map.empty
    }

    val source = Source.fromFile(muscleGroupsCSVPath)
    try {
      source
        .getLines()
        .drop(1)
        .zipWithIndex
        .map { case (line, index) =>
          val parts = parseCSVLine(line)
          val id = parts(0).toInt

          // Parse exercise IDs and get corresponding exercises
          val exerciseIds = parts(2).split("\\|").map(_.toInt)
          val exercises = exerciseIds.flatMap(id => exercisesMap.get(id)).toList

          // Parse warm-up IDs and get corresponding warm-ups
          val warmUpIds = parts(3).split("\\|").map(_.toInt)
          val warmUps = warmUpIds.flatMap(id => warmUpsMap.get(id)).toList

          val muscleGroup = MuscleGroup(
            name = parts(1),
            exercises = exercises,
            warmUps = warmUps
          )
          id -> muscleGroup
        }
        .toMap
    } finally {
      source.close()
    }
  }

  // Load workout splits from CSV
  private def loadWorkoutSplitsFromCSV(
      muscleGroupsMap: Map[Int, MuscleGroup]
  ): Map[Int, WorkoutSplit] = {
    if (!new File(workoutSplitsCSVPath).exists()) {
      return Map.empty
    }

    // First, get the mapping of split ID to muscle group IDs
    val splitToMuscleGroups = scala.collection.mutable.Map[Int, List[Int]]()

    val mgSource = Source.fromFile(muscleGroupsCSVPath)
    try {
      mgSource.getLines().drop(1).foreach { line =>
        val parts = parseCSVLine(line)
        val muscleGroupId = parts(0).toInt
        val splitId = parts(4).toInt

        val currentGroups = splitToMuscleGroups.getOrElse(splitId, List.empty)
        splitToMuscleGroups(splitId) = currentGroups :+ muscleGroupId
      }
    } finally {
      mgSource.close()
    }

    // Now load the splits
    val source = Source.fromFile(workoutSplitsCSVPath)
    try {
      source
        .getLines()
        .drop(1)
        .zipWithIndex
        .map { case (line, index) =>
          val parts = parseCSVLine(line)
          val id = parts(0).toInt

          // Get muscle groups for this split
          val muscleGroupIds = splitToMuscleGroups.getOrElse(id, List.empty)
          val muscleGroups =
            muscleGroupIds.flatMap(id => muscleGroupsMap.get(id))

          // Ensure multiline fields are properly processed
          val description = parts(2).replace("\\n", "\n")
          val notes = parts(4).replace("\\n", "\n")
          val progressionGuidelines = parts(5).replace("\\n", "\n").trim()

          // Validate and clean progression guidelines to ensure proper formatting
          val cleanedProgressionGuidelines =
            if (progressionGuidelines.contains("\n")) {
              progressionGuidelines
            } else {
              // If there are no actual newlines but the text contains numbered items,
              // try to format it with newlines for better display
              val formattedText =
                progressionGuidelines.replaceAll("(\\d+\\.)\\s+", "$1\n ")
              if (formattedText == progressionGuidelines) {
                // If no changes were made, try another approach with line breaks at periods
                progressionGuidelines.replaceAll("\\. ", ".\n")
              } else {
                formattedText
              }
            }

          val workoutSplit = WorkoutSplit(
            name = parts(1),
            description = description,
            muscleGroups = muscleGroups,
            frequency = parts(3),
            notes = notes,
            progressionGuidelines = cleanedProgressionGuidelines
          )
          id -> workoutSplit
        }
        .toMap
    } finally {
      source.close()
    }
  }

  // Load workout categories from CSV
  private def loadWorkoutCategoriesFromCSV(
      splitsMap: Map[Int, WorkoutSplit]
  ): List[WorkoutCategory] = {
    if (!new File(workoutCategoriesCSVPath).exists()) {
      return List.empty
    }

    val source = Source.fromFile(workoutCategoriesCSVPath)
    try {
      source
        .getLines()
        .drop(1)
        .map { line =>
          val parts = parseCSVLine(line)

          // Parse split IDs and get corresponding splits
          val splitIds = parts(3).split("\\|").map(_.toInt)
          val splits = splitIds.flatMap(id => splitsMap.get(id)).toList

          WorkoutCategory(
            name = parts(1),
            description = parts(2),
            splits = splits
          )
        }
        .toList
    } finally {
      source.close()
    }
  }

  // Load workout categories from CSV or fallback to hardcoded data
  private lazy val loadedWorkoutCategories: List[WorkoutCategory] =
    loadWorkoutDataFromCSV()

  def displayWorkoutOptions(): Unit = {
    println("\nAvailable Workout Categories:")
    loadedWorkoutCategories.zipWithIndex.foreach { case (category, index) =>
      println(s"${index + 1}. ${category.name}")
    }
  }

  def displaySplitOptions(category: WorkoutCategory): Unit = {
    println(s"\nAvailable ${category.name} Splits:")
    category.splits.zipWithIndex.foreach { case (split, index) =>
      println(s"\n${index + 1}. ${split.name}")
      println(s"Description: ${split.description}")
      println(s"Frequency: ${split.frequency}")
    }
  }

  def displayWarmUps(warmUps: List[WarmUp]): Unit = {
    println("\nRecommended Warm-up Routine:")
    warmUps.foreach { warmUp =>
      println(s"\n${warmUp.name}")
      println(s"Duration: ${warmUp.duration}")
      println(s"Description: ${warmUp.description}")
    }
  }

  def selectExercises(muscleGroup: MuscleGroup): List[Exercise] = {
    println(s"\nSelect exercises for ${muscleGroup.name}:")
    println("Available exercises:")

    if (muscleGroup.exercises.isEmpty) {
      println("No exercises available for this muscle group")
      return List.empty
    }

    muscleGroup.exercises.zipWithIndex.foreach { case (exercise, index) =>
      println(s"\n${index + 1}. ${exercise.name}")
      println(s"   Description: ${exercise.description}")
      println(s"   Difficulty: ${exercise.difficulty}")
      println(s"   Equipment: ${exercise.equipment}")
      println(s"   Sets: ${exercise.sets}")
      println(s"   Reps: ${exercise.reps}")
      println(s"   Progression: ${exercise.progression}")

      println("\n   Variations:")
      if (exercise.variations.isEmpty) {
        println("   - No variations available")
      } else {
        exercise.variations.foreach(v => println(s"   - ${v.trim}"))
      }
    }

    print(
      "\nEnter the numbers of exercises you want to include (comma-separated): "
    )
    val input = readLine().trim

    if (input.isEmpty) {
      println("No selections made. Including all exercises by default.")
      muscleGroup.exercises
    } else {
      try {
        val selections = input.split(",").map(_.trim.toInt - 1)
        val selectedExercises = selections
          .filter(i => i >= 0 && i < muscleGroup.exercises.length)
          .map(muscleGroup.exercises(_))
          .toList

        if (selectedExercises.isEmpty) {
          println(
            "No valid selections made. Including all exercises by default."
          )
          muscleGroup.exercises
        } else {
          selectedExercises
        }
      } catch {
        case _: NumberFormatException =>
          println("Invalid input. Including all exercises by default.")
          muscleGroup.exercises
      }
    }
  }

  def createWorkoutPlan(split: WorkoutSplit): Unit = {
    println(s"\nCreating your ${split.name} workout plan...")
    println(s"Description: ${split.description}")
    println(s"Recommended frequency: ${split.frequency}")
    println(s"\nNotes: ${split.notes}")

    println(s"\nProgression Guidelines:")
    // Ensure all progression guidelines are displayed properly
    val guidelines = split.progressionGuidelines.trim
    if (guidelines.nonEmpty) {
      guidelines.split("\n").foreach(line => println(line.trim))
    } else {
      println("No specific progression guidelines available.")
    }

    val selectedExercises = if (split.muscleGroups.isEmpty) {
      println("\nNo muscle groups available in this split.")
      List.empty
    } else {
      split.muscleGroups.flatMap { muscleGroup =>
        println(s"\n=== ${muscleGroup.name} ===")

        // Display warmups
        println("Recommended Warm-up Routine:")
        if (muscleGroup.warmUps.isEmpty) {
          println("No warm-up routines available.")
        } else {
          muscleGroup.warmUps.foreach { warmUp =>
            println(
              s"- ${warmUp.name} (${warmUp.duration}): ${warmUp.description}"
            )
          }
        }

        // Select exercises
        selectExercises(muscleGroup)
      }
    }

    if (selectedExercises.isEmpty) {
      println(
        "\nNo exercises were selected or available. Cannot create a workout plan."
      )
    } else {
      println("\nYour Custom Workout Plan:")

      // Group exercises by name for better display
      val exercisesByName = selectedExercises.groupBy(_.name)

      exercisesByName.foreach { case (name, exercises) =>
        println(s"\n$name:")
        exercises.foreach { exercise =>
          println(s"- ${exercise.description}")
          println(s"  Difficulty: ${exercise.difficulty}")
          println(s"  Equipment: ${exercise.equipment}")
          println(s"  Sets: ${exercise.sets}")
          println(s"  Reps: ${exercise.reps}")
          println(s"  Progression: ${exercise.progression}")

          println("  Variations:")
          if (exercise.variations.isEmpty) {
            println("  - No variations available")
          } else {
            exercise.variations.foreach(v => println(s"  - ${v.trim}"))
          }

          // Display tutorial link if available
          val tutorialLink =
            if (exercise.tutorialLink.nonEmpty) exercise.tutorialLink
            else getTutorialLink(exercise.name)
          if (tutorialLink.nonEmpty) {
            println(s"  Tutorial Link: $tutorialLink")
          }
        }
      }

      println(
        "\nRemember to follow the progression guidelines and adjust sets/reps based on your fitness level."
      )
    }
  }

  def workoutPlanner(): Unit = {
    workoutUsageCount += 1

    println("\nWelcome to the Workout Planner!")
    displayWorkoutOptions()

    print("\nSelect a workout category (1-3): ")
    val categoryInput = readLine().trim

    try {
      val categoryChoice = categoryInput.toInt

      if (
        categoryChoice >= 1 && categoryChoice <= loadedWorkoutCategories.length
      ) {
        val selectedCategory = loadedWorkoutCategories(categoryChoice - 1)

        if (selectedCategory.splits.isEmpty) {
          println(s"No workout splits available for ${selectedCategory.name}.")
          return
        }

        displaySplitOptions(selectedCategory)

        print("\nSelect a split (enter the number): ")
        val splitInput = readLine().trim

        try {
          val splitChoice = splitInput.toInt

          if (
            splitChoice >= 1 && splitChoice <= selectedCategory.splits.length
          ) {
            val selectedSplit = selectedCategory.splits(splitChoice - 1)
            createWorkoutPlan(selectedSplit)
          } else {
            println(
              s"Invalid split selection. Please enter a number between 1 and ${selectedCategory.splits.length}."
            )
          }
        } catch {
          case _: NumberFormatException =>
            println("Invalid input. Please enter a valid number.")
        }
      } else {
        println(
          s"Invalid category selection. Please enter a number between 1 and ${loadedWorkoutCategories.length}."
        )
      }
    } catch {
      case _: NumberFormatException =>
        println("Invalid input. Please enter a valid number.")
    }
  }

  def parseWorkoutType(input: String): Option[String] = {
    val lowerInput = input.toLowerCase()
    if (
      lowerInput.contains("bodybuilding") || lowerInput.contains(
        "gym"
      ) || lowerInput.contains("weight")
    ) {
      Some("bodybuilding")
    } else if (
      lowerInput.contains("calisthenics") || lowerInput.contains(
        "bodyweight"
      ) || lowerInput.contains("street workout")
    ) {
      Some("calisthenics")
    } else if (
      lowerInput.contains("martial") || lowerInput.contains(
        "fighting"
      ) || lowerInput.contains("combat")
    ) {
      Some("martial arts")
    } else {
      None
    }
  }

  def parseMartialArts(input: String): Option[String] = {
    val lowerInput = input.toLowerCase()
    if (
      lowerInput.contains("striking") || lowerInput.contains(
        "boxing"
      ) || lowerInput.contains("kickboxing") ||
      lowerInput.contains("muay thai") || lowerInput.contains("strike")
    ) {
      Some("Striking Arts")
    } else if (
      lowerInput.contains("grappling") || lowerInput.contains(
        "wrestling"
      ) || lowerInput.contains("bjj") ||
      lowerInput.contains("brazilian jiu jitsu") || lowerInput.contains(
        "sambo"
      ) || lowerInput.contains("grapple")
    ) {
      Some("Grappling Arts")
    } else if (
      lowerInput.contains("weapon") || lowerInput.contains("fma") || lowerInput
        .contains("arnis") ||
      lowerInput.contains("kali") || lowerInput.contains(
        "escrima"
      ) || lowerInput.contains("kenjutsu") ||
      lowerInput.contains("sword") || lowerInput.contains("stick")
    ) {
      Some("Weapons Training")
    } else {
      None
    }
  }

  def parseCalisthenics(input: String): Option[String] = {
    val lowerInput = input.toLowerCase()
    if (
      lowerInput.contains("basic") || lowerInput.contains(
        "beginner"
      ) || lowerInput.contains("foundation")
    ) {
      Some("1")
    } else if (
      lowerInput.contains("weighted") || lowerInput.contains(
        "advanced"
      ) || lowerInput.contains("strength") ||
      lowerInput.contains("endurance")
    ) {
      Some("2")
    } else {
      None
    }
  }

  def parseCalisthenicsFocus(input: String): Option[String] = {
    val lowerInput = input.toLowerCase()
    if (
      lowerInput.contains("strength") || lowerInput.contains(
        "power"
      ) || lowerInput.contains("heavy")
    ) {
      Some("1")
    } else if (
      lowerInput.contains("endurance") || lowerInput.contains(
        "volume"
      ) || lowerInput.contains("reps")
    ) {
      Some("2")
    } else {
      None
    }
  }

  def getTutorialLink(exercise: String): String = {
    val lowerExercise = exercise.toLowerCase()
    lowerExercise match {
      // Strength Training
      case ex if ex.contains("bench press") =>
        "https://www.youtube.com/watch?v=BYKScL2sgCs" // Jeff Nippard - Perfect Bench Press
      case ex
          if ex.contains("overhead press") || ex.contains("shoulder press") =>
        "https://www.youtube.com/watch?v=2yjwXTZQDDI" // Athlean-X - Perfect Overhead Press
      case ex if ex.contains("squat") =>
        "https://www.youtube.com/watch?v=SW_C1A-rejs" // Alan Thrall - How to Squat
      case ex if ex.contains("deadlift") =>
        "https://www.youtube.com/watch?v=op9kVnSso6Q" // Alan Thrall - How to Deadlift
      case ex if ex.contains("pull-up") || ex.contains("pullup") =>
        "https://www.youtube.com/watch?v=eGo4IYlbE5g" // Calisthenicmovement - Perfect Pull-up
      case ex if ex.contains("push-up") || ex.contains("pushup") =>
        "https://www.youtube.com/watch?v=IODxDxX7oi4" // Calisthenicmovement - Perfect Push-up
      case ex if ex.contains("dip") =>
        "https://www.youtube.com/watch?v=2z8JmcrW-As" // Calisthenicmovement - Perfect Dips
      case ex if ex.contains("row") =>
        "https://www.youtube.com/watch?v=G8l_8chR5BE" // Athlean-X - Perfect Barbell Row
      case ex if ex.contains("lunge") =>
        "https://www.youtube.com/watch?v=3JA0WqzqXeY" // Athlean-X - Perfect Lunges

      // Calisthenics
      case ex if ex.contains("muscle up") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // FitnessFAQs - Muscle Up Tutorial
      case ex if ex.contains("handstand") =>
        "https://www.youtube.com/watch?v=4vG5HktboZw" // FitnessFAQs - Handstand Tutorial
      case ex if ex.contains("planche") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // FitnessFAQs - Planche Tutorial
      case ex if ex.contains("front lever") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // FitnessFAQs - Front Lever Tutorial

      // Martial Arts
      case ex if ex.contains("shadow boxing") =>
        "https://www.youtube.com/watch?v=GJJvzw3jWig" // Precision Striking - Shadow Boxing
      case ex if ex.contains("heavy bag") =>
        "https://www.youtube.com/watch?v=1fD4ZtH3k8U" // Precision Striking - Heavy Bag Work
      case ex if ex.contains("drilling") || ex.contains("grappling") =>
        "https://www.youtube.com/watch?v=6l6UuT1p_9o" // BJJ Fanatics - Basic Drills
      case ex
          if ex.contains("weapon") || ex
            .contains("stick") || ex.contains("sword") =>
        "https://www.youtube.com/watch?v=8c3cl6YwH1E" // Inosanto Academy - Basic Stick Work

      // Additional Strength Exercises
      case ex if ex.contains("clean") || ex.contains("power clean") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Zach Telander - Power Clean Tutorial
      case ex if ex.contains("snatch") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Zach Telander - Snatch Tutorial
      case ex if ex.contains("jerk") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Zach Telander - Jerk Tutorial

      // Bodyweight Variations
      case ex if ex.contains("pike push") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // FitnessFAQs - Pike Push-up
      case ex if ex.contains("diamond push") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Calisthenicmovement - Diamond Push-up
      case ex if ex.contains("decline push") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Calisthenicmovement - Decline Push-up

      // Core Exercises
      case ex if ex.contains("plank") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Athlean-X - Perfect Plank
      case ex if ex.contains("crunch") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Athlean-X - Perfect Crunch
      case ex if ex.contains("leg raise") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Athlean-X - Perfect Leg Raises

      // Mobility
      case ex if ex.contains("mobility") || ex.contains("stretch") =>
        "https://www.youtube.com/watch?v=Yt1_6QjZ4WM" // Tom Merrick - Mobility Routine

      case _ => "" // Return empty string if no specific tutorial is found
    }
  }

  def displayExercise(exercise: Exercise): Unit = {
    println(s"- ${exercise.name}")
    println(s"  Description: ${exercise.description}")
    println(s"  Difficulty: ${exercise.difficulty}")
    println(s"  Equipment: ${exercise.equipment}")
    println(s"  Sets: ${exercise.sets}")
    println(s"  Reps: ${exercise.reps}")
    println(s"  Progression: ${exercise.progression}")
    println("  Variations:")
    if (exercise.variations.nonEmpty) {
      exercise.variations.foreach(variation =>
        println(s"    - ${variation.trim}")
      )
    } else {
      println("    No variations available")
    }

    // Get and display tutorial link
    val tutorialLink =
      if (exercise.tutorialLink.nonEmpty) exercise.tutorialLink
      else getTutorialLink(exercise.name)
    if (tutorialLink.nonEmpty) {
      println(s"\n  Tutorial Link: $tutorialLink")
      println("  Would you like to watch the tutorial? (yes/no)")
      val response = readLine().trim.toLowerCase()
      if (response == "yes" || response == "y") {
        println("Opening tutorial in your default browser...")
        // Note: In a real application, you would use java.awt.Desktop to open the URL
        // For now, we'll just display the link
      }
    }
  }

  def startWorkoutPlanner(): Unit = {
    workoutUsageCount += 1
    println("\n=== Workout Planner ===")
    println("What type of workout would you like to explore?")
    println(
      "You can type anything related to: bodybuilding, calisthenics, or martial arts"
    )

    val userInput = readLine().trim
    val workoutType = boundary[Option[String]] {
      parseWorkoutType(userInput).map(Some(_)).getOrElse {
        println("Sorry, I don't recognize that workout type. Please try again.")
        boundary.break(None)
      }
    }

    // Get the workout categories from CSV
    val categories = loadedWorkoutCategories

    val selectedCategory = workoutType match {
      case Some("bodybuilding") =>
        println("\nGreat choice! Here are the available splits:")
        val bodybuildingCategory = categories
          .find(_.name == "Bodybuilding")
          .getOrElse(
            categories.head // Fallback to first category if not found
          )
        bodybuildingCategory.splits.foreach(split =>
          println(s"- ${split.name}: ${split.description}")
        )
        println("\nType the name of the split you'd like to explore:")
        println("You can use full names or abbreviations:")
        println("- Push/Pull/Legs (PPL) or just 'PPL'")
        println("- Upper/Lower Split or just 'UL'")
        println("- Specialized Strength or just 'SS'")
        val splitChoice = readLine().trim.toLowerCase()
        bodybuildingCategory.splits.find(split =>
          split.name.toLowerCase.contains(splitChoice.toLowerCase) ||
            (split.name == "Push/Pull/Legs (PPL)" && splitChoice == "ppl") ||
            (split.name == "Upper/Lower Split" && splitChoice == "ul") ||
            (split.name == "Specialized Strength" && splitChoice == "ss")
        )
      case Some("calisthenics") =>
        println("\nGreat choice! Choose your calisthenics focus:")
        println("1. Basic Calisthenics")
        println("2. Weighted Calisthenics")
        println(
          "You can type your preference (e.g., 'basic', 'weighted', 'strength', 'endurance')"
        )

        val calisthenicsCategory = categories
          .find(_.name == "Calisthenics")
          .getOrElse(
            categories.head // Fallback to first category if not found
          )

        val calisthenicsInput = readLine().trim
        val calisthenicsChoice =
          parseCalisthenics(calisthenicsInput).getOrElse("1")
        calisthenicsChoice match {
          case "1" => calisthenicsCategory.splits.headOption
          case "2" =>
            println("\nChoose your focus for weighted calisthenics:")
            println("1. Strength Focus")
            println("2. Endurance Focus")
            println(
              "You can type your preference (e.g., 'strength', 'endurance', 'power', 'volume')"
            )
            val focusInput = readLine().trim
            val focusChoice = parseCalisthenicsFocus(focusInput).getOrElse("1")
            if (focusChoice == "1" || focusChoice == "2") {
              val selectedSplit = calisthenicsCategory.splits
                .find(_.name == "Weighted Calisthenics")
                .getOrElse(
                  calisthenicsCategory.splits.lastOption
                    .getOrElse(calisthenicsCategory.splits.head)
                )
              val filteredGroups = selectedSplit.muscleGroups.filter(group =>
                (focusChoice == "1" && group.name == "Strength Focus") ||
                  (focusChoice == "2" && group.name == "Endurance Focus")
              )
              Some(
                WorkoutSplit(
                  selectedSplit.name,
                  selectedSplit.description,
                  filteredGroups,
                  selectedSplit.frequency,
                  selectedSplit.notes,
                  selectedSplit.progressionGuidelines
                )
              )
            } else {
              println("Invalid choice. Showing all options.")
              calisthenicsCategory.splits.find(
                _.name == "Weighted Calisthenics"
              )
            }
          case _ =>
            println("Invalid choice. Showing basic calisthenics.")
            calisthenicsCategory.splits.headOption
        }
      case Some("martial arts") =>
        println("\nGreat choice! Choose your martial arts focus:")
        println("1. Striking Arts (Boxing, Kickboxing, Muay Thai)")
        println("2. Grappling Arts (Wrestling, BJJ, Sambo)")
        println("3. Weapons Training (FMA, Kenjutsu)")
        println(
          "You can type the specific art you're interested in (e.g., 'FMA', 'BJJ', 'Boxing')"
        )

        val martialArtsCategory = categories
          .find(_.name == "Martial Arts")
          .getOrElse(
            categories.head // Fallback to first category if not found
          )

        val martialArtsInput = readLine().trim
        val martialArtsChoice = parseMartialArts(martialArtsInput)
          .map {
            case "Striking Arts"    => "1"
            case "Grappling Arts"   => "2"
            case "Weapons Training" => "3"
          }
          .getOrElse("1")

        martialArtsChoice match {
          case "1" => martialArtsCategory.splits.find(_.name == "Striking Arts")
          case "2" =>
            martialArtsCategory.splits.find(_.name == "Grappling Arts")
          case "3" =>
            martialArtsCategory.splits.find(_.name == "Weapons Training")
          case _ =>
            println("Invalid choice. Showing striking arts.")
            martialArtsCategory.splits.headOption
        }
      case _ =>
        println("Sorry, I don't recognize that workout type. Please try again.")
        None
    }

    selectedCategory match {
      case Some(split) =>
        println(s"\n=== ${split.name} ===")
        println(s"Description: ${split.description}")
        println(s"Frequency: ${split.frequency}")
        println(s"Notes: ${split.notes}")
        println("\nMuscle Groups:")

        // Ensure proper display of each muscle group and its exercises
        split.muscleGroups.foreach { group =>
          println(s"\n${group.name}:")
          println("Exercises:")
          if (group.exercises.isEmpty) {
            println("  No exercises available for this muscle group")
          } else {
            // Ensure all exercises are displayed
            group.exercises.foreach { exercise =>
              println(s"\n- ${exercise.name}")
              println(s"  Description: ${exercise.description}")
              println(s"  Difficulty: ${exercise.difficulty}")
              println(s"  Equipment: ${exercise.equipment}")
              println(s"  Sets: ${exercise.sets}")
              println(s"  Reps: ${exercise.reps}")
              println(s"  Progression: ${exercise.progression}")
              println("  Variations:")
              // Explicitly print each variation to ensure they're all displayed
              exercise.variations.foreach { variation =>
                println(s"    - ${variation.trim}")
              }

              // Display tutorial link if available
              val tutorialLink =
                if (exercise.tutorialLink.nonEmpty) exercise.tutorialLink
                else getTutorialLink(exercise.name)
              if (tutorialLink.nonEmpty) {
                println(s"  Tutorial Link: $tutorialLink")
              }
            }
          }

          println("\nWarm-up Routine:")
          if (group.warmUps.isEmpty) {
            println("  No warm-up routine available for this muscle group")
          } else {
            // Ensure all warm-ups are displayed
            group.warmUps.foreach { warmUp =>
              println(
                s"- ${warmUp.name} (${warmUp.duration}): ${warmUp.description}"
              )
            }
          }
        }

        println("\nProgression Guidelines:")
        // Ensure complete display of progression guidelines by handling line breaks
        val guidelines = split.progressionGuidelines.trim
        if (guidelines.nonEmpty) {
          guidelines.split("\n").foreach(line => println(line.trim))
        } else {
          println("No specific progression guidelines available.")
        }

      case None =>
        println("Sorry, I couldn't find that split. Please try again.")
    }
  }
}
