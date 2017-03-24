package de.htwg.se.connectfour

import de.htwg.se.connectfour.model.{Cell, CellType, Grid}

object Hello {
  def main(args: Array[String]): Unit = {
    val smallGrid = new Grid(2, 4)
    val oneCell = new Cell(1, 2, CellType.O)

    println(smallGrid)
    println(oneCell)
  }
}
