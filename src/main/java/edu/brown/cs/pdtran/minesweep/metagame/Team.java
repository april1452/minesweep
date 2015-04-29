package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Team {

  protected String name;

  public Team(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public abstract Map<String, ? extends Player> getPlayers();

  public TeamInfo getTeamInfo() {
    List<PlayerInfo> playersInfo = new ArrayList<PlayerInfo>();
    for (Player player : getPlayers().values()) {
      playersInfo.add(player.getPlayerInfo());
    }
    return new TeamInfo(name, playersInfo);
  }
}
