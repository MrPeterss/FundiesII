package HW.a10;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import tester.Tester;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// Constants to use in the game
interface IConstants {
  int WINDOW_WIDTH = 1000;
  int WINDOW_HEIGHT = 600;
}

// Represents an edge in the maze
class Edge {
  IBoardPiece from;
  IBoardPiece to;
  int weight;

  Edge(Cell from, Cell to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  // does this edge contain the given cells?
  boolean containsCells(IBoardPiece c1, IBoardPiece c2) {
    return (this.from.equals(c1) && this.to.equals(c2))
        || (this.from.equals(c2) && this.to.equals(c1));
  }

  // are the cell codes equal? (used for merging sets)
  boolean cellCodesAreEqual() {
    return this.from.sameSetCode(this.to);
  }

  // EFFECT: merge the cell sets
  void mergeCellSets() {
    this.from.updateSet(this.to);
  }
}

// Represents a piece of the maze
interface IBoardPiece {

  // get the set from this cell
  boolean sameSetCode(IBoardPiece other);

  // get the set from this cell
  boolean sameSetCodeHelp(Cell other);

  // EFFECT: update the set of this Piece
  void updateSet(IBoardPiece to);

  // EFFECT: helper to update the set of this Piece
  void updateSetHelp(Cell c);

  // EFFECT: Change the neighbor sets
  void changeNeighborSets(int from, int to);

  // should this piece be drawn with max priority?
  boolean shouldOverrideDraw();

  // get the neighbors of this piece
  ArrayList<Cell> neighborsHelp(Cell cell, ArrayList<Edge> edges);

  // get the neighbors of this piece, ignore if it was visited or not
  ArrayList<Cell> neighborsHelpNoVisit(Cell cell, ArrayList<Edge> edges);
}

// Represents the border of a maze
class Border implements IBoardPiece {

  // does this border have the same set code as the other?
  public boolean sameSetCode(IBoardPiece other) {
    return false;
  }

  // helper for same set code
  public boolean sameSetCodeHelp(Cell other) {
    return false;
  }

  // EFFECT: update the set of this Piece
  public void updateSet(IBoardPiece to) {
    // do nothing
  }

  // EFFECT: helper to update the set of this Piece
  public void updateSetHelp(Cell c) {
    // do nothing
  }

  // EFFECT: Change the neighbor sets
  public void changeNeighborSets(int from, int to) {
    // do nothing
  }

  // should this piece be drawn with max priority?
  public boolean shouldOverrideDraw() {
    return true;
  }

  // get the neighbors of this piece
  public ArrayList<Cell> neighborsHelp(Cell cell, ArrayList<Edge> edges) {
    return new ArrayList<Cell>();
  }

  // get the neighbors of this piece, ignore if it was visited or not
  public ArrayList<Cell> neighborsHelpNoVisit(Cell cell, ArrayList<Edge> edges) {
    return new ArrayList<Cell>();
  }

  // override equals
  public boolean equals(Object other) {
    return other instanceof Border;
  }

  // override hashCode
  public int hashCode() {
    return 0;
  }
}

// Represents a cell in a maze
class Cell implements IBoardPiece {
  int x;
  int y;
  IBoardPiece top;
  IBoardPiece bottom;
  IBoardPiece left;
  IBoardPiece right;
  int setCode;
  boolean visited;
  int priority;

  Cell(int x, int y, IBoardPiece top, IBoardPiece bottom, IBoardPiece left,
       IBoardPiece right, int setCode, boolean visited, int priority) {
    this.x = x;
    this.y = y;
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
    this.setCode = setCode;
    this.visited = visited;
    this.priority = priority;
  }

  Cell(int x, int y, int setCode) {
    this(x, y, new Border(), new Border(), new Border(), new Border(), setCode, false,
        9999);
  }

  // EFFECT: update the set of this Piece
  public void updateSet(IBoardPiece to) {
    to.updateSetHelp(this);
  }

  // EFFECT: helper to update the set of this Piece
  public void updateSetHelp(Cell c) {
    c.changeNeighborSets(c.setCode, this.setCode);
  }

  // EFFECT: Change the neighbor sets
  public void changeNeighborSets(int from, int to) {
    if (this.setCode != from) {
      return;
    }
    this.setCode = to;
    this.top.changeNeighborSets(from, to);
    this.bottom.changeNeighborSets(from, to);
    this.left.changeNeighborSets(from, to);
    this.right.changeNeighborSets(from, to);
  }

  // should this piece be drawn with max priority?
  public boolean shouldOverrideDraw() {
    return false;
  }

