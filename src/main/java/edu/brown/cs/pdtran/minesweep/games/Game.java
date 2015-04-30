package edu.brown.cs.pdtran.minesweep.games;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.metagame.Session;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

public abstract class Game extends Session {

  public Game(String name, GameSpecs specs) {
    super(name, specs);
  }

  @Override
  public abstract ConcurrentMap<String, PlayerTeam> getTeams();

  public abstract Collection<String> getPlayers(String teamId);

  public abstract int getGameScore(GamePlayer player);

  public abstract void makeMove(String teamId, Move m);

  public abstract boolean play(int team, Move m);

  public abstract Board getBoard(String teamId);

}
