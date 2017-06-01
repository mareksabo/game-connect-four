package de.htwg.se.connectfour.controller

import de.htwg.se.connectfour.controller.StatusType.GameStatus
import de.htwg.se.connectfour.model.{Cell, Grid}
import de.htwg.se.connectfour.pattern.{PlayedColumn, RevertManager}

import scala.swing.Publisher

class GridController(var grid: Grid) extends Publisher {

  var gameStatus: GameStatus = _
  private val undoManager = new RevertManager


  def createEmptyGrid(columns: Int, rows: Int): Unit = {
    grid = new Grid(columns, rows)
    gameStatus = StatusType.NEW
    publish(new CellChanged)
  }

  def set(row: Int, col: Int, value: Int): Unit = {
    undoManager.execute(PlayedColumn(col))
    gameStatus = StatusType.SET
    publish(new CellChanged)
  }


  def undo(): Unit = {
    undoManager.undo()
    gameStatus = StatusType.UNDO
    publish(new CellChanged)
  }

  def redo(): Unit = {
    undoManager.redo()
    gameStatus = StatusType.REDO
    publish(new CellChanged)
  }

  def cell(row: Int, col: Int): Cell = grid.cell(row, col)

  def gridColumnSize: Int = grid.MAX_COLUMN
  def gridRowSize: Int = grid.MAX_ROW

  def statusText: String = StatusType.message(gameStatus)

}
