package HW.a9;

import java.util.ArrayList;

import javalib.impworld.*;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

import javalib.worldimages.*;
import tester.Tester;

// Represents the constants of the game
interface IConstants {
  int RECT_SIZE = 20;
  int WINDOW_SIZE_X = 600;
  int WINDOW_SIZE_Y = 600;
  double GAME_SPEED = 0.01;
}

// Represents a piece of the board
interface IBoardPiece extends IConstants {
  // draw this piece
  WorldImage drawCell();

  // EFFECT: flood this piece
  void flood(Color other);

  // EFFECT: change the color of this piece
  void changeColor(Color other);

  // get the neighbors of this piece
  ArrayList<IBoardPiece> getNeighbors();

  // queue this piece for animating
  // EFFECT: change the queue status of this piece
  ArrayList<IBoardPiece> queue();
}

// Represents a cell wall
class Wall implements IBoardPiece {
  // draw this wall
  public WorldImage drawCell() {
    return new EmptyImage();
  }

  // EFFECT: flood this wall (don't do anything)
  public void flood(Color other) {
    // don't do anything
  }

  // EFFECT: change the color of this wall (don't do anything)
  public void changeColor(Color other) {
    // don't do anything
  }

  // get the neighbors of this wall
  public ArrayList<IBoardPiece> getNeighbors() {
    return new ArrayList<IBoardPiece>();
  }

  // queue this wall
  public ArrayList<IBoardPiece> queue() {
    return new ArrayList<IBoardPiece>();
  }
}

// Represents a single square of the game area
class Cell implements IBoardPiece {
  // In logical coordinates, with the origin in the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  IBoardPiece left;
  IBoardPiece top;
  IBoardPiece right;
  IBoardPiece bottom;
  // is this cell queued?
  boolean isQueued;

  Cell(int x, int y, Color color, boolean flooded,
       IBoardPiece left, IBoardPiece top, IBoardPiece right, IBoardPiece bottom) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.isQueued = false;
  }

  // Convenience constructor for a cell that is not flooded and has no neighbors
  Cell(int x, int y, Color color) {
    this(x, y, color, false,
        new Wall(), new Wall(), new Wall(), new Wall());
  }

  // EFFECT: change the top neighbor of this cell
  void changeTop(Cell top) {
    this.top = top;
    top.bottom = this;
  }

  // EFFECT: change the left neighbor of this cell
  void changeLeft(Cell left) {
    this.left = left;
    left.right = this;
  }

  // draw this cell
  public WorldImage drawCell() {
    if (this.flooded) {
      return new OverlayImage(new TextImage("F", 10, Color.BLACK),
      new RectangleImage(20, 20,
          OutlineMode.SOLID, this.color));
    } else {
      return new RectangleImage(20, 20,
          OutlineMode.SOLID, this.color);
    }
  }

  // is this cell under the given position?
  boolean isUnder(Posn pos, int boardSize) {
    int minX = WINDOW_SIZE_X / 2 - boardSize / 2 * RECT_SIZE + RECT_SIZE * this.x - RECT_SIZE;
    int maxX = WINDOW_SIZE_X / 2 - boardSize / 2 * RECT_SIZE + RECT_SIZE * this.x;
    int minY = WINDOW_SIZE_Y / 2 - boardSize / 2 * RECT_SIZE + RECT_SIZE * this.y - RECT_SIZE;
    int maxY = WINDOW_SIZE_Y / 2 - boardSize / 2 * RECT_SIZE + RECT_SIZE * this.y;
    return pos.x >= minX && pos.x < maxX && pos.y >= minY && pos.y < maxY;
  }

  // EFFECT: flood this cell and appropriate neighbors
  public void flood(Color other) {
    if (this.flooded) {
      return;
    }
    if (this.color != other) {
      return;
    }
    this.flooded = true;
    this.floodAllConnections();

  }

  // EFFECT: flood all connections of this cell
  void floodAllConnections() {
    this.left.flood(this.color);
    this.top.flood(this.color);
    this.right.flood(this.color);
    this.bottom.flood(this.color);
  }

  // EFFECT: change the color of this cell
  public void changeColor(Color other) {
    this.color = other;
  }

  // get the neighbors of this cell
  // EFFECT: queue the appropriate neighbors
  public ArrayList<IBoardPiece> getNeighbors() {
    ArrayList<IBoardPiece> result = new ArrayList<IBoardPiece>();
    result.addAll(this.left.queue());
    result.addAll(this.right.queue());
    result.addAll(this.top.queue());
    result.addAll(this.bottom.queue());
    return result;
  }

  // queue this cell
  // EFFECT: change the queue status of this cell
  public ArrayList<IBoardPiece> queue() {
    if (!this.isQueued && this.flooded) {
      this.isQueued = true;
      return new ArrayList<IBoardPiece>(Arrays.asList(this));
    } else {
      return new ArrayList<IBoardPiece>();
    }
  }

  // EFFECT: unqueue this cell
  public void unqueue() {
    this.isQueued = false;
  }

  // EFFECT: unflood this cell
  public void unflood() {
    this.flooded = false;
  }

  // is this cell flooded?
  public boolean notFlooded() {
    return !this.flooded;
  }
}

