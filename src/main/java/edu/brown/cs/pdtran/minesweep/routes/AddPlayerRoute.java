package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.RoomSession;
import spark.Request;
import spark.Response;
import spark.Route;

public class AddPlayerRoute implements Route {

  private RequestHandler handler;

  public AddPlayerRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    String roomCookie = req.cookie("minesweepRoomId");
    String userCookie = req.cookie("minesweepId");

    RoomSession roomSession = handler.getRoom(roomCookie);

    roomSession.getRoom().addGamer(userCookie, "TEMPORARY");

    return true;
  }

}
