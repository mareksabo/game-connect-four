package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.command.Invoker
import de.htwg.se.connectfour.model.{Grid, SingletonGrid}
import de.htwg.se.connectfour.player.GamingPlayers

class Game(val gamePlayers: GamingPlayers) {

  val grid: Grid = SingletonGrid.getGrid
  val logic = new MoveLogic()
  val output = new PrintGame(gamePlayers)
  val checkWinner = new CheckWinner()

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
    var columnMove = currentPlayer.playTurn()
    while (!logic.checkAndAddCell(columnMove,  gamePlayers.currentPlayerCellType())) {
      output.wrongMove(columnMove)
      columnMove = currentPlayer.playTurn()
    }
    columnMove
  }

  def isMoveWinning(columnMove: Int): Boolean = {
    val rowMove = logic.findLastRowPosition(columnMove)
    checkWinner.checkForWinner(grid.cell(columnMove, rowMove))
  }

}