class FloodItWorld extends World implements IConstants {

  Random rand;
  int numberOfColors;
  ArrayList<ArrayList<Cell>> board;
  Cell initial;
  boolean displayFloodAnimation;
  ArrayList<IBoardPiece> animationQueue;
  Color activeColor;
  int maxSteps;
  int steps;
  int tickCount;
  boolean lost;
  boolean won;
  boolean darkMode;


  Color backgroundColor;
  Color stuffColor;

  FloodItWorld(int size, int numberOfColors, Random rand) {
    if (size % 2 == 0 || size < 3 || size > 21) {
      throw new IllegalArgumentException("Size must be odd, and between 3 and 21, inclusive.");
    }
    this.rand = rand;
    this.numberOfColors = numberOfColors;
    this.board = this.initBoard(size);
    this.initial = this.board.get(0).get(0);
    this.initial.flood(initial.color);
    this.displayFloodAnimation = false;
    this.animationQueue = new ArrayList<IBoardPiece>();
    this.activeColor = this.initial.color;
    this.tickCount = 0;
    this.steps = 0;
    this.maxSteps = getMaxSteps(size, numberOfColors);
    this.lost = false;
    this.won = false;
    this.darkMode = false;
    this.backgroundColor = Color.WHITE;
    this.stuffColor = Color.BLACK;
  }

  // compute the maximum steps given the size and number of colors
  int getMaxSteps(int size, int numberOfColors) {
    return (int) (numberOfColors * size * 0.225);
  }

