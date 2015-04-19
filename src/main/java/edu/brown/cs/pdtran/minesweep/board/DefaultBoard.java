package edu.brown.cs.pdtran.minesweep.board;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * DefaultBoard is the classic minesweeper board.
 *
 * @author agokasla
 *
 */
public class DefaultBoard implements Board, Cloneable {

  protected Tile[][] grid;
  private final int width;
  private final int height;
  private final int bombCount;

  public DefaultBoard() {
    this(16, 16, 40);
  }

  public DefaultBoard(int width, int height, int bombCount) {
    this.width = width;
    this.height = height;
    this.bombCount = bombCount;
    initializeBoard();
  }

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

  public int getHeight() {
    return height;
  }

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
        // for (int ii = -1; ii <= 1; ii++) {
        // for (int jj = -1; jj <= 1; jj++) {
        // if (isWithinBoard(i + ii, j + jj) && grid[i + ii][j + jj].isBomb()) {
        // adjacentBombCount++;
        // }
        // }
        // }
        grid[i][j].setAdjacentBombs(adjacentBombCount);
      }
    }
  }
  
  public Tile getTile (int row, int col) {
    return grid[row][col];
  }

  public List<Tile> getAdjacentTiles(int row, int col) {
    int i = row;
    int j = col;
    List<Tile> out = new ArrayList<>();
    for (int ii = -1; ii <= 1; ii++) {
      for (int jj = -1; jj <= 1; jj++) {
        if (isWithinBoard(i + ii, j + jj)) {
          out.add(grid[ii][jj]);
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
          Tile candidate, neighbor;
          int newRow, newColumn;
          // Determine adjacent 'empty' tiles
          while (!tilesWithNoAdjacentBombs.isEmpty()) {
            candidate = tilesWithNoAdjacentBombs.pop();
            for (int i = -1; i <= 1; i++) {
              for (int j = -1; j <= 1; j++) {
                // Check all of candidate's neighbors
                newRow = candidate.getRow() + i;
                newColumn = candidate.getColumn() + j;
                if ((i != 0 || j != 0) && isWithinBoard(newRow, newColumn)) {
                  neighbor = grid[newRow][newColumn];
                  // If is not already in list of tiles & should be revealed,
                  // add
                  if (!tilesToReveal.contains(neighbor)
                      && neighbor.getAdjacentBombs() == 0) {
                    tilesWithNoAdjacentBombs.add(neighbor);
                  }
                }
              }
            }
            tilesToReveal.add(candidate);
          }
          // Reveal all tiles adjacent to the 'empty' tiles
          Iterator<Tile> tilesIterator = tilesToReveal.iterator();
          while (tilesIterator.hasNext()) {
            candidate = tilesIterator.next();
            for (int i = -1; i <= 1; i++) {
              for (int j = -1; j <= 1; j++) {
                newRow = candidate.getRow() + i;
                newColumn = candidate.getColumn() + j;
                if (isWithinBoard(newRow, newColumn)) {
                  grid[candidate.getRow() + i][candidate.getColumn() + j]
                      .setVisited();
                }
              }
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
        if (currentTile.isBomb() && currentTile.hasBeenVisited()) {
          return false;
        }
        if (!currentTile.isBomb() && !currentTile.hasBeenVisited()) {
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

  public boolean isWithinBoard(final int x, final int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  @Override
  public Board clone() {
    return new DefaultBoard(Arrays.copyOf(grid, grid.length));
  }

}