  // get valid neighbors to add to stack
  public ArrayList<Cell> neighborsHelp(Cell cell, ArrayList<Edge> edges) {
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    for (Edge edge : edges) {
      if (edge.containsCells(this, cell)) {
        return neighbors;
      }
    }
    if (!this.visited) {
      neighbors.add(this);
    }
    return neighbors;
  }

  // get valid neighbors to add to stack, ignore if it was visited or not
  public ArrayList<Cell> neighborsHelpNoVisit(Cell cell, ArrayList<Edge> edges) {
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    for (Edge edge : edges) {
      if (edge.containsCells(this, cell)) {
        return neighbors;
      }
    }
    neighbors.add(this);
    return neighbors;
  }

  // override equals
  public boolean sameSetCode(IBoardPiece other) {
    return other.sameSetCodeHelp(this);
  }

  // override hashCode
  public boolean sameSetCodeHelp(Cell other) {
    return this.setCode == other.setCode;
  }

  // EFFECT: update the right cell
  void updateRight(Cell right) {
    this.right = right;
    right.left = this;
  }

  // EFFECT: update the bottom cell
  void updateBottom(Cell bottom) {
    this.bottom = bottom;
    bottom.top = this;
  }

  // Should this cell have a right wall?
  boolean shouldDrawRight(ArrayList<Edge> e) {
    for (Edge edge : e) {
      if (edge.containsCells(this, this.right)) {
        return true;
      }
    }
    return this.right.shouldOverrideDraw();
  }

  // Should this cell have a bottom wall?
  boolean shouldDrawBottom(ArrayList<Edge> e) {
    for (Edge edge : e) {
      if (edge.containsCells(this, this.bottom)) {
        return true;
      }
    }
    return this.bottom.shouldOverrideDraw();
  }

  // Should this cell have a top wall?
  boolean shouldDrawTop() {
    return this.top.shouldOverrideDraw();
  }

  // Should this cell have a left wall?
  boolean shouldDrawLeft() {
    return this.left.shouldOverrideDraw();
  }

  // get the neighbors of this cell
  ArrayList<Cell> getNeighbors(ArrayList<Edge> edges) {
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    neighbors.addAll(this.left.neighborsHelp(this, edges));
    neighbors.addAll(this.right.neighborsHelp(this, edges));
    neighbors.addAll(this.top.neighborsHelp(this, edges));
    neighbors.addAll(this.bottom.neighborsHelp(this, edges));
    return neighbors;
  }

  public Cell findLowestNeighbor(ArrayList<Edge> edges) {
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    neighbors.addAll(this.left.neighborsHelpNoVisit(this, edges));
    neighbors.addAll(this.right.neighborsHelpNoVisit(this, edges));
    neighbors.addAll(this.top.neighborsHelpNoVisit(this, edges));
    neighbors.addAll(this.bottom.neighborsHelpNoVisit(this, edges));
    Cell lowest = this;
    for (Cell c : neighbors) {
      if (c.priority < lowest.priority) {
        lowest = c;
      }
    }
    return lowest;
  }

  public void reset() {
    this.visited = false;
    this.priority = 9999;
  }
}

// Represents the game board for the maze
class GameBoard implements IConstants {
  ArrayList<ArrayList<Cell>> board;
  ArrayList<Edge> edges;
  Random random;
  int width;
  int height;

  GameBoard(ArrayList<ArrayList<Cell>> board, Random random, ArrayList<Edge> edges,
            int width, int height) {
    this.board = board;
    this.random = random;
    this.edges = edges;
    this.width = width;
    this.height = height;
  }

  // convenience constructor for playing
  GameBoard(int width, int height) {
    this(new ArrayList<ArrayList<Cell>>(), new Random(), new ArrayList<Edge>(), width, height);
    for (int i = 0; i < width; i++) {
      this.board.add(new ArrayList<Cell>());
      for (int j = 0; j < height; j++) {
        this.board.get(i).add(new Cell(i, j, j * width + i));
      }
    }
    this.fixBoard();
  }

  // convenience constructor for the tests
  GameBoard(int width, int height, boolean forTests) {
    this(new ArrayList<ArrayList<Cell>>(), new Random(1), new ArrayList<Edge>(), width,
        height);
    for (int i = 0; i < width; i++) {
      this.board.add(new ArrayList<Cell>());
      for (int j = 0; j < height; j++) {
        this.board.get(i).add(new Cell(i, j, j * width + i));
      }
    }
    this.fixBoard();
  }

  // fix the connections of the cells
  void fixBoard() {
    for (int i = 0; i < this.board.size(); i++) {
      for (int j = 0; j < this.board.get(i).size(); j++) {
        Cell c = this.board.get(i).get(j);
        if (i < this.board.size() - 1) {
          c.updateRight(this.board.get(i + 1).get(j));
          this.edges.add(new Edge(c, this.board.get(i + 1).get(j),
              random.nextInt((int) Math.pow(10, 6))));
        }
        if (j < this.board.get(i).size() - 1) {
          c.updateBottom(this.board.get(i).get(j + 1));
          this.edges.add(new Edge(c, this.board.get(i).get(j + 1),
              random.nextInt((int) Math.pow(10, 6))));
        }
      }
    }
    this.sortEdges();
  }

