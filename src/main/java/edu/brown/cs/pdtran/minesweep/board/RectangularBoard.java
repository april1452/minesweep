package edu.brown.cs.pdtran.minesweep.board;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.brown.cs.pdtran.minesweep.tile.Tile;
import edu.brown.cs.pdtran.minesweep.types.BoardType;

/**
 * This class adds an interesting element by linking some Tiles on the
 * board.
 * @author agokasla
 */
public class RectangularBoard extends DefaultBoard implements Board,
    Cloneable {

  private Table<Integer, Integer, List<Tile>> neighborTable;
  // private Table<Integer, Integer, Tile> overWrittenTiles;
  private Tile[][] links;

  /**
   * The constructor.
   * @param width The desired width of the board.
   * @param height The desired height of the board.
   * @param mines The number of mines on the board.
   */
  public RectangularBoard(int width, int height, int mines) {
    super(width, height, mines);
    neighborTable = HashBasedTable.create();
    // overWrittenTiles = HashBasedTable.create();
    links = new Tile[getHeight()][getWidth()];
    assert (neighborTable != null);
    reconfigureBoard(getWidth() * getHeight() / 10);
  }

  /**
   * The constructor.
   * @param grid Allows you to specify a grid.
   */
  public RectangularBoard(Tile[][] grid) {
    super(grid);
    neighborTable = HashBasedTable.create();
    assert (neighborTable != null);
  }

  /**
   * The constructor.
   * @param grid Allows you to specify a grid.
   * @param neighborTile A table that contains neighbors mapped to a
   *        certain spot on the board.
   * @param links An array of connected tiles.
   */
  public RectangularBoard(Tile[][] grid,
      Table<Integer, Integer, List<Tile>> neighborTile,
      Tile[][] links) {
    this(grid);
    this.neighborTable = neighborTile;
    this.links = links;
    // this.overWrittenTiles = overWrittenTiles;
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
          super
          .getAdjacentTiles(row, col)
          .stream()
          .filter(
              (t) -> (t.getColumn() == col || t.getRow() == row)
              && !t.isBomb()).collect(Collectors.toList());
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
    links[row][col] = tile2merge;
    // overWrittenTiles.put(row2, col2, tile);
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
  public RectangularBoard clone() {
    Tile[][] newGrid = new Tile[getHeight()][getWidth()];
    for (int i = 0; i < newGrid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        newGrid[i][j] = grid[i][j].clone();
      }
    }
    return new RectangularBoard(newGrid,
        HashBasedTable.create(neighborTable),
        links.clone());
  }

  @Override
  protected BoardType getBoardType() {
    return BoardType.RECTANGULAR;
  }

  @Override
  public JsonElement toJson() {
    JsonObject boardJson = new JsonObject();
    boardJson.addProperty("width", getWidth());
    boardJson.addProperty("height", getHeight());
    JsonArray tilesJson = new JsonArray();
    for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < getWidth(); j++) {
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
    boardJson.addProperty("type", getBoardType().toString());
    Gson gson = new Gson();
    /*
     * Map<String, Tile> neighborMap = new HashMap<>(); for (Integer i :
     * overWrittenTiles.columnKeySet()) { String output = "(" + i;
     * Map<Integer, Tile> c = overWrittenTiles.column(i); for (Integer j :
     * c.keySet()) { output += "," + j + ")"; neighborMap.put(output,
     * overWrittenTiles.get(i, j)); } }
     */
    boardJson.add("neighborTable",
        (new JsonParser()).parse(gson.toJson(neighborTable))
            .getAsJsonObject());
    boardJson.add("tilesArray",
        (new JsonParser()).parse(gson.toJson(links))
            .getAsJsonObject());
    return boardJson;
  }
}
