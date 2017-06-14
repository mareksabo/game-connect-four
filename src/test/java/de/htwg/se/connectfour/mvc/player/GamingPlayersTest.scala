package de.htwg.se.connectfour.mvc.player

import de.htwg.se.connectfour.mvc.model.CellType
import de.htwg.se.connectfour.mvc.model.player.{GamingPlayers, RealPlayer}
import org.specs2.mutable.Specification


class GamingPlayersTest extends Specification{
  "Gaming Players" should{
    val player1 = RealPlayer("David")
    val player2 = RealPlayer("Marek")
    val gamingPlayers = GamingPlayers(player1, player2)
    "be set right" in{
      gamingPlayers.isFirstGoing must be_==(true)
    }
    "let player1 start the game" in {
      gamingPlayers.currentPlayer must be_==(player1)
    }
    "let player2 make the 2nd turn"in {
      gamingPlayers.previousPlayer must be_==(player2)
    }
    "be able to retrieve Celltype fot current player" in {
      gamingPlayers.currentPlayerCellType() must be_==(CellType.FIRST)
    }
    "change current player" in {
      val localGamingPlayers = GamingPlayers(player1, player2)
      localGamingPlayers.changePlayer()
      localGamingPlayers.currentPlayer must be_==(player2)
    }
  }

}
