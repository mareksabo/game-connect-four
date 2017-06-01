package de.htwg.se.connectfour.model

import org.specs2.mutable.Specification

class CellTest extends Specification {

  "A new Cell" should {
    val cell = new Cell(1, 2)

    "be empty if not set" in {
      cell.cellType must be_==(CellType.EMPTY)
    }

    "the empty symbol must be a space" in {
      cell.toString must be_==(" ")
    }


  }
}
