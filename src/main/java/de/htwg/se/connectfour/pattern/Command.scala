package de.htwg.se.connectfour.pattern

trait Command {
  def execute() : Unit
  def undo() : Unit
  def redo() : Unit
}
