package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.move.Move;

/**
 * A class that is used as a test to verify whether a certain move is
 * valid.
 * @author Clayton
 */
public class TestReferee implements Referee {

  @Override
  public boolean validateMove(Move m) {
    return true;
  }

}
