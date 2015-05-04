package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.move.Move;

/**
 * An interface that when implemented handles moves and updates the
 * session.
 * @author Clayton Sanford
 */
public interface MoveHandler {

  /**
   * Submits a move to affect the board and game.
   * @param sessionId The unique ID of the session.
   * @param teamId The unique ID of the team.
   * @param m The Move being passed.
   * @throws NoSuchSessionException Thrown if the session does not exist.
   */
  void makeMove(String sessionId, String teamId, Move m)
      throws NoSuchSessionException;

}
