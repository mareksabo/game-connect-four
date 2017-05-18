package de.htwg.se.connectfour.command

object Invoker {
  private var history: Seq[Command] = Seq.empty

  def invoke(command: Command): Unit = {
    command.execute()
    history :+= command
  }

  def undo() : Unit = {
    if (history.nonEmpty) {
      val last = history.last
      history = history.init
      last.undo()
    }
  }
}
