package de.htwg.se.connectfour.command

import de.htwg.se.connectfour.logic.MoveLogic

case class PlayedColumn(moveLogic: MoveLogic, playedColumn: Int) extends Command {

  override def execute(): Unit = {
    // TODO: add logic from MoveLogic class
  }

  override def undo(): Unit = {
    moveLogic.removeSymbolFromColumn(playedColumn)
  }

  override def toString = s"$playedColumn "
}
