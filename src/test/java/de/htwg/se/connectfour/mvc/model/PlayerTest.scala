package de.htwg.se.connectfour.mvc.model

import java.io.ByteArrayInputStream

import org.specs2.mutable.Specification

class PlayerTest extends Specification {
  "A new Player" should {
    val player = Player("playerName", CellType.FIRST)
    val input = System.in
    "the read column number must be 1" in {
      val inputStream = new ByteArrayInputStream("1".getBytes())
      System.setIn(inputStream)
      val column = player.readInput()
      System.setIn(input)
      column must be_==(1)
    }
  }
}
