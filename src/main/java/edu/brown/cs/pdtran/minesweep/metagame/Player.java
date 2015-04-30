package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.types.PlayerType;

/**
 * An abstract class that represents a player in game. These can be human
 * or AI players.
 * @author Clayton Sanford
 */
public abstract class Player {

  protected String name;

  /**
   * A constructor for a player.
   * @param name A string representing the player's name.
   */
  public Player(String name) {
    this.name = name;
  }

  /**
   * Gets the name corresponding the player.
   * @return A string representing the player's name.
   */
  public String getName() {
    return name;
  }

  /**
   * An abstract method that returns the type of player (human or AI).
   * @return An enum PlayerType that can be human or AI.
   */
  public abstract PlayerType getType();

  /**
   * Gets the PlayerInfo object corresponding to a Player.
   * @return The PlayerInfo object that has the player's name and type.
   */
  public PlayerInfo getPlayerInfo() {
    return new PlayerInfo(getName(), getType());
  }
}
