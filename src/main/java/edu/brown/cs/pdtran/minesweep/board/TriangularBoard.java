package edu.brown.cs.pdtran.minesweep.board;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.tile.Tile;
import edu.brown.cs.pdtran.minesweep.types.BoardType;

/**
 * This class implements triangular boards for iterations to the game.
 * @author agokasla
 */
public class TriangularBoard extends DefaultBoard implements Board,
Cloneable {

  private static final int ADJACENT_ARRAY_SIZE = 3;

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

  /**
   * Constucts a triangular board.
   * @param width The width of the tiles in the board.
   * @param height The height of the tiles in the board.
   * @param mines The total number of mines in the board.
   */
  public TriangularBoard(int width, int height, int mines) {
    super(width, height, mines);
  }

  @Override
  public List<Tile> getAdjacentTiles(int row, int col) {
    List<Tile> tiles = new ArrayList<>(ADJACENT_ARRAY_SIZE);

    boolean goDown = col % 2 == 0; // decides which side the isoceles
    // triangle
    // is.
    int newRow = goDown ? row - 1 : row + 1;
    int newCol = goDown ? col + 1 : col - 1;

    if (isWithinBoard(newCol, newRow)) {
      tiles.add(grid[newRow][newCol]);
    }

    for (int i = -1; i <= 1; i += 2) {
      if (isWithinBoard(row, col + i)) {
        tiles.add(grid[row][col + i]);
      }
    }
    return tiles;
  }

  @Override
  public BoardType getBoardType() {
    return BoardType.TRIANGULAR;
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
