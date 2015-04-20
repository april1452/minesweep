package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

import edu.brown.cs.pdtran.minesweep.options.GameMode;
import edu.brown.cs.pdtran.minesweep.options.SessionType;

public class RoomInfo {

  private String roomName;
  private SessionType sessionType;
  private GameMode gameMode;
  private List<TeamInfo> teams;

  public RoomInfo(String roomName, SessionType sessionType, GameMode gameMode,
    List<TeamInfo> teams) {
    this.roomName = roomName;
    this.sessionType = sessionType;
    this.gameMode = gameMode;
    this.teams = teams;
  }

  public String getRoomName() {
    return roomName;
  }

  public SessionType getSessionType() {
    return sessionType;
  }

  public GameMode getGameMode() {
    return gameMode;
  }

  public List<TeamInfo> getTeams() {
    return teams;
  }

}
