package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Player;
import edu.brown.cs.pdtran.minesweep.player.Team;

public class ClassicGame implements Game {

  private long startTime;
  private Team[] teams;
  private int turn;

  public ClassicGame(Team[] teams) {
    startTime = System.currentTimeMillis();
    this.teams = teams;
    this.turn = 0;
  }

  @Override
  public void makeMove(int teamNumber, Move m) {
    teams[teamNumber].getBoard().makeMove(m.getYCoord(), m.getXCoord());
  }

  /**
   * This is a player method for turnless play. The referee determines who is
   * allowed to click. Typically, only one person is allowed to click
   * particularily in classic where score is time based.
   *
   * @param player
   *          Player the player whose turn it is.
   * @param m
   *          The move you wish to make.
   * @return True if the game is over false otherwise.
   */
  @Override
  public boolean play(int teamNumber, Move m) {
    makeMove(teamNumber, m);
    long endTime = System.currentTimeMillis();
    int score = (int) (endTime - startTime);
    // if (gameBoard.isGameOver()) {
    // player.changeScore(score);
    // return true;
    // }
    return false;
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

  @Override
  public Team[] getTeams() {
    return teams;
  }

}
