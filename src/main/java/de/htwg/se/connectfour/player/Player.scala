package de.htwg.se.connectfour.player

import de.htwg.se.connectfour.model.Grid

trait Player {
  val name: String

  def playTurn(grid: Grid): Int
}
