package de.htwg.se.connectfour.mvc.view

import de.htwg.se.connectfour.mvc.controller.GridController

import scala.io.StdIn
import scala.swing.Reactor

/**
  * Represents text user interface.
  * Whenever player plays turn,
  */
case class Tui(gridController: GridController, gamingPlayers: GamingPlayers) extends Reactor {

  listenTo(gridController)
  reactions += {
    case _: PlayerGridChanged => showGridWithMessage()
    case _: GridChanged => showGrid()
    case _: PlayerWon => showWon()
    case _: Draw => showDraw()
    case _: FilledColumn => showGridWithMessage()
    case _: InvalidMove => showPlayAgain()
  }

  processInputLine("help")
  processInputLine("new 7 6")

  while (true) {
    while (!gridController.gameFinished) {
      val currentPlayer = gamingPlayers.currentPlayer
      if (currentPlayer.isReal) {
        processInputLine(StdIn.readLine())
      } else {
        processInputLine(currentPlayer.playTurn().toString)
      }
    }
    processInputLine(StdIn.readLine())
  }

  def processInputLine(input: String): Unit = {
    val parsedInput = input.split(" ")
    try {
      parsedInput(0) match {
        case "quit" => quit()
        case "new" => gridController.createEmptyGrid(parsedInput.apply(1).toInt, parsedInput.apply(2).toInt)
        case "undo" => 1 to parsedInput(1).toInt foreach { _ => gridController.undo() }
        case "redo" => 1 to parsedInput(1).toInt foreach { _ => gridController.redo() }
        case "show" => showGridWithMessage()
        case "help" => println("Commands are: help, new <num> <num>, quit, undo <num>, redo <num>, show, <num> <num>, <num>")

        case _ => if (!gridController.gameFinished) {
          gamingPlayers.applyTurn(parsedInput(0).toInt)
        } else showFinished()
      }
    } catch {
      case e: Exception =>
        println("Unknown command")
        throw e
    }
  }

  def showGridWithMessage(): Unit = {
    showGrid()
    showMessage()
  }

  def showMessage(): Unit = {
    println(gridController.statusText)
    println("Player " + gamingPlayers.currentPlayer + " (" + gamingPlayers.currentPlayerCellType + ")"
      + " played turn.")
  }

  def showWon(): Unit = {
    println("Congratulations!")
    println("Player " + gamingPlayers.previousPlayer.name + " has won.")
    showFinished()
  }

  def showDraw(): Unit = {
    println("Draw, nobody won.")
    showFinished()
  }

  def showFinished(): Unit = {
    println("Game is finished.")
  }

  def showGrid(): Unit = println(gridController.grid)

  def showPlayAgain(): Unit = {
    showGrid()
    println("Player " + gamingPlayers.currentPlayer + " (" + gamingPlayers.currentPlayerCellType + "), "
      + "please play again.")
  }

  def quit(): Nothing = sys.exit(0)
}
