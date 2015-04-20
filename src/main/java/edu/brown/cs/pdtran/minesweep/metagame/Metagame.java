package edu.brown.cs.pdtran.minesweep.metagame;

import java.io.File;
import java.io.IOException;

import edu.brown.cs.pdtran.minesweep.routes.AddPlayerRoute;
import edu.brown.cs.pdtran.minesweep.routes.BoardRoute;
import edu.brown.cs.pdtran.minesweep.routes.CreateRoomRoute;
import edu.brown.cs.pdtran.minesweep.routes.GameHandler;
import edu.brown.cs.pdtran.minesweep.routes.GamesRoute;
import edu.brown.cs.pdtran.minesweep.routes.HomeRoute;
import edu.brown.cs.pdtran.minesweep.routes.MoveRoute;
import edu.brown.cs.pdtran.minesweep.routes.RoomHandler;
import edu.brown.cs.pdtran.minesweep.routes.SetupHandler;
import edu.brown.cs.pdtran.minesweep.routes.StartGameRoute;
import edu.brown.cs.pdtran.minesweep.routes.UpdateRoomRoute;
import freemarker.template.Configuration;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public class Metagame {

  private static final String FREEMARKER_LOCATION =
    "src/main/resources/spark/template/freemarker";

  public Metagame(int port) throws IOException {
    RequestHandler handler = new RequestHandler();

    Spark.setPort(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    FreeMarkerEngine engine = createEngine();
    Spark.get("/", new HomeRoute(handler), engine);
    Spark.get("/games", new GamesRoute(handler));
    Spark.get("/setup", new SetupHandler(handler), engine);
    Spark.post("/create", new CreateRoomRoute(handler));
    Spark.get("/room", new RoomHandler(handler), engine);
    Spark.post("/roomAdd", new AddPlayerRoute(handler));
    Spark.get("/roomUpdate", new UpdateRoomRoute(handler));
    Spark.post("/start", new StartGameRoute(handler));
    Spark.get("/game", new GameHandler(handler), engine);
    Spark.get("/board", new BoardRoute(handler));
    Spark.post("/move", new MoveRoute(handler));
  }

  /**
   * createEngine creates the FreeMarker engine.
   *
   * @return a freemarker engine
   */
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File(FREEMARKER_LOCATION);
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.\n",
        templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }
}
