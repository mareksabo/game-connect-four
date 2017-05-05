package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.{ GamingPlayers, Grid }

class Game(val gamePlayers: GamingPlayers) {

  val grid = new Grid(7, 6)
  val logic = new MoveLogic(grid)
  val output = new PrintGame(grid, gamePlayers)
  val checkWinner = new CheckWinner(grid)

  def startGame(): Unit = {
    output.welcomePlayers()
    var usersMove = processTurn()
    while (!isMoveWinning(usersMove)) {
      gamePlayers.changePlayer()
      usersMove = processTurn()
    }
    output.congratulateWinner()
  }

  def processTurn(): Int = {
    output.displayGridWithMessage()
    loadValidMove()
  }

  def loadValidMove(): Int = {
    val currentPlayer = gamePlayers.currentPlayer
    var columnMove = currentPlayer.readInput()
    while (!logic.checkAndAddCell(columnMove, currentPlayer._cellType)) {
      output.wrongMove(columnMove)
      columnMove = currentPlayer.readInput()
    }
    columnMove
  }

  def isMoveWinning(columnMove: Int): Boolean = {
    val rowMove = logic.findLastRowPosition(columnMove)
    checkWinner.checkForWinner(grid.cell(columnMove, rowMove))
  }

}
