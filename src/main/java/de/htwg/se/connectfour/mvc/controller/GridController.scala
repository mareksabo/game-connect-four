package de.htwg.se.connectfour.mvc.controller

import de.htwg.se.connectfour.logic.CheckWinner
import de.htwg.se.connectfour.mvc.controller.StatusType.GameStatus
import de.htwg.se.connectfour.mvc.model.CellType.CellType
import de.htwg.se.connectfour.mvc.model.EffectType.EffectType
import de.htwg.se.connectfour.mvc.model.{Cell, CellType, EffectType, Grid}
import de.htwg.se.connectfour.mvc.view.{GridChanged, PlayerGridChanged, StatusBarChanged}
import de.htwg.se.connectfour.pattern.{PlayedColumn, RevertManager}

import scala.swing.Publisher

class GridController(var grid: Grid) extends Publisher {

  var gameStatus: GameStatus = StatusType.NEW
  private val revertManager = new RevertManager
  private val checkWinner = new CheckWinner(this)

  def createEmptyGrid(columns: Int, rows: Int): Unit = {
    grid = new Grid(columns, rows)
    gameStatus = StatusType.NEW
    publish(new GridChanged)
  }

  def undo(): Unit = {
    revertManager.undo()
    gameStatus = StatusType.UNDO
    publish(new PlayerGridChanged)
  }

  def redo(): Unit = {
    revertManager.redo()
    gameStatus = StatusType.REDO
    publish(new PlayerGridChanged)
  }

  def cell(col: Int, row: Int): Cell = grid.cell(col, row)

  def columnSize: Int = grid.columns

  def rowSize: Int = grid.rows

  def isFull: Boolean = {
    val isFull = grid.isFull
    if (isFull) gameStatus = StatusType.DRAW
    isFull
  }

  def statusText: String = StatusType.message(gameStatus)

  def addCell(column: Int, cellType: CellType): Unit = {
      revertManager.execute(PlayedColumn(column, findLowestEmptyRow(column), cellType, this))
      gameStatus = StatusType.SET
      publish(new PlayerGridChanged)

  }

  def isColumnValidAndNotFull(column: Int): Boolean = {
    grid.isColumnValid(column) && !isColumnFull(column)
  }

  def isColumnFull(column: Int): Boolean = {
    gameStatus = StatusType.FULL
    publish(new StatusBarChanged)
    findLowestEmptyRow(column) < 0
  }

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
    publish(new PlayerGridChanged)
  }

  def isMoveWinning(columnMove: Int): EffectType = {
    val rowMove = findLastRowPosition(columnMove)
    val hasWon = checkWinner.checkForWinner(columnMove, rowMove)
    if (hasWon) {
      gameStatus = StatusType.FINISHED
      publish(new StatusBarChanged)
      return EffectType.WON
    }
    if (isFull) return EffectType.DRAW
    EffectType.NOTHING
  }

  def findLastRowPosition(column: Int): Int = {
    findLowestEmptyRow(column) + 1
  }
}
