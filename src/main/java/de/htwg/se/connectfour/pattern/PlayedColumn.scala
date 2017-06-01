package de.htwg.se.connectfour.pattern

import de.htwg.se.connectfour.logic.MoveLogic

case class PlayedColumn(playedColumn: Int) extends Command {

  override def execute(): Unit = {
    // TODO: add logic from MoveLogic class
  }

  override def undo(): Unit = {
    new MoveLogic().removeSymbolFromColumn(playedColumn)
  }

  override def redo(): Unit = ???

  override def toString = s"$playedColumn "

}
