package edu.brown.cs.pdtran.minesweep.setup;

import edu.brown.cs.pdtran.minesweep.types.PlayerType;

import edu.brown.cs.pdtran.minesweep.games.BoardData;
import edu.brown.cs.pdtran.minesweep.player.AIPlayer;

/**
 * Represents an AI gamer. Turned into AIPlayer in game.
 * @author pdtran
 */
public class AIGamer extends Gamer {
  private int difficulty;

  /**
   * Constructs an AIGamer.
   * @param name A string for each AIGamer.
   * @param difficulty An integer representing the difficulty of an AI.
   */
  public AIGamer(String name, int difficulty) {
    super(name);
    this.difficulty = difficulty;
  }

  /**
   * Return AI difficulty level. The higher the level, the smarter the AI.
   * @return AI difficulty level
   */
  public int getDifficulty() {
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
