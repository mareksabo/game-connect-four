package de.htwg.se.connectfour.player

import de.htwg.se.connectfour.model.Grid

import scala.io.StdIn

class RealPlayer(val name: String) extends Player {

  override def playTurn(grid: Grid): Int = {
    readInput()
  }

  def readInput(): Int = {
    print("Player " + name + ", write column number: ")
    StdIn.readInt()
  }
}
