package edu.brown.cs.pdtran.minesweep.games;

import java.util.TimerTask;

/**
 * Keeps track of a timer that regulates how much time a player has left in
 * the Timer game mode.
 * @author Clayton
 */
public class PlayerTimer extends TimerTask {

  TimerGame timerGame;
  String teamId;
  private long startTime;
  private long delay;

  /**
   * Constructs a new PlayerTimer.
   * @param timerGame The TimerGame object the player is in.
   * @param teamId The id of the team that contains the timer.
   * @param startTime The initial time for the timer.
   * @param delay The amount of time the player has left.
   */
  public PlayerTimer(TimerGame timerGame, String teamId, long startTime,
      long delay) {
    this.timerGame = timerGame;
    this.teamId = teamId;
    this.startTime = startTime;
    this.delay = delay;
  }

  /**
   * Gets the starting time.
   * @return A long representing the initial time of the team.
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * Gets the delay time.
   * @return A long representing the amount of time the player has left.
   */
  public long getDelay() {
    return delay;
  }

  @Override
  public void run() {
    timerGame.timerLoss(teamId);
  }

}
