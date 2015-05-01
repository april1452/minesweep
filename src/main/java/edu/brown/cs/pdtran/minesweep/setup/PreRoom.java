package edu.brown.cs.pdtran.minesweep.setup;

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
  private String host;
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

  public String addHuman(String teamId, String gamerId, HumanGamer hg) {
    TeamFormation teamToAdd = teams.get(teamId);
    if (teamToAdd.getPlayers().entrySet().size() < specs.getNumTeamPlayers()) {
      removeFromAllTeams(gamerId);
      teams.get(teamId).addHumanGamer(gamerId, hg);
    }
    return null;
  }

  public String addAi(String teamId, String gamerId, AIGamer ag) {
    TeamFormation teamToAdd = teams.get(teamId);
    if (teamToAdd.getPlayers().entrySet().size() < specs.getNumTeamPlayers()) {
      removeFromAllTeams(gamerId);
      teams.get(teamId).addAIGamer(gamerId, ag);
    }
    // TODO check if game is full
    return null;
  }

  public void removeFromAllTeams(String gamerId) {
    for (TeamFormation team : getTeams().values()) {
      team.getPlayers().remove(gamerId);
      team.getHumans().remove(gamerId);
    }
  }

  @Override
  public SessionType getSessionType() {
    return SessionType.SETUP;
  }
}
