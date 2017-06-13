package de.htwg.se.connectfour.mvc.player

import java.io.ByteArrayInputStream

import de.htwg.se.connectfour.mvc.model.player.RealPlayer
import org.specs2.mutable.Specification

/**
 * Created by Kuba on 13.06.2017.
 */
class RealPlayerTest extends Specification {
  "A real player" should {
    val player = RealPlayer("testPlayer")
    val input = System.in
    "be able to read input" in {
      val inputStream = new ByteArrayInputStream("1".getBytes())
      System.setIn(inputStream)
      val column = player.playTurn()
      System.setIn(input)
      column must be_==(1)
    }
    "be displayed as real player" in {
      player.isReal must be_==(true)
    }
  }

}
