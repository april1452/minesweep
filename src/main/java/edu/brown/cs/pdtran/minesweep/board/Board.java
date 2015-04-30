package edu.brown.cs.pdtran.minesweep.board;

import java.util.List;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * This is an interface for a standard board.
 *
 * @author agokasla
 *
 */
public interface Board extends Cloneable {

  /**
   * Makes the move at the specified row and col.
   *
   * @param row
   *          The row you want to move at.
   * @param col
   *          The col you want to move at.
   */
  void makeMove(int row, int col);

  /**
   * Tells you if the board is a winning board.
   *
   * @return true if board is winning.
   */
  boolean isWinningBoard();

  /**
   * Tells you if the board is a losing board.
   *
   * @return true if the board is a losing board.
   */
  boolean isLosingBoard();

  /**
   * Tells you if the board is in a game over state.
   *
   * @return True if the board is in a game over state.
   */
  boolean isGameOver();

  /**
   * Clones the board.
   *
   * @return The cloned board.
   */
  Board clone();

  /**
   * Gets the (maximum) height of the board.
   *
   * @return the maximum height of the board.
   */
  int getHeight();

  /**
   * Gets the maximum width of the board.
   *
   * @return the maximum width of the board.
   */
  int getWidth();
  
  int getBombCount();
  
  Tile getTile(int h, int w);
  
  List<Tile> getAdjacentTiles(int h, int w);

  String toJson();
}
