package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.Map;

import edu.brown.cs.pdtran.minesweep.types.SessionType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

/**
 * Contains information necessary in a room to produce a game from it: the
 * name of the room, the type of session, the game specifications, and the
 * teams.
 * @author Clayton Sanford
 */
public class SessionInfo {
  private String roomName;
  private SessionType sessionType;
  private GameSpecs gameSpecs;
  private Map<String, TeamInfo> teams;
  private static final Gson GSON = new Gson();

  /**
   * Constructs a RoomInfo object.
   * @param roomName The name of the room to join.
   * @param sessionType An enum representing whether it is in setup or in
   *        game.
   * @param gameSpecs The GameSpecs object defined before making the room.
   * @param teams The Map linking unique id strings to TeamInfo objects.
   */
  public SessionInfo(String roomName, SessionType sessionType,
      GameSpecs gameSpecs, Map<String, TeamInfo> teams) {
    this.roomName = roomName;
    this.sessionType = sessionType;
    this.gameSpecs = gameSpecs;
    this.teams = teams;
  }

  /**
   * Gets the name of the room.
   * @return A string representing the room's name.
   */
  public String getRoomName() {
    return roomName;
  }

  /**
   * Gets whether the session is in game or in setup.
   * @return An enum, SessionType, regarding whether it's in game or in
   *         setup.
   */
  public SessionType getSessionType() {
    return sessionType;
  }

  /**
   * Gets the specifications for the game.
   * @return The GameSpecs object contained in the RoomInfo object.
   */
  public GameSpecs getGameSpecs() {
    return gameSpecs;
  }

  /**
   * Gets the teams that are in the room.
   * @return A map of unique ids to TeamInfo objects of the teams in the
   *         room.
   */
  public Map<String, TeamInfo> getTeams() {
    return teams;
  }

  /**
   * Gets the RoomInfo object in the form of a JsonObject to be sent to the
   * frontend.
   * @return A JsonObject that contains all information contained the
   *         RoomInfo object.
   */
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
