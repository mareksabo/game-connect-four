package de.htwg.se.connectfour.mvc.model.player

trait Player {
  val name: String

  def playTurn(): Int

  def isReal: Boolean
}
