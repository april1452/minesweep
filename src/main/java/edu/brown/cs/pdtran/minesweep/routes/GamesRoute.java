package edu.brown.cs.pdtran.minesweep.routes;

import java.util.Iterator;

import edu.brown.cs.pdtran.minesweep.games.Game;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class GamesRoute implements Route {

  public RequestHandler handler;

  public GamesRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    Iterator<Game> games = handler.getGames();
    StringBuilder sb = new StringBuilder();

    while(games.hasNext()) {
      Game game = games.next();
      sb.append(game.)
    }

    return null;
  }
}
