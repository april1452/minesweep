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
  private Tile[][] minePlacement;
  
  /**
   * Creates a MovePossibility using a given Tile from the Board.
   * @param tile A Tile object that is included in the Board used by the
   * players.
   * @param mineProbability A double representing the likelihood that there is
   * a mine on that space.
   */
  public MovePossibility(Tile tile, double mineProbability) {
    this.tile = tile;
    mineCount = tile.getAdjacentBombs();
    this.mineProbability = mineProbability;
    xCoord = tile.getRow();
    yCoord = tile.getColumn();
  }
  
  /**
   * Gets the value of the x coordinate.
   * @return An integer representing the x coordinate of the tile.
   */
  public int getXCoord() {
    return xCoord;
  }
  
  /**
   * Gets the value of the y coordinate.
   * @return An integer representing the y coordinate of the tile.
   */
  public int getYCoord() {
    return yCoord;
  }
  
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
  
  /**
   * Gets the tile corresponding to the MovePossibility.
   * @return The Tile object held in this spot.
   */
  public Tile getTile() {
    return tile;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof MovePossibility) {
      MovePossibility mp = (MovePossibility) o;
      return (tile.equals(mp.getTile()) 
          && mineProbability == mp.getMineProbability());
    } else {
      return false;
    }
  }

}
