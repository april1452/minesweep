package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A route that processes all moves made to the server.
 * @author Clayton Sanford
 */
public class MoveRoute implements Route {

  private RequestHandler handler;

  /**
   * Constructs a new MoveRoute.
   * @param handler A RequestHandler used to get server information.
   */
  public MoveRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {

    // String roomCookie = req.cookie("minesweepRoomId");
    // int teamNumber =
    // Integer.parseInt(req.cookie("minesweepTeamNumber"));
    //
    // QueryParamsMap qm = req.queryMap();
    //

    return true;
  }

}
