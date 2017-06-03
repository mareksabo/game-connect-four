package de.htwg.se.connectfour.mvc.model

case class Cell(x: Int, y: Int, cellType: CellType.Value) {

  def this(x: Int, y: Int) {
    this(x, y, CellType.EMPTY)
  }

  override def toString: String  = {
    cellType match {
      case CellType.FIRST => "O"
      case CellType.SECOND => "X"
      case _ => " "
    }
  }

}
