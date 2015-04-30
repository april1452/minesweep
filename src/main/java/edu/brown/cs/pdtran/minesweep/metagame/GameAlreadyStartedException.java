package edu.brown.cs.pdtran.minesweep.metagame;

/**
 * An exception that is raised when a user attempts to start the game more than
 * once with the "Start game" button.
 * @author Clayton
 */
public class GameAlreadyStartedException extends Exception {

  private static final long serialVersionUID = 2213276209248039968L;

  /**
   * Constructs the exception detailed in the class comments.
   */
  public GameAlreadyStartedException() {
    super();
  }

  /**
   * Constructs the exception detailed in the class comments with a specified
   * message.
   */
  public GameAlreadyStartedException(String message) {
    super(message);
  }

}
