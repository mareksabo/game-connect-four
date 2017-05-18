package de.htwg.se.connectfour.command

trait Command {
  def execute() : Unit
  def undo() : Unit
}
