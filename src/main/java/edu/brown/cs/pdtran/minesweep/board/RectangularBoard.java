package edu.brown.cs.pdtran.minesweep.board;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

public class RectangularBoard extends DefaultBoard implements Board {

  private Table<Integer, Integer, List<Tile>> neighborTable = HashBasedTable
      .create();

  public RectangularBoard() {
    super();
    reconfigureBoard(getWidth() * getHeight() / 10);
  }

  public RectangularBoard(Tile[][] grid) {
    super(grid);
  }

  public void reconfigureBoard(int mergeNum) {
    for (int i = 0; i < mergeNum; i++) {
      int row = (int) (Math.random() * getHeight());
      int col = (int) (Math.random() * getWidth());
      List<Tile> candidateList =
          super
              .getAdjacentTiles(row, col)
              .stream()
              .filter(
                  (t) -> (t.getColumn() == col || t.getRow() == row)
                      && !t.isBomb()).collect(Collectors.toList());
      Tile randomTile =
          candidateList.get((int) (Math.random() * candidateList.size()));
      assert (!randomTile.isBomb());
      assert (!randomTile.hasBeenVisited());
      mergeTiles(row, col, randomTile.getRow(), randomTile.getColumn());
    }
  }

  private void mergeTiles(int row, int col, int row2, int col2) {
    Tile tile = getTile(row, col);
    Tile tile2merge = getTile(row2, col2);
    tile.setAdjacentBombs(tile.getAdjacentBombs()
        + tile2merge.getAdjacentBombs());
    List<Tile> neighbors = super.getAdjacentTiles(row, col);
    neighbors.addAll(super.getAdjacentTiles(row2, col2));
    neighborTable.put(row, col, neighbors);
    neighborTable.put(row2, col2, neighbors);
    setTile(tile, row2, col2);
  }

  @Override
  public List<Tile> getAdjacentTiles(int row, int col) {
    if (neighborTable.contains(row, col)) {
      return neighborTable.get(row, col);
    } else {
      return super.getAdjacentTiles(row, col);
    }
  }

}
