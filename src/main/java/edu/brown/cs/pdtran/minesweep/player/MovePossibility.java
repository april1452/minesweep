package edu.brown.cs.pdtran.minesweep.player;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * Represents a possible move for the AI to take. Each tile in the board has a
 * MovePossibility corresponding to it that represents the probability of there
 * being a mine in that tile.
 * @author Clayton Sanford
 *
 */
public class MovePossibility {
  
  private int xCoord;
  private int yCoord;
  private double mineProbability;
  private int mineCount;
  private int revealedMines;
  private int clearMines;
  private Tile tile;
  
  /**
   * Creates a MovePossibility using a given Tile from the Board.
   * @param tile A Tile object that is included in the Board used by the
   * players.
   */
  public MovePossibility(Tile tile) {
    this.tile = tile;
  }
  
  /*public int getXCoord() {
    return xCoord;
  }
  
  public int getYCoord() {
    return yCoord;
  }*/
  
  /**
   * Gets the probability of a mine being in that tile.
   * @return A double with 1.0 being certainty that there is a mine and 0.0
   * being certainty that there is not a mine.
   */
  public double getMineProbability() {
    return mineProbability;
  }
  
  /**
   * Sets the probability of a mine being in that tile.
   * @param mineProbability A double with 1.0 being certainty that there is a
   * mine and 0.0 being certainty that there is not a mine.
   */
  public void setMineProbability(double mineProbability) {
    this.mineProbability = mineProbability;
  }

}