  // Initialize the board with the given size and number of colors
  ArrayList<ArrayList<Cell>> initBoard(int size) {
    ArrayList<ArrayList<Cell>> result = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < size; i++) {
      ArrayList<Cell> row = new ArrayList<Cell>();
      for (int j = 0; j < size; j++) {
        row.add(new Cell(i, j, this.randomColor(this.numberOfColors)));
      }
      result.add(row);
    }
    this.fixNeighbors(result);
    return result;
  }

  // EFFECT: updates the neighbors of each cell
  // Fix the neighbors of each cell
  void fixNeighbors(ArrayList<ArrayList<Cell>> board) {
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.size(); j++) {
        Cell cell = board.get(i).get(j);
        if (i > 0) {
          cell.changeTop(board.get(i - 1).get(j));
        }
        if (j > 0) {
          cell.changeLeft(board.get(i).get(j - 1));
        }
      }
    }
  }

  // chose a color at random
  Color randomColor(int numberOfColors) {
    ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE,
        Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK));
    if (numberOfColors > colors.size()) {
      throw new IllegalArgumentException("Number of colors chosen exceeds the max possible!");
    }
    int index = this.rand.nextInt(numberOfColors);
    return colors.get(index);
  }

  // draw the scene
  public WorldScene makeScene() {
    WorldScene scene = getEmptyScene();
    // draw the background in the appropriate color
    scene.placeImageXY(new RectangleImage(WINDOW_SIZE_X, WINDOW_SIZE_Y,
            OutlineMode.SOLID, this.backgroundColor), WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2);
    // draw the board
    for (int i = 0; i < this.board.size(); i++) {
      for (int j = 0; j < this.board.size(); j++) {
        scene.placeImageXY(this.board.get(i).get(j).drawCell(),
            (i * RECT_SIZE) + WINDOW_SIZE_X / 2 - this.board.size() * RECT_SIZE / 2,
            (j * RECT_SIZE) + WINDOW_SIZE_Y / 2 - this.board.size() * RECT_SIZE / 2);
      }
    }
    // display the time and steps
    scene.placeImageXY(new TextImage(String.valueOf(String.format("%.2f",
            (tickCount * GAME_SPEED))), 20, FontStyle.BOLD,
        this.stuffColor), WINDOW_SIZE_X / 2, 20);
    scene.placeImageXY(new TextImage(steps + "/" + maxSteps + " steps", 20,
            FontStyle.BOLD, this.stuffColor)
        , WINDOW_SIZE_X / 2, 50);
    // place dark mode button
    scene.placeImageXY(new TextImage("Dark Mode", 20, FontStyle.BOLD, this.stuffColor),
        70, WINDOW_SIZE_Y - 40);
    scene.placeImageXY(new RectangleImage(30, 30, OutlineMode.SOLID, this.stuffColor),
        150, WINDOW_SIZE_Y - 40);
    scene.placeImageXY(new RectangleImage(25, 25, OutlineMode.SOLID,
            this.backgroundColor),
        150, WINDOW_SIZE_Y - 40);
    if (this.darkMode) {
      scene.placeImageXY(new TextImage("✔", 30, FontStyle.BOLD, new Color(47,
              156, 62)), 150, WINDOW_SIZE_Y - 40);
    }
    if (this.lost) {
      // if the user lost, show losing screen
      scene.placeImageXY(new RectangleImage(400, 225, OutlineMode.SOLID,
              this.stuffColor), WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2);
      scene.placeImageXY(new RectangleImage(380, 205, OutlineMode.SOLID,
              this.backgroundColor), WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2);
      scene.placeImageXY(new TextImage("You Lost", 20, FontStyle.BOLD, this.stuffColor),
          WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2 - 40);
      scene.placeImageXY(new TextImage("Press \"r\" to play again", 20,
              FontStyle.BOLD, this.stuffColor), WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2);

    } else if (this.won) {
      // if the user has won, show winning screen
      scene.placeImageXY(new RectangleImage(400, 225, OutlineMode.SOLID,
              this.stuffColor), WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2);
      scene.placeImageXY(new RectangleImage(380, 205, OutlineMode.SOLID,
              this.backgroundColor), WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2);
      scene.placeImageXY(new TextImage("You Won!", 20, FontStyle.BOLD, this.stuffColor),
          WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2 - 40);
      scene.placeImageXY(new TextImage("Press \"r\" to play again", 20, FontStyle.BOLD,
              this.stuffColor), WINDOW_SIZE_X / 2, WINDOW_SIZE_Y / 2);
    }
    return scene;
  }

  // Handle a mouse click
  // EFFECT: update the state of the game based on the mouse click
  public void onMouseClicked(Posn pos, String buttonName) {
    // is the click over the dark mode button?
    if (pos.x > 135 && pos.x < 165 && pos.y > WINDOW_SIZE_Y - 55 && pos.y < WINDOW_SIZE_Y - 25) {
      if (this.darkMode) {
        this.backgroundColor = Color.WHITE;
        this.stuffColor = Color.BLACK;
      } else {
        this.backgroundColor = new Color(33, 41, 55);
        this.stuffColor = new Color(148, 154, 163);
      }
      this.darkMode = !this.darkMode;
      return;
    }
    if (!this.lost) {
      // if the game is in animation mode, get out of here
      if (this.displayFloodAnimation) {
        return;
      }
      System.out.println("Clicked on " + pos.x + ", " + pos.y);

      // if the click is not over the board, get out of here
      if (!this.isPosnOverBoard(pos)) {
        return;
      }
      // otherwise, get the cell that was clicked and flood it
      Cell clicked = this.getCellOverPosn(pos);
      if (clicked.color == this.activeColor) {
        return;
      }
      steps++;
      this.activeColor = clicked.color;
      this.animationQueue = initial.queue();
      this.displayFloodAnimation = true;
    }
  }

  // Handle a key press
  // EFFECT: update the state of the game based on the key press
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      newGame();
    }
  }

  // Handle a tick
  // EFFECT: update the state of the game based on the tick
  public void onTick() {
    // if the game is not over, increment the tick count
    if (!this.lost && !this.won) {
      this.tickCount++;
    }
    // if the game is not in animation mode, get out of here
    if (!this.displayFloodAnimation) {
      return;
    }
    // if the animation queue is empty, stop the animation, unqueue all, and get out of here
    if (this.animationQueue.isEmpty()) {
      this.displayFloodAnimation = false;
      this.unqueueAll();
      this.unfloodAll();
      // check flooded cells surrounding for same color for new flooded cells
      this.initial.flood(this.activeColor);
      if (this.allFlooded()) {
        this.won = true;
      } else if (this.steps >= this.maxSteps) {
        this.lost = true;
      }
      return;
    }
    // otherwise, animate the queue and update the queue
    ArrayList<IBoardPiece> neighbors = new ArrayList<IBoardPiece>();
    for (IBoardPiece piece : this.animationQueue) {
      piece.changeColor(this.activeColor);
      neighbors.addAll(piece.getNeighbors());
    }
    animationQueue = neighbors;
  }

  // is the given position over the board?
  boolean isPosnOverBoard(Posn p) {
    return p.x >= WINDOW_SIZE_X / 2 - this.board.size() * RECT_SIZE / 2 - RECT_SIZE / 2
        && p.x < WINDOW_SIZE_X / 2 + this.board.size() * RECT_SIZE / 2 - RECT_SIZE / 2
        && p.y >= WINDOW_SIZE_Y / 2 - this.board.size() * RECT_SIZE / 2 - RECT_SIZE / 2
        && p.y < WINDOW_SIZE_Y / 2 + this.board.size() * RECT_SIZE / 2 - RECT_SIZE / 2;
  }

  // get the cell that is under the given position
  Cell getCellOverPosn(Posn pos) {
    for (ArrayList<Cell> row : this.board) {
      for (Cell c : row) {
        if (c.isUnder(pos, board.size())) {
          return c;
        }
      }
    }
    throw new IllegalArgumentException("No cell under this position!");
  }

  // EFFECT: unqueue all cells
  void unqueueAll() {
    for (ArrayList<Cell> row : this.board) {
      for (Cell c : row) {
        c.unqueue();
      }
    }
  }

  // EFFECT: unflood all cells
  void unfloodAll() {
    for (ArrayList<Cell> row : this.board) {
      for (Cell c : row) {
        c.unflood();
      }
    }
  }

  // EFFECT: start a new game
  void newGame() {
    this.board = this.initBoard(this.board.size());
    this.initial = this.board.get(0).get(0);
    this.initial.flood(initial.color);
    this.displayFloodAnimation = false;
    this.animationQueue = new ArrayList<IBoardPiece>();
    this.activeColor = this.initial.color;
    this.tickCount = 0;
    this.steps = 0;
    this.lost = false;
    this.won = false;
  }

  // are all the cells flooded?
  boolean allFlooded() {
    for (ArrayList<Cell> row : this.board) {
      for (Cell c : row) {
        if (c.notFlooded()) {
          return false;
        }
      }
    }
    return true;
  }
}

