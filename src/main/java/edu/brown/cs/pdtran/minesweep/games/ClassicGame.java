package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Player;

public class ClassicGame implements Game {

  private long startTime;
  private Board gameBoard;
  private Player[] players;
  private int turn;

  public ClassicGame(Board gameBoard, Player[] players) {
    startTime = System.currentTimeMillis();
    this.gameBoard = gameBoard;
    this.players = players;
    this.turn = 0;
  }

  @Override
  public void makeMove(Move m) {
    gameBoard.makeMove(m.getYCoord(), m.getXCoord());
  }

  /**
   * This is a player method for turnless play. The referee determines who is
   * allowed to click. Typically, only one person is allowed to click
   * particularily in classic where score is time based.
   *
   * @param player Player the player whose turn it is.
   * @param m The move you wish to make.
   * @return True if the game is over false otherwise.
   */
  @Override
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

  public boolean play(Move m) {
    Player currentPlayer = players[turn % players.length];
    boolean isOver = play(currentPlayer, m);
    turn++;
    return isOver;
  }

  /**
   * @return This will calculate how many moves are left.
   */
  @Override
  public int getGameScore(Player player) {
    int score = (int) (System.currentTimeMillis() - startTime);
    score = score / 1000; // Number of seconds
    return score;
  }

}
