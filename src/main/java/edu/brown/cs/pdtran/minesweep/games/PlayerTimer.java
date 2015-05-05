package edu.brown.cs.pdtran.minesweep.games;

import java.util.TimerTask;

public class PlayerTimer extends TimerTask {

  TimerGame timerGame;
  String teamId;
  private long startTime;
  private long delay;

  public PlayerTimer(TimerGame timerGame, String teamId, long startTime,
      long delay) {
    this.timerGame = timerGame;
    this.teamId = teamId;
    this.startTime = startTime;
    this.delay = delay;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getDelay() {
    return delay;
  }

  @Override
  public void run() {
    timerGame.timerLoss(teamId);
  }

}
