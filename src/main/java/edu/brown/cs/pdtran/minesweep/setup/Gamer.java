package edu.brown.cs.pdtran.minesweep.setup;

/**
 * Represents pregame/setup phase gamer. Turned into a Player object once game
 * begins.
 * 
 * @author pdtran
 */
public interface Gamer {
  /**
   * Return player's username.
   * 
   * @return player's username.
   */
  public String getUserName();
}
