package de.htwg.se.connectfour.mvc.controller

import de.htwg.se.connectfour.mvc.model.{Cell, CellType, Grid}
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
      val localGridController = new GridController(grid)
      localGridController.isCellValidAndNotEmpty(oCell.x, oCell.y) must be_==(true)
      localGridController.isCellValidAndNotEmpty(xCell.x, xCell.y) must be_==(true)
      localGridController.isCellValidAndNotEmpty(1, 1) must be_==(false)
      localGridController.isCellValidAndNotEmpty(grid.MAX_COLUMN + 1, 0) must be_==(false)
      localGridController.isCellValidAndNotEmpty(0, grid.MAX_ROW + 1) must be_==(false)
    }

    "have empty grid" in {
      val localGridController = new GridController(grid)
      localGridController.gameStatus = StatusType.SET
      localGridController.createEmptyGrid(3, 2)
      localGridController.gameStatus must be_==(StatusType.NEW)
    }

    "have undone move" in {
      val localGrid = new Grid
      val localGridController = new GridController(localGrid)
      localGridController.undo()
      localGridController.gameStatus must be_==(StatusType.UNDO)
    }

    "have redone move" in {
      val localGrid = new Grid
      val localGridController = new GridController(localGrid)
      localGridController.redo()
      localGridController.gameStatus must be_==(StatusType.REDO)
    }

    "return rowSize 2" in {
      gridController.rowSize must be_==(2)
    }

    "return columnSize 3" in {
      gridController.columnSize must be_==(3)
    }

    "have full grid" in {
      val localGrid = new Grid
      for (row <- 0 until localGrid.rows; column <- 0 until localGrid.columns) {
        localGrid.set(column, row, CellType.SECOND)
      }
      val localGridController = new GridController(localGrid)
      localGridController.isFull must be_==(true)
    }

    "have statusText" in {
      val localGrid = new Grid
      val localGridController = new GridController(localGrid)
      localGridController.statusText must be_==("A new game was created")
    }

    "have added cell" in {
      val localGrid = new Grid
      val localGridController = new GridController(localGrid)
      localGridController.addCell(0, CellType.FIRST)
      localGridController.gameStatus must be_==(StatusType.SET)
    }

    "have valid and not full column" in {
      gridController.isColumnValidAndNotFull(0) must be_==(true)
    }

    "have removed symbol from column" in {
      val localGridController = new GridController(grid)
      localGridController.set(0, 0, CellType.FIRST)
      localGridController.removeSymbolFromColumn(0)
      localGridController.grid.cell(0, 0).cellType must be_==(CellType.EMPTY)
    }

    "have won with this move (horizontal)" in {
      val localGrid = new Grid(7, 6)
      val localGridController = new GridController(localGrid)
      for (row <- 0 until 4) {
        localGridController.set(row, 5, CellType.FIRST)
      }
      localGridController.isMoveWinning(3) must be_==(true)
    }

    "have also won with this move (vertical)" in {
      val localGrid = new Grid(7, 6)
      val localGridController = new GridController(localGrid)
      for (column <- 2 until 6) {
        localGridController.set(0, column, CellType.FIRST)
      }
      localGridController.isMoveWinning(0) must be_==(true)
    }

    "have also won with this move (diagonal)" in {
      val localGrid = new Grid(7, 6)
      val localGridController = new GridController(localGrid)
      var counter = 0
      for (column <- 2 until 6) {
        localGridController.set(counter, column, CellType.FIRST)
        counter+=1
      }
      localGridController.isMoveWinning(3) must be_==(true)
    }

    "have also won with this move (diagonal)" in {
      val localGrid = new Grid(7, 6)
      val localGridController = new GridController(localGrid)
      var counter = 3
      for (column <- 2 until 6) {
        localGridController.set(counter, column, CellType.FIRST)
        counter-=1
      }
      localGridController.isMoveWinning(0) must be_==(true)
    }

  }
}
