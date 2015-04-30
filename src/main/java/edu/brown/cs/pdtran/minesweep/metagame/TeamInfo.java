package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

/**
 * Contains the information necessary for a Team object, which is its name
 * and the PlayerInfo for all of its players.
 * @author Clayton Sanford
 */
public class TeamInfo {
  private String name;
  private List<PlayerInfo> players;

  /**
   * Constructs a TeamInfo object.
   * @param name A string representing the team's name.
   * @param players A list of PlayerInfo objects corresponding to all
   *        players in the Team.
   */
  public TeamInfo(String name, List<PlayerInfo> players) {
    this.name = name;
    this.players = players;
  }

  /**
   * Gets the name of the Team.
   * @return A string representing the name of the Team.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets all the corresponding player info.
   * @return A list of PlayerInfo objects corresponding to all players in
   *         the Team.
   */
  public List<PlayerInfo> getPlayers() {
    return players;
  }

}
