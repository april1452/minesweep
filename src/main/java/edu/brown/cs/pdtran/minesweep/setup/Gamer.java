package edu.brown.cs.pdtran.minesweep.setup;

import java.util.List;

import edu.brown.cs.pdtran.minesweep.games.BoardData;
import edu.brown.cs.pdtran.minesweep.metagame.Player;
import edu.brown.cs.pdtran.minesweep.player.AIPlayer;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;

/**
 * Represents pregame/setup phase gamer. Turned into a Player object once
 * game begins.
 * @author pdtran
 */
public abstract class Gamer extends Player {

  /**
   * Constructs a new Gamer (a pre-game player).
   * @param name The string corresponding to the Gamer's name.
   */
  public Gamer(String name) {
    super(name);
  }

  /**
   * An abstract method that converts the Gamer to a GamePlayer.
   * @param data The BoardData object where a player (especially an AI) can
   *        find out information about the board object.
   * @param aiPlayers
   * @return The GamePlayer corresponding to the Gamer, which can be used
   *         in-game.
   */
  public abstract GamePlayer toGamePlayer(BoardData data,
      List<AIPlayer> aiPlayers);

}
