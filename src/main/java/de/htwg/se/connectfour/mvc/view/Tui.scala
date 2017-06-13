package de.htwg.se.connectfour.mvc.view

import de.htwg.se.connectfour.mvc.controller.{GridController, StatusType}
import de.htwg.se.connectfour.mvc.model.player.GamingPlayers

import scala.io.StdIn
import scala.swing.Reactor

case class Tui(gridController: GridController, gamingPlayers: GamingPlayers) extends Reactor {

  listenTo(gridController)
  reactions += {
    case _: PlayerGridChanged => printTui()
    case _: GridChanged => printTui()
  }


  processInputLine("new 7 6")
  while (true) {
    processInputLine(StdIn.readLine())
  }

  def processInputLine(input: String): Unit = {
    val parsedInput = input.split(" ")
    println(parsedInput(0).trim)
    try {
      parsedInput(0) match {
        case "quit" => System.exit(0);
        case "new" => gridController.createEmptyGrid(parsedInput.apply(1).toInt, parsedInput.apply(2).toInt)
        case "undo" => gridController.undo()
        case "redo" => gridController.redo()
        case "help" => println("Commands are: help, new <num> <num>, quit, undo, redo, <num> <num>, <num>")

        case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: col :: Nil => println(gridController.cell(row, col))
          case col :: Nil => gridController.addCell(col, gamingPlayers.currentPlayerCellType())
            println(gridController.gameStatus)
        }
      }
    } catch {
      case _ : Exception => println("Unknown command")
    }
  }

  def printTui(): Unit = {
    println(gridController.grid)
    println(StatusType.message(gridController.gameStatus))
    println("Current player: " + gamingPlayers.currentPlayer)
  }


}
