package edu.brown.cs.pdtran.minesweep.routes;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import spark.TemplateViewRoute;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;

public class SetupHandler implements TemplateViewRoute {

  private RequestHandler handler;

  public SetupHandler(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    handler.ad
    return null;
  }
}
