package edu.brown.cs.pdtran.minesweep.board;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * DefaultBoard is the classic minesweeper board.
 * @author agokasla
 */
public class DefaultBoard implements Board, Cloneable {

  protected Tile[][] grid;
  private final int width;
  private final int height;
  private final int bombCount;

  /**
   * The default constructor.
   */
  public DefaultBoard() {
    this(16, 16, 40);
  }

  /**
   * The constructor that provide the necessary seed information.
   * @param width The width of the board.
   * @param height Th height of the board.
   * @param bombCount The number of bombs on the board.
   */
  public DefaultBoard(int width, int height, int bombCount) {
    this.width = width;
    this.height = height;
    this.bombCount = bombCount;
    initializeBoard();
  }

  /**
   * This board constructor allows you to specify the grid. You may find it
   * useful for testing.
   * @param grid The grid that you get to specify.
   */
  public DefaultBoard(Tile[][] grid) {
    this.width = grid[0].length;
    this.height = grid.length;
    int bombCount = 0;
    for (Tile[] row : grid) {
      for (Tile tile : row) {
        if (tile.isBomb()) {
          bombCount++;
        }
      }
    }
    this.bombCount = bombCount;
    this.grid = grid;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  private void initializeBoard() {
    grid = new Tile[height][width];
    // Initialize all tiles
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j] = new Tile(false, 0, false, i, j);
      }
    }

    // Choose bombs randomly
    int numBombs = bombCount;
    int randomX, randomY;
    Random rn = new Random();
    while (numBombs > 0) {
      randomX = rn.nextInt(width);
      randomY = rn.nextInt(height);

      if (!grid[randomX][randomY].isBomb()) {
        grid[randomX][randomY].makeBomb();
        numBombs--;
      }
    }

    updateBombNumbers();
  }

  /**
   * Updates the bomb number. It is protected so it can be overwrriten
   * later.
   */
  protected void updateBombNumbers() {
    // Calculate adjacent bomb values for each tile
    int adjacentBombCount;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        adjacentBombCount = 0;
        for (Tile t : getAdjacentTiles(i, j)) {
          if (t.isBomb()) {
            adjacentBombCount++;
          }
        }
        grid[i][j].setAdjacentBombs(adjacentBombCount);
      }
    }
  }

  /**
   * Gets the tile at the specified point.
   * @param row The specified row.
   * @param col The specified col.
   * @return The tile you want.
   */
  @Override
  public Tile getTile(int row, int col) {
    return grid[row][col];
  }

  /**
   * Sets the tile at the specified row.
   * @param tile The tile you want to swap.
   * @param row The row you wish to swap out.
   * @param col The col of the tile you wish to swap.
   */
  public void setTile(Tile tile, int row, int col) {
    grid[row][col] = tile;
  }

  /**
   * Gets the tile adjacent to the one at the speciifed row and col. NOTE:
   * You must override this method if you want to change the geometry of
   * the board.
   * @param row The row of tile you want.
   * @param col The col of the tile you want.
   * @return a list of adjacent tiles.
   */
  @Override
  public List<Tile> getAdjacentTiles(int row, int col) {
    int i = row;
    int j = col;
    List<Tile> out = new ArrayList<>();
    for (int ii = -1; ii <= 1; ii++) {
      for (int jj = -1; jj <= 1; jj++) {
        if (isWithinBoard(i + ii, j + jj)) {
          out.add(grid[i + ii][j + jj]);
        }
      }
    }
    return out;
  }

  @Override
  public void makeMove(final int row, final int column) {
    if (isWithinBoard(row, column)) {
      Tile target = grid[row][column];
      if (!target.hasBeenVisited()) {
        target.setVisited();
        // If target has no adjacent bombs, reveal adjacent tiles
        // that have no bombs.
        if (target.getAdjacentBombs() == 0) {
          Deque<Tile> tilesWithNoAdjacentBombs = new ArrayDeque<Tile>();
          LinkedList<Tile> tilesToReveal = new LinkedList<Tile>();
          tilesWithNoAdjacentBombs.add(target);
          Tile candidate;
          int newRow, newColumn;
          // Determine adjacent 'empty' tiles
          while (!tilesWithNoAdjacentBombs.isEmpty()) {
            candidate = tilesWithNoAdjacentBombs.pop();
            List<Tile> newCandidates =
                getAdjacentTiles(candidate.getRow(), candidate.getColumn());
            for (Tile neighbor : newCandidates) {
              if (!tilesToReveal.contains(neighbor)
                  && neighbor.getAdjacentBombs() == 0 && !neighbor.isBomb()) {
                tilesWithNoAdjacentBombs.add(neighbor);
              }
            }
            tilesToReveal.add(candidate);
          }
          // Reveal all tiles adjacent to the 'empty' tiles
          Iterator<Tile> tilesIterator = tilesToReveal.iterator();
          while (tilesIterator.hasNext()) {
            candidate = tilesIterator.next();
            List<Tile> candidateNeighbors =
                getAdjacentTiles(candidate.getRow(), candidate.getColumn());
            for (Tile t : candidateNeighbors) {
              t.setVisited();
            }
          }
        }
      }
    }
  }

  @Override
  public boolean isWinningBoard() {
    // Check for all bombs unvisited, and all non-bombs visited
    Tile currentTile;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        currentTile = grid[i][j];
        // if (currentTile.isBomb() && currentTile.hasBeenVisited()) {
        // return false;
        // }
        if (!currentTile.isBomb() && !currentTile.hasBeenVisited()) {
          System.out.println(i + " " + j);
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isLosingBoard() {
    // Check to see if any bombs have been visited
    Tile currentTile;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        currentTile = grid[i][j];
        if (currentTile.isBomb() && currentTile.hasBeenVisited()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isGameOver() {
    return isWinningBoard() || isLosingBoard();
  }

  /**
   * Tells you if it's within the board.
   * @param x The x position.
   * @param y The y position.
   * @return True if it's within th board, otherwise false.
   */
  public boolean isWithinBoard(final int x, final int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  @Override
  public DefaultBoard clone() {
    DefaultBoard board = new DefaultBoard();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board.setTile(grid[i][j].clone(), i, j);
      }
    }
    return board;
  }

  @Override
  public String toJson() {
    JsonObject boardJson = new JsonObject();
    boardJson.addProperty("width", width);
    boardJson.addProperty("height", height);
    JsonArray tilesJson = new JsonArray();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Tile tile = grid[i][j];
        JsonObject tileJson = new JsonObject();
        tileJson.addProperty("row", tile.getRow());
        tileJson.addProperty("column", tile.getColumn());
        tileJson.addProperty("isBomb", tile.isBomb());
        tileJson.addProperty("visited", tile.hasBeenVisited());
        if (tile.hasBeenVisited()) {
          tileJson.addProperty("adjacentBombs", tile.getAdjacentBombs());
        }
        tilesJson.add(tileJson);
      }
    }
    boardJson.add("tiles", tilesJson);
    String boardType = this.getClass().getSimpleName();
    // boardType = boardType.substring(boardType.indexOf('.'));
    boardJson.addProperty("type", boardType);
    return boardJson.toString();
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + bombCount;
    result = prime * result + Arrays.hashCode(grid);
    result = prime * result + height;
    result = prime * result + width;
    return result;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof DefaultBoard)) {
      return false;
    }
    DefaultBoard other = (DefaultBoard) obj;
    if (bombCount != other.bombCount) {
      return false;
    }
    if (!Arrays.deepEquals(grid, other.grid)) {
      return false;
    }
    if (height != other.height) {
      return false;
    }
    if (width != other.width) {
      return false;
    }
    return true;
  }

  @Override
  public int getBombCount() {
    return bombCount;
  }
}
