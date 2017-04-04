package de.htwg.se.connectfour.logic

import de.htwg.se.connectfour.model.{Cell, CellType, Grid}

class MoveLogic(val grid: Grid) {
  var somebodyWon = false

  def addSymbolToColumn(column: Int, cellType: CellType.Value): Boolean = {
    if (!grid.isColumnValid(column) || isColumnFull(column)) return false
    val freeRow = findLowestEmptyRow(column)
    grid.setupCell(new Cell(column, freeRow, cellType))
    checkForWinner(column,freeRow,cellType)
    true
  }

  def checkForWinner(column: Int, row: Int, cellType: CellType.Value): Unit = {
    if(checkVertical(column, row, cellType) || checkHorizontal(column, row, cellType) || checkDiagonal(column, row, cellType))
      {
        somebodyWon = true
      }



  }
  def checkVertical(column: Int, row: Int, cellType: CellType.Value): Boolean = {
    var winCounter = 0
    if(grid.isCellValid(column, row + 3)) {
      for (i <- row to row + 3) {
        if (grid.cell(column, i).cellType.equals(cellType)) {
          winCounter += 1
        }
        else{
          winCounter == 0
        }
      }
    }
    if (winCounter == 4) {
        true
      } else
        false
  }

  def checkHorizontal(column: Int, row: Int, cellType: CellType.Value): Boolean = {
    var winCounter = 0
    for (i <- column - 3  to column + 3) {
      if(grid.isCellValid(i, row))
        {
          if (grid.cell(i, row).cellType.equals(cellType)){
            winCounter += 1
          }else {
            winCounter == 0
          }
        }

    }
    if (winCounter == 4) {
      true
    } else
      false
  }

  def checkDiagonal(column: Int, row: Int, cellType: CellType.Value): Boolean = {
    if(upToDown(column, row, cellType) || downToUp(column, row, cellType))
      true
    else
      false


  }
  def downToUp(column: Int, row: Int, cellType: CellType.Value): Boolean = {
    var winCounter = 0
    for (i <- -3 to 3){
      if(grid.isCellValid(column-i, row+i))
      {
        if (grid.cell(column-i, row+i).cellType.equals(cellType)){
          winCounter += 1
        }else {
          winCounter == 0
        }

      }
    }
    if (winCounter == 4) {
      true
    } else
      false
  }

  def upToDown(column: Int, row: Int, cellType: CellType.Value): Boolean = {
    var winCounter = 0
    for (i <- -3 to 3){
      if(grid.isCellValid(column+i, row+i))
      {
        if (grid.cell(column+i, row+i).cellType.equals(cellType)){
          winCounter += 1
        }else {
          winCounter == 0
        }

      }
    }
    if (winCounter == 4) {
      true
    } else
      false
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
}
