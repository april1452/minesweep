package edu.brown.cs.pdtran.minesweep.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
import edu.brown.cs.pdtran.minesweep.session.Session;
import edu.brown.cs.pdtran.minesweep.session.Team;
import edu.brown.cs.pdtran.minesweep.types.SessionType;
import edu.brown.cs.pdtran.minesweep.websockets.SessionFullException;

/**
 * This class contains data for the Room information before the room is
 * ready to be played.
 * @author Clayton Sanford
 */
public class Room extends Session {
  private String hostId;
  private ConcurrentMap<String, TeamFormation> teams;

  /**
   * Create a room that has been processed from the GUI. This room contains
   * specs that will be used to create an actual game.
   * @param hostId The id string corresponding to the host.
   * @param name The string corresponding to the name of the game.
   * @param specs The specifications for the room from the Setup page.
   */
  public Room(String hostId, String name, GameSpecs specs) {
    super(name, specs);
    this.hostId = hostId;
    teams = new ConcurrentHashMap<String, TeamFormation>();
    for (int i = 0; i < specs.getNumTeams(); i++) {
      RequestHandler.addAndGetKey(teams, new TeamFormation("Team "
          + (i + 1)));
    }
  }

  @Override
  public ConcurrentMap<String, TeamFormation> getTeams() {
    return teams;
  }

  /**
   * Checks whether a given player is the host.
   * @param id The unique id of the player.
   * @return True if the player is the host.
   */
  public boolean isHost(String id) {
    return hostId.equals(id);
  }

  /**
   * Adds a human player to a room.
   * @param teamId The ID for the team to add the human.
   * @param gamerId The ID for the game to add the human.
   * @param hg The HumanGamer to be added.
   * @throws SessionFullException Thrown if the game session has too many
   *         players.
   */
  public void addHuman(String teamId, String gamerId, HumanGamer hg)
      throws SessionFullException {
    TeamFormation teamToAdd = teams.get(teamId);
    if (teamToAdd.getSize() >= specs.getNumTeamPlayers()) {
      throw new SessionFullException();
    }
    teams.get(teamId).addHumanGamer(gamerId, hg);
  }

  /**
   * Switches a human player to another team.
   * @param teamId The ID for the team the player is switching from.
   * @param gamerId The ID for the player being switched.
   * @param newTeamId The ID for the team to player is being switched to.
   * @throws SessionFullException Thrown when the session the player is in
   *         is full.
   */
  public void switchTeam(String teamId, String gamerId, String newTeamId)
      throws SessionFullException {
    TeamFormation oldTeam = teams.get(teamId);
    TeamFormation newTeam = teams.get(newTeamId);

    if (newTeam.getSize() >= specs.getNumTeamPlayers()) {
      throw new SessionFullException();
    }

    oldTeam.getHumans().remove(gamerId);
    Gamer gamer = oldTeam.getPlayers().remove(gamerId);

    newTeam.getHumans().add(gamerId);
    newTeam.getPlayers().put(gamerId, gamer);

  }

  /**
   * Adds an AI to a requested team in the room.
   * @param teamId The ID for the team to add it to.
   * @param gamerId The ID for the AI gamer to add.
   * @param ag The AIGamer object ot add.
   * @throws SessionFullException Thrown if the team the AI tries to join
   *         is full.
   */
  public void addAi(String teamId, String gamerId, AIGamer ag)
      throws SessionFullException {
    TeamFormation teamToAdd = teams.get(teamId);
    if (teamToAdd.getSize() >= specs.getNumTeamPlayers()) {
      throw new SessionFullException();
    }
    teams.get(teamId).addAIGamer(gamerId, ag);
  }


  @Override
  public SessionType getSessionType() {
    return SessionType.SETUP;
  }

  /**
   * Removes all AI players from a team.
   * @param teamId The unique id for a team.
   */
  public void removeAis(String teamId) {
    // somewhat hacky method to remove all Ais
    TeamFormation team = teams.get(teamId);
    List<AIGamer> aiGamers = team.getAis();
    List<String> toRemove = new ArrayList<>();
    for (Entry<String, Gamer> entry : team.getPlayers().entrySet()) {
      for (AIGamer ai : aiGamers) {
        if (entry.getValue() == ai) {
          toRemove.add(entry.getKey());
        }
      }
    }
    aiGamers.clear();
    for (String removeId : toRemove) {
      team.getPlayers().remove(removeId);
    }
  }

  /**
   * Removes a human player from the game.
   * @param teamId The unique id corresponding to that player's team.
   * @param userId The unique id corresponding to the removed user.
   */
  public void removeHuman(String teamId, String userId) {
    Team team = teams.get(teamId);
    team.getHumans().remove(userId);
    team.getPlayers().remove(userId);
  }

}
