package de.htwg.se.connectfour.model

case class Cell(x: Int, y: Int, cellType: CellType.Value) {

  def this(x: Int, y: Int) {
    this(x, y, CellType.Empty)
  }

  override def toString: String  = {
    cellType match {
      case CellType.O => "O"
      case CellType.X => "X"
      case _ => " "
    }
  }

}
