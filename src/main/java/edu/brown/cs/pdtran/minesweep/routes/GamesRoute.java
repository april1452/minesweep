package edu.brown.cs.pdtran.minesweep.routes;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo;
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
    JsonObject sessionsJson = new JsonObject();

    for (Map.Entry<String, RoomInfo> entry : infos) {
      RoomInfo info = entry.getValue();

      sessionsJson.add(entry.getKey(), info.toJson());
    }

    return sessionsJson.toString();
  }
}
