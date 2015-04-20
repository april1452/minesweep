package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.player.Move;

public class TestReferee implements Referee {

  @Override
  public boolean validateMove(Move m) {
    return true;
  }

}
