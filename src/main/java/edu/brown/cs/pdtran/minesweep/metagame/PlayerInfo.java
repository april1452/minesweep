package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.options.PlayerType;

public class PlayerInfo {

  private String name;
  private PlayerType type;

  public PlayerInfo(String name, PlayerType type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public PlayerType getType() {
    return type;
  }

}
