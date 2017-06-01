package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.Grid
import de.htwg.se.connectfour.model.player.GamingPlayers

import scala.io.StdIn

class PrintGame(gamePlayers: GamingPlayers, grid: Grid) {

  def welcomePlayers(): Unit = {
    println("-- Welcome to the game --")
  }

  def displayGridWithMessage(): Unit = {
    displayGrid()
    displayGoingMessage()
  }

  def displayGrid(): Unit = {
    println(grid)
  }

  private def displayGoingMessage(): Unit = {
    val number = if (gamePlayers.isFirstGoing) "First" else "Second"
    println(number + " player is going.")
  }

  def congratulateWinner(): Unit = {
    displayGrid()
    println("Congratulations!")
    println(gamePlayers.currentPlayer.name + ", you won.")
  }

  def wrongMove(columnMove: Int): Unit = {
    println("Column " + columnMove + " is invalid. Please try again.")
  }

  def getUndoMovesCount(): Int = {
    print("How many moves should I undo: ")
    StdIn.readInt()
  }

}
