package de.htwg.se.connectfour.controller

import de.htwg.se.connectfour.controller.StatusType.GameStatus
import de.htwg.se.connectfour.logic.CheckWinner
import de.htwg.se.connectfour.model.CellType.CellType
import de.htwg.se.connectfour.model.{Cell, CellType, Grid}
import de.htwg.se.connectfour.pattern.{PlayedColumn, RevertManager}

import scala.swing.Publisher

class GridController(var grid: Grid) extends Publisher {

  var gameStatus: GameStatus = _
  private val revertManager = new RevertManager
  private val checkWinner = new CheckWinner(this)

  def createEmptyGrid(columns: Int, rows: Int): Unit = {
    grid = new Grid(columns, rows)
    gameStatus = StatusType.NEW
    publish(new CellChanged)
  }

  def undo(): Unit = {
    revertManager.undo()
    gameStatus = StatusType.UNDO
    publish(new CellChanged)
  }

  def redo(): Unit = {
    revertManager.redo()
    gameStatus = StatusType.REDO
    publish(new CellChanged)
  }

  def cell(col: Int, row: Int): Cell = grid.cell(col, row)

  def columnSize: Int = grid.columns

  def rowSize: Int = grid.rows
  def isFull: Boolean = grid.isFull
  def statusText: String = StatusType.message(gameStatus)

  def isFullAndAddCell(column: Int, cellType: CellType): Boolean = {
    if (isColumnValidAndNotFull(column)) {
      revertManager.execute(PlayedColumn(column, findLowestEmptyRow(column), cellType, this))
      gameStatus = StatusType.SET
      return false
    }
    gameStatus = StatusType.FULL
    true
  }

  def isColumnValidAndNotFull(column: Int): Boolean = {
    grid.isColumnValid(column) && !isColumnFull(column)
  }

  private[this] def isColumnFull(column: Int): Boolean =
    findLowestEmptyRow(column) < 0

  private[this] def findLowestEmptyRow(column: Int): Int = {
    var currentRow = grid.MAX_ROW
    while (isCellValidAndNotEmpty(column, currentRow)) {
      currentRow -= 1
    }
    currentRow
  }

  def isCellValidAndNotEmpty(column: Int, row: Int): Boolean =
    isCellValid(column, row) &&
      cell(column, row).cellType != CellType.EMPTY

  def isCellValid(column: Int, row: Int): Boolean =
    grid.isColumnValid(column) && grid.isRowValid(row)

  def removeSymbolFromColumn(column: Int): Unit = {
    val lastFilledRow = findLastRowPosition(column)
    grid.setupCell(Cell(column, lastFilledRow, CellType.EMPTY))
  }


  def set(col: Int, row: Int, value: CellType): Unit = {
    revertManager.execute(PlayedColumn(col, row, value, this))
    gameStatus = StatusType.SET
    publish(new CellChanged)
  }

  def isMoveWinning(columnMove: Int): Boolean = {
    val rowMove = findLastRowPosition(columnMove)
    checkWinner.checkForWinner(columnMove, rowMove)
  }

  def findLastRowPosition(column: Int): Int = {
    findLowestEmptyRow(column) + 1
  }
}
