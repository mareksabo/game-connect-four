package de.htwg.se.connectfour.logic

trait Command {
  def execute() : Unit
  def undo() : Unit
  def redo() : Unit
}
