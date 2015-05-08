package edu.brown.cs.pdtran.minesweep.routes;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.session.SessionInfo;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A route that allows for rooms to be added to the sessions registered on
 * the main page.
 * @author Clayton Sanford
 */
public class GamesRoute implements Route {

  private RequestHandler handler;

  /**
   * Constructs a new GamesRoute.
   * @param handler A RequestHandler used to get server information.
   */
  public GamesRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public Object handle(Request req, Response res) {
    List<Map.Entry<String, SessionInfo>> infos = handler.getSessions();
    JsonObject sessionsJson = new JsonObject();

    for (Map.Entry<String, SessionInfo> entry : infos) {
      SessionInfo info = entry.getValue();

      sessionsJson.add(entry.getKey(), info.toJson());
    }

    return sessionsJson.toString();
  }
}
