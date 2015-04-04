package edu.brown.cs.pdtran.minesweep.player;

/**
 * This interface represents what a player controls within a game of Minesweep
 * to make moves.
 * @author Clayton Sanford
 *
 */
public interface Player {
    
  /**
   * Gets the username corresponding to the player.
   * @return A unique string for a player's username.
   */
  public String getUsername();
  
  /**
   * Gets the score corresponding to the player.
   * @return An integer representing the player's score.
   */
  public int getScore();
  
  /**
   * Increments the score by an entered value.
   * @param change An integer to be added to the score. Negative if points are
   * to be lost.
   */
  public void changeScore(int change);
  
  /**
   * Sends a Move to the network to be processed and implemented on the board.
   * @param move A Move to be sent.
   */
  public void makeMove(Move move);
  
  /**
   * Sets canPlay to true, meaning that the Player can make Moves.
   */
  public void beginPlay();
  
  /**
   * Sets canPlay to false, meaning that the Player cannot make Moves.
   */
  public void endPlay();

}
