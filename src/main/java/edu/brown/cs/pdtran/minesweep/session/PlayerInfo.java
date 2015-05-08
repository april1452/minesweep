package edu.brown.cs.pdtran.minesweep.session;

import edu.brown.cs.pdtran.minesweep.types.PlayerType;

/**
 * Contains information - the name and type - of a given player.
 * @author Clayton Sanford
 */
public class PlayerInfo {

  private String name;
  private PlayerType type;

  /**
   * Constructs a PlayerInfo object.
   * @param name A string representing the name of the player.
   * @param type An enum representing if the player is a human or AI.
   */
  public PlayerInfo(String name, PlayerType type) {
    this.name = name;
    this.type = type;
  }

  /**
   * Gets the name of the PlayerInfo object.
   * @return A string representing the name of the player.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the type of the PlayerInfo object.
   * @return An enum representing if the player is a human or AI.
   */
  public PlayerType getType() {
    return type;
  }

}
