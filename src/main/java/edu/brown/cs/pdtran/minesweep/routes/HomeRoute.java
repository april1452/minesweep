package edu.brown.cs.pdtran.minesweep.routes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A route that creates the home page.
 * @author Clayton Sanford
 */
public class HomeRoute implements TemplateViewRoute {

  RequestHandler handler;

  /**
   * Constructs a new HomeRoute.
   * @param handler A RequestHandler used to get server information.
   */
  public HomeRoute(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, Object> variables = ImmutableMap.of("title", "Minesweep");

    String id = handler.getUserId();

    if (req.cookie("minesweepId") == null) {
      res.cookie("minesweepId", id);
    }

    return new ModelAndView(variables, "main.ftl");
  }

}
