package edu.brown.cs.pdtran.minesweep.board;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * This class adds an interesting element by linking some Tiles on the
 * board.
 * @author agokasla
 */
public class EntangledBoard extends DefaultBoard implements Board {

  private Table<Integer, Integer, List<Tile>> neighborTable;
  private Table<Integer, Integer, Tile> overWrittenTiles;

  /**
   * The constructor.
   * @param width The desired width of the board.
   * @param height The desired height of the board.
   * @param mines The number of mines on the board.
   */
  public EntangledBoard(int width, int height, int mines) {
    super(width, height, mines);
    neighborTable = HashBasedTable.create();
    assert (neighborTable != null);
    overWrittenTiles = HashBasedTable.create();
    reconfigureBoard(getWidth() * getHeight() / 10);
  }

  /**
   * The constructor.
   * @param grid Allows you to specify a grid.
   */
  public EntangledBoard(Tile[][] grid) {
    super(grid);
    neighborTable = HashBasedTable.create();
    assert (neighborTable != null);
  }

  /**
   * The constructor.
   * @param grid Allows you to specify a grid.
   * @param neighborTile A table that contains neighbors mapped to a
   *        certain spot on the board.
   * @param overWrittenTile A table that contains Tiles that have been
   *        overwritten.
   */
  public EntangledBoard(Tile[][] grid,
      Table<Integer, Integer, List<Tile>> neighborTile,
      Table<Integer, Integer, Tile> overWrittenTiles) {
    this(grid);
    this.neighborTable = neighborTile;
    this.overWrittenTiles = overWrittenTiles;
    assert (neighborTable != null);
  }

  /**
   * Reconfigures the grid as you see fit.
   * @param mergeNum The number you wish to merge together.
   */
  public void reconfigureBoard(int mergeNum) {
    for (int i = 0; i < mergeNum; i++) {
      int row = (int) (Math.random() * getHeight());
      int col = (int) (Math.random() * getWidth());
      List<Tile> candidateList =
          super.getAdjacentTiles(row, col).stream()
          .filter((t) -> t.isBomb() == getTile(row, col).isBomb())
          .collect(Collectors.toList());
      if (candidateList.isEmpty()) {
        continue;
      }
      Tile randomTile =
          candidateList.get((int) (Math.random() * candidateList.size()));
      assert (!randomTile.isBomb());
      assert (!randomTile.hasBeenVisited());
      mergeTiles(row, col, randomTile.getRow(), randomTile.getColumn());
      assert (randomTile != null);
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
    System.out.println(neighborTable);// Something is wrong here...
    if (neighborTable != null && neighborTable.contains(row, col)) {
      return neighborTable.get(row, col);
    } else {
      return super.getAdjacentTiles(row, col);
    }
  }

  @Override
  public EntangledBoard clone() {
    Tile[][] newGrid = new Tile[getHeight()][getWidth()];
    for (int i = 0; i < newGrid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        newGrid[i][j] = grid[i][j].clone();
      }
    }
    return new EntangledBoard(newGrid,
        HashBasedTable.create(neighborTable),
        HashBasedTable.create(overWrittenTiles));
  }
}
