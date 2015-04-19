package edu.brown.cs.pdtran.minesweep.setup;

import java.util.List;

public class PreRoom {
  private String roomName;
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
    this.specs = specs;
    this.teams = teams;
  }

  public String getRoomName() {
    return roomName;
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
}