class ExamplesFloodIt implements IConstants {
  Cell c1;
  Cell c2;
  Cell c3;
  Cell c4;
  Cell c5;
  Cell c6;

  Wall wall;

  FloodItWorld w1;
  FloodItWorld wSimple;

  FloodItWorld toPlay = new FloodItWorld(15, 4, new Random(1));

  void initData() {
    c1 = new Cell(0, 0, Color.RED);
    c2 = new Cell(0, 1, Color.YELLOW);
    c3 = new Cell(1, 0, Color.GREEN);
    c4 = new Cell(1, 1, Color.BLUE);
    c5 = new Cell(1, 2, Color.MAGENTA, false, c1, c2, c3, c4);
    c6 = new Cell(1, 2, Color.RED, false, c1, c2, c3, c4);

    wall = new Wall();

    w1 = new FloodItWorld(11, 3, new Random(1));
    wSimple = new FloodItWorld(3, 2, new Random(1));
  }

  void testInitBoard(Tester t) {
    initData();
    w1.initBoard(10);
    t.checkExpect(this.w1.board.size(), 11);
    t.checkExpect(this.w1.board.get(0).size(), 11);
    t.checkExpect(this.w1.board.get(0).get(0).color, Color.RED);
    t.checkExpect(this.w1.board.get(0).get(1).color, Color.GREEN);
    t.checkExpect(this.w1.board.get(1).get(0).color, Color.GREEN);
    t.checkExpect(this.w1.board.get(1).get(1).color, Color.GREEN);
    t.checkExpect(this.w1.board.get(1).get(2).color, Color.RED);
  }

  void testFixNeighbors(Tester t) {
    initData();
    w1.fixNeighbors(w1.board);
    t.checkExpect(this.w1.board.get(0).get(0).left, wall);
    t.checkExpect(this.w1.board.get(0).get(0).right, this.w1.board.get(0).get(1));
    t.checkExpect(this.w1.board.get(0).get(1).left, this.w1.board.get(0).get(0));
    t.checkExpect(this.w1.board.get(0).get(1).right, this.w1.board.get(0).get(2));
    t.checkExpect(this.w1.board.get(0).get(2).left, this.w1.board.get(0).get(1));
    t.checkExpect(this.w1.board.get(0).get(2).right, this.w1.board.get(0).get(3));
    t.checkExpect(this.w1.board.get(0).get(3).left, this.w1.board.get(0).get(2));
    t.checkExpect(this.w1.board.get(0).get(3).right, this.w1.board.get(0).get(4));
    t.checkExpect(this.w1.board.get(0).get(4).left, this.w1.board.get(0).get(3));
    t.checkExpect(this.w1.board.get(0).get(4).right, this.w1.board.get(0).get(5));
    t.checkExpect(this.w1.board.get(0).get(5).left, this.w1.board.get(0).get(4));
    t.checkExpect(this.w1.board.get(0).get(5).right, this.w1.board.get(0).get(6));
    t.checkExpect(this.w1.board.get(0).get(6).left, this.w1.board.get(0).get(5));
    t.checkExpect(this.w1.board.get(0).get(6).right, this.w1.board.get(0).get(7));
    t.checkExpect(this.w1.board.get(0).get(7).left, this.w1.board.get(0).get(6));
    t.checkExpect(this.w1.board.get(0).get(7).right, this.w1.board.get(0).get(8));
    t.checkExpect(this.w1.board.get(0).get(8).left, this.w1.board.get(0).get(7));
    t.checkExpect(this.w1.board.get(0).get(8).right, this.w1.board.get(0).get(9));
    t.checkExpect(this.w1.board.get(0).get(9).left, this.w1.board.get(0).get(8));
    t.checkExpect(this.w1.board.get(0).get(9).right, this.w1.board.get(0).get(10));
    t.checkExpect(this.w1.board.get(0).get(10).left, this.w1.board.get(0).get(9));
    t.checkExpect(this.w1.board.get(0).get(10).right, wall);
    t.checkExpect(this.w1.board.get(0).get(9).top, wall);
    t.checkExpect(this.w1.board.get(0).get(9).bottom, this.w1.board.get(1).get(9));
    t.checkExpect(this.w1.board.get(1).get(9).top, this.w1.board.get(0).get(9));
    t.checkExpect(this.w1.board.get(1).get(9).bottom, this.w1.board.get(2).get(9));
    t.checkExpect(this.w1.board.get(2).get(9).top, this.w1.board.get(1).get(9));
    t.checkExpect(this.w1.board.get(2).get(9).bottom, this.w1.board.get(3).get(9));
    t.checkExpect(this.w1.board.get(3).get(9).top, this.w1.board.get(2).get(9));
    t.checkExpect(this.w1.board.get(3).get(9).bottom, this.w1.board.get(4).get(9));
    t.checkExpect(this.w1.board.get(4).get(9).top, this.w1.board.get(3).get(9));
    t.checkExpect(this.w1.board.get(4).get(9).bottom, this.w1.board.get(5).get(9));
    t.checkExpect(this.w1.board.get(5).get(9).top, this.w1.board.get(4).get(9));
    t.checkExpect(this.w1.board.get(5).get(9).bottom, this.w1.board.get(6).get(9));
    t.checkExpect(this.w1.board.get(6).get(9).top, this.w1.board.get(5).get(9));
    t.checkExpect(this.w1.board.get(6).get(9).bottom, this.w1.board.get(7).get(9));
    t.checkExpect(this.w1.board.get(7).get(9).top, this.w1.board.get(6).get(9));
    t.checkExpect(this.w1.board.get(7).get(9).bottom, this.w1.board.get(8).get(9));
    t.checkExpect(this.w1.board.get(8).get(9).top, this.w1.board.get(7).get(9));
    t.checkExpect(this.w1.board.get(8).get(9).bottom, this.w1.board.get(9).get(9));
    t.checkExpect(this.w1.board.get(9).get(9).top, this.w1.board.get(8).get(9));
    t.checkExpect(this.w1.board.get(9).get(9).bottom, this.w1.board.get(10).get(9));
    t.checkExpect(this.w1.board.get(10).get(9).top, this.w1.board.get(9).get(9));
    t.checkExpect(this.w1.board.get(10).get(9).bottom, wall);
  }

