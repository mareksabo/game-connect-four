package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.mvc.model.Grid
import de.htwg.se.connectfour.mvc.model.player.{ GamingPlayers, RealPlayer }
import org.specs2.mutable.Specification

class GameTest extends Specification {

  "New GridController of grid 3x2" should {
    val player1 = RealPlayer("player1")
    val player2 = RealPlayer("player2")
    val grid = new Grid
    val gridController = new GridController(grid)
    val players = GamingPlayers(player1, player2)
    val game = new Game(gridController, players)
    "have initialized output" in {
      game.gridController must be_==(gridController)
    }


  }
}
