package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.mvc.controller.GridController
import de.htwg.se.connectfour.types.CellType
import de.htwg.se.connectfour.types.CellType.CellType

case class PlayedCommand(col: Int, row: Int, cellType: CellType, gridController: GridController) extends Command {

  override def execute(): Unit = {
    gridController.grid.set(col, row, cellType)
  }

  override def undo(): Unit = {
    gridController.grid.set(col, row, CellType.EMPTY)
  }

  override def redo(): Unit = execute()

}
