package edu.brown.cs.pdtran.minesweep.setup;

import java.util.ArrayList;
import java.util.List;

public class PreRoom {
  private String roomName;
  private List<String> humanIds;
  private GameSpecs specs;
  private List<TeamFormation> teams;

  /**
   * Create a room that has been processed from the GUI. This room contains
   * specs that will be used to create an actual game.
   *
   * @param host
   * @param specs
   * @param teams
   */
  public PreRoom(String roomName, GameSpecs specs, List<TeamFormation> teams) {
    this.roomName = roomName;
    humanIds = new ArrayList<String>();
    this.specs = specs;
    this.teams = teams;
  }

  public String getRoomName() {
    return roomName;
  }

  public List<String> getHumanIds() {
    return humanIds;
  }

  /**
   * Return GameSpecs object.
   *
   * @return game specifications as a GameSpecs object
   */
  public GameSpecs getGameSpecs() {
    return specs;
  }

  /**
   * Return list of all preformed teams.
   *
   * @return list of teams where each team is a TeamFormation object
   */
  public List<TeamFormation> getAllTeams() {
    return teams;
  }

  // this isnt concurrent yet
  public int addGamer(String id, String name) {
    humanIds.add(id);
    for (int i = 0; i < specs.getNumTeamPlayers(); i++) {
      for (int j = 0; j < teams.size(); j++) {
        List<Gamer> teamGamers = teams.get(j).getGamers();
        if (teamGamers.size() < i + 1) {
          teamGamers.add(new HumanGamer(id, name));
          return j;
        }
      }
    }
    return 0;
  }
}
