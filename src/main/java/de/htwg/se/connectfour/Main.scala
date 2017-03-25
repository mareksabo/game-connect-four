package de.htwg.se.connectfour

import de.htwg.se.connectfour.model.{Cell, CellType, Grid, Player}

object Main {
  def main(args: Array[String]): Unit = {
    val standardGrid = new Grid(7, 6)
    standardGrid.setupCell(new Cell(0, 5, CellType.X))
    standardGrid.setupCell(new Cell(6, 5, CellType.O))

    val player1 = new Player("Marek", CellType.O, true)
    val player2 = new Player("David", CellType.X, false)

    println(standardGrid.nicePrint())
  }
}
