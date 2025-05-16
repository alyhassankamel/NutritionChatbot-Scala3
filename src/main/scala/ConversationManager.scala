import scala.collection.mutable.ListBuffer

object ConversationManager {
  private case class ConversationState(
    history: ListBuffer[String] = ListBuffer(),
    var lastCommand: Option[String] = None
  )

  private var state = ConversationState()

  def logInteraction(input: String, response: String): Unit = {
    state.history += s"User: $input"
    state.history += s"Bot: $response"
    state.lastCommand = Some(input)
  }

  def clearHistory(): Unit = {
    state = ConversationState()
  }

  def getHistory: List[String] = state.history.toList

  def getLastCommand: Option[String] = state.lastCommand

  def viewHistory(): String = {
    if (state.history.isEmpty) {
      "No conversation history available."
    } else {
      state.history.mkString("\n")
    }
  }
} 