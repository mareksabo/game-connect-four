package de.htwg.se.connectfour.mvc.model.player

import de.htwg.se.connectfour.mvc.model.CellType
import de.htwg.se.connectfour.mvc.model.CellType.CellType

case class GamingPlayers(firstPlayer: Player, secondPlayer: Player) { // add grid controller

  private var _isFirstGoing = true

  def isFirstGoing: Boolean = _isFirstGoing

  def currentPlayer: Player = if (_isFirstGoing) firstPlayer else secondPlayer

  def previousPlayer: Player = if (!_isFirstGoing) firstPlayer else secondPlayer

  def changePlayer(): Unit = {
    _isFirstGoing = !_isFirstGoing
  }

  def currentPlayerCellType(): CellType.Value = cellType(currentPlayer)

  private[this] def cellType(player: Player): CellType = {
    if (player == firstPlayer) CellType.FIRST else CellType.SECOND
  }
}
