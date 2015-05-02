package edu.brown.cs.pdtran.minesweep.setup;

import java.util.List;

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
  private static final String[] AI_NAMES = {"jj", "glyons", "jsl15", "vqtran",
    "atreil", "dgattey", "gcarling", "ireardon", "jr51", "jts1", "mjfuller",
    "mjrowlan", "mthiesme", "sdooman", "shartse", "smt3", "tmurcuri",
    "vgavriel", "avshah"};

  /**
   * Constructs an AIGamer.
   * @param difficulty An enum representing the difficulty of an AI.
   */
  public AIGamer(AiDifficulty difficulty) {
    // int nameChoice = (int) Math.ceil(Math.random() * AI_NAMES.length) -
    // 1;
    super(AI_NAMES[(int) Math.ceil(Math.random() * AI_NAMES.length) - 1]);
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
  public AIPlayer toGamePlayer(BoardData data, List<AIPlayer> aiPlayers) {
    AIPlayer aiPlayer = new AIPlayer(name, difficulty, data);
    aiPlayers.add(aiPlayer);
    return aiPlayer;
  }
}
