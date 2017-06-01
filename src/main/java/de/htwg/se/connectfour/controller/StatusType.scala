package de.htwg.se.connectfour.controller

object StatusType extends Enumeration {
  type GameStatus = Value
  val STARTED, NEW, SET, FULL, UNDO, REDO, PLAYER1, PLAYER2, FINISHED = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    NEW -> "A new game was created",
    SET -> "A cell was set",
    FULL -> "Column is full",
    UNDO -> "One step reverted",
    REDO -> "One step reapplied",
    PLAYER1 -> "Player 1 is going",
    PLAYER2 -> "Player 2 is going",
    FINISHED -> "Game is finished"
  )

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }

}
