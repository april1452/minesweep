package edu.brown.cs.pdtran.minesweep.player;

public interface Player {
    
  public String getUsername();
  
  public int getScore();
  
  public void changeScore(int change);
  
  public void makeMove(Move move);

}
