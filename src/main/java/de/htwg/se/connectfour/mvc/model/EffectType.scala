package de.htwg.se.connectfour.mvc.model

object EffectType extends Enumeration {
  type EffectType = Value
  val WON, DRAW, NOTHING, COLUMN_FULL = Value
}
