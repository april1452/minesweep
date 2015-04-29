package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

import edu.brown.cs.pdtran.minesweep.options.SessionType;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

public class RoomInfo {
  private String roomName;
  private SessionType sessionType;
  private GameSpecs gameSpecs;
  private List<TeamInfo> teams;

  public RoomInfo(String roomName, SessionType sessionType,
    GameSpecs gameSpecs, List<TeamInfo> teamsInfo) {
    this.roomName = roomName;
    this.sessionType = sessionType;
    this.gameSpecs = gameSpecs;
    this.teams = teamsInfo;
  }

  public String getRoomName() {
    return roomName;
  }

  public SessionType getSessionType() {
    return sessionType;
  }

  public GameSpecs getGameSpecs() {
    return gameSpecs;
  }

  public List<TeamInfo> getTeams() {
    return teams;
  }

}
