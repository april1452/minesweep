package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.options.SessionType;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

public abstract class Session {

  protected String name;
  protected GameSpecs specs;

  public Session(String name, GameSpecs specs) {
    this.name = name;
    this.specs = specs;
  }

  public String getName() {
    return name;
  }

  public GameSpecs getSpecs() {
    return specs;
  }

  public List<String> getUsers() {
    List<String> users = new ArrayList<String>();
    for (Team t : getTeams().values()) {
      for (String id : t.getPlayers().keySet()) {
        users.add(id);
      }
    }
    return users;
  }

  public abstract SessionType getSessionType();

  public abstract ConcurrentMap<String, ? extends Team> getTeams();

  public RoomInfo getRoomInfo() {
    Map<String, TeamInfo> teams = new HashMap<String, TeamInfo>();
    for (Map.Entry<String, ? extends Team> entry : getTeams().entrySet()) {
      teams.put(entry.getKey(), entry.getValue().getTeamInfo());
    }
    return new RoomInfo(name, getSessionType(), specs, teams);
  }
}