  void testRandomColor(Tester t) {
    this.initData();
    t.checkExpect(this.w1.randomColor(3), Color.BLUE);
    t.checkExpect(this.w1.randomColor(4), Color.GREEN);
    t.checkException(new IllegalArgumentException("Number of colors chosen exceeds " +
            "the max possible!"), this.w1, "randomColor", 16);
  }

  void testDrawCell(Tester t) {
    this.initData();
    t.checkExpect(this.c1.drawCell(), new RectangleImage(20, 20,
        OutlineMode.SOLID, Color.RED));
    t.checkExpect(this.c2.drawCell(), new RectangleImage(20, 20,
        OutlineMode.SOLID, Color.YELLOW));
    t.checkExpect(this.wall.drawCell(), new EmptyImage());
  }

  void testMakeScene(Tester t) {
    this.initData();
    WorldScene scene = wSimple.getEmptyScene();
    scene.placeImageXY(new RectangleImage(RECT_SIZE, RECT_SIZE,
        OutlineMode.SOLID, Color.GREEN), 0, 0);
    scene.placeImageXY(new RectangleImage(RECT_SIZE, RECT_SIZE,
        OutlineMode.SOLID, Color.RED), 0, 20);
    scene.placeImageXY(new RectangleImage(RECT_SIZE, RECT_SIZE,
        OutlineMode.SOLID, Color.RED), 20, 0);
    scene.placeImageXY(new RectangleImage(RECT_SIZE, RECT_SIZE,
        OutlineMode.SOLID, Color.RED), 20, 20);
    t.checkExpect(this.wSimple.makeScene(), scene);
  }

  void testBigBang(Tester t) {
    this.initData();
    toPlay.bigBang(WINDOW_SIZE_X, WINDOW_SIZE_Y, GAME_SPEED);
  }

  void testFlood(Tester t) {
    this.initData();
    t.checkExpect(this.c1.flooded, false);
    t.checkExpect(this.c2.flooded, false);
    t.checkExpect(this.c3.flooded, false);
    t.checkExpect(this.c4.flooded, false);
    t.checkExpect(this.c6.flooded, false);
    this.c6.flood(Color.RED);
    t.checkExpect(this.c6.flooded, true);
    t.checkExpect(this.c4.flooded, false);
    t.checkExpect(this.c3.flooded, false);
    t.checkExpect(this.c2.flooded, false);
    t.checkExpect(this.c1.flooded, true);
    this.c2.flood(Color.RED);
    t.checkExpect(this.c2.flooded, false);
  }

  void testChangeColor(Tester t) {
    this.initData();
    t.checkExpect(this.c1.color, Color.RED);
    this.c1.changeColor(Color.GREEN);
    t.checkExpect(this.c1.color, Color.GREEN);
    t.checkExpect(this.c6.color, Color.RED);
    this.c6.changeColor(Color.GREEN);
    t.checkExpect(this.c6.color, Color.GREEN);
  }

