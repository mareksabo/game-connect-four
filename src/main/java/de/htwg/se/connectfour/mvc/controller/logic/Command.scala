package de.htwg.se.connectfour.mvc.controller.logic

trait Command {
  def execute(): Unit

  def undo(): Unit

  def redo(): Unit
}
