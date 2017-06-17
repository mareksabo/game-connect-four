package de.htwg.se.connectfour.mvc.controller.logic

import de.htwg.se.connectfour.mvc.model.Grid
import de.htwg.se.connectfour.mvc.model.types.CellType
import de.htwg.se.connectfour.mvc.model.types.CellType.CellType

case class PlayedCommand(col: Int, row: Int, cellType: CellType, grid: Grid) extends Command {

  override def execute(): Unit = {
    grid.set(col, row, cellType)
  }

  override def undo(): Unit = {
    grid.set(col, row, CellType.EMPTY)
  }

  override def redo(): Unit = execute()

}
