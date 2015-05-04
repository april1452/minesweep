package edu.brown.cs.pdtran.minesweep.metagame;

/**
 * Thrown when the game session is full and players cannot join.
 * @author Clayton Sanford
 */
public class SessionFullException extends Exception {

  private static final long serialVersionUID = 5738143710998450436L;

  /**
   * The exception to be thrown when the game session is full and players
   * cannot join.
   */
  public SessionFullException() {
    super();
  }

  /**
   * The exception to be thrown when the game session is full and players
   * cannot join with an attached message.
   * @param The message to be sent.
   */
  public SessionFullException(String message) {
    super(message);
  }

}
