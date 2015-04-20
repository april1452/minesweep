package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

public class TeamInfo {
  private String name;
  private List<PlayerInfo> players;

  public TeamInfo(String name, List<PlayerInfo> players) {
    this.name = name;
    this.players = players;
  }

  public String getName() {
    return name;
  }

  public List<PlayerInfo> getPlayers() {
    return players;
  }

}
