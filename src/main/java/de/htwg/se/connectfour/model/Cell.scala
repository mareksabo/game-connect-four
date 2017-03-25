package de.htwg.se.connectfour.model

class Cell(val x: Int, val y: Int, val cellType: CellType.Value) {

  def this(x: Int, y: Int) {
    this(x, y, CellType.Empty)
  }

  def symbol: Char = {
    cellType match {
      case CellType.O => 'O'
      case CellType.X => 'X'
      case _ => ' '
    }
  }

  override def toString: String = s"[$x, $y] cellType=$cellType"
}
