package edu.brown.cs.pdtran.minesweep.routes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Sets up the "/play" page before other handlers set up more complicated
 * aspects of it.
 * @author Clayton Sanford
 */
public class PlayHandler implements TemplateViewRoute {

  private RequestHandler handler;

  /**
   * Constructs a new PlayHandler.
   * @param handler A RequestHandler used to get server information.
   */
  public PlayHandler(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, Object> variables = ImmutableMap.of("title", "Minesweep");

    return new ModelAndView(variables, "play.ftl");
  }

}
