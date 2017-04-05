package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.{Cell, Grid}

/**
  * Created by Kuba on 05.04.2017.
  */
class CheckWinner {
  val ADJOININGCELLS: Int = 3

  def checkForWinner(grid: Grid, cell: Cell): Boolean =
    checkVertical(grid, cell) || checkHorizontal(grid, cell) || checkDiagonal(grid, cell)


  def checkVertical(grid: Grid, cell: Cell): Boolean = {
    var winCounter = 0
    for (i <- cell.y to cell.y + ADJOININGCELLS) {
      if (grid.isCellValid(cell.x, cell.y + ADJOININGCELLS) && grid.cell(cell.x, i).cellType == cell.cellType)
        winCounter += 1
      else
        winCounter = 0
    }
    winCounter == 4
  }

  def checkHorizontal(grid: Grid, cell: Cell): Boolean = {
    var winCounter = 0
    for (i <- cell.x - ADJOININGCELLS to cell.x + ADJOININGCELLS) {
      if (grid.isCellValid(i, cell.y) && (grid.cell(i, cell.y).cellType == cell.cellType))
        winCounter += 1
      else if (winCounter != 4)
        winCounter = 0
    }
    winCounter == 4
  }

  def checkDiagonal(grid: Grid, cell: Cell): Boolean = {
    leftUpToRightDown(grid, cell) || leftDownToRightUp(grid, cell)
  }

  def leftDownToRightUp(grid: Grid, cell: Cell): Boolean = {
    var winCounter = 0
    for (i <- -ADJOININGCELLS to ADJOININGCELLS) {
      {
        if (grid.isCellValid(cell.x - i, cell.y + i) && grid.cell(cell.x - i, cell.y + i).cellType == cell.cellType)
          winCounter += 1
        else if (winCounter != 4)
          winCounter = 0
      }
    }
    winCounter == 4
  }

  def leftUpToRightDown(grid: Grid, cell: Cell): Boolean = {
    var winCounter = 0
    for (i <- -ADJOININGCELLS to ADJOININGCELLS) {
      {
        if (grid.isCellValid(cell.x + i, cell.y + i) && grid.cell(cell.x + i, cell.y + i).cellType == cell.cellType) {
          winCounter += 1
        } else if (winCounter != 4)
          winCounter = 0
      }
    }
    winCounter == 4
  }

}
