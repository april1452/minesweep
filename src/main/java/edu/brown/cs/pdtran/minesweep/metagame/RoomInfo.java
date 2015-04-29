package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

  public String toJson() {
    JsonObject roomJson = new JsonObject();
    roomJson.addProperty("roomName", getRoomName());
    String gameModeString;
    switch (getGameSpecs().getMode()) {
      case CLASSIC:
        gameModeString = "Classic";
        break;
      default:
        gameModeString = "";
    }
    roomJson.addProperty("gameMode", gameModeString);

    JsonArray teamsJson = new JsonArray();
    for (TeamInfo team : getTeams()) {
      JsonObject teamJson = new JsonObject();
      JsonArray playersJson = new JsonArray();
      for (PlayerInfo player : team.getPlayers()) {
        JsonObject playerJson = new JsonObject();
        playerJson.addProperty("name", player.getName());
        playersJson.add(playerJson);
      }
      teamJson.addProperty("name", team.getName());
      teamJson.add("players", playersJson);
      teamsJson.add(teamJson);
    }

    roomJson.add("teams", teamsJson);

    return roomJson.toString();
  }

}
