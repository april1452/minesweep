package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

import edu.brown.cs.pdtran.minesweep.options.GameMode;

public class RoomInfo {

  public enum SESSION_TYPE {
    SETUP, IN_GAME;
  }

  private String roomName;
  private SESSION_TYPE sessionType;
  private GameMode gameMode;
  private List<String> playerNames;

  public RoomInfo(String roomName, SESSION_TYPE sessionType, String gameMode,
    List<String> playerNames) {
    this.roomName = roomName;
    this.sessionType = sessionType;
    this.gameMode = gameMode;
    this.playerNames = playerNames;
  }

  public String getRoomName() {
    return roomName;
  }

  public SESSION_TYPE sessionType() {
    return sessionType;
  }

  public String getGameMode() {
    return gameMode;
  }

  public List<String> getPlayerNames() {
    return playerNames;
  }

}
