package de.htwg.se.connectfour.mvc.view

import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.mvc.model.player.Player
import de.htwg.se.connectfour.types.CellType.CellType
import de.htwg.se.connectfour.types.EffectType.EffectType
import de.htwg.se.connectfour.types.{CellType, EffectType}

import scala.swing.Reactor

case class GamingPlayers(firstPlayer: Player, secondPlayer: Player, gridController: GridController) extends Reactor {

  listenTo(gridController)
  reactions += {
    case _: PlayerGridChanged => changePlayer()
  }

  private var _isFirstGoing = true

  def isFirstGoing: Boolean = _isFirstGoing

  def currentPlayer: Player = if (_isFirstGoing) firstPlayer else secondPlayer

  def previousPlayer: Player = if (!_isFirstGoing) firstPlayer else secondPlayer

  private def changePlayer(): Unit = {
    _isFirstGoing = !_isFirstGoing
  }

  def applyTurn(column: Int): EffectType = {
    if (gridController.gameFinished) return EffectType.FINISHED
    if (gridController.isColumnFull(column)) return EffectType.COLUMN_FULL
    gridController.addCell(column, currentPlayerCellType)
    gridController.isMoveWinning(column)
  }

  def currentPlayerCellType: CellType = cellType(currentPlayer)

  private[this] def cellType(player: Player): CellType = {
    if (player == firstPlayer) CellType.FIRST else CellType.SECOND
  }
}
