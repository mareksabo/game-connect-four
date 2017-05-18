package de.htwg.se.connectfour.model

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import de.htwg.se.connectfour.player.RealPlayer
import org.specs2.mutable.Specification

class RealPlayerTest extends Specification {

  args(skipAll = true) // TODO: not working on travis, input is null

  "A new player Ralf" should {

    val player = new RealPlayer("Ralf")

    "method parses a number which player writes" in {
      val number = 5
      val input = number + sys.props("line.separator")
      System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)))

      val readInput = player.readInput()
      number must be_==(readInput)
    }

  }
}
