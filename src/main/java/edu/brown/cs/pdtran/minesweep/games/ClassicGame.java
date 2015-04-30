package edu.brown.cs.pdtran.minesweep.games;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;

import edu.brown.cs.pdtran.minesweep.metagame.Team;
import edu.brown.cs.pdtran.minesweep.options.SessionType;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;

public class ClassicGame extends Game {

  private long startTime;
  private ConcurrentMap<String, PlayerTeam> teams;

  public ClassicGame(String name, GameSpecs specs,
    ConcurrentMap<String, PlayerTeam> teams) {
    super(name, specs);
    this.teams = teams;
    startTime = System.currentTimeMillis();
  }

  public ClassicGame(PreRoom room) {
    super(room.getName(), room.getSpecs());
    teams = new ConcurrentHashMap<String, PlayerTeam>();
    for(Map.Entry<String, TeamFormation> entry : room.getTeams().entrySet()) {
      teams.put(entry.getKey(), new PlayerTeam(entry.getValue());
    }

  }

  @Override
  public void makeMove(String teamId, Move m) {
    teams.get(teamId).getBoard().makeMove(m.getYCoord(), m.getXCoord());
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
    makeMove("do something later", m);
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
  public int getGameScore(GamePlayer player) {
    int score = (int) (System.currentTimeMillis() - startTime);
    score = score / 1000; // Number of seconds
    return score;
  }

  @Override
  public SessionType getSessionType() {
    return SessionType.IN_GAME;
  }

  @Override
  public ConcurrentMap<String, ? extends Team> getTeams() {
    return teams;
  }

}
