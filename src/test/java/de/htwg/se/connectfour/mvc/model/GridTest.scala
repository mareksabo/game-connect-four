package de.htwg.se.connectfour.mvc.model

import de.htwg.se.connectfour.types.CellType
import org.specs2.mutable.Specification

class GridTest extends Specification {

  "New Grid 3x2" should {

    val grid = new Grid(3, 2)

    val xCell = Cell(2, 0, CellType.SECOND)
    val oCell = Cell(0, 1, CellType.FIRST)
    grid.setupCell(xCell)
    grid.setupCell(oCell)

    "another new grid should be empty" in {
      val localGrid = new Grid(7, 6)
      for (i <- 0 to localGrid.MAX_COLUMN; j <- 0 to localGrid.MAX_ROW) {
        localGrid.cell(i, j).cellType must be_==(CellType.EMPTY)
      }
      ok
    }

    "have two non-empty cells" in {

      val shouldBeXCell = grid.cell(xCell.x, xCell.y)
      xCell must be_==(shouldBeXCell)

      val shouldBeOCell = grid.cell(oCell.x, oCell.y)
      oCell must be_==(shouldBeOCell)
    }

    "have pretty print" in {
      val expectedString =
        "\n" +
          "|   |   | X |\n" +
          "+---+---+---+\n" +
          "| O |   |   |\n" +
          "+---+---+---+\n" +
          "| 0 | 1 | 2 |\n"
      grid.toString() must beEqualTo(expectedString)
    }

    "have valid and invalid columns" in {
      grid.isColumnValid(grid.MAX_COLUMN + 1) must be_==(false)
      grid.isColumnValid(grid.MAX_COLUMN) must be_==(true)
    }

    "have valid and invalid rows" in {
      grid.isRowValid(grid.MAX_ROW + 1) must be_==(false)
      grid.isRowValid(grid.MAX_ROW) must be_==(true)
    }
  }
}
