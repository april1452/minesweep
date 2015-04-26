package edu.brown.cs.pdtran.minesweep.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.brown.cs.pdtran.minesweep.metagame.PlayerInfo;
import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo;
import edu.brown.cs.pdtran.minesweep.metagame.Session;
import edu.brown.cs.pdtran.minesweep.metagame.TeamInfo;
import edu.brown.cs.pdtran.minesweep.options.PlayerType;
import edu.brown.cs.pdtran.minesweep.options.SessionType;

public class PreRoom extends Session {
  private GameSpecs specs;

  /**
   * Create a room that has been processed from the GUI. This room contains
   * specs that will be used to create an actual game.
   *
   * @param host
   * @param specs
   * @param teams
   */
  public PreRoom(String roomName, GameSpecs specs) {
    this.name = roomName;
    this.specs = specs;
    teams = new ConcurrentHashMap<String, TeamFormation>();
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

  // this isnt concurrent yet
  public int addGamer(String id, String name) {
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

  @Override
  public RoomInfo getRoomInfo() {
    List<TeamInfo> teamsInfo = new ArrayList<TeamInfo>();
    for (TeamFormation team : teams.values()) {
      List<Gamer> gamers = team.getGamers();
      List<PlayerInfo> players = new ArrayList<PlayerInfo>();
      for (Gamer gamer : gamers) {
        players.add(new PlayerInfo(gamer.getUserName(), PlayerType.HUMAN)); // CHANGE
        // THIS
        // EVENTUALLY
      }
      teamsInfo.add(new TeamInfo("TEMPORARY TEAM", players));
    }
    return new RoomInfo(roomName, SessionType.SETUP, specs.getMode(), teamsInfo);
  }
}
