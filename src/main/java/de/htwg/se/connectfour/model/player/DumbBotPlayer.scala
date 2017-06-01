package de.htwg.se.connectfour.model.player

import de.htwg.se.connectfour.controller.GridController

import scala.util.Random

case class DumbBotPlayer(gridController: GridController) extends Player {
  override val name: String = "Dumb bot"
  val r = Random

  override def playTurn(): Int = {
    var robotsColumn: Int = -1
    if (gridController.isFull) return -1
    do {
    robotsColumn = r.nextInt(gridController.columnSize)
    } while (!gridController.isColumnValidAndNotFull(robotsColumn))
    robotsColumn
  }

  override def isReal: Boolean = false
}
