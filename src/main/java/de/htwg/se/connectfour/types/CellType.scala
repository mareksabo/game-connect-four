package de.htwg.se.connectfour.types

object CellType extends Enumeration {
  type CellType = Value
  val EMPTY = Value(" ")
  val FIRST = Value("O")
  val SECOND = Value("X")
}
