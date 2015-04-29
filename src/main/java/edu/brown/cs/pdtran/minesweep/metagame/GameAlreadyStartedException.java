package edu.brown.cs.pdtran.minesweep.metagame;

public class GameAlreadyStartedException extends Exception {

  private static final long serialVersionUID = 2213276209248039968L;

  public GameAlreadyStartedException() {
    super();
  }

  public GameAlreadyStartedException(String message) {
    super(message);
  }

}
