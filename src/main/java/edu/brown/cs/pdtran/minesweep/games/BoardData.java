package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.board.Board;

/**
 * An interface that holds a board.
 * @author Clayton
 */
public interface BoardData {

  /**
   * Gets the board held by the BoardData object.
   * @return The Board held by the object.
   */
  Board getCurrentBoard();

}
