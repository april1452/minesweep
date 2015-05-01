package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.player.AIPlayer;
import edu.brown.cs.pdtran.minesweep.player.Move;

public class AIRunnable implements Runnable {

  private String sessionId;
  private String teamId;
  private AIPlayer ai;
  private MoveHandler handler;
  private int moveTime;
  private static final double BASE_TIME = 10;
  private static final double TIME_MULTIPLIER = 400;
  private static final int MAX_DIFFICULTY = 10;
  private static final double RANDOM_SUBTRACTOR = .5;

  public AIRunnable(String sessionId, String teamId, AIPlayer ai,
      MoveHandler handler) {
    this.sessionId = sessionId;
    this.teamId = teamId;
    this.ai = ai;
    this.handler = handler;
    moveTime = (int) ((BASE_TIME - ai.getDifficulty()) * TIME_MULTIPLIER);
  }

  @Override
  public void run() {
    System.out.println("Ai started.");
    while (ai.getCanPlay()) {
      try {
        System.out.println(moveTime);
        int moveTimeRandomness =
            (int) Math.round((Math.random() - RANDOM_SUBTRACTOR) * moveTime);
        Thread.sleep(moveTime + moveTimeRandomness);
        Move move = ai.getMove();
        System.out.println(move.getXCoord() + " " + move.getYCoord());
        handler.makeMove(sessionId, teamId, move);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (NoSuchSessionException e) {
        e.printStackTrace();
      }
    }

  }

}
