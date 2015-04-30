package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.brown.cs.pdtran.minesweep.options.SessionType;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

public class RoomInfo {
  private String roomName;
  private SessionType sessionType;
  private GameSpecs gameSpecs;
  private Map<String, TeamInfo> teams;
  private static final Gson GSON = new Gson();

  public RoomInfo(String roomName, SessionType sessionType,
      GameSpecs gameSpecs, Map<String, TeamInfo> teams) {
    this.roomName = roomName;
    this.sessionType = sessionType;
    this.gameSpecs = gameSpecs;
    this.teams = teams;
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

  public Map<String, TeamInfo> getTeams() {
    return teams;
  }

  public JsonObject toJson() {
    // JsonObject roomJson = new JsonObject();
    // roomJson.addProperty("roomName", getRoomName());
    //
    // roomJson.addProperty("gameMode",
    // getGameSpecs().getMode().toString());
    //
    // roomJson.addProperty("sessionType", getSessionType().toString());

    JsonObject roomJson =
        new JsonParser().parse(GSON.toJson(this)).getAsJsonObject();
    // JsonArray teamsJson = new JsonArray();
    // for (Map.Entry<String, TeamInfo> entry : teams.entrySet()) {
    // JsonObject teamJson = new JsonObject();
    // JsonArray playersJson = new JsonArray();
    // TeamInfo team = entry.getValue();
    // for (PlayerInfo player : team.getPlayers()) {
    // JsonObject playerJson = new JsonObject();
    // playerJson.addProperty("name", player.getName());
    // playersJson.add(playerJson);
    // }
    // teamJson.addProperty("name", team.getName());
    // teamJson.addProperty("id", entry.getKey());
    // teamJson.add("players", playersJson);
    // teamsJson.add(teamJson);
    // }
    //
    // roomJson.add("teams", teamsJson);

    return roomJson;
  }

}
