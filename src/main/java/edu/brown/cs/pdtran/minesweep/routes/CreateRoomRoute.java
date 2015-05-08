package edu.brown.cs.pdtran.minesweep.routes;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;
import edu.brown.cs.pdtran.minesweep.setup.Room;
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

    String roomName = params.value("roomName");

    String gameModeString = params.value("gameMode");
    GameMode gameMode = GameMode.valueOf(gameModeString);
    int difficulty = Integer.parseInt(params.value("difficulty"));

    if (difficulty > 10) {
      difficulty = 10;
    } else if (difficulty < 0) {
      difficulty = 0;
    }

    String boardTypeString = params.value("boardType");
    BoardType boardType = BoardType.valueOf(boardTypeString);

    int boardWidth = Integer.parseInt(params.value("boardWidth"));
    if (boardWidth > 32) {
      boardWidth = 32;
    } else if (boardWidth < 0) {
      boardWidth = 0;
    }
    int boardHeight = Integer.parseInt(params.value("boardHeight"));
    if (boardHeight > 32) {
      boardHeight = 32;
    } else if (boardHeight < 0) {
      boardHeight = 0;
    }

    int numTeams = Integer.parseInt(params.value("numTeams"));
    int numPlayers = Integer.parseInt(params.value("numPlayers"));

    int numLives = Integer.parseInt(params.value("numLives"));

    String hostId = params.value("hostId");

    int[] boardDims = {boardWidth, boardHeight};
    GameSpecs specs =
        new GameSpecs(gameMode, boardType, 1, numTeams, numPlayers,
            numLives,
            boardDims, difficulty);

    Room room = new Room(hostId, roomName, specs);

    String roomId = handler.addRoom(room);

    res.cookie("minesweepRoomId", roomId);

    return true;
  }
}
