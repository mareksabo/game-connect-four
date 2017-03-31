package de.htwg.se.connectfour.model

/**
  * Gaming 2D grid with starting coordinates in left upper corner.
  *
  * Let's have grid size 3x2, X on position [2,0] and O on [1,1]:
  *
  * |   |   | X |
  * +---+---+---+
  * |   | O |   |
  * +---+---+---+
  *
  * @param columns represents width of grid (x coordinate, from 0 to columns - 1)
  * @param rows    represents height of grid (y coordinate, from 0 to rows - 1)
  *
  */
class Grid(val columns: Int, val rows: Int) {

  val MAX_COLUMN: Int = columns - 1
  val MAX_ROW: Int = rows - 1

  private val cells: Array[Array[Cell]] = Array.ofDim[Cell](columns, rows)

  for (row <- 0 until rows; column <- 0 until columns) {
    cells(column)(row) = new Cell(column, row)
  }

  def setupCell(cell: Cell): Unit = cells(cell.x)(cell.y) = cell

  def isCellValidAndNotEmpty(column: Int, row: Int): Boolean =
    isCellValid(column, row) &&
      cell(column, row).cellType != CellType.Empty

  def cell(x: Int, y: Int): Cell = cells(x)(y)

  def isCellValid(column: Int, row: Int): Boolean =
    isColumnValid(column) && isRowValid(row)

  def isColumnValid(column: Int): Boolean =
    0 <= column && column <= MAX_COLUMN

  def isRowValid(row: Int): Boolean =
    0 <= row && row <= MAX_ROW

  def niceString(): String = {
    val gridInString = buildGridInString()
    addColumnNumbers(gridInString)
  }

  private[this] def buildGridInString(): String = {
    var result = "\n"
    for (y <- 0 until rows) {
      for (x <- 0 until columns) {
        result += "|%2s ".format(cells(x)(y).symbol)
      }
      result += "|\n"
      result += "+---" * columns + "+\n"
    }
    result
  }

  private[this] def addColumnNumbers(partialString: String): String = {
    var result = partialString
    for (x <- 0 until columns) {
      result += "|%2s ".format(x)
    }
    result += "|\n"
    result
  }

}
