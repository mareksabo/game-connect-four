package de.htwg.se.connectfour.mvc.model


import java.io.{ByteArrayInputStream, InputStream}

import org.specs2.mutable.Specification

class PlayerTest extends Specification{
    "A new Player" should {
        val player = Player("playerName", CellType.FIRST)
      "the read column number must be 1" in {
        val inputStream = new ByteArrayInputStream("1".getBytes())
        System.setIn(inputStream)
        player.readInput() must be_==(1)
      }
    }
}
