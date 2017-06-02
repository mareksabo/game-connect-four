package de.htwg.se.connectfour.view

import java.awt.Color

import de.htwg.se.connectfour.controller.GridController
import de.htwg.se.connectfour.model.CellType
import de.htwg.se.connectfour.model.player.GamingPlayers

import scala.swing.event.{Event, Key}
import scala.swing.{Action, Button, Dialog, Dimension, Frame, GridPanel, Label, MainFrame, Menu, MenuBar, MenuItem, Swing}

class PlayerGridChanged extends Event

class GridChanged extends Event

class Gui(val gridController: GridController, val gamingPlayers: GamingPlayers) extends Frame {

  val columns: Int = gridController.columnSize
  val rows: Int = gridController.rowSize

  val blocks: Array[Array[Label]] = {
    val blocks: Array[Array[Label]] = Array.ofDim[Label](columns, rows)
    for (i <- 0 until rows; j <- 0 until columns) {
      blocks(j)(i) = new Label {
        opaque = true
        horizontalAlignment
        border = Swing.LineBorder(Color.BLACK, 1)
      }
    }
    blocks
  }

  setupMainFrame()
  setupReactions
  listenTo(gridController)

  def setupMainFrame(): Unit = {
    val WIDTH = 700
    val HEIGHT = 500

    val mainFrame = new MainFrame()
    mainFrame.title = "Connect four game"
    mainFrame.preferredSize = new Dimension(WIDTH, HEIGHT)
    mainFrame.contents = createPanel()
    mainFrame.menuBar = createMenuBar()
    mainFrame.centerOnScreen()
    mainFrame.visible = true
  }

  def createPanel(): GridPanel = {
    new GridPanel(columns, rows + 1) {
      val buttons: Array[Button] = new Array[Button](rows)
      for (i <- 0 until rows) contents += Button(String.valueOf(i + 1))(buttonAction(i))
      for (j <- 0 until columns - 1; i <- 0 until rows) {
        contents += blocks(i)(j)
      }
    }
  }

  def createMenuBar(): MenuBar = {
    new MenuBar {
      contents += new Menu("Game") {
        mnemonic = Key.G
        contents += new MenuItem(Action("New") {
          startNewGame()
        })
        contents += new MenuItem(Action("Quit") {
          mnemonic = Key.Q
          quit()
        })
      }
      contents += new MenuItem(Action("Undo") {
        gridController.undo()
      })
      contents += new MenuItem(Action("Redo") {
        gridController.redo()
      })
    }
  }

  def setupReactions: reactions.type = {
    reactions += {
      case _: GridChanged =>
        redraw()
      case _: PlayerGridChanged =>
        gamingPlayers.changePlayer()
        redraw()
    }
  }

  def buttonAction(chosenColumn: Int): Unit = {
    evaluateMove(chosenColumn)
    if (!gamingPlayers.currentPlayer.isReal) playBot()
  }

  def playBot(): Unit = {
    val robotsColumn = gamingPlayers.currentPlayer.playTurn()
    evaluateMove(robotsColumn)
  }

  def evaluateMove(chosenColumn: Int): Unit = {

    val columnFull: Boolean = gridController.isFullAndAddCell(chosenColumn, gamingPlayers.currentPlayerCellType())

    if (columnFull) {
      Dialog.showMessage(message = "Please choose another one.", title = "Column is filled")
      return
    }

    if (gridController.isMoveWinning(chosenColumn)) showWon()
    else if (gridController.isFull) showDraw()
  }

  def redraw(): Unit = for (i <- 0 until columns; j <- 0 until rows) redrawCell(i, j)

  def redrawCell(column: Int, row: Int): Unit = {
    gridController.cell(column, row).cellType match {
      case CellType.FIRST =>
        blocks(column)(row).background = Color.red
      case CellType.SECOND =>
        blocks(column)(row).background = Color.blue
      case CellType.EMPTY =>
        blocks(column)(row).background = Color.white
    }
  }

  def showWon(): Unit = {
    gamingPlayers.changePlayer() // TODO: solve currentPlayer
    val winner = "Player " + gamingPlayers.currentPlayer.name + " has won"
    val option = Dialog.showConfirmation(message = "Play a new game?", optionType = Dialog.Options.YesNo, title = winner)
    if (option == Dialog.Result.Ok) startNewGame()
  }

  def showDraw(): Unit = {
    val option = Dialog.showConfirmation(message = "Nobody won.\nPlay a new game?", optionType = Dialog.Options.YesNo, title = "Draw")
    if (option == Dialog.Result.Ok) startNewGame()
  }

  def startNewGame(): Unit = gridController.createEmptyGrid(gridController.columnSize, gridController.rowSize)

  def quit(): Unit = sys.exit(0)


}