  void testGetNeighbors(Tester t) {
    this.initData();
    t.checkExpect(this.c1.getNeighbors(), new ArrayList<Cell>());
    t.checkExpect(this.c2.getNeighbors(), new ArrayList<Cell>());
    t.checkExpect(this.c3.getNeighbors(), new ArrayList<Cell>());
    t.checkExpect(this.c4.getNeighbors(), new ArrayList<Cell>());
    t.checkExpect(this.c5.getNeighbors(), new ArrayList<Cell>());
    this.c1.flooded = true;
    this.c2.flooded = true;
    this.c3.flooded = true;
    this.c4.flooded = true;
    this.c5.flooded = true;
    t.checkExpect(this.c5.getNeighbors(), new ArrayList<Cell>(Arrays.asList(this.c1, this.c3,
        this.c2, this.c4)));
    t.checkExpect(this.c6.getNeighbors(), new ArrayList<Cell>());
    t.checkExpect(this.wall.getNeighbors(), new ArrayList<Cell>());
  }

  void testQueue(Tester t) {
    this.initData();
    t.checkExpect(this.c1.isQueued, false);
    t.checkExpect(this.c1.queue(), new ArrayList<Cell>());
    t.checkExpect(this.c1.isQueued, false);
    this.c1.flooded = true;
    t.checkExpect(this.c1.queue(), new ArrayList<Cell>(Arrays.asList(this.c1)));
    t.checkExpect(this.c1.isQueued, true);
    t.checkExpect(this.c1.queue(), new ArrayList<Cell>());
    t.checkExpect(this.c1.isQueued, true);
    t.checkExpect(this.wall.queue(), new ArrayList<Cell>());
  }

  void testChangeTop(Tester t) {
    initData();
    t.checkExpect(this.c1.top, this.wall);
    t.checkExpect(this.c2.bottom, this.wall);
    this.c1.changeTop(this.c2);
    t.checkExpect(this.c1.top, this.c2);
    t.checkExpect(this.c2.bottom, this.c1);
  }

  void testChangeLeft(Tester t) {
    initData();
    t.checkExpect(this.c1.left, this.wall);
    t.checkExpect(this.c2.right, this.wall);
    this.c1.changeLeft(this.c2);
    t.checkExpect(this.c1.left, this.c2);
    t.checkExpect(this.c2.right, this.c1);
  }

  void testIsUnder(Tester t) {
    initData();
    t.checkExpect(this.c1.isUnder(new Posn(255, 255), 4), true);
    t.checkExpect(this.c1.isUnder(new Posn(400, 100), 4), false);
  }

  void testFloodAllConnections(Tester t) {
    this.initData();
    t.checkExpect(this.c1.flooded, false);
    t.checkExpect(this.c2.flooded, false);
    t.checkExpect(this.c3.flooded, false);
    t.checkExpect(this.c4.flooded, false);
    t.checkExpect(this.c6.flooded, false);
    this.c6.floodAllConnections();
    t.checkExpect(this.c1.flooded, true);
    t.checkExpect(this.c2.flooded, false);
    t.checkExpect(this.c3.flooded, false);
    t.checkExpect(this.c4.flooded, false);
    t.checkExpect(this.c6.flooded, false);
  }

  void testUnqueue(Tester t) {
    this.initData();
    this.c1.flooded = true;
    this.c1.isQueued = true;
    t.checkExpect(this.c1.isQueued, true);
    this.c1.unqueue();
    t.checkExpect(this.c1.isQueued, false);
  }

  void testUnflood(Tester t) {
    this.initData();
    this.c1.flooded = true;
    t.checkExpect(this.c1.flooded, true);
    this.c1.unflood();
    t.checkExpect(this.c1.flooded, false);
  }

  void testNotFlooded(Tester t) {
    this.initData();
    t.checkExpect(this.c1.notFlooded(), true);
    this.c1.flooded = true;
    t.checkExpect(this.c1.notFlooded(), false);
  }

  void testGetMaxSteps(Tester t) {
    this.initData();
    t.checkExpect(this.w1.getMaxSteps(11, 2), 4);
    t.checkExpect(this.wSimple.getMaxSteps(11, 2), 4);
  }

