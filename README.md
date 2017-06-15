# Connect four game
[![Travis](https://travis-ci.org/mareksabo/game-connect-four.svg)](https://travis-ci.org/mareksabo/game-connect-four)

School project at HTWG school in a _Software engineering_ class.

### Project description

Project represents [connect four](https://en.wikipedia.org/wiki/Connect_Four) game
with eligible dimensions. Game could be played in either graphical or text mode.

### Project composition

In `de.htwg.se.connectfour`:

- `ConnectFourModule` is configuration for dependency injection
- `Main` is main class which is run

In `types` are enum classes.

In `logic`:
- `CheckWinner` containing rules how player can win
- `Validator` has logic validating cells in grid
- other classes are used for undo/redo command logic

In `mvc`:
- `model` consists of simple pojo classes:
  - `Cell` represents a basic unit
  - `Grid` consists of cells, represents *playing field*, `GridImpl` is its implementation
  - `player` has:
    - `Player` interface defining players
    - `RealPlayer` represents real player, his/her input is read
    - `RandomBotPlayer` represents bot playing randomly
- `view`
  - `Gui` is graphical user interface
  - `Tui` is text user interface
  - `GamingPlayers` helper storing two playing players, remembers (and changes) current player   
- `controller`
  - `Controller` defines contract of grid controller
  - `MockController` is simple controller for basic testing
  - `GridController` uses model classes and creates events which are being caught by ui classes
  
### Tools used

- IDE: _IntelliJ IDEA_
- Ci service: _Travis_
- build tool: _sbt_
- GUI library: _Swing_
- test library : _Specs2_
- injection library: _Google Guice_

### Authors

Marek Šabo, David Kubatzki