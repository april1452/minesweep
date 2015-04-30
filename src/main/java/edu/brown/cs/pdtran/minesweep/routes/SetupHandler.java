package edu.brown.cs.pdtran.minesweep.routes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Sets up the "/setup" page.
 * @author Clayton Sanford
 */
public class SetupHandler implements TemplateViewRoute {

  private RequestHandler handler;

  /**
   * Constructs a new SetupHandler.
   * @param handler A RequestHandler used to get server information.
   */
  public SetupHandler(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, Object> variables = ImmutableMap.of("title", "Minesweep+");

    return new ModelAndView(variables, "setup.ftl");
  }
}
