package edu.brown.cs.pdtran.minesweep.player;

import edu.brown.cs.pdtran.minesweep.move.Move;
import edu.brown.cs.pdtran.minesweep.websockets.MoveHandler;
import edu.brown.cs.pdtran.minesweep.websockets.NoSuchSessionException;

/**
 * Represents the thread that an AI Player runs on.
 * <p>
 * Governs whenever an AI player makes a move by waiting a certain amount
 * of time before requesting information from the AI as to the best moves
 * to choose.
 * @author Clayton Sanford
 */
public class AIRunnable implements Runnable {

  private String sessionId;
  private String aiId;
  private String teamId;
  private AIPlayer ai;
  private MoveHandler handler;
  private int moveTime;
  private PlayerTeam team;
  private static final double BASE_TIME = 4000;
  private static final double TIME_MULTIPLIER = 350;
  private static final int MAX_DIFFICULTY = 10;
  private static final double RANDOM_SUBTRACTOR = .5;

  /**
   * Constructs an AIRunnable.
   * @param sessionId The unique id for the session.
   * @param team The PlayerTeam object the AI is in.
   * @param teamId The id of the team the AI Player belongs to.
   * @param ai The AIPlayer that generates move possibilities.
   * @param handler The MoveHandler for the AI's moves.
   */
  public AIRunnable(String sessionId, PlayerTeam team, String teamId,
      AIPlayer ai, MoveHandler handler) {
    this.sessionId = sessionId;
    this.teamId = teamId;
    this.ai = ai;
    this.handler = handler;
    moveTime = (int) (BASE_TIME - (ai.getDifficulty() * TIME_MULTIPLIER));
    this.team = team;
  }

  @Override
  public void run() {
    while (ai.getCanPlay()) {
      try {
        System.out.println(moveTime);
        int moveTimeRandomness =
            (int) Math.round((Math.random() - RANDOM_SUBTRACTOR)
                * moveTime);
        Thread.sleep(moveTime + moveTimeRandomness);
        Move move = ai.getMove(team);
        handler.makeMove(sessionId, teamId, move);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (NoSuchSessionException e) {
        e.printStackTrace();
      }
    }

  }
}
