package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.metagame.GameSession;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class BoardRoute implements Route {

  private RequestHandler handler;

  public BoardRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    String roomId = req.cookie("minesweepRoomId");
    int teamNumber = Integer.parseInt(req.cookie("minesweepTeamNumber"));

    GameSession game = handler.getGame(roomId);

    Board board = game.getBoard(teamNumber);

    return board.toJson();
  }

}
