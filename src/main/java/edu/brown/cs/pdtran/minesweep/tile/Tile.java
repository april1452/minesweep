package edu.brown.cs.pdtran.minesweep.tile;

public class Tile {
  /**
   * Whether or not the tile contains a bomb.
   */
  private boolean isBomb;
  /**
   * Count of adjacent tiles that contain bombs.
   */
  private int adjacentBombs;
  /**
   * Whether or not the tile has been clicked by the player.
   */
  private boolean visited;
  /**
   * The tile's row on the board.
   */
  private int row;
  /**
   * The tile's column on the board.
   */
  private int column;

  /**
   * Default constructor for a Tile.
   *
   * @param bomb Boolean representing if tile is a bomb.
   * @param adjacent Count for number of adjacent bombs.
   * @param visit Whether or not the tile has been visited.
   * @param x The row value of the tile.
   * @param y The column value of the tile.
   */
  public Tile(final boolean bomb, final int adjacent, final boolean visit,
      final int x, final int y) {
    this.isBomb = bomb;
    this.adjacentBombs = adjacent;
    this.visited = visit;
    this.row = x;
    this.column = y;
  }

  /**
   * Adds a bomb to the Tile
   */
  public void makeBomb() {
    isBomb = true;
  }

  /**
   * @return true if the bomb exists, otherwise false.
   */
  public boolean isBomb() {
    return isBomb;
  }

  /**
   * determines the number of adjacent bombs
   * 
   * @param num
   */
  public void setAdjacentBombs(final int num) {
    adjacentBombs = num;
  }

  /**
   * @return number of adjacent bombs
   */
  public int getAdjacentBombs() {
    return adjacentBombs;
  }

  /**
   * makes the tile visted.
   */
  public void setVisited() {
    visited = true;
  }

  /**
   * @return the visited boolean.
   */
  public boolean hasBeenVisited() {
    return visited;
  }

  /**
   * @return row the tile is in.
   */
  public int getRow() {
    return row;
  }

  /**
   * @return the column
   */
  public int getColumn() {
    return column;
  }
  
  public Boolean equals(Tile tile2) {
    return (row == tile2.getRow() && column == tile2.getColumn());
  }
}
