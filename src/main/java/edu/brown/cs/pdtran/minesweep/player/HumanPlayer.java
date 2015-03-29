package edu.brown.cs.pdtran.minesweep.player;

public class HumanPlayer implements Player {
  
  private String username;
  private int score;
  
  public HumanPlayer(HumanGamer g) {
    username = g.getUsername();
    score = 0;
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

  public Move setFlag(int x, int y) {
    return new FlagTile(x, y);
  }

  public Move checkTile(int x, int y) {
    return new CheckTile(x, y);
  }

}
