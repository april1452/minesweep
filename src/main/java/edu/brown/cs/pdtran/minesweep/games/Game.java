package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Player;

public interface Game {

  public int getGameScore(Player player);

  public void makeMove(int team, Move m);

  public boolean play(int team, Move m);

}
