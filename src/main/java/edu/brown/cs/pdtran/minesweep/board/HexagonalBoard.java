package edu.brown.cs.pdtran.minesweep.board;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.tile.Tile;
import edu.brown.cs.pdtran.minesweep.types.BoardType;

/**
 * Represents a board with hexagonal-shaped tiles.
 * @author Clayton Sanford
 */
public class HexagonalBoard extends DefaultBoard implements Board,
Cloneable {

  private static final int ADJACENT_ARRAY_SIZE = 3;
  private static final int SURROUNDING_TILES = 6;

  /**
   * The constructor.
   * @param width An integer representing the width in tiles.
   * @param height An integer representing the height in tiles.
   * @param mines The number of mines on the board.
   */
  public HexagonalBoard(int width, int height, int mines) {
    super(width, height, mines);
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
    List<Tile> tiles = new ArrayList<>(ADJACENT_ARRAY_SIZE);

    boolean isIndented = col % 2 == 1; // decides which side the isoceles
    // triangle
    // is.

    int dRow = isIndented ? row : row - 1;

    for (int i = -1; i <= 1; i += 2) {
      if (isWithinBoard(col, row + i)) {
        tiles.add(grid[row + i][col]);
      }
    }

    for (int i = -1; i <= 1; i += 2) {
      for (int j = 0; j <= 1; j++) {
        if (isWithinBoard(col + i, dRow + j)) {
          tiles.add(grid[dRow + j][col + i]);
        }
      }

      assert (tiles.size() <= SURROUNDING_TILES);

      // for (int newCol = -1; newCol <= 1; newCol++) {
      // // System.out.println("newCol:");
      // for (int i = 0; i <= 1; i++) {
      // if (isWithinBoard(col + newCol, dRow + i) && newCol == 0) {
      // Tile t = getTile(dRow + i, col + newCol);
      // tiles.add(t);
      // // System.out.println("hello");
      // // System.out.println(t.getColumn());
      // }
      // }
    }


    return tiles;
  }

  @Override
  protected BoardType getBoardType() {
    return BoardType.HEXAGONAL;
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