  void testOnMouseClicked(Tester t) {
    this.initData();
    t.checkExpect(wSimple.backgroundColor, Color.WHITE);
    t.checkExpect(wSimple.stuffColor, Color.BLACK);
    t.checkExpect(wSimple.darkMode, false);
    t.checkExpect(wSimple.lost, false);
    t.checkExpect(wSimple.won, false);
    t.checkExpect(wSimple.displayFloodAnimation, false);
    t.checkExpect(wSimple.steps, 0);
    t.checkExpect(wSimple.activeColor, Color.GREEN);
    t.checkExpect(wSimple.animationQueue, new ArrayList<Cell>());
    this.wSimple.onMouseClicked(new Posn(0, 0), "LeftButton");
    t.checkExpect(wSimple.backgroundColor, Color.WHITE);
    t.checkExpect(wSimple.stuffColor, Color.BLACK);
    t.checkExpect(wSimple.darkMode, false);
    t.checkExpect(wSimple.lost, false);
    t.checkExpect(wSimple.won, false);
    t.checkExpect(wSimple.displayFloodAnimation, false);
    t.checkExpect(wSimple.steps, 0);
    t.checkExpect(wSimple.activeColor, Color.GREEN);
    t.checkExpect(wSimple.animationQueue, new ArrayList<Cell>());
    this.wSimple.onMouseClicked(new Posn(147, 562), "LeftButton");
    t.checkExpect(wSimple.backgroundColor, new Color(33, 41, 55));
    t.checkExpect(wSimple.stuffColor, new Color(148, 154, 163));
    t.checkExpect(wSimple.darkMode, true);
    t.checkExpect(wSimple.lost, false);
    t.checkExpect(wSimple.won, false);
    t.checkExpect(wSimple.displayFloodAnimation, false);
    t.checkExpect(wSimple.steps, 0);
    t.checkExpect(wSimple.activeColor, Color.GREEN);
    t.checkExpect(wSimple.animationQueue, new ArrayList<Cell>());
    this.wSimple.onMouseClicked(new Posn(290, 312), "LeftButton");
    t.checkExpect(wSimple.backgroundColor, new Color(33, 41, 55));
    t.checkExpect(wSimple.stuffColor, new Color(148, 154, 163));
    t.checkExpect(wSimple.darkMode, true);
    t.checkExpect(wSimple.lost, false);
    t.checkExpect(wSimple.won, false);
    t.checkExpect(wSimple.displayFloodAnimation, true);
    t.checkExpect(wSimple.steps, 1);
    t.checkExpect(wSimple.activeColor, Color.RED);
    t.checkExpect(wSimple.animationQueue.size(), 1);
  }

  void testOnKeyEvent(Tester t) {
    this.initData();
    t.checkExpect(wSimple.board.get(0).get(0).flooded, true);
    t.checkExpect(wSimple.board.get(0).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(0).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(0).get(2).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(0).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(2).color, Color.RED);
    t.checkExpect(wSimple.board.get(2).get(0).color, Color.RED);
    t.checkExpect(wSimple.board.get(2).get(1).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(2).color, Color.GREEN);
    wSimple.onKeyEvent("r");
    t.checkExpect(wSimple.board.get(0).get(0).flooded, true);
    t.checkExpect(wSimple.board.get(0).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(0).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(0).get(2).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(1).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(2).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(1).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(2).color, Color.GREEN);
    wSimple.onKeyEvent("q");
    t.checkExpect(wSimple.board.get(0).get(0).flooded, true);
    t.checkExpect(wSimple.board.get(0).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(0).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(0).get(2).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(1).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(2).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(1).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(2).color, Color.GREEN);
  }

  void testOnTick(Tester t) {
    this.initData();
    t.checkExpect(wSimple.tickCount, 0);
    wSimple.onTick();
    t.checkExpect(wSimple.tickCount, 1);
    wSimple.displayFloodAnimation = true;
    wSimple.animationQueue = new ArrayList<IBoardPiece>(Arrays.asList(wSimple.board.get(1).get(2)));
    wSimple.onTick();
    t.checkExpect(wSimple.tickCount, 2);
    t.checkExpect(wSimple.animationQueue.size(), 0);
    t.checkExpect(wSimple.board.get(1).get(2).color, wSimple.activeColor);
    t.checkExpect(wSimple.won, false);
    t.checkExpect(wSimple.lost, false);
  }

  void testIsPosnOverBoard(Tester t) {
    t.checkExpect(wSimple.isPosnOverBoard(new Posn(250, 250)), false);
    t.checkExpect(wSimple.isPosnOverBoard(new Posn(267, 288)), true);
  }

  void testGetCellOverPosn(Tester t) {
    t.checkExpect(wSimple.getCellOverPosn(new Posn(300, 300)), wSimple.board.get(2).get(2));
    t.checkException(new IllegalArgumentException("No cell under this position!"),
        wSimple, "getCellOverPosn", new Posn(100, 100));
  }

