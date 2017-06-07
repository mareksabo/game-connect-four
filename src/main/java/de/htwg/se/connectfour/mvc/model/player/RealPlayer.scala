package de.htwg.se.connectfour.mvc.model.player

import scala.io.StdIn

case class RealPlayer(name: String) extends Player {

  override def playTurn(): Int = {
    readInput()
  }

  def readInput(): Int = {
    print("Player " + name + ", write column number: ")
    StdIn.readInt()
  }

  override def isReal: Boolean = true
}
