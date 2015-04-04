package edu.brown.cs.pdtran.minesweep.routes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class HomeRoute implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request arg0, Response arg1) {
    Map<String, Object> variables = ImmutableMap.of("title", "Bacon");
    return new ModelAndView(variables, "main.ftl");
  }

}