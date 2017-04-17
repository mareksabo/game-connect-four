package de.htwg.se.connectfour.model

class GamingPlayers(val firstPlayer: Player, val secondPlayer: Player) {

  private var _isFirstGoing = true
  def isFirstGoing: Boolean = _isFirstGoing

  def currentPlayer: Player = if (_isFirstGoing) firstPlayer else secondPlayer

  def changePlayer(): Unit = {
    _isFirstGoing = !_isFirstGoing
  }
}
