package edu.brown.cs.pdtran.minesweep.routes;

import java.util.Iterator;

import com.google.gson.Gson;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.RoomSession;
import spark.Request;
import spark.Response;
import spark.Route;

public class GamesRoute implements Route {

  private RequestHandler handler;
  private final Gson gson = new Gson();

  public GamesRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    Iterator<RoomSession> sessions = handler.getRooms();
    StringBuilder sb = new StringBuilder();

    while (sessions.hasNext()) {
      RoomSession session = sessions.next();
      sb.append(session.getRoom().getRoomInfo().toString());
    }

    return gson.toJson(sb.toString());
  }
}
