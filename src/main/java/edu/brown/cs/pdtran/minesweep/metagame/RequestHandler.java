package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.brown.cs.pdtran.minesweep.games.Game;

public class RequestHandler {

  private Map<Integer, Game> games;

  public RequestHandler() {
    games = new HashMap<Integer, Game>();
  }

  public Iterator<Game> getGames() {
    return games.values().iterator();
  }
}
