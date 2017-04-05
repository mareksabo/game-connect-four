package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.{Cell, Grid}

class CheckWinner {
  val NUMBER_OF_CELLS_TO_WIN = 4
  val CELLS_AROUND_TO_WIN: Int = NUMBER_OF_CELLS_TO_WIN - 1

  def checkForWinner(grid: Grid, cell: Cell): Boolean =
    checkVertical(grid, cell) || checkHorizontal(grid, cell) || checkDiagonal(grid, cell)


  def checkVertical(grid: Grid, cell: Cell): Boolean = {
    var winCounter = 0
    for (i <- cell.y to cell.y + CELLS_AROUND_TO_WIN) {
      if (grid.isCellValid(cell.x, cell.y + CELLS_AROUND_TO_WIN) && grid.cell(cell.x, i).cellType == cell.cellType)
        winCounter += 1
      else
        winCounter = 0
    }
    winCounter == NUMBER_OF_CELLS_TO_WIN
  }

  def checkHorizontal(grid: Grid, cell: Cell): Boolean = {
    var winCounter = 0
    for (i <- cell.x - CELLS_AROUND_TO_WIN to cell.x + CELLS_AROUND_TO_WIN) {
      if (grid.isCellValid(i, cell.y) && (grid.cell(i, cell.y).cellType == cell.cellType))
        winCounter += 1
      else if (winCounter != NUMBER_OF_CELLS_TO_WIN)
        winCounter = 0
    }
    winCounter == NUMBER_OF_CELLS_TO_WIN
  }

  def checkDiagonal(grid: Grid, cell: Cell): Boolean = {
    leftUpToRightDown(grid, cell) || leftDownToRightUp(grid, cell)
  }

  def leftDownToRightUp(grid: Grid, cell: Cell): Boolean = {
    var winCounter = 0
    for (i <- -CELLS_AROUND_TO_WIN to CELLS_AROUND_TO_WIN) {
      {
        if (grid.isCellValid(cell.x - i, cell.y + i) && grid.cell(cell.x - i, cell.y + i).cellType == cell.cellType)
          winCounter += 1
        else if (winCounter != NUMBER_OF_CELLS_TO_WIN)
          winCounter = 0
      }
    }
    winCounter == NUMBER_OF_CELLS_TO_WIN
  }

  def leftUpToRightDown(grid: Grid, cell: Cell): Boolean = {
    var winCounter = 0
    for (i <- -CELLS_AROUND_TO_WIN to CELLS_AROUND_TO_WIN) {
      {
        if (grid.isCellValid(cell.x + i, cell.y + i) && grid.cell(cell.x + i, cell.y + i).cellType == cell.cellType) {
          winCounter += 1
        } else if (winCounter != NUMBER_OF_CELLS_TO_WIN)
          winCounter = 0
      }
    }
    winCounter == NUMBER_OF_CELLS_TO_WIN
  }

}
