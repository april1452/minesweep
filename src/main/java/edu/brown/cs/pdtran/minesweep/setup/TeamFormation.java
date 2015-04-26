package edu.brown.cs.pdtran.minesweep.setup;

import java.util.concurrent.ConcurrentHashMap;

import edu.brown.cs.pdtran.minesweep.metagame.Team;

/**
 * Pregame/setup phase team. Sets up a team of Gamers who will be turned into a
 * team of Player objects in game.
 *
 * @author pdtran
 *
 */
public class TeamFormation extends Team {

  /**
   * Create team formation.
   *
   * @param gamers
   *          list of gamers
   * @param lives
   *          number of lives
   */
  public TeamFormation(String name) {
    this.name = name;
    players = new ConcurrentHashMap<String, Gamer>();
  }
}
