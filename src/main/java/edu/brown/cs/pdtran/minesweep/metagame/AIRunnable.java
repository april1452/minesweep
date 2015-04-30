package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.player.AIPlayer;

public class AIRunnable implements Runnable {

  private AIPlayer ai;
  private int moveTime;
  private static final double BASE_TIME = 5;
  private static final double TIME_MULTIPLIER = 25;
  private static final int MAX_DIFFICULTY = 10;
  private static final double RANDOM_SUBTRACTOR = .5;

  public AIRunnable(AIPlayer ai) {
    this.ai = ai;
    moveTime = (int) (BASE_TIME - ai.getDifficulty() * TIME_MULTIPLIER);
  }

  @Override
  public void run() {
    while (ai.getCanPlay()) {
      try {
        int moveTimeRandomness =
            (int) Math.round((Math.random() - RANDOM_SUBTRACTOR) * moveTime);
        Thread.sleep(moveTime + moveTimeRandomness);
        ai.getMove();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

}
