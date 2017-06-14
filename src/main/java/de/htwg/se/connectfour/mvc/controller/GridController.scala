package de.htwg.se.connectfour.mvc.controller

import de.htwg.se.connectfour.logic.{CheckWinner, PlayedCommand, RevertManager, Validator}
import de.htwg.se.connectfour.mvc.model.{Cell, Grid}
import de.htwg.se.connectfour.mvc.view.{GridChanged, PlayerGridChanged, StatusBarChanged}
import de.htwg.se.connectfour.types.CellType.CellType
import de.htwg.se.connectfour.types.EffectType.EffectType
import de.htwg.se.connectfour.types.StatusType.GameStatus
import de.htwg.se.connectfour.types.{CellType, EffectType, StatusType}

import scala.swing.Publisher

case class GridController(columns: Int, rows: Int) extends Publisher {

  var grid : Grid = _
  private var gameStatus: GameStatus = StatusType.NEW
  private val revertManager = new RevertManager
  private var checkWinner : CheckWinner = _
  private var validator : Validator = _
  private var _gameFinished = false

  createEmptyGrid(columns, rows)

  def this() = this(7, 6)

  def createEmptyGrid(columns: Int, rows: Int): Unit = {
    grid = new Grid(columns, rows)
    _gameFinished = false
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

  def statusText: String = StatusType.message(gameStatus)

  def addCell(column: Int, cellType: CellType): Unit = {
      revertManager.execute(PlayedCommand(column, validator.lowestEmptyRow(column), cellType, grid))
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
      _gameFinished = true
      return EffectType.WON
    } else if (grid.isFull) {
      gameStatus = StatusType.DRAW
      return EffectType.DRAW
    }
    EffectType.NOTHING
  }

  def gameFinished: Boolean = _gameFinished

}
