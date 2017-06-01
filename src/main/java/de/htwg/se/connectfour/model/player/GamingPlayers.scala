package de.htwg.se.connectfour.model.player

import de.htwg.se.connectfour.model.CellType

case class GamingPlayers(firstPlayer: Player, secondPlayer: Player) {

  private var _isFirstGoing = true

  def isFirstGoing: Boolean = _isFirstGoing

  def currentPlayer: Player = if (_isFirstGoing) firstPlayer else secondPlayer

  def changePlayer(): Unit = {
    _isFirstGoing = !_isFirstGoing
  }

  def currentPlayerCellType(): CellType.Value = cellType(currentPlayer)

  private[this] def cellType(player: Player): CellType.Value = {
    if (player == firstPlayer) CellType.FIRST else CellType.SECOND
  }
}