  // get the cell at the given coordinates
  Cell get(int x, int y) {
    return this.board.get(x).get(y);
  }

  // EFFECT: sort the edges from least weight to greatest weight
  void sortEdges() {
    this.edges.sort((e1, e2) -> e1.weight - e2.weight);
  }

  // get the cell size of the board
  int getCellSize() {
    int limitingScreenSize = Math.min(IConstants.WINDOW_WIDTH, IConstants.WINDOW_HEIGHT);
    return limitingScreenSize / Math.max(this.width, this.height);
  }

  // get the edge thickness of the board
  int getEdgeThickness() {
    return Math.max(1, this.getCellSize() / 10);
  }

  public void reset() {
    for (int i = 0; i < this.board.size(); i++) {
      for (int j = 0; j < this.board.get(i).size(); j++) {
        this.board.get(i).get(j).reset();
      }
    }
  }
}

// Represents a maze
class Maze extends World implements IConstants {

  GameBoard board;
  boolean isInitializing;
  ArrayList<Edge> worklist;
  int index;
  int cellSize;
  int edgeThickness;
  int width;
  int height;
  int iterationsPerTick;

  // variables to handle depth first and breadth first searches
  boolean isSearching;
  boolean isBreadthFirst;
  ArrayList<Cell> stack;
  boolean displayPath;

  Maze(int width, int height) {
    this.board = new GameBoard(width, height);
    this.isInitializing = true;
    this.worklist = new ArrayList<Edge>(this.board.edges);
    this.index = 0;
    this.cellSize = this.board.getCellSize();
    this.edgeThickness = this.board.getEdgeThickness();
    this.width = width;
    this.height = height;
    this.iterationsPerTick = Math.max(1, width * height / 120);
    this.isSearching = false;
    this.isBreadthFirst = false;
    this.stack = new ArrayList<Cell>();
    this.displayPath = false;
  }

  // convenience constructor for the tests
  Maze(int width, int height, boolean forTests) {
    this.board = new GameBoard(width, height, forTests);
    this.isInitializing = true;
    this.worklist = new ArrayList<Edge>(this.board.edges);
    this.index = 0;
    this.cellSize = this.board.getCellSize();
    this.edgeThickness = this.board.getEdgeThickness();
    this.width = width;
    this.height = height;
    this.iterationsPerTick = Math.max(1, width * height / 120);
    this.isSearching = false;
    this.isBreadthFirst = false;
    this.stack = new ArrayList<Cell>();
    this.displayPath = false;
  }

