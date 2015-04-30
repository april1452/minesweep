package edu.brown.cs.pdtran.minesweep.setup;

import edu.brown.cs.pdtran.minesweep.games.BoardData;
import edu.brown.cs.pdtran.minesweep.options.PlayerType;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.HumanPlayer;

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
  public GamePlayer toGamePlayer(BoardData data) {
    return new HumanPlayer(name);
  }
}
