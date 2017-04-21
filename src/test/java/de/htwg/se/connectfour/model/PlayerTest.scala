package de.htwg.se.connectfour.model

import java.io.ByteArrayInputStream

import org.specs2.mutable.Specification

class PlayerTest extends Specification {

  "A new player Ralf" should {

    val player = new Player("Ralf", CellType.O)

    "method parses a number which player writes" in {
      val number = 5
      val input = number + "\n"
      System.setIn(new ByteArrayInputStream(input.getBytes))

      val readInput = player.readInput()
      number must be_==(readInput)
    }

  }
}
