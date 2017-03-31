package de.htwg.se.connectfour

import de.htwg.se.connectfour.logic.Game
import de.htwg.se.connectfour.model.{CellType, Player}

object Main {
  def main(args: Array[String]): Unit = {

    val player1 = new Player("Marek", CellType.O)
    val player2 = new Player("David", CellType.X)

    val game = new Game(player1, player2)
    game.startGame()

  }
}
