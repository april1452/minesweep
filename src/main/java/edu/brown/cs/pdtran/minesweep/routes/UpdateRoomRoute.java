package edu.brown.cs.pdtran.minesweep.routes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo;
import edu.brown.cs.pdtran.minesweep.metagame.RoomSession;
import spark.Request;
import spark.Response;
import spark.Route;

public class UpdateRoomRoute implements Route {

  private RequestHandler handler;

  public UpdateRoomRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    String roomCookie = req.cookie("minesweepRoomId");
    String userCookie = req.cookie("minesweepId");

    RoomSession roomSession = handler.getRoom(roomCookie);

    RoomInfo info = roomSession.getRoomInfo();

    JsonObject roomJson = new JsonObject();
    roomJson.addProperty("roomName", info.getRoomName());
    String gameModeString;
    switch (info.getGameMode()) {
      case CLASSIC:
        gameModeString = "Classic";
        break;
      default:
        gameModeString = "";
    }
    roomJson.addProperty("gameMode", gameModeString);

    JsonArray playersJson = new JsonArray();
    for (String playerName : info.getPlayerNames()) {
      playersJson.add(new JsonPrimitive(playerName));
    }
    roomJson.add("playerNames", playersJson);

    return roomJson.toString();
  }

}
