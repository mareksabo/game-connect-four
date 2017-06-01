package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.controller.GridController
import de.htwg.se.connectfour.model.player.GamingPlayers

class Game(val gridController: GridController, val gamePlayers: GamingPlayers) {

  val output = new PrintGame(gamePlayers, gridController.grid)

  def startGame(): Unit = {
    output.welcomePlayers()
    playGame()
    undoMoves()
    playGame()
  }

  def playGame(): Unit = {
    var usersMove = processTurn()
    while (!new CheckWinner(gridController).isMoveWinning(usersMove)) {
      gamePlayers.changePlayer()
      usersMove = processTurn()
    }
    output.congratulateWinner()
  }

  def undoMoves(): Unit = {
    for (_ <- 1 to output.getUndoMovesCount()) {
      gridController.undo()
    }
  }

  def processTurn(): Int = {
    output.displayGridWithMessage()
    loadValidMove()
  }

  def loadValidMove(): Int = {
    val currentPlayer = gamePlayers.currentPlayer
    var columnMove = currentPlayer.playTurn()
    while (gridController.isFullAndAddCell(columnMove, gamePlayers.currentPlayerCellType())) {
      output.wrongMove(columnMove)
      columnMove = currentPlayer.playTurn()
    }
    columnMove
  }

}
