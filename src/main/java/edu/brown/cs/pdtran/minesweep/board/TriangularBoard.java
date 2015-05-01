package edu.brown.cs.pdtran.minesweep.board;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * This class implements triangular boards for iterations to the game.
 * @author agokasla
 */
public class TriangularBoard extends DefaultBoard implements Board, Cloneable {

  /**
   * The constructor.
   */
  public TriangularBoard() {
    super();
  }

  /**
   * The Constructor.
   * @param grid the tile grid. Use only for testing.
   */
  public TriangularBoard(Tile[][] grid) {
    super(grid);
  }

  @Override
  public List<Tile> getAdjacentTiles(int row, int col) {
    List<Tile> tiles = new ArrayList<>(3);

    boolean goDown = col % 2 == 1; // decides which side the isoceles
                                   // triangle
    // is.
    int newRow = goDown ? row - 1 : row + 1;

    if (isWithinBoard(newRow, col)) {
      tiles.add(grid[newRow][col]);
    }

    for (int i = -1; i <= 1; i++) {
      if (isWithinBoard(row, col + i) && i != 0) {
        tiles.add(grid[row][col + i]);
      }
    }
    return tiles;
  }

  @Override
  public TriangularBoard clone() {
    Tile[][] newGrid = new Tile[getHeight()][getWidth()];
    for (int i = 0; i < newGrid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        newGrid[i][j] = grid[i][j].clone();
      }
    }
    return new TriangularBoard(newGrid);
  }

}
