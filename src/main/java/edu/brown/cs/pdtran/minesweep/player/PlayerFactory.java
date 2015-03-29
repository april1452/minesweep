package edu.brown.cs.pdtran.minesweep.player;

public class PlayerFactory {
  
  public static Player getPlayer(Gamer g) {
    if (g instanceof HumanGamer) {
      return new HumanPlayer(g);
    } else if (g instanceof AIGamer){
      return new AIPlayer(g);
    }
  }

}
