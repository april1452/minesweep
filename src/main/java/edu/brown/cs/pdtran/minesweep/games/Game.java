package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Player;

public interface Game {

  public int getGameScore();

  public void makeMove(Move m);

  public boolean play(Player player, Move m);

}
