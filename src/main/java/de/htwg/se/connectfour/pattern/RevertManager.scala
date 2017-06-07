package de.htwg.se.connectfour.pattern

class RevertManager {

  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def execute(command: Command): Unit = {
    undoStack = command :: undoStack
    redoStack = Nil
    command.execute()
  }

  def undo(): Unit = {
    if (undoStack.isEmpty) return

    val head :: stack = undoStack
    head.undo()
    undoStack = stack
    redoStack = head :: redoStack
  }

  def redo(): Unit = {
    if (redoStack.isEmpty) return

    val head :: stack = redoStack
    head.redo()
    redoStack = stack
    undoStack = head :: undoStack
  }

}
