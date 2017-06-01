package de.htwg.se.connectfour.model.player

trait Player {
  val name: String

  def playTurn(): Int

  def isReal: Boolean
}
