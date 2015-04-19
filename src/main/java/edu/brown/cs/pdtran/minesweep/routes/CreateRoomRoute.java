package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.options.BoardType;
import edu.brown.cs.pdtran.minesweep.options.GameMode;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.setup.Setup;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateRoomRoute implements Route {

  @Override
  public Object handle(Request req, Response res) {
    QueryParamsMap params = req.queryMap();
    String gameModeString = params.value("gameMode");
    GameMode gameMode;
    switch (gameModeString) {
      case "classic":
        gameMode = GameMode.CLASSIC;
        break;
      default:
        return null; // TODO CHANGE
    }

    // HARDCODED FOR NOW
    int[] boardDims = { 10, 10 };
    GameSpecs specs =
      new GameSpecs(gameMode, 2, 2, 2, 1, BoardType.DEFAULT, boardDims);

    PreRoom room =
      Setup.setup("TEMP", req.cookie("minesweepId"), "TEMP", specs);

  }
}
