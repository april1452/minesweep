package edu.brown.cs.pdtran.minesweep.board;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * This class implements triangular boards for iterations to the game.
 *
 * @author agokasla
 *
 */
public class TriangularBoard extends DefaultBoard implements Board {

  /**
   * The constructor.
   */
  public TriangularBoard() {
    super();
  }

  /**
   * The Constructor.
   * 
   * @param grid the tile grid. Use only for testing.
   */
  public TriangularBoard(Tile[][] grid) {
    super(grid);
  }

  @Override
  public List<Tile> getAdjacentTiles(int row, int col) {
    List<Tile> tiles = new ArrayList<>(3);

    boolean goDown = col % 2 == 0; // decides which side the isoceles triangle
    // is.
    int newRow = goDown ? row - 1 : row + 1;

    if (isWithinBoard(newRow, col)) {
      tiles.add(grid[newRow][col]);
    }

    for (int i = -1; i < 1; i++) {
      if (isWithinBoard(row, col + i) && i != 0) {
        tiles.add(grid[row][col + i]);
      }
    }
    return tiles;
  }
}
