package edu.brown.cs.pdtran.minesweep.player;

/**
 * This class's objects are used to communicate a player's wish to place a flag
 * on a certain tile to the network. These flags have no impact on gameplay, but
 * can be seen on the board.
 * @author Clayton
 */
public class FlagTile implements Move {

  private int xCoord;
  private int yCoord;

  /**
   * Constructs a FlagTile object.
   * @param xCoord
   *          An integer representing the tile's x-coordinate.
   * @param yCoord
   *          An integer representing the tile's y-coordinate.
   */
  public FlagTile(int xCoord, int yCoord) {
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
