package de.htwg.se.connectfour

import com.google.inject.Guice
import de.htwg.se.connectfour.mvc.controller.Controller
import de.htwg.se.connectfour.mvc.model.player.{RandomBotPlayer, RealPlayer}
import de.htwg.se.connectfour.mvc.view.{GamingPlayers, Gui, Tui}

object Main {
  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new ConnectFourModule)
    val controller = injector.getInstance(classOf[Controller])
    val player1 = RealPlayer("Marek")
    val player2 = RandomBotPlayer(controller)
    val players = GamingPlayers(player1, player2, controller)

    startGame(controller, players)
  }

  def startGame(controller: Controller, players: GamingPlayers): Unit = {
    print("Do you want to start gui (y/n): ")
    val input = "n" //StdIn.readLine()
    if (input.equalsIgnoreCase("y")) {
      Gui(controller, players)
    } else {
      Tui(controller, players)
    }
  }

}
