package de.htwg.se.connectfour.mvc.controller.logic

class RevertManager {

  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def execute(command: Command): Unit = {
    undoStack = command :: undoStack
    redoStack = Nil
    command.execute()
  }

  def undo(): Boolean = {
    if (undoStack.isEmpty) return false

    val head :: stack = undoStack
    head.undo()
    undoStack = stack
    redoStack = head :: redoStack
    true
  }

  def redo(): Boolean = {
    if (redoStack.isEmpty) return false

    val head :: stack = redoStack
    head.redo()
    redoStack = stack
    undoStack = head :: undoStack
    true
  }

}
