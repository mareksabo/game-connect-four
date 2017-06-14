package de.htwg.se.connectfour.mvc.controller

import de.htwg.se.connectfour.mvc.model.{Cell, Grid}
import de.htwg.se.connectfour.types.CellType
import org.specs2.mutable.Specification

class GridControllerTest extends Specification {

  "New GridController of grid 3x2" should {

    val grid = new Grid(3, 2)

    val xCell = Cell(2, 0, CellType.SECOND)
    val oCell = Cell(0, 1, CellType.FIRST)
    grid.setupCell(xCell)
    grid.setupCell(oCell)

    val gridController = new GridController(grid)

    "have valid and invalid cells" in {
      gridController.isCellValid(grid.MAX_COLUMN + 1, grid.MAX_ROW + 1) must be_==(false)
      gridController.isCellValid(0, 0) must be_==(true)
    }

    "have valid nonempty cells" in {
      gridController.isCellValidAndNotEmpty(oCell.x, oCell.y) must be_==(true)
      gridController.isCellValidAndNotEmpty(xCell.x, xCell.y) must be_==(true)
      gridController.isCellValidAndNotEmpty(1, 1) must be_==(false)
      gridController.isCellValidAndNotEmpty(grid.MAX_COLUMN + 1, 0) must be_==(false)
      gridController.isCellValidAndNotEmpty(0, grid.MAX_ROW + 1) must be_==(false)
    }

  }
}
