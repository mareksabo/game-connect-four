package de.htwg.se.connectfour.player

import scala.io.StdIn

class RealPlayer(val name: String) extends Player {

  override def playTurn(): Int = {
    readInput()
  }

  def readInput(): Int = {
    print("Player " + name + ", write column number: ")
    StdIn.readInt()
  }

  override def isReal: Boolean = true
}
