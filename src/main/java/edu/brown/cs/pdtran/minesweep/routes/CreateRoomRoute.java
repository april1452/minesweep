package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.types.BoardType;
import edu.brown.cs.pdtran.minesweep.types.GameMode;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Creates a Room using information from entered on the setup page.
 * @author Clayton Sanford
 */
public class CreateRoomRoute implements Route {

  private RequestHandler handler;

  /**
   * Constructs a new CreateRoomRoute.
   * @param handler A RequestHandler used to get server information.
   */
  public CreateRoomRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    QueryParamsMap params = req.queryMap();
    // System.out.println(Arrays.toString(params.values()));
    String gameModeString = params.value("gameMode");
    String boardTypeString = params.value("boardType");
    GameMode gameMode = GameMode.valueOf(gameModeString);
    BoardType boardType = BoardType.valueOf(boardTypeString);

    // HARDCODED FOR NOW
    int[] boardDims = {10, 10};
    GameSpecs specs = new GameSpecs(gameMode, 1, boardType, boardDims);

    PreRoom room = new PreRoom("Room name.", specs);

    String roomId = handler.addRoom(room);

    res.cookie("minesweepRoomId", roomId);

    return true;
  }
}
