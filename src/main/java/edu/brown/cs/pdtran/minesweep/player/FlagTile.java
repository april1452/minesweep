package edu.brown.cs.pdtran.minesweep.player;

public class FlagTile implements Move {
  
  private int xCoord;
  private int yCoord;
  
  public FlagTile(int xCoord, int yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
  }

  @Override
  public int getXCoord() {
    return xCoord;
  }

  @Override
  public int getYCoord() {
    return yCoord;
  }

}
