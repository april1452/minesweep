package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.move.Move;

/**
 * An interface to that assesses whether a move is valid.
 * @author Clayton Sanford
 */
public interface Referee {

  /**
   * Verifies whether a move is valid.
   * @param m A Move used by a Player.
   * @return True if the move is valid; false if it is not.
   */
  boolean validateMove(Move m);

}
