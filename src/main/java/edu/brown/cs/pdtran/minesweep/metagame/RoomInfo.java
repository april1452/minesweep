package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

public class RoomInfo {

  private String roomName;
  private String gameMode;
  private List<String> playerNames;

  public RoomInfo(String roomName, String gameMode, List<String> playerNames) {
    this.roomName = roomName;
    this.gameMode = gameMode;
    this.playerNames = playerNames;
  }

  public String getRoomName() {
    return roomName;
  }

  public String getGameMode() {
    return gameMode;
  }

  public List<String> getPlayerNames() {
    return playerNames;
  }

}