  // Draw the maze
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(WINDOW_WIDTH, WINDOW_HEIGHT);
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        Cell c = this.board.get(i, j);
        if (stack.contains(c)) {
          scene.placeImageXY(new RectangleImage(this.cellSize, this.cellSize,
                  OutlineMode.SOLID, new Color(37, 46, 203)),
              i * this.cellSize + WINDOW_WIDTH / 2 - (this.width * this.cellSize / 2)
                  + this.cellSize / 2,
              j * this.cellSize + WINDOW_HEIGHT / 2 - (this.height * this.cellSize / 2)
                  + this.cellSize / 2);
        }
        else if (c.visited) {
          scene.placeImageXY(new RectangleImage(this.cellSize, this.cellSize,
                  OutlineMode.SOLID, new Color(124, 124, 124)),
              i * this.cellSize + WINDOW_WIDTH / 2 - (this.width * this.cellSize / 2)
                  + this.cellSize / 2,
              j * this.cellSize + WINDOW_HEIGHT / 2 - (this.height * this.cellSize / 2)
                  + this.cellSize / 2);
        }
        if (i == 0 && j == 0) {
          scene.placeImageXY(new RectangleImage(this.cellSize, this.cellSize,
                  OutlineMode.SOLID, Color.GREEN),
              i * this.cellSize + WINDOW_WIDTH / 2 - (this.width * this.cellSize / 2)
                  + this.cellSize / 2,
              j * this.cellSize + WINDOW_HEIGHT / 2 - (this.height * this.cellSize / 2)
                  + this.cellSize / 2);
        }
        else if (i == this.width - 1 && j == this.height - 1) {
          scene.placeImageXY(new RectangleImage(this.cellSize, this.cellSize,
                  OutlineMode.SOLID, Color.RED),
              i * this.cellSize + WINDOW_WIDTH / 2 - (this.width * this.cellSize / 2)
                  + this.cellSize / 2,
              j * this.cellSize + WINDOW_HEIGHT / 2 - (this.height * this.cellSize / 2)
                  + this.cellSize / 2);
        }
        if (c.shouldDrawRight(this.board.edges)) {
          scene.placeImageXY(new RectangleImage(this.edgeThickness, this.cellSize,
                  OutlineMode.SOLID, Color.BLACK),
              i * this.cellSize + WINDOW_WIDTH / 2 - (this.width * this.cellSize / 2)
                  + this.cellSize,
              j * this.cellSize + WINDOW_HEIGHT / 2 - (this.height * this.cellSize / 2)
                  + this.cellSize / 2);
        }
        if (c.shouldDrawBottom(this.board.edges)) {
          scene.placeImageXY(new RectangleImage(this.cellSize, this.edgeThickness,
                  OutlineMode.SOLID, Color.BLACK),
              i * this.cellSize + WINDOW_WIDTH / 2 - (this.width * this.cellSize / 2)
                  + this.cellSize / 2,
              j * this.cellSize + WINDOW_HEIGHT / 2 - (this.height * this.cellSize / 2)
                  + this.cellSize);
        }
        if (c.shouldDrawLeft()) {
          scene.placeImageXY(new RectangleImage(this.edgeThickness, this.cellSize,
                  OutlineMode.SOLID, Color.BLACK),
              i * this.cellSize + WINDOW_WIDTH / 2 - (this.width * this.cellSize / 2),
              j * this.cellSize + WINDOW_HEIGHT / 2 - (this.height * this.cellSize / 2)
                  + this.cellSize / 2);
        }
        if (c.shouldDrawTop()) {
          scene.placeImageXY(new RectangleImage(this.cellSize, this.edgeThickness,
                  OutlineMode.SOLID, Color.BLACK),
              i * this.cellSize + WINDOW_WIDTH / 2 - (this.width * this.cellSize / 2)
                  + this.cellSize / 2,
              j * this.cellSize + WINDOW_HEIGHT / 2 - (this.height * this.cellSize / 2));
        }
      }
    }
    return scene;
  }

  // Handle the tick event
  public void onTick() {
    if (this.isInitializing) {
      for (int z = 0; z < this.iterationsPerTick; z++) {
        for (int i = 0; i < 1; i++) {
          if (this.worklist.size() == 0) {
            this.isInitializing = false;
            return;
          }
          // get the cheapest edge in the edges list
          Edge e = this.worklist.get(0);
          // if the cells that edge divides are different sets
          while (e.cellCodesAreEqual()) {
            if (this.worklist.size() == 0) {
              this.isInitializing = false;
              return;
            }
            e = this.worklist.remove(0);
          }
          // remove the edge from the edges list
          this.board.edges.remove(e);
          // merge the sets, keep the lower code
          e.mergeCellSets();
        }
      }
    } else if (this.isSearching) {
      for (int z = 0; z < this.iterationsPerTick; z++) {
        if (!this.isBreadthFirst) {
          if (stack.size() == 0) {
            this.stack.add(this.board.get(0, 0));
          }
          if (stack.get(stack.size() - 1).equals(this.board.get(this.width - 1,
              this.height - 1))) {
            this.isSearching = false;
            return;
          }
          // depth-first search
          ArrayList<Cell> neighbors = stack.get(stack.size() - 1).getNeighbors(this.board.edges);
          if (neighbors.size() == 0) {
            stack.remove(stack.size() - 1);
          } else {
            Cell c = neighbors.get(0);
            stack.add(c);
            c.visited = true;
          }
        }
        else {
          if (this.displayPath) {
            if (this.stack.get(this.stack.size() - 1).equals(this.board.get(0, 0))) {
              this.displayPath = false;
              this.isSearching = false;
              return;
            }
            Cell nextCell = this.stack.get(this.stack.size()
                - 1).findLowestNeighbor(this.board.edges);
            this.stack.add(nextCell);
            return;
          }
          else {
            Cell current = stack.remove(0);

            if (current.equals(this.board.get(this.width - 1, this.height - 1))) {
              this.displayPath = true;
              this.stack = new ArrayList<Cell>();
              this.stack.add(this.board.get(this.width - 1, this.height - 1));
              return;
            }
            // breadth-first search
            ArrayList<Cell> neighbors = current.getNeighbors(this.board.edges);
            for (Cell c : neighbors) {
              stack.add(c);
              c.visited = true;
              c.priority = current.priority + 1;
            }
          }
        }
      }
    }
  }

  // Handle the key event
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      // reset everything
      this.board = new GameBoard(this.width, this.height);
      this.isInitializing = true;
      this.worklist = new ArrayList<Edge>(this.board.edges);
      this.index = 0;
      this.isSearching = false;
      this.isBreadthFirst = false;
      this.displayPath = false;
      this.stack = new ArrayList<Cell>();
    }
    if (this.isInitializing || this.isSearching) {
      return;
    }
    if (key.equals("d")) {
      // initiate depth-first search
      this.isSearching = true;
      this.isBreadthFirst = false;
      this.board.reset();
      this.stack = new ArrayList<Cell>();
      this.stack.add(this.board.get(0, 0));
      this.stack.get(0).visited = true;
    } else if (key.equals("b")) {
      // initiate breadth-first search
      this.isSearching = true;
      this.isBreadthFirst = true;
      this.displayPath = false;
      this.board.reset();
      this.stack = new ArrayList<Cell>();
      this.stack.add(this.board.get(0, 0));
      this.stack.get(0).visited = true;
      this.stack.get(0).priority = 0;
    }
  }
}

