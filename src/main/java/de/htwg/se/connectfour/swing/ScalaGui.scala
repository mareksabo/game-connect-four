package de.htwg.se.connectfour.swing


import java.awt.Color

import de.htwg.se.connectfour.logic.{CheckWinner, MoveLogic}
import de.htwg.se.connectfour.model.{CellType, Grid, SingletonGrid}
import de.htwg.se.connectfour.player.GamingPlayers

import scala.swing._

/**
  * Created by Kuba on 31.05.2017.
  */
class ScalaGui {
  val WIDTH = 700
  val HEIGHT = 500

  val grid: Grid = SingletonGrid.getGrid
  val rows: Int = SingletonGrid.DEFAULT_ROWS
  val columns: Int = SingletonGrid.DEFAULT_COLUMNS

  var slots: Array[Array[Label]] = Array.ofDim[Label](rows, columns)
  val buttons: Array[Button] = new Array[Button](rows)
  var panel = new GridPanel(rows, columns + 1)

  var mainFrame = new MainFrame()

  var gamingPlayers: GamingPlayers = _

  def init(gamingPlayers: GamingPlayers): Unit = {
    this.gamingPlayers = gamingPlayers
  }

  setup()

  def setup(): Unit = {
    setupPanel()
    setupMenuBar()
    setupMainFrame()
  }

  def setupMainFrame(): Unit = {
    mainFrame.title = "connect-four"
    mainFrame.preferredSize = new Dimension(WIDTH, HEIGHT)
    mainFrame.contents = panel
    mainFrame.visible = true
    mainFrame.centerOnScreen()
  }


  def setupPanel(): Unit = {
    setupButtons()
    setupSlots()
    panel = new GridPanel(rows, columns + 1) {
      for (i <- 0 until rows) {
        contents += buttons(i)
      }
      for (column <- 0 until columns - 1; row <- 0 until rows) {
        contents += slots(row)(column)
      }
    }

  }

  def setupMenuBar(): Unit = {
    val menu = new MenuBar {
      contents += new Menu("menu") {
        contents += new MenuItem(Action("new game") {
          startNewGame()
        })
        contents += new MenuItem(Action("undo") {
        })
        contents += new MenuItem(Action("redo") {
        })
        contents += new MenuItem(Action("exit") {
          quit()
        })
      }
    }
    mainFrame.menuBar = new MenuBar
  }

  def setupButtons(): Unit = {
    for (i <- 0 until rows) {
      val chosenColumn: Int = i
      buttons(i) = Button(String.valueOf(i + 1))(
        buttonAction(chosenColumn)
      )

    }
  }

  def buttonAction(chosenColumn: Int): Unit = {
    slots(1)(1).background = Color.blue
    evaluateMove(chosenColumn)
    if (!gamingPlayers.currentPlayer.isReal) playBot()
  }

  def setupSlots(): Unit = {

    for (column <- 0 until columns; row <- 0 until rows) {
      slots(row)(column) = new Label {
        opaque = true
        horizontalAlignment
        border = Swing.LineBorder(Color.BLACK, 2)
      }
    }
  }


  def updateBoard(): Unit = {
    for (row <- 0 until rows; column <- 0 until columns) {
      updateCell(row, column)
    }
  }

  def evaluateMove(chosenColumn: Int): Unit = {

    val columnFull: Boolean = MoveLogic.isFullAndAddCell(chosenColumn, gamingPlayers.currentPlayerCellType())
    updateBoard()

    if (!columnFull) {
      if (CheckWinner.isMoveWinning(chosenColumn)) showWon()
      else if (grid.isFull) showDraw()

      gamingPlayers.changePlayer()
      mainFrame.title = "Connect four - player " + gamingPlayers.currentPlayer.name
    } else {
      Dialog.showMessage(null, "Please choose another one.", "Column is filled")
    }
  }


  def updateCell(row: Int, column: Int): Unit = {
    grid.cell(row, column).cellType match {
      case CellType.FIRST =>
        slots(row)(column).background = Color.blue
      case CellType.SECOND =>
        slots(row)(column).background = Color.red
      case CellType.Empty =>
        slots(row)(column).background = Color.white
    }
  }


  def showWon(): Unit = {
    val winner: String = "Player " + gamingPlayers.currentPlayer.name + " has won"
    val option = Dialog.showConfirmation(null, "Play a new game?", optionType = Dialog.Options.YesNo, title = winner)
    startNewOrQuit(option == Dialog.Result.Ok)
  }

  def showDraw(): Unit = {
    val option = Dialog.showConfirmation(null, "Nobody won.\nPlay a new game?", optionType = Dialog.Options.YesNo, title = "Draw")
    startNewOrQuit(option == Dialog.Result.Ok)
  }

  def playBot(): Unit = {
    val robotsColumn = gamingPlayers.currentPlayer.playTurn()
    evaluateMove(robotsColumn)
  }

  private def startNewOrQuit(startNew: Boolean) = {
    if (startNew) {
      startNewGame()
    } else {
      quit()
    }
  }

  def startNewGame(): Unit = {
    grid.emptyGrid()
    setup()
  }

  def quit(): Unit = {
    mainFrame.dispose()
    sys.exit(0)
  }


}
