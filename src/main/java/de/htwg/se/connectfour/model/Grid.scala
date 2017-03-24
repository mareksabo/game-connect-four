package de.htwg.se.connectfour.model

/**
  * Gaming grid.
  *
  * @param columns represents width of grid (x coordinate)
  * @param rows    represents height of grid (y coordinate)
  */
class Grid(columns: Int, rows: Int) {

  /**
    * Constructor for square grid layout.
    *
    * @param size size of columns and rows
    */
  def this(size: Int) {
    this(size, size)
  }

  var cells: Array[Array[Cell]] = Array.ofDim[Cell](columns, rows)

  for (row <- 0 until rows; column <- 0 until columns) {
    cells(column)(row) = new Cell(column, row)
  }

  override def toString: String = s"Grid $columns x $rows"

}
