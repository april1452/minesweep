package edu.brown.cs.pdtran.minesweep.setup;

import edu.brown.cs.pdtran.minesweep.games.BoardData;
import edu.brown.cs.pdtran.minesweep.metagame.Player;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;

/**
 * Represents pregame/setup phase gamer. Turned into a Player object once game
 * begins.
 *
 * @author pdtran
 */
public abstract class Gamer extends Player {

  public Gamer(String name) {
    super(name);
  }

  public abstract GamePlayer toGamePlayer(BoardData data);

}
