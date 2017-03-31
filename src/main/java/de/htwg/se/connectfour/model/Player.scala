package de.htwg.se.connectfour.model

import scala.io.StdIn


class Player(val name: String, val _cellType: CellType.Value) {

  def getInput: Int = {
    print("Player " + name + ", write column number: ")
    StdIn.readInt()
  }

}
