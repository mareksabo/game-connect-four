package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.{Cell, CellType, Grid}

class MoveLogic(val grid: Grid) {
  var somebodyWon = false

  def addSymbolToColumn(column: Int, cellType: CellType.Value): Boolean = {
    if (!grid.isColumnValid(column) || isColumnFull(column)) return false
    val freeRow = findLowestEmptyRow(column)
    grid.setupCell(new Cell(column, freeRow, cellType))
    true
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

  def getLastRowPosition(column: Int): Int ={
    findLowestEmptyRow(column)+ 1
  }
}
