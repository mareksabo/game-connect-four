package de.htwg.se.connectfour

import de.htwg.se.connectfour.logic.Game
import de.htwg.se.connectfour.player.{DumbBotPlayer, GamingPlayers, RealPlayer}
import de.htwg.se.connectfour.swing.Gui

import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {

    val player1 = new RealPlayer("Marek")
    val player2 = new DumbBotPlayer()
    val players = new GamingPlayers(player1, player2)

    startGame(players)
  }

  def startGame(players: GamingPlayers): Unit = {
    Console.print("Do you want to start gui (y/n): ")
    val input = StdIn.readLine()
    if (input.equalsIgnoreCase("y")) {
      Gui.init(players)
    } else {
      new Game(players).startGame()
    }
  }

}
