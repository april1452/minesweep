package edu.brown.cs.pdtran.minesweep.board;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

public class HexagonalBoard extends DefaultBoard implements Board, Cloneable {

  /**
   * The constructor.
   */
  public HexagonalBoard() {
    super();
  }

  /**
   * The Constructor.
   * @param grid the tile grid. Use only for testing.
   */
  public HexagonalBoard(Tile[][] grid) {
    super(grid);
  }

  @Override
  public List<Tile> getAdjacentTiles(int row, int col) {
    List<Tile> tiles = new ArrayList<>(3);

    boolean isIndented = col % 2 == 1; // decides which side the isoceles
    // triangle
    // is.
    int newCol = isIndented ? col - 1 : col + 1;

    for (int i = -1; i <= 1; i += 2) {
      if (isWithinBoard(row + i, col)) {
        tiles.add(grid[row + i][col]);
      }
      if (isWithinBoard(row + i, newCol)) {
        tiles.add(grid[row + i][newCol]);
      }
    }

    for (int i = -1; i <= 1; i++) {
      if (isWithinBoard(row, col + i) && i != 0) {
        tiles.add(grid[row][col + i]);
      }
    }
    return tiles;
  }

  @Override
  public HexagonalBoard clone() {
    Tile[][] newGrid = new Tile[getHeight()][getWidth()];
    for (int i = 0; i < newGrid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        newGrid[i][j] = grid[i][j].clone();
      }
    }
    return new HexagonalBoard(newGrid);
  }

}
