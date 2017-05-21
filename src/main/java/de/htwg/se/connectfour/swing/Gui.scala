package de.htwg.se.connectfour.swing

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, GridLayout}
import javax.swing.border.LineBorder
import javax.swing.{JButton, JFrame, JLabel, JOptionPane, JPanel, SwingConstants, JMenuBar, JMenu, JMenuItem}

import de.htwg.se.connectfour.logic.{CheckWinner, MoveLogic}
import de.htwg.se.connectfour.model.{CellType, Grid, SingletonGrid}
import de.htwg.se.connectfour.player.GamingPlayers

object Gui {
  val grid: Grid = SingletonGrid.getGrid
  val rows: Int = SingletonGrid.DEFAULT_ROWS
  val columns: Int = SingletonGrid.DEFAULT_COLUMNS

  val frame: JFrame = new JFrame()
  val panel: JPanel = frame.getContentPane.asInstanceOf[JPanel]
  var slots: Array[Array[JLabel]] = Array.ofDim[JLabel](rows, columns)
  val buttons: Array[JButton] = new Array[JButton](rows)

  val menuBar: JMenuBar = new JMenuBar()
  val menu: JMenu = new JMenu("menu")
  val undo: JMenuItem = new JMenuItem("undo")
  val redo: JMenuItem = new JMenuItem("redo")
  val newGame: JMenuItem = new JMenuItem("new game")
  val exit: JMenuItem = new JMenuItem("exit")



  var gamingPlayers: GamingPlayers = _

  def init(gamingPlayers: GamingPlayers): Unit = {
    this.gamingPlayers = gamingPlayers
  }

  setup()

  def setup(): Unit = {
    panel.removeAll()
    panel.setLayout(new GridLayout(rows, columns + 1))

    setupMenu()
    setupButtons()
    setupSlots()
    setupFrame()

    updateBoard()
  }

  def setupMenu(): Unit ={
    newGame.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        startNewGame()
      }
    })
    menu.add(newGame)
    menu.add(undo)
    menu.add(redo)
    exit.addActionListener(new ActionListener {
       def actionPerformed(e: ActionEvent): Unit = {
         quit()
       }
    })
    menu.add(exit)
    menuBar.add(menu)

  }


  def setupButtons(): Unit = {
    for (i <- 0 until rows) {
      buttons(i) = new JButton(String.valueOf(i + 1))
      buttons(i).setActionCommand(String.valueOf(i))
      buttons(i).addActionListener(new ActionListener() {
        def actionPerformed(e: ActionEvent): Unit = {
          val chosenColumn: Int = java.lang.Integer.parseInt(e.getActionCommand)
          evaluateMove(chosenColumn)

          if (!gamingPlayers.currentPlayer.isReal) playBot()
        }
      })
      panel.add(buttons(i))
    }
  }

  def evaluateMove(chosenColumn: Int): Unit = {

    val columnFull: Boolean = MoveLogic.isFullAndAddCell(chosenColumn, gamingPlayers.currentPlayerCellType())
    updateBoard()

    if (!columnFull) {
      if (CheckWinner.isMoveWinning(chosenColumn)) showWon()
      else if (grid.isFull) showDraw()

      gamingPlayers.changePlayer()
      frame.setTitle("Connect four - player " + gamingPlayers.currentPlayer.name)
    } else {
      JOptionPane.showMessageDialog(null, "Please choose another one.", "Column is filled", JOptionPane.INFORMATION_MESSAGE)
    }
  }

  def playBot(): Unit = {
      val robotsColumn = gamingPlayers.currentPlayer.playTurn()
      evaluateMove(robotsColumn)
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
    frame.setJMenuBar(menuBar)
    frame.setContentPane(panel)
    frame.setSize(700, 600)
    frame.setVisible(true)
    frame.setLocationRelativeTo(null)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
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
        slots(row)(column).setBackground(Color.white)
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