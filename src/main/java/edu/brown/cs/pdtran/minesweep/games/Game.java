package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.metagame.Session;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

public abstract class Game extends Session {

  public Game(String name, GameSpecs specs) {
    super(name, specs);
  }

  public abstract int getGameScore(GamePlayer player);

  public abstract void makeMove(String teamId, Move m);

  public abstract boolean play(int team, Move m);

}