// represents examples
class ExamplesMaze implements IConstants {

  Maze m;
  Maze toPlay;

  Cell c1;
  Cell c2;
  Cell c3;
  Border b1;

  Edge e1;
  Edge e2;

  GameBoard board;

  void initData() {
    this.m = new Maze(2, 1, true);
    this.toPlay = new Maze(5, 5);
    this.c1 = new Cell(0, 0, 0);
    this.c2 = new Cell(0, 1, c1, new Border(), new Border(), new Border(), 0,
        false, 0);
    this.c3 = new Cell(1, 0, 1);
    this.b1 = new Border();
    this.e1 = new Edge(c1, c2, 0);
    this.e2 = new Edge(c1, c3, 0);
    this.board = new GameBoard(20, 15, true);
  }

  // test the maze
  void testMaze(Tester t) {
    this.initData();
    toPlay.bigBang(IConstants.WINDOW_WIDTH, IConstants.WINDOW_HEIGHT, Math.pow(10, -10));
  }

  // tests for containsCell
  void testContainsCell(Tester t) {
    this.initData();
    t.checkExpect(this.e1.containsCells(c1, c2), true);
    t.checkExpect(this.e1.containsCells(c2, c1), true);
    t.checkExpect(this.e1.containsCells(c1, c1), false);
    t.checkExpect(this.e1.containsCells(c2, c2), false);
    t.checkExpect(this.e1.containsCells(c1, c3), false);
    t.checkExpect(this.e1.containsCells(c3, c1), false);
    t.checkExpect(this.e1.containsCells(c2, c3), false);
    t.checkExpect(this.e1.containsCells(c3, c2), false);
  }

  // tests for codesAreEqual
  void testCodesAreEqual(Tester t) {
    this.initData();
    t.checkExpect(this.e1.cellCodesAreEqual(), true);
    t.checkExpect(this.e2.cellCodesAreEqual(), false);
  }

  // tests for mergeCellSets
  void testMergeCellSets(Tester t) {
    this.initData();
    t.checkExpect(this.c1.setCode, 0);
    t.checkExpect(this.c2.setCode, 0);
    t.checkExpect(this.c3.setCode, 1);
    this.e1.mergeCellSets();
    t.checkExpect(this.c1.setCode, 0);
    t.checkExpect(this.c2.setCode, 0);
    t.checkExpect(this.c3.setCode, 1);
    this.e2.mergeCellSets();
    t.checkExpect(this.c1.setCode, 1);
    t.checkExpect(this.c2.setCode, 0);
    t.checkExpect(this.c3.setCode, 1);
  }

  // tests for sameSetCode
  void testSameSetCode(Tester t) {
    this.initData();
    t.checkExpect(this.c1.sameSetCode(this.c2), true);
    t.checkExpect(this.c1.sameSetCode(this.c3), false);
    t.checkExpect(this.b1.sameSetCode(this.c1), false);
    t.checkExpect(this.b1.sameSetCode(new Border()), false);
  }

  // tests for sameSetCodeHelp
  void testSameSetCodeHelp(Tester t) {
    this.initData();
    t.checkExpect(this.c1.sameSetCodeHelp(this.c2), true);
    t.checkExpect(this.c1.sameSetCodeHelp(this.c3), false);
    t.checkExpect(this.b1.sameSetCodeHelp(this.c1), false);
  }

