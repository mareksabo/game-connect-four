package de.htwg.se.connectfour.mvc.controller

import de.htwg.se.connectfour.logic.{CheckWinner, PlayedCommand, RevertManager, Validator}
import de.htwg.se.connectfour.mvc.model.{Cell, Grid}
import de.htwg.se.connectfour.mvc.view.{GridChanged, PlayerGridChanged, StatusBarChanged}
import de.htwg.se.connectfour.types.CellType.CellType
import de.htwg.se.connectfour.types.EffectType.EffectType
import de.htwg.se.connectfour.types.StatusType.GameStatus
import de.htwg.se.connectfour.types.{CellType, EffectType, StatusType}

import scala.swing.Publisher

case class GridController() extends Publisher {

  var grid = new Grid()
  var gameStatus: GameStatus = StatusType.NEW
  private val revertManager = new RevertManager
  private var checkWinner : CheckWinner = _
  private var validator : Validator = _

  def createEmptyGrid(columns: Int, rows: Int): Unit = {
    grid = new Grid(columns, rows)
    checkWinner = CheckWinner(grid)
    validator = Validator(grid)
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

  def statusText: String = StatusType.message(gameStatus)

  def addCell(column: Int, cellType: CellType): Unit = {
      revertManager.execute(PlayedCommand(column, validator.lowestEmptyRow(column), cellType, this))
      gameStatus = StatusType.SET
      publish(new PlayerGridChanged)
  }

  def isColumnFull(column: Int): Boolean = {
    gameStatus = StatusType.FULL
    publish(new StatusBarChanged)
    validator.isColumnFull(column)
  }

  def removeSymbolFromColumn(column: Int): Unit = {
    val lastFilledRow = validator.lastRowPosition(column)
    grid.setupCell(Cell(column, lastFilledRow, CellType.EMPTY))
  }

  def isMoveWinning(columnMove: Int): EffectType = {
    val rowMove = validator.lastRowPosition(columnMove)
    val hasWon = checkWinner.checkForWinner(columnMove, rowMove)
    if (hasWon) {
      gameStatus = StatusType.FINISHED
      publish(new StatusBarChanged)
      return EffectType.WON
    }
    if (grid.isFull) return EffectType.DRAW
    EffectType.NOTHING
  }

}
