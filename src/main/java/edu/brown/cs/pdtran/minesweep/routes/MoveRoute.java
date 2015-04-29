package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class MoveRoute implements Route {

  private RequestHandler handler;

  public MoveRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {

    // String roomCookie = req.cookie("minesweepRoomId");
    // int teamNumber = Integer.parseInt(req.cookie("minesweepTeamNumber"));
    //
    // QueryParamsMap qm = req.queryMap();
    //
    // int row = Integer.parseInt(qm.value("row"));
    // int col = Integer.parseInt(qm.value("column"));
    //
    // Move move = new CheckTile(col, row);
    //
    // handler.getGame(roomCookie).requestMove(teamNumber, move);

    return true;
  }

}
