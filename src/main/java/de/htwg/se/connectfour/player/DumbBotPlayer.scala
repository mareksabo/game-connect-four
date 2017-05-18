package de.htwg.se.connectfour.player

import de.htwg.se.connectfour.model.Grid

import scala.util.Random

class DumbBotPlayer extends Player {
  override val name: String = "Dumb bot"
  val r = Random

  override def playTurn(grid: Grid): Int = {
    r.nextInt(grid.columns)
  }
}
