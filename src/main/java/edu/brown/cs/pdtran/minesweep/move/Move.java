package edu.brown.cs.pdtran.minesweep.move;

import edu.brown.cs.pdtran.minesweep.types.MoveType;

/**
 * An interface whose implementations represent an action taken by a player
 * onto a tile. These allow the players to directly interact with the
 * board.
 * @author Clayton Sanford
 */
public interface Move {

  /**
   * Gets the x-coordinate of the tile.
   * @return An integer representing the tile's x-coordinate.
   */
  int getXCoord();

  /**
   * Gets the y-coordinate of the tile.
   * @return An integer representing the tile's y-coordinate.
   */
  int getYCoord();

  /**
   * Gets the move type corresponding to the move.
   * @return An enum representing the type of move: "CHECK" or "FLAG".
   */
  MoveType getMoveType();

}
