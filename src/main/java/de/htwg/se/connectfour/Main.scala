package de.htwg.se.connectfour

import com.google.inject.Guice
import de.htwg.se.connectfour.mvc.controller.Controller
import de.htwg.se.connectfour.mvc.model.player.{RandomBotPlayer, RealPlayer}
import com.typesafe.scalalogging.LazyLogging
import de.htwg.se.connectfour.mvc.view.{GamingPlayers, Gui, Tui}

import scala.io.StdIn

object Main extends LazyLogging{
  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new ConnectFourModule)
    val controller = injector.getInstance(classOf[Controller])
    val player1 = RealPlayer("Marek")
    val player2 = RandomBotPlayer(controller)
    val players = new GamingPlayers(player1, player2, controller)

    startGame(controller, players)
  }

  def startGame(controller: Controller, players: GamingPlayers): Unit = {
    Console.print("Do you want to start gui (y/n): ")
    val input = StdIn.readLine()
    if (input.equalsIgnoreCase("y")) {
      logger.info("started GUI")
      Gui(controller, players)
    } else {
      logger.info("started TUI")
      Tui(controller, players)
    }
  }

}
