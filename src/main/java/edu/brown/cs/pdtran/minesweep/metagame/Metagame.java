package edu.brown.cs.pdtran.minesweep.metagame;

import java.io.File;
import java.io.IOException;

import edu.brown.cs.pdtran.minesweep.routes.GamesRoute;
import edu.brown.cs.pdtran.minesweep.routes.HomeRoute;
import edu.brown.cs.pdtran.minesweep.routes.SetupHandler;
import freemarker.template.Configuration;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public class Metagame {

  private static final String FREEMARKER_LOCATION =
    "src/main/resources/spark/template/freemarker";

  public Metagame(int port) {
    RequestHandler handler = new RequestHandler();

    Spark.setPort(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    FreeMarkerEngine engine = createEngine();
    Spark.get("/", new HomeRoute(handler), engine);
    Spark.get("/games", new GamesRoute(handler));
    Spark.get("/setup", new SetupHandler(handler), engine);
    // Spark.get("/room/:roomId", new ResultsHandler(engine));
    // Spark.get("/game/:gameId", new GameHandler(engine), new
    // FreeMarkerEngine());
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
