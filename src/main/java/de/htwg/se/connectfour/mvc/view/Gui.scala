package de.htwg.se.connectfour.mvc.view

import java.awt.Color

import de.htwg.se.connectfour.mvc.controller._
import de.htwg.se.connectfour.types.CellType

import scala.swing.event.Key
import scala.swing._

case class Gui(controller: Controller, gamingPlayers: GamingPlayers) extends Frame {

  var columns: Int = controller.columns
  var rows: Int = controller.rows
  val statusLine = new TextField(controller.statusText, 20)

  var blocks: Array[Array[Label]] = {
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
  listenTo(controller)

  def setupMainFrame(): Unit = {
    val WIDTH = 700
    val HEIGHT = 500

    val mainFrame = new MainFrame()
    mainFrame.title = "Connect four game"
    mainFrame.preferredSize = new Dimension(WIDTH, HEIGHT)
    mainFrame.contents = new BorderPanel {
      add(createPanel(), BorderPanel.Position.Center)
      add(statusLine, BorderPanel.Position.South)
    }

    mainFrame.menuBar = createMenuBar()
    mainFrame.centerOnScreen()
    mainFrame.visible = true
  }

  def initBlocks(): Unit = {
      blocks = Array.ofDim[Label](columns, rows)
      for (i <- 0 until rows; j <- 0 until columns) {
        blocks(j)(i) = new Label {
          opaque = true
          horizontalAlignment
          border = Swing.LineBorder(Color.BLACK, 1)
        }
    }
  }


  // !bug for new dimensions is probably here!
  def createPanel(): GridPanel = {
    new GridPanel(columns, rows + 1) {
      val buttons: Array[Button] = new Array[Button](rows)
      for (i <- 0 until rows) contents += Button(String.valueOf(i + 1))(buttonAction(i))
      for (i <- 0 until rows  ; j <- 0 until columns) {
        contents += blocks(j)(i)
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
        controller.undo()
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo()
      })
    }
  }

  def setupReactions: reactions.type = {
    reactions += {
      case _: PlayerGridChanged => redraw()
      case _: GridChanged => redraw()
      case _: PlayerWon => showWon()
      case _: Draw => showDraw()
      case _: FilledColumn => Dialog.showMessage(message = "Please choose another one.", title = "Column is filled")
      case _: InvalidMove => redraw()
    }
  }

  def buttonAction(chosenColumn: Int): Unit = {
    gamingPlayers.applyTurn(chosenColumn)
    playBotIfGoing()
  }

  def playBotIfGoing(): Unit = {
    if (!gamingPlayers.currentPlayer.isReal) {
      val robotsColumn = gamingPlayers.currentPlayer.playTurn()
      gamingPlayers.applyTurn(robotsColumn)
    }
  }


  def redraw(): Unit = {
    for (i <- 0 until columns; j <- 0 until rows) redrawCell(i, j)
    statusLine.text = controller.statusText
  }

  def redrawCell(column: Int, row: Int): Unit = {
    controller.cell(column, row).cellType match {
      case CellType.FIRST =>
        blocks(column)(row).background = Color.red
      case CellType.SECOND =>
        blocks(column)(row).background = Color.blue
      case CellType.EMPTY =>
        blocks(column)(row).background = Color.white
    }
  }

  def showWon(): Unit = {
    val winner = "Player " + gamingPlayers.previousPlayer.name + " has won"
    val option = Dialog.showConfirmation(message = "Play a new game?", optionType = Dialog.Options.YesNo, title = winner)
    newDimensions()
    //startNewOrQuit(option == Dialog.Result.Ok)
  }

  def showDraw(): Unit = {
    val option = Dialog.showConfirmation(message = "Nobody won.\nPlay a new game?", optionType = Dialog.Options.YesNo, title = "Draw")
    startNewOrQuit(option == Dialog.Result.Ok)
  }

  def newDimensions(): Unit = {
    val dimensionMainFrame = new MainFrame()
    val field1 = new TextField(columns.toString)
    val field2 = new TextField(rows.toString)
    val columLabel = new Label("columns")
    val rowLabel = new Label("rows")
    val okButton = Button("Ok")(startWithNewDimension(field1.text.toInt, field2.text.toInt, dimensionMainFrame))
    val cancelButton = Button("Cancel")(dimensionMainFrame.visible = false)
    dimensionMainFrame.contents = new GridPanel(3, 2){
      contents += columLabel
      contents += field1
      contents += rowLabel
      contents += field2
      contents += okButton
      contents += cancelButton
    }
    dimensionMainFrame.title = "set new Dimension"
    dimensionMainFrame.preferredSize = new Dimension(200,200)
    dimensionMainFrame.centerOnScreen()
    dimensionMainFrame.visible = true
  }

  def startWithNewDimension(cls: Int, rws: Int, mainFrame: MainFrame): Unit = {
    columns = cls
    rows = rws
    mainFrame.visible = false
    initBlocks()
    setupMainFrame()
    controller.createEmptyGrid(columns, rows)
  }

  def startNewOrQuit(startNew: Boolean): Unit = if (startNew) startNewGame() else quit()

  def startNewGame(): Unit = controller.createEmptyGrid(controller.columns, controller.rows)

  def quit(): Unit = sys.exit(0)

}
