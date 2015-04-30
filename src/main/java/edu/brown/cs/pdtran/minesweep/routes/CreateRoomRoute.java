package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.options.BoardType;
import edu.brown.cs.pdtran.minesweep.options.GameMode;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateRoomRoute implements Route {

  private RequestHandler handler;

  public CreateRoomRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    System.out.println("test2");

    QueryParamsMap params = req.queryMap();
    String gameModeString = params.value("gameMode");
    String boardTypeString = params.value("boardType");
    GameMode gameMode = GameMode.valueOf(gameModeString);
    BoardType mode = BoardType.valueOf(boardTypeString);
    BoardType boardType = BoardType.valueOf()

        // HARDCODED FOR NOW
        int[] boardDims = {10, 10};
    GameSpecs specs = new GameSpecs(gameMode, 1, BoardType.DEFAULT, boardDims);

    PreRoom room = new PreRoom("Room name.", specs);

    String roomId = handler.addRoom(room);

    res.cookie("minesweepRoomId", roomId);

    System.out.println("test");

    return true;
  }
}