  // tests for updateSet
  void testUpdateSet(Tester t) {
    this.initData();
    t.checkExpect(this.c1.setCode, 0);
    t.checkExpect(this.c2.setCode, 0);
    t.checkExpect(this.c3.setCode, 1);
    this.c2.updateSet(c3);
    t.checkExpect(this.c1.setCode, 1);
    t.checkExpect(this.c2.setCode, 1);
    t.checkExpect(this.c3.setCode, 1);
    this.b1.updateSet(c1);
    t.checkExpect(this.c1.setCode, 1);
    t.checkExpect(this.c2.setCode, 1);
    t.checkExpect(this.c3.setCode, 1);
    this.b1.updateSet(new Border());
    t.checkExpect(this.c1.setCode, 1);
    t.checkExpect(this.c2.setCode, 1);
    t.checkExpect(this.c3.setCode, 1);
  }

  // tests for updateSetHelp
  void testUpdateSetHelp(Tester t) {
    this.initData();
    t.checkExpect(this.c1.setCode, 0);
    t.checkExpect(this.c2.setCode, 0);
    t.checkExpect(this.c3.setCode, 1);
    this.c2.updateSetHelp(c3);
    t.checkExpect(this.c1.setCode, 0);
    t.checkExpect(this.c2.setCode, 0);
    t.checkExpect(this.c3.setCode, 0);
  }

  // tests for changeNeighborSets
  void testChangeNeighborSets(Tester t) {
    this.initData();
    t.checkExpect(this.c1.setCode, 0);
    t.checkExpect(this.c2.setCode, 0);
    t.checkExpect(this.c3.setCode, 1);
    this.c1.changeNeighborSets(0, 1);
    t.checkExpect(this.c1.setCode, 1);
    t.checkExpect(this.c2.setCode, 0);
    t.checkExpect(this.c3.setCode, 1);
    this.c1.setCode = 0;
    this.c2.changeNeighborSets(0, 1);
    t.checkExpect(this.c1.setCode, 1);
    t.checkExpect(this.c2.setCode, 1);
    t.checkExpect(this.c3.setCode, 1);
  }

  // tests for shouldOverrideDraw
  void testShouldOverrideDraw(Tester t) {
    this.initData();
    t.checkExpect(this.c1.shouldOverrideDraw(), false);
    t.checkExpect(this.c2.shouldOverrideDraw(), false);
    t.checkExpect(this.c3.shouldOverrideDraw(), false);
    t.checkExpect(this.b1.shouldOverrideDraw(), true);
  }

  // tests for Equals
  void testOverrideEqualsBorder(Tester t) {
    this.initData();
    t.checkExpect(this.b1.equals(new Border()), true);
    t.checkExpect(this.b1.equals(this.c1), false);
  }

  // tests for getNeighbors
  void testGetNeighbor(Tester t) {
    t.checkExpect(this.m.board.get(0, 0).getNeighbors(new ArrayList<Edge>()),
        new ArrayList<Cell>(Arrays.asList(this.m.board.get(1, 0))));
  }

  // tests for findLowestNeighbor
  void testFindLowestNeighbor(Tester t) {
    this.initData();
    t.checkExpect(this.m.board.get(0, 0).findLowestNeighbor(new ArrayList<Edge>()),
        this.m.board.get(0, 0));
    t.checkExpect(this.m.board.get(0, 0).findLowestNeighbor(new ArrayList<Edge>()),
        this.m.board.get(0, 0));
  }

  // tests for neighborHelp
  void testNeighborHelp(Tester t) {
    this.initData();
    t.checkExpect(this.m.board.get(0, 0).neighborsHelp(this.board.get(0, 0),
            new ArrayList<Edge>()),
        new ArrayList<Cell>(Arrays.asList(this.m.board.get(0, 0))));
    t.checkExpect(this.m.board.get(0, 0).neighborsHelp(this.board.get(0, 0),
            new ArrayList<Edge>()),
        new ArrayList<Cell>(Arrays.asList(this.m.board.get(0, 0))));
  }

  // tests for neighborHelpNoVisit
  void testNeighborHelpNoVisit(Tester t) {
    this.initData();
    t.checkExpect(this.m.board.get(0, 0).neighborsHelpNoVisit(this.board.get(0, 0),
        new ArrayList<Edge>()), new ArrayList<Cell>(Arrays.asList(this.m.board.get(0, 0))));
    t.checkExpect(this.m.board.get(0, 0).neighborsHelpNoVisit(this.board.get(0, 0),
        new ArrayList<Edge>()), new ArrayList<Cell>(Arrays.asList(this.m.board.get(0, 0))));
  }

  // tests for overriding hash code
  void testOverrideHashCodeBorder(Tester t) {
    this.initData();
    t.checkExpect(this.b1.hashCode(), 0);
    t.checkExpect(new Border().hashCode(), 0);
  }

  // tests for updateRight
  void testUpdateRight(Tester t) {
    this.initData();
    t.checkExpect(this.c1.right, this.b1);
    t.checkExpect(this.c2.right, this.b1);
    t.checkExpect(this.c3.right, this.b1);
    this.c1.updateRight(this.c2);
    t.checkExpect(this.c1.right, this.c2);
    t.checkExpect(this.c2.left, this.c1);
    this.c1.updateRight(this.c3);
    t.checkExpect(this.c1.right, this.c3);
    t.checkExpect(this.c3.left, this.c1);
  }

