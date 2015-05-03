package edu.brown.cs.pdtran.minesweep.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.Session;
import edu.brown.cs.pdtran.minesweep.types.SessionType;

/**
 * This class contains data for the Room information before the room is
 * ready to be played.
 * @author Clayton Sanford
 */
public class PreRoom extends Session {
  private String hostId;
  private ConcurrentMap<String, TeamFormation> teams;

  /**
   * Create a room that has been processed from the GUI. This room contains
   * specs that will be used to create an actual game.
   * @param name The string corresponding to the name of the game.
   * @param specs The specifications for the room from the Setup page.
   */
  public PreRoom(String name, GameSpecs specs) {
    super(name, specs);
    teams = new ConcurrentHashMap<String, TeamFormation>();
    for (int i = 0; i < specs.getNumTeams(); i++) {
      RequestHandler.addAndGetKey(teams, new TeamFormation("Team " + (i + 1)));
    }
  }

  @Override
  public ConcurrentMap<String, TeamFormation> getTeams() {
    return teams;
  }

  public void addHuman(String teamId, String gamerId, HumanGamer hg) {
    TeamFormation teamToAdd = teams.get(teamId);
    if (teamToAdd.getPlayers().entrySet().size() < specs.getNumTeamPlayers()) {
      teams.get(teamId).addHumanGamer(gamerId, hg);
    }
  }

  public void switchTeam(String teamId, String gamerId, String newTeamId) {
    TeamFormation oldTeam = teams.get(teamId);
    oldTeam.getHumans().remove(gamerId);
    Gamer gamer = oldTeam.getPlayers().remove(gamerId);

    TeamFormation newTeam = teams.get(teamId);
    newTeam.getHumans().add(gamerId);
    newTeam.getPlayers().put(gamerId, gamer);
  }

  public void addAi(String teamId, String gamerId, AIGamer ag) {
    TeamFormation teamToAdd = teams.get(teamId);
    if (teamToAdd.getPlayers().entrySet().size() < specs.getNumTeamPlayers()) {
      teams.get(teamId).addAIGamer(gamerId, ag);
    }
  }


  @Override
  public SessionType getSessionType() {
    return SessionType.SETUP;
  }

  @Override
  public Map<String, List<String>> getHumans() {
    Map<String, List<String>> teamHumans = new HashMap<String, List<String>>();
    for (Entry<String, TeamFormation> entry : teams.entrySet()) {
      teamHumans.put(entry.getKey(), entry.getValue().getHumans());
    }
    return teamHumans;
  }
}
