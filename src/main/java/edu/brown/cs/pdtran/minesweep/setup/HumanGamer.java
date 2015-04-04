package edu.brown.cs.pdtran.minesweep.setup;

/**
 * Represents a human gamer. Will be turned into HumanPlayer object in game.
 * @author pdtran
 */
public class HumanGamer implements Gamer {
  private String name;

  @Override
  public String getUserName() {
    return name;
  }
}
