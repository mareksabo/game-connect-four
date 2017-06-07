package de.htwg.se.connectfour.mvc.controller

object StatusType extends Enumeration {
  type GameStatus = Value
  val STARTED, NEW, SET, FULL, DRAW, UNDO, REDO, FINISHED = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    NEW -> "A new game was created",
    SET -> "A cell was set",
    FULL -> "Column is full",
    DRAW -> "Draw game",
    UNDO -> "One step reverted",
    REDO -> "One step reapplied",
    FINISHED -> "Game is finished"
  )

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }

}
