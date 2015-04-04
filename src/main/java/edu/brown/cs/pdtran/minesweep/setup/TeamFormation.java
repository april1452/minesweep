package edu.brown.cs.pdtran.minesweep.setup;

import java.util.List;

/**
 * Pregame/setup phase team. Sets up a team of Gamers who will be turned into a
 * team of Player objects in game.
 * @author pdtran
 *
 */
public class TeamFormation {
  private String id;
  private List<Gamer> gamers;
  private int lives;

  /**
   * Create team formation.
   * @param gamers list of gamers
   * @param lives number of lives
   */
  public TeamFormation(List<Gamer> gamers, int lives) {
    // TODO create ID
    this.gamers = gamers;
    this.lives = lives;
  }

  /**
   * Return list of all gamers.
   * @return all gamers
   */
  public List<Gamer> getGamers() {
    return gamers;
  }

  /**
   * Return number of lives.
   * @return number of lives.
   */
  public int getLives() {
    return lives;
  }
}