  void testUnqueueAll(Tester t) {
    this.initData();
    wSimple.board.get(0).get(0).isQueued = true;
    wSimple.board.get(0).get(1).isQueued = true;
    wSimple.board.get(0).get(2).isQueued = true;
    wSimple.board.get(1).get(0).isQueued = true;
    wSimple.board.get(1).get(1).isQueued = true;
    wSimple.board.get(1).get(2).isQueued = true;
    wSimple.board.get(2).get(0).isQueued = true;
    wSimple.board.get(2).get(1).isQueued = true;
    wSimple.board.get(2).get(2).isQueued = true;
    wSimple.unqueueAll();
    t.checkExpect(wSimple.board.get(0).get(0).isQueued, false);
    t.checkExpect(wSimple.board.get(0).get(1).isQueued, false);
    t.checkExpect(wSimple.board.get(0).get(2).isQueued, false);
    t.checkExpect(wSimple.board.get(1).get(0).isQueued, false);
    t.checkExpect(wSimple.board.get(1).get(1).isQueued, false);
    t.checkExpect(wSimple.board.get(1).get(2).isQueued, false);
    t.checkExpect(wSimple.board.get(2).get(0).isQueued, false);
    t.checkExpect(wSimple.board.get(2).get(1).isQueued, false);
    t.checkExpect(wSimple.board.get(2).get(2).isQueued, false);
    wSimple.unqueueAll();
    t.checkExpect(wSimple.board.get(0).get(0).isQueued, false);
    t.checkExpect(wSimple.board.get(0).get(1).isQueued, false);
    t.checkExpect(wSimple.board.get(0).get(2).isQueued, false);
    t.checkExpect(wSimple.board.get(1).get(0).isQueued, false);
    t.checkExpect(wSimple.board.get(1).get(1).isQueued, false);
    t.checkExpect(wSimple.board.get(1).get(2).isQueued, false);
    t.checkExpect(wSimple.board.get(2).get(0).isQueued, false);
    t.checkExpect(wSimple.board.get(2).get(1).isQueued, false);
    t.checkExpect(wSimple.board.get(2).get(2).isQueued, false);
  }

  void testUnfloodAll(Tester t) {
    this.initData();
    wSimple.board.get(0).get(0).flooded = true;
    wSimple.board.get(0).get(1).flooded = true;
    wSimple.board.get(0).get(2).flooded = true;
    wSimple.board.get(1).get(0).flooded = true;
    wSimple.board.get(1).get(1).flooded = true;
    wSimple.board.get(1).get(2).flooded = true;
    wSimple.board.get(2).get(0).flooded = true;
    wSimple.board.get(2).get(1).flooded = true;
    wSimple.board.get(2).get(2).flooded = true;
    wSimple.unfloodAll();
    t.checkExpect(wSimple.board.get(0).get(0).flooded, false);
    t.checkExpect(wSimple.board.get(0).get(1).flooded, false);
    t.checkExpect(wSimple.board.get(0).get(2).flooded, false);
    t.checkExpect(wSimple.board.get(1).get(0).flooded, false);
    t.checkExpect(wSimple.board.get(1).get(1).flooded, false);
    t.checkExpect(wSimple.board.get(1).get(2).flooded, false);
    t.checkExpect(wSimple.board.get(2).get(0).flooded, false);
    t.checkExpect(wSimple.board.get(2).get(1).flooded, false);
    t.checkExpect(wSimple.board.get(2).get(2).flooded, false);
    wSimple.unfloodAll();
    t.checkExpect(wSimple.board.get(0).get(0).flooded, false);
    t.checkExpect(wSimple.board.get(0).get(1).flooded, false);
    t.checkExpect(wSimple.board.get(0).get(2).flooded, false);
    t.checkExpect(wSimple.board.get(1).get(0).flooded, false);
    t.checkExpect(wSimple.board.get(1).get(1).flooded, false);
    t.checkExpect(wSimple.board.get(1).get(2).flooded, false);
    t.checkExpect(wSimple.board.get(2).get(0).flooded, false);
    t.checkExpect(wSimple.board.get(2).get(1).flooded, false);
    t.checkExpect(wSimple.board.get(2).get(2).flooded, false);
  }

  void testNewGame(Tester t) {
    this.initData();
    t.checkExpect(wSimple.board.get(0).get(0).flooded, true);
    t.checkExpect(wSimple.board.get(0).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(0).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(0).get(2).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(0).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(2).color, Color.RED);
    t.checkExpect(wSimple.board.get(2).get(0).color, Color.RED);
    t.checkExpect(wSimple.board.get(2).get(1).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(2).color, Color.GREEN);
    wSimple.newGame();
    t.checkExpect(wSimple.board.get(0).get(0).flooded, true);
    t.checkExpect(wSimple.board.get(0).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(0).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(0).get(2).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(1).get(1).color, Color.RED);
    t.checkExpect(wSimple.board.get(1).get(2).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(0).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(1).color, Color.GREEN);
    t.checkExpect(wSimple.board.get(2).get(2).color, Color.GREEN);
  }

  void testAllFlooded(Tester t) {
    this.initData();
    t.checkExpect(wSimple.allFlooded(), false);
    wSimple.board.get(0).get(0).flooded = true;
    wSimple.board.get(0).get(1).flooded = true;
    wSimple.board.get(0).get(2).flooded = true;
    wSimple.board.get(1).get(0).flooded = true;
    wSimple.board.get(1).get(1).flooded = true;
    wSimple.board.get(1).get(2).flooded = true;
    wSimple.board.get(2).get(0).flooded = true;
    wSimple.board.get(2).get(1).flooded = true;
    t.checkExpect(wSimple.allFlooded(), false);
    wSimple.board.get(2).get(2).flooded = true;
    t.checkExpect(wSimple.allFlooded(), true);
  }
}
