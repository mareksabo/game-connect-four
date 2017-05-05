package de.htwg.se.connectfour

import de.htwg.se.connectfour.logic.Game
import de.htwg.se.connectfour.model.{ CellType, GamingPlayers, Player }

object Main {
  def main(args: Array[String]): Unit = {

    val player1 = new Player("Marek", CellType.O)
    val player2 = new Player("David", CellType.X)

    val players = new GamingPlayers(player1, player2)
    val game = new Game(players)
    game.startGame()

  }
}
