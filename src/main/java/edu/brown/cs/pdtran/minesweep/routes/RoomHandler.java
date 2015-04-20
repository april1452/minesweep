package edu.brown.cs.pdtran.minesweep.routes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class RoomHandler implements TemplateViewRoute {

  private RequestHandler handler;

  public RoomHandler(RequestHandler handler) {
    this.handler = handler;
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, Object> variables = ImmutableMap.of("title", "Minesweep");

    return new ModelAndView(variables, "room.ftl");
  }

}
