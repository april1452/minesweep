package edu.brown.cs.pdtran.minesweep.player;

import edu.brown.cs.pdtran.minesweep.options.PlayerType;

/**
 * This class represents what a human player controls when playing the game.
 * This gives the play the ability to make moves.
 *
 * @author Clayton Sanford
 *
 */
public class HumanPlayer extends GamePlayer {

  /**
   * Creates an HumanPlayer with a username. This version will be used primarily
   * for testing.
   *
   * @param name
   *          A string unique to that player.
   */
  public HumanPlayer(String name) {
    super(name);
    canPlay = true;
  }

  @Override
  /**
   * Gets the username corresponding to the player.
   * @return A unique string for a player's username.
   */
  public String getName() {
    return name;
  }

  @Override
  /**
   * Gets the score corresponding to the player.
   * @return An integer representing the player's score.
   */
  public int getScore() {
    return score;
  }

  @Override
  /**
   * Increments the score by an entered value.
   * @param change An integer to be added to the score. Negative if points are
   * to be lost.
   */
  public void changeScore(int change) {
    score += change;
  }

  /**
   * Sets up the command to place a flag at a given tile.
   *
   * @param x
   *          An integer representing the x-coordinate.
   * @param y
   *          An integer representing the y-coordinate.
   * @return A FlagTile Move that encapsulates that data.
   */
  public Move setFlag(int x, int y) {
    return new FlagTile(x, y);
  }

  /**
   * Sets up the command to check a given tile for a mine.
   *
   * @param x
   *          An integer representing the x-coordinate.
   * @param y
   *          An integer representing the y-coordinate.
   * @return A CheckTile Move that encapsulates that data.
   */
  public Move checkTile(int x, int y) {
    return new CheckTile(x, y);
  }

  @Override
  public PlayerType getType() {
    return PlayerType.HUMAN;
  }
}
