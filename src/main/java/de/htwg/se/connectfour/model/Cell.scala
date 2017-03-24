package de.htwg.se.connectfour.model

class Cell(x: Int, y: Int, cellType: CellType.Value) {

  def this(x: Int, y: Int) {
    this(x, y, CellType.Empty)
  }

  override def toString: String = s"[$x, $y] cellType=$cellType"
}
