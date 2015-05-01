package edu.brown.cs.pdtran.minesweep.setup;

import java.util.List;

import edu.brown.cs.pdtran.minesweep.games.BoardData;
import edu.brown.cs.pdtran.minesweep.player.AIPlayer;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.HumanPlayer;
import edu.brown.cs.pdtran.minesweep.types.PlayerType;

/**
 * Represents a human gamer. Will be turned into HumanPlayer object in
 * game.
 * @author pdtran
 */
public class HumanGamer extends Gamer {

  /**
   * Constructs a HumanGamer.
   * @param name The string that represents the HumanGamer's name.
   */
  public HumanGamer(String name) {
    super(name);
  }

  @Override
  public PlayerType getType() {
    return PlayerType.HUMAN;
  }

  @Override
  public GamePlayer toGamePlayer(BoardData data, List<AIPlayer> aiPlayers) {
    return new HumanPlayer(name);
  }
}
