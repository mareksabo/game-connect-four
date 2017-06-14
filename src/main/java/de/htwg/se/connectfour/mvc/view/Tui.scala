package de.htwg.se.connectfour.mvc.view

import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.types.EffectType

import scala.io.StdIn
import scala.swing.Reactor

case class Tui(gridController: GridController, gamingPlayers: GamingPlayers) extends Reactor {

  listenTo(gridController)
  reactions += {
    case _: PlayerGridChanged => showGridWithMessage()
    case _: GridChanged => showGridWithMessage()
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
    println("Game is finished.")
    processInputLine(StdIn.readLine())
  }

  def processInputLine(input: String): Unit = {
    val parsedInput = input.split(" ")
    try {
      parsedInput(0) match {
        case "quit" => quit()
        case "new" => gridController.createEmptyGrid(parsedInput.apply(1).toInt, parsedInput.apply(2).toInt)
        case "undo" => gridController.undo()
        case "redo" => gridController.redo()
        case "show" => showGridWithMessage()
        case "help" => println("Commands are: help, new <num> <num>, quit, undo, redo, show, <num> <num>, <num>")

        case _ => if (!gridController.gameFinished) {
          input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
            case row :: col :: Nil => println(gridController.cell(row, col))
            case col :: Nil =>
              val winType = gamingPlayers.applyTurn(col)
              winType match {
                case EffectType.WON => showWon()
                case EffectType.DRAW => showDraw()
                case EffectType.COLUMN_FULL => showGridWithMessage()
                case EffectType.FINISHED =>
                case EffectType.NOTHING =>
              }
          }
        }
      }
    } catch {
      case _: Exception => println("Unknown command")
    }
  }

  def showGridWithMessage(): Unit = {
    showGrid()
    showMessage()
  }

  def showMessage(): Unit = {
    println(gridController.statusText)
    println("Current player: " + gamingPlayers.currentPlayer + " (" + gamingPlayers.currentPlayerCellType + ")")
  }

  def showWon(): Unit = {
    println("Congratulations!")
    println("Player " + gamingPlayers.previousPlayer.name + " has won.")
  }

  def showDraw(): Unit = {
    println("Draw, nobody won.")
  }

  def showGrid(): Unit = println(gridController.grid)

  def quit(): Nothing = sys.exit(0)
}
