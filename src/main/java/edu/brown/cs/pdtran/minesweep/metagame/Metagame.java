package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.routes.GamesRoute;

import spark.Spark;
import spark.SparkBase;
import spark.template.freemarker.FreeMarkerEngine;

public class Metagame {

  public Metagame(int port) {
    RequestHandler handler = new RequestHandler();

    Spark.setPort(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.get("", new HomeRoute(), new FreeMarkerEngine());
    Spark.get("/games", new GamesRoute(handler));
    //Spark.post("/pregame", new ResultsHandler(engine));
    //Spark.get("/game/:gameId", new gameHandler(engine),
    new FreeMarkerEngine());
  }


}

}
