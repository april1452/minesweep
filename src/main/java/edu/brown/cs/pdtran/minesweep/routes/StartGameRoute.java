package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class StartGameRoute implements Route {

  RequestHandler handler;

  public StartGameRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    String roomCookie = req.cookie("minesweepRoomId");
    String userCookie = req.cookie("minesweepId");

    handler.convert(roomCookie);

    return true;
  }

}
