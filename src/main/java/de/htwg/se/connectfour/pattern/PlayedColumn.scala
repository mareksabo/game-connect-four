package de.htwg.se.connectfour.pattern

import de.htwg.se.connectfour.controller.GridController
import de.htwg.se.connectfour.model.CellType
import de.htwg.se.connectfour.model.CellType.CellType

case class PlayedColumn(col: Int, row: Int, cellType: CellType, gridController: GridController) extends Command {

  override def execute(): Unit = {
    gridController.grid.set(col, row, cellType)
  }

  override def undo(): Unit = {
    gridController.grid.set(col, row, CellType.EMPTY)
  }

  override def redo(): Unit = execute()

}
