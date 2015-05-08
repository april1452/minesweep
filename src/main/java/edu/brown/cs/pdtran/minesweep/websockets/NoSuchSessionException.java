package edu.brown.cs.pdtran.minesweep.websockets;

/**
 * An exception when an ID for a game that does not exist is used.
 * @author Clayton Sanford
 */
public class NoSuchSessionException extends Exception {

  private static final long serialVersionUID = -8972484746152200399L;

  /**
   * Creates an exception when an invalid ID is used.
   */
  public NoSuchSessionException() {
    super();
  }

  /**
   * Creates an exception when an invalid ID is used with a specified
   * message.
   * @param message The message to be returned when the exception is
   *        thrown.
   */
  public NoSuchSessionException(String message) {
    super(message);
  }

}
