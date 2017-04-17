package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.{Cell, CellType, Grid}

class MoveLogic(val grid: Grid) {

  def checkAndAddCell(column: Int, cellType: CellType.Value): Boolean = {
    if (isColumnValidAndNotFull(column)) {
      addSymbolToColumn(column, cellType)
      return true
    }
    false
  }

  def isColumnValidAndNotFull(column: Int) : Boolean = {
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

  private[this] def addSymbolToColumn(column: Int, cellType: CellType.Value): Unit = {
    val freeRow = findLowestEmptyRow(column)
    grid.setupCell(new Cell(column, freeRow, cellType))
  }

  def findLastRowPosition(column: Int): Int = {
    findLowestEmptyRow(column) + 1
  }
}