  // tests for updateBottom
  void testUpdateBottom(Tester t) {
    this.initData();
    t.checkExpect(this.c1.bottom, this.b1);
    t.checkExpect(this.c2.bottom, this.b1);
    t.checkExpect(this.c3.bottom, this.b1);
    this.c1.updateBottom(this.c2);
    t.checkExpect(this.c1.bottom, this.c2);
    t.checkExpect(this.c2.top, this.c1);
    this.c1.updateBottom(this.c3);
    t.checkExpect(this.c1.bottom, this.c3);
    t.checkExpect(this.c3.top, this.c1);
  }

  // tests for shouldDrawRight
  void testShouldDrawRight(Tester t) {
    this.initData();
    this.c1.right = c2;
    t.checkExpect(this.c1.shouldDrawRight(new ArrayList<>(Arrays.asList(new Edge(c1,
        c2, 0)))), true);
    t.checkExpect(this.c1.shouldDrawRight(new ArrayList<>(Arrays.asList(new Edge(c2,
        c1, 0)))), true);
    t.checkExpect(this.c1.shouldDrawRight(new ArrayList<>(Arrays.asList(new Edge(c1,
        c3, 0)))), false);
  }

  // tests for shouldDrawBottom
  void testShouldDrawBottom(Tester t) {
    this.initData();
    this.c1.bottom = c2;
    t.checkExpect(this.c1.shouldDrawBottom(new ArrayList<>(Arrays.asList(new Edge(c1,
        c2, 0)))), true);
    t.checkExpect(this.c1.shouldDrawBottom(new ArrayList<>(Arrays.asList(new Edge(c2,
        c1, 0)))), true);
    t.checkExpect(this.c1.shouldDrawBottom(new ArrayList<>(Arrays.asList(new Edge(c1,
        c3, 0)))), false);
  }

  // tests for shouldDrawLeft
  void testShouldDrawLeft(Tester t) {
    this.initData();
    this.c1.left = c2;
    t.checkExpect(this.c1.shouldDrawLeft(), false);
    t.checkExpect(this.c2.shouldDrawLeft(), true);
  }

  // tests for shouldDrawTop
  void testShouldDrawTop(Tester t) {
    this.initData();
    this.c1.top = c2;
    t.checkExpect(this.c1.shouldDrawTop(), false);
    t.checkExpect(this.c2.shouldDrawTop(), false);
  }

  // tests for fixBoard
  void testFixBoard(Tester t) {
    this.initData();
    t.checkExpect(this.board.get(0, 0).right, this.board.get(1, 0));
    t.checkExpect(this.board.get(0, 0).bottom, this.board.get(0, 1));
    t.checkExpect(this.board.get(0, 0).left, new Border());
    t.checkExpect(this.board.get(0, 0).top, new Border());
    t.checkExpect(this.board.get(1, 0).right, this.board.get(2, 0));
    t.checkExpect(this.board.get(1, 0).bottom, this.board.get(1, 1));
    t.checkExpect(this.board.get(1, 0).left, this.board.get(0, 0));
    t.checkExpect(this.board.get(1, 0).top, new Border());
    t.checkExpect(this.board.get(0, 1).right, this.board.get(1, 1));
    t.checkExpect(this.board.get(0, 1).bottom, this.board.get(0, 2));
  }

  // tests for get
  void testGet(Tester t) {
    this.initData();
    t.checkExpect(this.board.get(0, 0), this.board.board.get(0).get(0));
    t.checkExpect(this.board.get(1, 0), this.board.board.get(1).get(0));
    t.checkExpect(this.board.get(0, 1), this.board.board.get(0).get(1));
    t.checkExpect(this.board.get(1, 1), this.board.board.get(1).get(1));
  }

  // tests for sortEdges
  void testSortEdges(Tester t) {
    this.initData();
    this.board.edges = new ArrayList<>(Arrays.asList(new Edge(c1, c2, 2),
        new Edge(c1, c3, 3), new Edge(c1, c2, 0), new Edge(c1, c3, 1)));
    this.board.sortEdges();
    t.checkExpect(this.board.edges,
        new ArrayList<>(Arrays.asList(new Edge(c1, c2, 0),
            new Edge(c1, c3, 1), new Edge(c1, c2, 2), new Edge(c1, c3, 3))));
  }

  // tests for getCellSize
  void testGetCellSize(Tester t) {
    this.initData();
    t.checkExpect(this.board.getCellSize(), 30);
  }

