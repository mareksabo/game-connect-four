package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.command.{Invoker, PlayedColumn}
import de.htwg.se.connectfour.model.{Cell, CellType, SingletonGrid, Grid}

object MoveLogic {

  val grid: Grid = SingletonGrid.getGrid

  def isFullAndAddCell(column: Int, cellType: CellType.Value): Boolean = {
    if (isColumnValidAndNotFull(column)) {
      Invoker.invoke(PlayedColumn(column))
      addSymbolToColumn(column, cellType)
      return false
    }
    true
  }

  def isColumnValidAndNotFull(column: Int): Boolean = {
    grid.isColumnValid(column) && !isColumnFull(column)
  }

  private[this] def isColumnFull(column: Int): Boolean =
    findLowestEmptyRow(column) < 0

  private[this] def findLowestEmptyRow(column: Int): Int = {
    var currentRow = grid.MAX_ROW
    while (grid.isCellValidAndNotEmpty(column, currentRow)) {
      currentRow -= 1
    }
    currentRow
  }
  def removeSymbolFromColumn(column: Int): Unit = {
    val lastFilledRow = findLastRowPosition(column)
    grid.setupCell(new Cell(column, lastFilledRow, CellType.Empty))
  }

  private[this] def addSymbolToColumn(column: Int, cellType: CellType.Value): Unit = {
    val freeRow = findLowestEmptyRow(column)
    grid.setupCell(new Cell(column, freeRow, cellType))
  }

  def findLastRowPosition(column: Int): Int = {
    findLowestEmptyRow(column) + 1
  }
}