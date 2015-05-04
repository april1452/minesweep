package edu.brown.cs.pdtran.minesweep.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.metagame.Team;

/**
 * Pregame/setup phase team. Sets up a team of Gamers who will be turned
 * into a team of Player objects in game.
 * @author pdtran
 */
public class TeamFormation extends Team {

  private List<String> humanGamers;
  private List<AIGamer> aiGamers;
  private ConcurrentMap<String, Gamer> gamers;

  /**
   * Create team formation.
   * @param name The name of the specified team.
   */
  public TeamFormation(String name) {
    super(name);
    humanGamers = new ArrayList<>();
    aiGamers = new ArrayList<>();
    gamers = new ConcurrentHashMap<String, Gamer>();
  }

  @Override
  public ConcurrentMap<String, Gamer> getPlayers() {
    return gamers;
  }

  /**
   * Adds an AI gamer to the room.
   * @param gamerId The ID corresponding to the AI.
   * @param ag The AIGamer object to be added.
   */
  public void addAIGamer(String gamerId, AIGamer ag) {
    aiGamers.add(ag);
    addGamer(gamerId, ag);
  }

  /**
   * Adds a human gamer to the room.
   * @param gamerId The ID corresponding to the player.
   * @param hg The HumanPlayer object to be added.
   */
  public void addHumanGamer(String gamerId, HumanGamer hg) {
    humanGamers.add(gamerId);
    addGamer(gamerId, hg);
  }

  /**
   * Gets the number of gamers in a particular team.
   * @return An integer representing the number of gamers.
   */
  public int getSize() {
    return gamers.size();
  }

  @Override
  public List<AIGamer> getAis() {
    return aiGamers;
  }

  @Override
  public List<String> getHumans() {
    return humanGamers;
  }

  /**
   * Adds a Gamer to a TeamFormation object.
   * @param id The unique id corresponding to the Gamer being added.
   * @param g A Gamer object to be added to the TeamFormation player map.
   */
  private void addGamer(String id, Gamer g) {
    gamers.putIfAbsent(id, g);
  }
}
