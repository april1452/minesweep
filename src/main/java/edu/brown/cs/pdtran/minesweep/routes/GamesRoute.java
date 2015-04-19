package edu.brown.cs.pdtran.minesweep.routes;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo;
import edu.brown.cs.pdtran.minesweep.metagame.RoomSession;
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
    Map<Integer, RoomSession> sessions = handler.getRooms();
    JsonArray sessionsJson = new JsonArray();

    for (Map.Entry<Integer, RoomSession> entry : sessions.entrySet()) {
      RoomInfo info = entry.getValue().getRoomInfo();
      JsonObject sessionJson = new JsonObject();
      sessionJson.addProperty("roomId", entry.getKey());
      sessionJson.addProperty("roomName", info.getRoomName());
      sessionJson.addProperty("gameMode", info.getGameMode());

      JsonArray playersJson = new JsonArray();
      for (String playerName : info.getPlayerNames()) {
        playersJson.add(new JsonPrimitive(playerName));
      }
      sessionJson.add("playerNames", playersJson);

      sessionsJson.add(sessionJson);
    }

    return sessionsJson.toString();
  }
}
