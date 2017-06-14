package de.htwg.se.connectfour.mvc.player

import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.mvc.model.Grid
import de.htwg.se.connectfour.mvc.model.player.DumbBotPlayer
import org.specs2.mutable.Specification


class DumbBotPlayerTest extends Specification{

  "Dumb bot player" should{
    val grid = new Grid(2,3)
    val gridController = new GridController(grid)
    val dumbBotPlayer = DumbBotPlayer(gridController)
    "be displayed as non-real player" in{
      dumbBotPlayer.isReal must be_==(false)
    }
  }
}
