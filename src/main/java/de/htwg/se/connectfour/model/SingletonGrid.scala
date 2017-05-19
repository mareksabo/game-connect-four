package de.htwg.se.connectfour.model

object SingletonGrid {

  val DEFAULT_ROWS = 7
  val DEFAULT_COLUMNS = 6

  private val grid = new Grid(DEFAULT_ROWS, DEFAULT_COLUMNS)

  def getGrid: Grid = grid
}
