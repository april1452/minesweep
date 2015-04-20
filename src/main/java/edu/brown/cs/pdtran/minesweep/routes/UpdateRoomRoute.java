package edu.brown.cs.pdtran.minesweep.routes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.brown.cs.pdtran.minesweep.metagame.PlayerInfo;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo;
import edu.brown.cs.pdtran.minesweep.metagame.RoomSession;
import edu.brown.cs.pdtran.minesweep.metagame.TeamInfo;
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

    if (roomSession == null) {
      return "gameStarted";
    }

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

    roomJson.add("teams", teamsJson);

    return roomJson.toString();
  }

}
