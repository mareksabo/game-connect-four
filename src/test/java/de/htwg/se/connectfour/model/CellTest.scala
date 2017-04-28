package de.htwg.se.connectfour.model

import org.specs2.mutable.Specification

class CellTest extends Specification {

  "A new Cell" should {
    val cell = new Cell(1, 2)

    "be empty if not set" in {
      cell.cellType must be_==(CellType.Empty)
    }

    "the empty symbol must be a space" in {
      cell.symbol must be_==(' ')
    }

    "generates a string of the form [4, 3] cellType=X" in {
      new Cell(4, 3, CellType.X).toString must be_==("[4, 3] cellType=X")
    }

  }
}
