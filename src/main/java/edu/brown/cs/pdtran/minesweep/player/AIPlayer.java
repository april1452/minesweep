package edu.brown.cs.pdtran.minesweep.player;

import java.util.PriorityQueue;

public class AIPlayer implements Player {

  private String username;
  private int score;
  private PriorityQueue<MovePossibility> movePossibilities;
  
  public AIPlayer(AIGamer g) {
    username = g.getUsername();
    score = 0;
    movePossibilities = new PriorityQueue<>();
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public int getScore() {
    return score;
  }

  @Override
  public void changeScore(int change) {
    score += change;
  }

  @Override
  public void makeMove(Move move) {
    // TODO Auto-generated method stub
    
  }
  
  public void generateMovePossibilities(Board board) {
    movePossibilities = new PriorityQueue<>();
  }

  public Move setFlag() {
    for (MovePossibility m: movePossibilities) {
      if (m.getMineProbability() == 0) {
        return new FlagTile(m.getXCoord(), m.getYCoord());
      }
    }
  }

  public Move checkTile() {
    MovePossibility bestCheck = movePossibilities.poll();
    return new CheckTile(bestCheck.getXCoord(), bestCheck.getYCoord());
  }
  
}
