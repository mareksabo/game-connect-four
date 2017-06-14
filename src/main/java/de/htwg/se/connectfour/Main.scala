package de.htwg.se.connectfour

import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.mvc.model.player.{DumbBotPlayer, RealPlayer}
import de.htwg.se.connectfour.mvc.view.{GamingPlayers, Gui, Tui}

object Main {
  def main(args: Array[String]): Unit = {

    val gridController = new GridController()
    val player1 = RealPlayer("Marek")
    val player2 = DumbBotPlayer(gridController)
    val players = GamingPlayers(player1, player2, gridController)

    startGame(gridController, players)
  }

  def startGame(gridController: GridController, players: GamingPlayers): Unit = {
    Console.print("Do you want to start gui (y/n): ")
    val input = "n" //StdIn.readLine()
    if (input.equalsIgnoreCase("y")) {
      Gui(gridController, players)
    } else {
      Tui(gridController, players)
    }
  }

}
