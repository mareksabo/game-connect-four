package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.command.Invoker
import de.htwg.se.connectfour.model.Grid
import de.htwg.se.connectfour.player.GamingPlayers

class Game(val gamePlayers: GamingPlayers) {

  val grid = new Grid(7, 6)
  val logic = new MoveLogic(grid)
  val output = new PrintGame(grid, gamePlayers)
  val checkWinner = new CheckWinner(grid)

  def startGame(): Unit = {
    output.welcomePlayers()
    playGame()
    undoMoves()
    playGame()
  }

  def playGame(): Unit = {
    var usersMove = processTurn()
    while (!isMoveWinning(usersMove)) {
      gamePlayers.changePlayer()
      usersMove = processTurn()
    }
    output.congratulateWinner()
  }

  def undoMoves(): Unit = {
    for (_ <- 1 to output.getUndoMovesCount()) {
      Invoker.undo()
    }
  }

  def processTurn(): Int = {
    output.displayGridWithMessage()
    loadValidMove()
  }

  def loadValidMove(): Int = {
    val currentPlayer = gamePlayers.currentPlayer
    var columnMove = currentPlayer.playTurn(grid)
    while (!logic.checkAndAddCell(columnMove,  gamePlayers.currentPlayerCellType())) {
      output.wrongMove(columnMove)
      columnMove = currentPlayer.playTurn(grid)
    }
    columnMove
  }

  def isMoveWinning(columnMove: Int): Boolean = {
    val rowMove = logic.findLastRowPosition(columnMove)
    checkWinner.checkForWinner(grid.cell(columnMove, rowMove))
  }

}
