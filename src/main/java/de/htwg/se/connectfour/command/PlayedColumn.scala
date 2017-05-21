package de.htwg.se.connectfour.command

import de.htwg.se.connectfour.logic.MoveLogic

case class PlayedColumn(playedColumn: Int) extends Command {

  override def execute(): Unit = {
    // TODO: add logic from MoveLogic class
  }

  override def undo(): Unit = {
    MoveLogic.removeSymbolFromColumn(playedColumn)
  }

  override def toString = s"$playedColumn "
}
