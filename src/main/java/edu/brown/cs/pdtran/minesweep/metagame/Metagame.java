package edu.brown.cs.pdtran.minesweep.metagame;

import java.io.File;
import java.io.IOException;

import edu.brown.cs.pdtran.minesweep.routes.CreateRoomRoute;
import edu.brown.cs.pdtran.minesweep.routes.GamesRoute;
import edu.brown.cs.pdtran.minesweep.routes.HomeRoute;
import edu.brown.cs.pdtran.minesweep.routes.PlayHandler;
import edu.brown.cs.pdtran.minesweep.routes.SetupHandler;
import edu.brown.cs.pdtran.minesweep.websockets.GameServer;
import freemarker.template.Configuration;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The class that represents the Metagame, which is the first thing
 * activated by the Main class. It allows for multiple games at once to be
 * run at the same time.
 * @author Clayton Sanford
 */
public class Metagame {

  private static final String FREEMARKER_LOCATION =
      "src/main/resources/spark/template/freemarker";

  /**
   * Constructs a Metagame object.
   * @param httpPort An integer representing the port value for HTTP.
   * @param wsPort An integer representing the port value for WebSockets.
   * @throws IOException Thrown if there are errors with IO.
   */
  public Metagame(int httpPort, int wsPort) throws IOException {
    RequestHandler handler = new RequestHandler();

    Spark.setPort(httpPort);
    Spark.externalStaticFileLocation("src/main/resources/static");
    FreeMarkerEngine engine = createEngine();
    Spark.get("/", new HomeRoute(handler), engine);
    Spark.get("/games", new GamesRoute(handler));
    Spark.get("/setup", new SetupHandler(handler), engine);
    Spark.post("/create", new CreateRoomRoute(handler));
    Spark.get("/play", new PlayHandler(handler), engine);
    // Spark.post("/move", new MoveRoute(handler));

    GameServer server = new GameServer(wsPort, handler);
    server.start();
  }

  /**
   * createEngine creates the FreeMarker engine.
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
