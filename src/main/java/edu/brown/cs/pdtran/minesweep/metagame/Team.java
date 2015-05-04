package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An abstract class representing a Team.
 * @author Clayton Sanford
 */
public abstract class Team {

  protected String name;

  /**
   * Constructs a Team object.
   * @param name The name that represents the given team.
   */
  public Team(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the team.
   * @return A string representing the name of the Team.
   */
  public String getName() {
    return name;
  }

  /**
   * An abstract method that produces a map between strings and players.
   * @return A map that relates unique strings and human or AI players.
   */
  public abstract Map<String, ? extends Player> getPlayers();

  /**
   * Gets the AIPlayers on a given team.
   * @return A List of AIPlayer objects on that team.
   */
  public abstract List<? extends Player> getAis();

  /**
   * Gets the HumanPlayers on a given team.
   * @return A List of HumanPlayer objects on that team.
   */
  public abstract List<String> getHumans();

  /**
   * Gets the TeamInfo object corresponding to the Team, which is created
   * using a map of all PlayerInfo objects.
   * @return The TeamInfo object corresponding to the Team.
   */
  public TeamInfo getTeamInfo() {
    List<PlayerInfo> playersInfo = new ArrayList<PlayerInfo>();
    for (Player player : getPlayers().values()) {
      playersInfo.add(player.getPlayerInfo());
    }
    return new TeamInfo(name, playersInfo);
  }
}
