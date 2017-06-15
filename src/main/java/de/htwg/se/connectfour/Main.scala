package de.htwg.se.connectfour

import com.typesafe.scalalogging.LazyLogging
import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.mvc.model.player.{DumbBotPlayer, RealPlayer}
import de.htwg.se.connectfour.mvc.view.{GamingPlayers, Gui, Tui}

import scala.io.StdIn

object Main extends LazyLogging{
  def main(args: Array[String]): Unit = {

    val gridController = new GridController()
    val player1 = RealPlayer("Marek")
    val player2 = DumbBotPlayer(gridController)
    val players = GamingPlayers(player1, player2, gridController)

    startGame(gridController, players)
  }

  def startGame(gridController: GridController, players: GamingPlayers): Unit = {
    Console.print("Do you want to start gui (y/n): ")
    val input = StdIn.readLine() //n
    if (input.equalsIgnoreCase("y")) {
      logger.info("started GUI")
      Gui(gridController, players)
    } else {
      logger.info("started TUI")
      Tui(gridController, players)
    }
  }

}
