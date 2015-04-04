package edu.brown.cs.pdtran.minesweep.player;

/**
 * This class represents what a human player controls when playing the game.
 * This gives the play the ability to make moves.
 * @author Clayton Sanford
 *
 */
public class HumanPlayer implements Player {
  
  private String username;
  private int score;
  private Boolean canPlay; 
  
  /**
   * Creates an HumanPlayer with a username. This version will
   * be used primarily for testing.
   * @param username A string unique to that player.
   */
  public HumanPlayer(String username) {
    this.username = username;
    canPlay = true;
  }
  
  /**
   * Uses an HumanGamer to produce a HumanPlayer.
   * @param g A HumanGamer produced for the purpose of being used in only the
   * game setup. The actual HumanPlayer has the characteristics needed to make
   * moves.
   */
  public HumanPlayer(HumanGamer g) {
    username = g.getUsername();
    score = 0;
    canPlay = true;
  }

  @Override
  /**
   * Gets the username corresponding to the player.
   * @return A unique string for a player's username.
   */
  public String getUsername() {
    return username;
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

  @Override
  /**
   * Sends a Move to the network to be processed and implemented on the board.
   * @param move A Move to be sent.
   */
  public void makeMove(Move move) {
    if (canPlay) {
    }
    // TODO Auto-generated method stub
    
  }

  /**
   * Sets up the command to place a flag at a given tile.
   * @param x An integer representing the x-coordinate.
   * @param y An integer representing the y-coordinate.
   * @return A FlagTile Move that encapsulates that data.
   */
  public Move setFlag(int x, int y) {
    return new FlagTile(x, y);
  }

  /**
   * Sets up the command to check a given tile for a mine.
   * @param x An integer representing the x-coordinate.
   * @param y An integer representing the y-coordinate.
   * @return A CheckTile Move that encapsulates that data.
   */
  public Move checkTile(int x, int y) {
    return new CheckTile(x, y);
  }

  @Override
  /**
   * Sets canPlay to true, meaning that the Player can make Moves.
   */
  public void beginPlay() {
    canPlay = true;
    
  }

  @Override
  /**
   * Sets canPlay to false, meaning that the Player cannot make Moves.
   */
  public void endPlay() {
    canPlay = false;
  }

}
