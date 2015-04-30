package edu.brown.cs.pdtran.minesweep.setup;

import edu.brown.cs.pdtran.minesweep.games.BoardData;
import edu.brown.cs.pdtran.minesweep.player.AIPlayer;
import edu.brown.cs.pdtran.minesweep.types.AiDifficulty;
import edu.brown.cs.pdtran.minesweep.types.PlayerType;

/**
 * Represents an AI gamer. Turned into AIPlayer in game.
 * @author pdtran
 */
public class AIGamer extends Gamer {
  private AiDifficulty difficulty;

  /**
   * Constructs an AIGamer.
   * @param name A string for each AIGamer.
   * @param difficulty An enum representing the difficulty of an AI.
   */
  public AIGamer(String name, AiDifficulty difficulty) {
    super(name);
    this.difficulty = difficulty;
  }

  /**
   * Return AI difficulty level. The higher the level, the smarter the AI.
   * @return AI difficulty level
   */
  public AiDifficulty getDifficulty() {
    return difficulty;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public PlayerType getType() {
    return PlayerType.AI;
  }

  @Override
  public AIPlayer toGamePlayer(BoardData data) {
    return new AIPlayer(name, difficulty, data);
  }

}
