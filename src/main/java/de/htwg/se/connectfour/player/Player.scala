package de.htwg.se.connectfour.player

trait Player {
  val name: String

  def playTurn(): Int
}
