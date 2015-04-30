package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.types.SessionType;

import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

/**
 * An abstract class representing a session of gameplay, which can be
 * either a room or a game in play.
 * @author Clayton Sanford
 */
public abstract class Session {

  protected String name;
  protected GameSpecs specs;

  /**
   * Constructs a Session object.
   * @param name A string corresponding to the name of the session.
   * @param specs A GameSpecs object that contains the information
   *        necessary to run the game.
   */
  public Session(String name, GameSpecs specs) {
    this.name = name;
    this.specs = specs;
  }

  /**
   * Gets the name of the Session.
   * @return A string representing the session's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the GameSpecs of the Session.
   * @return The GameSpecs object that the session holds.
   */
  public GameSpecs getSpecs() {
    return specs;
  }

  /**
   * Gets a list of all users in the Session.
   * @return A List of unique string ids for each user in the teams.
   */
  public List<String> getUsers() {
    List<String> users = new ArrayList<String>();
    for (Team t : getTeams().values()) {
      for (String id : t.getPlayers().keySet()) {
        users.add(id);
      }
    }
    return users;
  }

  /**
   * An abstract method that returns the type of the session.
   * @return A SessionType enum that represents whether the session is in
   *         game or setup.
   */
  public abstract SessionType getSessionType();

  /**
   * An abstract method that returns the ConcurrentMap relating string ids
   * to specified types of Teams.
   * @return The Concurrentmap relating string ids to specified types of
   *         Teams.
   */
  public abstract ConcurrentMap<String, ? extends Team> getTeams();

  /**
   * Gets the RoomInfo contained in the session.
   * @return The RoomInfo object that depends upon the name, session type,
   *         game specs, and teams.
   */
  public RoomInfo getRoomInfo() {
    Map<String, TeamInfo> teams = new HashMap<String, TeamInfo>();
    for (Map.Entry<String, ? extends Team> entry : getTeams().entrySet()) {
      teams.put(entry.getKey(), entry.getValue().getTeamInfo());
    }
    return new RoomInfo(name, getSessionType(), specs, teams);
  }
}