  // tests for getEdgeThickness
  void testGetEdgeThickness(Tester t) {
    this.initData();
    t.checkExpect(this.board.getEdgeThickness(), 3);
  }

  // tests for makeScene
  void testMakeScene(Tester t) {
    this.initData();
    t.checkExpect(this.m.edgeThickness, 30);
    t.checkExpect(this.m.cellSize, 300);
    WorldScene scene = new WorldScene(1000, 600);
    scene.placeImageXY(new RectangleImage(300, 300, OutlineMode.SOLID, Color.GREEN),
        350, 300);
    scene.placeImageXY(new RectangleImage(30, 300, OutlineMode.SOLID, Color.BLACK),
        500, 300);
    scene.placeImageXY(new RectangleImage(300, 30, OutlineMode.SOLID, Color.BLACK),
        350, 450);
    scene.placeImageXY(new RectangleImage(30, 300, OutlineMode.SOLID, Color.BLACK),
        200, 300);
    scene.placeImageXY(new RectangleImage(300, 30, OutlineMode.SOLID, Color.BLACK),
        350, 150);
    scene.placeImageXY(new RectangleImage(300, 300, OutlineMode.SOLID, Color.RED),
        650, 300);
    scene.placeImageXY(new RectangleImage(30, 300, OutlineMode.SOLID, Color.BLACK),
        800, 300);
    scene.placeImageXY(new RectangleImage(300, 30, OutlineMode.SOLID, Color.BLACK),
        650, 450);
    scene.placeImageXY(new RectangleImage(300, 30, OutlineMode.SOLID, Color.BLACK),
        650, 150);
    t.checkExpect(this.m.makeScene(), scene);
  }

  // tests for onTick
  void testOnTick(Tester t) {
    this.initData();
    t.checkExpect(this.m.isInitializing, true);
    t.checkExpect(this.m.board.edges,
        new ArrayList<Edge>(Arrays.asList(new Edge(this.m.board.get(0, 0),
            this.m.board.get(1, 0), 548985))));
    this.m.onTick();
    t.checkExpect(this.m.board.edges, new ArrayList<Edge>());
    this.m.onTick();
    t.checkExpect(this.m.isInitializing, false);
    this.m.onKeyEvent("d");
    t.checkExpect(this.m.isBreadthFirst, false);
    t.checkExpect(this.m.isInitializing, false);
    t.checkExpect(this.m.isSearching, true);
    t.checkExpect(this.m.stack.size(), 1);
    this.m.onTick();
    t.checkExpect(this.m.stack.size(), 2);
    this.m.onTick();
    t.checkExpect(this.m.stack.size(), 2);
    t.checkExpect(this.m.isSearching, false);
    this.m.onKeyEvent("b");
    t.checkExpect(this.m.isBreadthFirst, true);
    t.checkExpect(this.m.isInitializing, false);
    t.checkExpect(this.m.isSearching, true);
    t.checkExpect(this.m.displayPath, false);
    t.checkExpect(this.m.stack.size(), 1);
    this.m.onTick();
    t.checkExpect(this.m.stack.size(), 1);
    this.m.onTick();
    t.checkExpect(this.m.stack.size(), 1);
    this.m.onTick();
    t.checkExpect(this.m.displayPath, true);
    t.checkExpect(this.m.stack.size(), 2);
    this.m.onTick();
    t.checkExpect(this.m.isSearching, false);
  }

  // tests for onKeyPressed
  void testKeyPressed(Tester t) {
    this.initData();
    t.checkExpect(this.m.isBreadthFirst, false);
    t.checkExpect(this.m.isInitializing, true);
    t.checkExpect(this.m.isSearching, false);
    this.m.onKeyEvent("d");
    t.checkExpect(this.m.isBreadthFirst, false);
    t.checkExpect(this.m.isInitializing, true);
    t.checkExpect(this.m.isSearching, false);
    this.m.onKeyEvent("b");
    t.checkExpect(this.m.isBreadthFirst, false);
    t.checkExpect(this.m.isInitializing, true);
    t.checkExpect(this.m.isSearching, false);
    this.m.isInitializing = false;
    this.m.onKeyEvent("d");
    t.checkExpect(this.m.isBreadthFirst, false);
    t.checkExpect(this.m.isInitializing, false);
    t.checkExpect(this.m.isSearching, true);
    this.m.isSearching = false;
    this.m.onKeyEvent("b");
    t.checkExpect(this.m.isBreadthFirst, true);
    t.checkExpect(this.m.isInitializing, false);
    t.checkExpect(this.m.isSearching, true);
    this.m.onKeyEvent("r");
    t.checkExpect(this.m.isInitializing, true);
    t.checkExpect(this.m.isSearching, false);
  }
}