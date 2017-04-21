package de.htwg.se.connectfour.model

class Player(val name: String, val _cellType: CellType.Value) {

  def readInput(): Int = {
    print("Player " + name + ", write column number: ")
    Console.readInt()
  }

}
