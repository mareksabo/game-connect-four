package de.htwg.se.connectfour.swing

import java.awt._
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing._
import javax.swing.border.LineBorder

import de.htwg.se.connectfour.logic.{CheckWinner, MoveLogic}
import de.htwg.se.connectfour.model.{CellType, Grid, SingletonGrid}
import de.htwg.se.connectfour.player.{GamingPlayers, RealPlayer}

object Gui {
  val grid: Grid = SingletonGrid.getGrid
  val rows: Int = SingletonGrid.DEFAULT_ROWS
  val columns: Int = SingletonGrid.DEFAULT_COLUMNS

  val frame: JFrame = new JFrame()
  val panel: JPanel = frame.getContentPane.asInstanceOf[JPanel]
  var slots: Array[Array[JLabel]] = Array.ofDim[JLabel](rows, columns)
  val buttons: Array[JButton] = new Array[JButton](rows)

  val player1 = new RealPlayer("Marek")
  val player2 = new RealPlayer("David")
  val gamingPlayers = new GamingPlayers(player1, player2)

  setup()

  def setup(): Unit = {
    panel.removeAll()
    panel.setLayout(new GridLayout(rows, columns + 1))

    setupButtons()
    setupSlots()
    setupFrame()

    updateBoard()
  }

  def setupButtons(): Unit = {
    for (i <- 0 until rows) {
      buttons(i) = new JButton(String.valueOf(i + 1))
      buttons(i).setActionCommand(String.valueOf(i))
      buttons(i).addActionListener(new ActionListener() {
        def actionPerformed(e: ActionEvent): Unit = {
          val chosenColumn: Int = java.lang.Integer.parseInt(e.getActionCommand)

          val columnNotFull: Boolean = MoveLogic.checkAndAddCell(chosenColumn, gamingPlayers.currentPlayerCellType())
          updateBoard()

          if (columnNotFull) {
            if (isMoveWinning(chosenColumn)) showWon()
            else if (grid.isFull) showDraw()

            gamingPlayers.changePlayer()
            frame.setTitle("Connect four - player " + gamingPlayers.currentPlayer.name)
          } else {
            JOptionPane.showMessageDialog(null, "Please choose another one.", "Column is filled", JOptionPane.INFORMATION_MESSAGE)
          }
        }
      })
      panel.add(buttons(i))
    }
  }

  def setupSlots(): Unit = {
    slots = Array.ofDim[JLabel](rows, columns)

    for (column <- 0 until columns; row <- 0 until rows) {
      slots(row)(column) = new JLabel()
      slots(row)(column).setHorizontalAlignment(SwingConstants.CENTER)
      slots(row)(column).setBorder(new LineBorder(Color.black))
      panel.add(slots(row)(column))
    }
  }

  def setupFrame(): Unit = {
    frame.setTitle("Connect four game")
    frame.setContentPane(panel)
    frame.setSize(700, 600)
    frame.setVisible(true)
    frame.setLocationRelativeTo(null)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  }

  def isMoveWinning(columnMove: Int): Boolean = {
    val rowMove = MoveLogic.findLastRowPosition(columnMove)
    new CheckWinner().checkForWinner(grid.cell(columnMove, rowMove))
  }

  def updateBoard(): Unit = {
    for (row <- 0 until rows; column <- 0 until columns) {
      updateCell(row, column)
    }
  }

  def updateCell(row: Int, column: Int): Unit = {
    slots(row)(column).setOpaque(true)

    grid.cell(row, column).cellType match {
      case CellType.FIRST =>
        slots(row)(column).setBackground(Color.red)
      case CellType.SECOND =>
        slots(row)(column).setBackground(Color.blue)
      case CellType.Empty =>
        // do nothing
    }
  }

  def showWon(): Unit = {
    val winner: String = "Player " + gamingPlayers.currentPlayer.name + " has won"
    val option: Int = JOptionPane.showConfirmDialog(frame, "Play a new game?", winner, JOptionPane.YES_NO_OPTION)
    startNewOrQuit(option == 0)
  }

  def showDraw(): Unit = {
    val option: Int = JOptionPane.showConfirmDialog(frame, "Nobody won.\nPlay a new game?", "Draw", JOptionPane.YES_NO_OPTION)
    startNewOrQuit(option == 0)
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
    frame.dispose()
    sys.exit(0)
  }

}