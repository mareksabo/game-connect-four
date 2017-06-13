package de.htwg.se.connectfour.logic

import java.io.{ByteArrayOutputStream, PrintStream}

import de.htwg.se.connectfour.mvc.model.Grid
import de.htwg.se.connectfour.mvc.model.player.{GamingPlayers, RealPlayer}
import org.specs2.mutable.Specification


class PrintGameTest extends Specification{


  "Print Game" should {
    val player1 = RealPlayer("David")
    val player2 = RealPlayer("Marek")
    val gamingPlayers = GamingPlayers(player1, player2)
    val grid = new Grid(3, 2)
    val printGame = new PrintGame(gamingPlayers, grid)
    val output = new ByteArrayOutputStream
    val print = new PrintStream(output)
    val stdOut = System.out
    "welcome Players" in {
      System.setOut(print)
      printGame.welcomePlayers()
      val message = output.toString.trim()
      System.out.flush()
      output.reset()
      System.setOut(stdOut)
      message must be_==("-- Welcome to the game --")
    }
    "display grid with message" in {
      val expectedString = "|   |   |   |\n" +
                           "+---+---+---+\n" +
                           "|   |   |   |\n" +
                           "+---+---+---+\n" +
                           "| 0 | 1 | 2 |\n\n" +
                           "First player is going."
      val expectedList = expectedString.split("\\r?\\n")

      System.setOut(print)
      printGame.displayGridWithMessage()
      val message = output.toString.trim().split("\\r?\\n")
      System.out.flush()
      output.reset()
      System.setOut(stdOut)
      message must be_==(expectedList)
    }

    /*
    "congratulate Winner" in {
      val expectedString = "Congratulations!\n\n" +
                           "|   |   |   |\n" +
                           "+---+---+---+\n" +
                           "|   |   |   |\n" +
                           "+---+---+---+\n" +
                           "| 0 | 1 | 2 |\n\n" +
                           "David, you won."
      val expectedList = expectedString.split("\\r?\\n")

      System.setOut(print)
      printGame.congratulateWinner()
      val message = output.toString.trim()//.split("\\r?\\n")
      System.out.flush()
      output.reset()
      System.setOut(stdOut)
      message must be_==("")
    }
    */
  }
}
