package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

import edu.brown.cs.pdtran.minesweep.options.GameMode;

public class RoomInfo {

  public enum SessionType {
    SETUP, IN_GAME;
  }

  private String roomName;
  private SessionType sessionType;
  private GameMode gameMode;
  private List<String> playerNames;

  public RoomInfo(String roomName, SessionType sessionType, GameMode gameMode,
    List<String> playerNames) {
    this.roomName = roomName;
    this.sessionType = sessionType;
    this.gameMode = gameMode;
    this.playerNames = playerNames;
  }

  public String getRoomName() {
    return roomName;
  }

  public SessionType sessionType() {
    return sessionType;
  }

  public GameMode getGameMode() {
    return gameMode;
  }

  public List<String> getPlayerNames() {
    return playerNames;
  }

}
