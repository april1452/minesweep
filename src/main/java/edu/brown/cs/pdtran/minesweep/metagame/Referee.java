package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.player.Move;

public interface Referee {

  public boolean validateMove(Move m);

}
