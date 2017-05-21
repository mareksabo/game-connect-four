package de.htwg.se.connectfour.player

import de.htwg.se.connectfour.logic.MoveLogic
import de.htwg.se.connectfour.model.SingletonGrid

import scala.util.Random

class DumbBotPlayer extends Player {
  override val name: String = "Dumb bot"
  val r = Random

  override def playTurn(): Int = {
    var robotsColumn: Int = -1
    if (SingletonGrid.getGrid.isFull) return -1
    do {
    robotsColumn = r.nextInt(SingletonGrid.getGrid.columns)
    } while (!MoveLogic.isColumnValidAndNotFull(robotsColumn))
    robotsColumn
  }

  override def isReal: Boolean = false
}
