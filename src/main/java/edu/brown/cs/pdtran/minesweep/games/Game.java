package edu.brown.cs.pdtran.minesweep.games;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.JsonElement;
import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.metagame.Session;
import edu.brown.cs.pdtran.minesweep.metagame.Update;
import edu.brown.cs.pdtran.minesweep.move.Move;
import edu.brown.cs.pdtran.minesweep.player.AIPlayer;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;

/**
 * An Abstract class representing a Game.
 * <p>
 * This is implemented by the different types of games (Classic, FSU,
 * etc.).
 * @author Clayton
 */
public abstract class Game extends Session {

  protected ConcurrentMap<String, PlayerTeam> teams;

  /**
   * The constructor that builds a Game by using the Session constructor
   * that it extends.
   * @param name The string representing the name of the game.
   * @param specs The GameSpecs object that represents the settins for the
   *        game.
   */
  protected Game(PreRoom room) {
    super(room.getName(), room.getSpecs());
    teams = makeTeams(room.getTeams());
  }

  protected abstract ConcurrentMap<String, PlayerTeam> makeTeams(ConcurrentMap<String, TeamFormation> preteams);

  @Override
  public ConcurrentMap<String, PlayerTeam> getTeams() {
    return teams;
  }

  public Map<String, List<AIPlayer>> getAis() {
    Map<String, List<AIPlayer>> ais = new HashMap<String, List<AIPlayer>>();
    for (Entry<String, PlayerTeam> entry : getTeams().entrySet()) {
      ais.put(entry.getKey(), entry.getValue().getAis());
    }
    return ais;
  }

  /**
   * Gets the score of a player given the player's GamePlayer object.
   * @param player A GamePlayer object representing a certain player.
   * @return An integer representing that player's score.
   */
  public abstract int getGameScore(GamePlayer player);

  /**
   * Makes a move to the board on behalf of a team.
   * @param teamId The unique string corresponding to a team.
   * @param playerId The unique string corresponding to a player.
   * @param m A Move object that specifies that the team is doing.
   */
  public abstract List<Update> makeMove(String teamId, String playerId, Move m);

  /**
   * Gets the board corresponding to a given team.
   * @param teamId The unique string representing a team.
   * @return The Board object that the team is using.
   */
  public abstract Board getBoard(String teamId);

  public abstract JsonElement getGameData();

}
