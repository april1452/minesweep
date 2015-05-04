package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.move.Move;

public interface MoveHandler {

  void makeMove(String sessionId, String teamId, Move m)
      throws NoSuchSessionException;

}
