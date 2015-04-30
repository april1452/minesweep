package edu.brown.cs.pdtran.minesweep.player;

import edu.brown.cs.pdtran.minesweep.metagame.Player;

/**
 * This interface represents what a player controls within a game of Minesweep
 * to make moves.
 *
 * @author Clayton Sanford
 *
 */
public abstract class GamePlayer extends Player {

  protected int score;
  protected boolean canPlay;

  protected GamePlayer(String name) {
    super(name);
    score = 0;
    canPlay = true;
  }

  /**
   * Gets the score corresponding to the player.
   *
   * @return An integer representing the player's score.
   */
  public int getScore() {
    return score;
  }

  /**
   * Increments the score by an entered value.
   *
   * @param change
   *          An integer to be added to the score. Negative if points are to be
   *          lost.
   */
  public void changeScore(int change) {
    score += change;
  }

  /**
   * Sets canPlay to false, meaning that the Player cannot make Moves.
   */
  public void endPlay() {
    canPlay = false;
  }
  
  public void beginPlay() {
    canPlay = true;
  }

  public void makeMove() {

  }
}
