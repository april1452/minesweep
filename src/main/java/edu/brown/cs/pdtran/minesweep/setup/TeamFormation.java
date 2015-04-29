package edu.brown.cs.pdtran.minesweep.setup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.metagame.Player;
import edu.brown.cs.pdtran.minesweep.metagame.Team;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;

/**
 * Pregame/setup phase team. Sets up a team of Gamers who will be turned into a
 * team of Player objects in game.
 *
 * @author pdtran
 *
 */
public class TeamFormation extends Team {

  private ConcurrentMap<String, Gamer> gamers;

  /**
   * Create team formation.
   *
   * @param gamers
   *          list of gamers
   * @param lives
   *          number of lives
   */
  public TeamFormation(String name) {
    super(name);
    gamers = new ConcurrentHashMap<String, Gamer>();
  }

  public PlayerTeam toTeam() {
    return null;
  }

  @Override
  public Map<String, ? extends Player> getPlayers() {
    return gamers;
  }

  public void addPlayer(String id, Gamer g) {
    gamers.putIfAbsent(id, g);
  }
}
