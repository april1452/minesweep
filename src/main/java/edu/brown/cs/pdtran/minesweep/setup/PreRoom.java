package edu.brown.cs.pdtran.minesweep.setup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.types.SessionType;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.metagame.Session;

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

  /**
   * Adds a Gamer to a TeamFormation object in the Room.
   * @param id A unique string for the Gamer being added.
   * @param g A Gamer to be added to a TeamFormation object.
   * @return The id corresponding to the TeamFormation object the Gamer was
   *         added to.
   */
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
