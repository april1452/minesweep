package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Player;

public class Classic {

  private long startTime;
  private Board gameBoard;

  public Classic(Board gameBoard, Player player) {
    startTime = System.currentTimeMillis();
    this.gameBoard = gameBoard;
  }

  public void makeMove(Move m) {
    gameBoard.makeMove(m.getYCoord(), m.getXCoord());
  }

  public boolean play(Player player, Move m) {
    makeMove(m);
    long endTime = System.currentTimeMillis();
    int score = (int) (endTime - startTime);
    if (gameBoard.isGameOver()) {
      player.changeScore(score);
      return true;
    }
    return false;
  }

  /**
   * This will calculate 3BV minesweep board.
   *
   * @return
   */
  public int getGameScore() {
    Board board = gameBoard.clone();

  }


}
