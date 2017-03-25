package de.htwg.se.connectfour.model

/**
  * Gaming grid.
  *
  * @param columns represents width of grid (x coordinate)
  * @param rows    represents height of grid (y coordinate)
  */
class Grid(val columns: Int, val rows: Int) {

  private val cells: Array[Array[Cell]] = Array.ofDim[Cell](columns, rows)

  /**
    * Constructor for square grid layout.
    *
    * @param size size of columns and rows
    */
  def this(size: Int) = this(size, size)

  for (row <- 0 until rows; column <- 0 until columns) {
    cells(column)(row) = new Cell(column, row)
  }

  def setupCell(cell: Cell): Unit = cells(cell.x)(cell.y) = cell
  def cell(x : Int, y : Int) : Cell = cells(x)(y)

  override def toString: String = s"Grid $columns x $rows"

  def nicePrint(): String = {
    var result = "\n"
    for (y <- 0 until rows) {
      for (x <- 0 until columns) {
        result += "| %1s ".format(cells(x)(y).symbol)
      }
      result += "|\n"
      result += "+---" * columns + "+\n"
    }
    result
  }

}
