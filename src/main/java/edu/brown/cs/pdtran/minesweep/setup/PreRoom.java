package edu.brown.cs.pdtran.minesweep.setup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.Session;
import edu.brown.cs.pdtran.minesweep.options.SessionType;

public class PreRoom extends Session {
  private String host;
  private ConcurrentMap<String, TeamFormation> teams;

  /**
   * Create a room that has been processed from the GUI. This room contains
   * specs that will be used to create an actual game.
   *
   * @param host
   * @param specs
   * @param teams
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

  public synchronized String addGamer(String id, Gamer g) {
    for (Map.Entry<String, TeamFormation> entry : teams.entrySet()) {
      TeamFormation tf = entry.getValue();
      if (tf.getPlayers().size() < specs.getNumTeamPlayers()) {
        tf.addPlayer(id, g);
        return entry.getKey();
      }
    }
    // TODO throw GAME FULL EXCEPTIOn
    return null;
    // throw new Exception();
  }

  @Override
  public SessionType getSessionType() {
    return SessionType.SETUP;
  }
}
