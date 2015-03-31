package edu.brown.cs.pdtran.minesweep.board;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

public class TriangularBoard extends DefaultBoard implements Board {

  public TriangularBoard() {
    super();
  }

  @Override
  public List<Tile> getAdjacentTiles(int row, int col) {
    boolean goDown = col % 2 == 0; // Will go down if the triangular is oriented

    List<Tile> tiles = new ArrayList<>(3);
    int newRow = goDown ? row - 1 : row + 1;

    // like A, otherise it's like a V.
    if (isWithinBoard(newRow, col)) {
      tiles.add(grid[newRow][col]);
    }

    for (int i = -1; i < 1; i++) {
      if (isWithinBoard(row, col + i)) {
        tiles.add(grid[row][col + i]);
      }
    }
    return tiles;
  }
}
