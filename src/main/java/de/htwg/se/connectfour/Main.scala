package de.htwg.se.connectfour

import de.htwg.se.connectfour.logic.Game
import de.htwg.se.connectfour.model._
import de.htwg.se.connectfour.player.{DumbBotPlayer, GamingPlayers, RealPlayer}

object Main {
  def main(args: Array[String]): Unit = {

    val player1 = new RealPlayer("Marek")
    val player2 = new DumbBotPlayer()

    val players = new GamingPlayers(player1, player2)
    val game = new Game(players)
    game.startGame()

  }
}
