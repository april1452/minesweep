package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.options.PlayerType;

public abstract class Player {

  protected String name;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public abstract PlayerType getType();

  public PlayerInfo getPlayerInfo() {
    return new PlayerInfo(getName(), getType());
  }
}
