package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public abstract class Team {

  protected String name;
  protected ConcurrentMap<String, ? extends Player> players;

  public String getName() {
    return name;
  }

  public TeamInfo getTeamInfo() {
    List<PlayerInfo> playersInfo = new ArrayList<PlayerInfo>();
    for (Player player : players.values()) {
      playersInfo.add(player.getPlayerInfo());
    }
    return new TeamInfo(name, playersInfo);
  }
}
