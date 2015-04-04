package edu.brown.cs.pdtran.minesweep.player;

import java.util.PriorityQueue;

import edu.brown.cs.pdtran.minesweep.board.Board;

/**
 * This class represents the AI that controls what moves the AI makes and how
 * it releases those moves.
 * @author Clayton Sanford
 *
 */
public class AIPlayer implements Player {

  private String username;
  private int score;
  private PriorityQueue<MovePossibility> movePossibilities;
  private int difficulty;
  private int moveTime;
  private double mistakeProbability;
  private Boolean canPlay;
  
  private static final double BASE_TIME = 5;
  private static final double TIME_MULTIPLIER = 25;
  private static final int MAX_DIFFICULTY = 10;
  private static final double MISTAKE_MULTIPLIER = .01;
  private static final double FLAG_PROBABILITY = .3;
  
  /**
   * Creates an AIPlayer with a username and a difficulty. This version will
   * be used primarily for testing.
   * @param username A string unique to that player.
   * @param difficulty An integer from 1 to 10 with 10 being the most
   * difficult.
   */
  public AIPlayer(String username, int difficulty) {
    this.username = username;
    score = 0;
    movePossibilities = new PriorityQueue<>();
    this.difficulty = difficulty;
    moveTime = (int) (BASE_TIME - difficulty * TIME_MULTIPLIER);
    mistakeProbability = (MAX_DIFFICULTY - difficulty) * MISTAKE_MULTIPLIER;
  }
  
  /**
   * Uses an AIGamer to produce an AIPlayer.
   * @param g An AIGamer produced for the purpose of being used in only the
   * game setup. The actual AIPlayer has the characteristics needed to make
   * moves.
   */
  public AIPlayer(AIGamer g) {
    username = g.getUsername();
    score = 0;
    movePossibilities = new PriorityQueue<>();
    difficulty = g.getDifficulty();
  }

  @Override
  /**
   * Gets the username corresponding to the player.
   * @return A unique string for a player's username.
   */
  public String getUsername() {
    return username;
  }

  @Override
  /**
   * Gets the score corresponding to the player.
   * @return An integer representing the player's score.
   */
  public int getScore() {
    return score;
  }

  @Override
  /**
   * Increments the score by an entered value.
   * @param change An integer to be added to the score. Negative if points are
   * to be lost.
   */
  public void changeScore(int change) {
    score += change;
  }

  @Override
  /**
   * Sends a Move to the network to be processed and implemented on the board.
   * @param move A Move to be sent.
   */
  public void makeMove(Move move) {
    // TODO Auto-generated method stub
  }
  
  private void generateMovePossibilities() {
    movePossibilities = new PriorityQueue<>();
  }
  
  private void checkForFlag() {
    for (MovePossibility m: movePossibilities) {
      if (m.getMineProbability() == 0) {
        makeMove(setFlag(m));
      }
    }
  }

  private Move setFlag(MovePossibility m) {
        Move myMove = new FlagTile(m.getXCoord(), m.getYCoord());
        return myMove;
  }

  private Move checkTile() {
    MovePossibility bestCheck = movePossibilities.poll();
    return new CheckTile(bestCheck.getXCoord(), bestCheck.getYCoord());
  }
  
  private Move randomTile() {
    int moveNum = (int) Math.random() * movePossibilities.size() - 1;
    MovePossibility randomCheck = null;
    for (MovePossibility m: movePossibilities) {
      if (moveNum == 0) {
        randomCheck = m;
        break;
      }
      moveNum--;
    }
    return new CheckTile(randomCheck.getXCoord(), randomCheck.getYCoord());
  }
  
  public void play() {
    while (canPlay) {
      try {
        Thread.sleep(moveTime);
        generateMovePossibilities();
        double moveChoice = Math.random();
        if (moveChoice < mistakeProbability) {
          makeMove(randomTile());
        } else if (moveChoice < mistakeProbability + FLAG_PROBABILITY) {
          checkForFlag();
        } else {
          makeMove(checkTile());
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  @Override
  /**
   * Sets canPlay to true, meaning that the Player can make Moves.
   */
  public void beginPlay() {
    canPlay = true;
    
  }

  @Override
  /**
   * Sets canPlay to false, meaning that the Player cannot make Moves.
   */
  public void endPlay() {
    canPlay = false;
  }
  
}
