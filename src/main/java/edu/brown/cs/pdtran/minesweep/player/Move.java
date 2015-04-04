package edu.brown.cs.pdtran.minesweep.player;

/**
 * An interface whose implementations represent an action taken by a player
 * onto a tile. These allow the players to directly interact with the board. 
 * @author Clayton Sanford
 *
 */
public interface Move {
  
  /**
   * Gets the x-coordinate of the tile.
   * @return An integer representing the tile's x-coordinate.
   */
  public int getXCoord();
  
  /**
   * Gets the y-coordinate of the tile.
   * @return An integer representing the tile's y-coordinate.
   */
  public int getYCoord();

}
