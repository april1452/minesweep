package edu.brown.cs.pdtran.minesweep.routes;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.brown.cs.pdtran.minesweep.metagame.PlayerInfo;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo;
import edu.brown.cs.pdtran.minesweep.metagame.TeamInfo;
import spark.Request;
import spark.Response;
import spark.Route;

public class GamesRoute implements Route {

  private RequestHandler handler;

  public GamesRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    List<Map.Entry<String, RoomInfo>> infos = handler.getRooms();
    JsonArray sessionsJson = new JsonArray();

    for (Map.Entry<String, RoomInfo> entry : infos) {
      RoomInfo info = entry.getValue();
      JsonObject sessionJson = new JsonObject();
      sessionJson.addProperty("roomId", entry.getKey());
      sessionJson.addProperty("roomName", info.getRoomName());
      String sessionType;
      switch (info.getSessionType()) {
        case IN_GAME:
          sessionType = "inGame";
          break;
        case SETUP:
          sessionType = "setup";
          break;
        default:
          sessionType = "inGame";
      }
      sessionJson.addProperty("sessionType", sessionType);
      String gameModeString;
      switch (info.getGameSpecs().getMode()) {
        case CLASSIC:
          gameModeString = "Classic";
          break;
        default:
          gameModeString = "";
      }
      sessionJson.addProperty("gameMode", gameModeString);

      JsonArray teamsJson = new JsonArray();
      for (TeamInfo team : info.getTeams()) {
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
      sessionJson.add("teams", teamsJson);

      sessionsJson.add(sessionJson);
    }

    return sessionsJson.toString();
  }
}
