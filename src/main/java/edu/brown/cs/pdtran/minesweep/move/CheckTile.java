package edu.brown.cs.pdtran.minesweep.move;


/**
 * This class represents a Move that "touches" a tile, checking whether or not
 * it is a mine.
 * @author Clayton
 */
public class CheckTile implements Move {

  private int xCoord;
  private int yCoord;

  /**
   * Constructs a CheckTile object.
   * @param xCoord
   *          An integer representing the tile's x-coordinate.
   * @param yCoord
   *          An integer representing the tile's y-coordinate.
   */
  public CheckTile(int xCoord, int yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
  }

  @Override
  /**
   * Gets the x-coordinate of the tile.
   * @return An integer representing the tile's x-coordinate.
   */
  public int getXCoord() {
    return xCoord;
  }

  @Override
  /**
   * Gets the y-coordinate of the tile.
   * @return An integer representing the tile's y-coordinate.
   */
  public int getYCoord() {
    return yCoord;
  }

}
