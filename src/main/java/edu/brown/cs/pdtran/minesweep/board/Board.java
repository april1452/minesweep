package edu.brown.cs.pdtran.minesweep.board;

import java.util.List;

import com.google.gson.JsonElement;
import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * This is an interface for a standard board.
 * @author agokasla
 */
public interface Board extends Cloneable {

  /**
   * Makes the move at the specified row and col.
   * @param row The row you want to move at.
   * @param col The col you want to move at.
   * @return True if there is a mine at the move's location.
   */
  Boolean makeMove(int row, int col);

  /**
   * Tells you if the board is a winning board.
   * @return true if board is winning.
   */
  boolean isWinningBoard();

  /**
   * Tells you if the board is a losing board.
   * @return true if the board is a losing board.
   */
  boolean isLosingBoard();

  /**
   * Tells you if the board is in a game over state.
   * @return True if the board is in a game over state.
   */
  boolean isGameOver();

  /**
   * Clones the board.
   * @return The cloned board.
   */
  Board clone();

  /**
   * Gets the (maximum) height of the board.
   * @return the maximum height of the board.
   */
  int getHeight();

  /**
   * Gets the maximum width of the board.
   * @return the maximum width of the board.
   */
  int getWidth();

  /**
   * Gets the total number of bombs on the board.
   * @return the total number of bombs on the board.
   */
  int getBombCount();

  /**
   * Gets the object corresponding to a specified tile.
   * @param h value for the "height" of the tile in the coordinates
   * @param w value for the "width" of the tile in the coordinates
   * @return the Tile represented in the given location
   */
  Tile getTile(int h, int w);

  /**
   * Gets all tiles that surround a specified tile.
   * @param h value for the "height" of the tile in the coordinates
   * @param w value for the "width" of the tile in the coordinates
   * @return a List of Tiles that all surround the location specified by
   *         the coordinates
   */
  List<Tile> getAdjacentTiles(int h, int w);

  /**
   * Converts the Board object to a JSON object.
   * @return a JSON string representing the board.
   */
  JsonElement toJson();
}
