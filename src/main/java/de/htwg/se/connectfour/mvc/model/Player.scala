package de.htwg.se.connectfour.mvc.model

import de.htwg.se.connectfour.types.CellType

import scala.io.StdIn

case class Player(name: String, _cellType: CellType.Value) {

  def readInput(): Int = {
    print("Player " + name + ", write column number: ")
    StdIn.readInt()
  }

}
