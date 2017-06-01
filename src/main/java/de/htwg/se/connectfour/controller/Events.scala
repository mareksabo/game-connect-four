package de.htwg.se.connectfour.controller

import scala.swing.event.Event

class CellChanged extends Event
class PlayerChanged extends Event
case class GridSizeChanged(newSize: Int) extends Event