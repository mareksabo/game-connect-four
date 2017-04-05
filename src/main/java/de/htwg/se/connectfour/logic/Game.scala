package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.{Grid, Player}

class Game(val firstPlayer: Player, val secondPlayer: Player) {

  val grid = new Grid(7, 6)
  val logic = new MoveLogic(grid)
  val checkWinner = new CheckWinner(grid)
  var isFirstGoing = true
  var end = false

  def startGame(): Unit = {
    println("-- Welcome to the game --")
    while (!somebodyWon) {
      displayGrid()
      processMove()
      changePlayer()
    }
  }

  def displayGrid(): Unit = {
    println(grid.niceString())

    val number = if (isFirstGoing) "First" else "Second"
    println(number + " player is going.")
  }

  def somebodyWon: Boolean = {
    if (end) {
      displayGrid()
      changePlayer()
      println(currentPlayer.name + " won")
      true
    }
    else {
      false
    }
  }

  def processMove(): Unit = {
    var columnMove = currentPlayer.getInput
    while (!logic.addSymbolToColumn(columnMove, currentPlayer._cellType)) {
      println("Column " + columnMove + " is invalid. Please try again.")
      columnMove = currentPlayer.getInput
    }
    if (checkWinner.checkForWinner(grid.cell(columnMove, logic.getLastRowPosition(columnMove))))
      end = true
  }

  def currentPlayer: Player = if (isFirstGoing) firstPlayer else secondPlayer

  def changePlayer(): Unit = {
    isFirstGoing = !isFirstGoing
  }

}
