package de.htwg.se.connectfour.pattern

import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.mvc.model.{ CellType, Grid }
import org.specs2.mutable.Specification

class PlayedColumnTest extends Specification {
  "A played column" should {
    val grid = new Grid(3, 2)
    val gridController = new GridController(grid)
    val playedColumn = PlayedColumn(0, 0, CellType.FIRST, gridController)
    "undo turn should be empty" in {
      playedColumn.undo()
      gridController.grid.cell(0, 0).cellType must be_==(CellType.EMPTY)
    }
    "redo turn should be first" in {
      val localPlayedColumn = PlayedColumn(1, 1, CellType.FIRST, gridController)
      localPlayedColumn.undo()
      localPlayedColumn.redo()
      gridController.grid.cell(1, 1).cellType must be_==(CellType.FIRST)
    }
  }

}
