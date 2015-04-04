package edu.brown.cs.pdtran.minesweep.setup;

/**
 * Represents an AI gamer. Turned into AIPlayer in game.
 * @author pdtran
 */
public class AIGamer implements Gamer {
  private String name;
  private int difficulty;

  @Override
  public String getUserName() {
    return name;
  }

  /**
   * Return AI difficulty level. The higher the level, the smarter the AI.
   * @return AI difficulty level
   */
  public int getDifficulty() {
    return difficulty;
  }

}
