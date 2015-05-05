package edu.brown.cs.pdtran.minesweep.games;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.metagame.Session;
import edu.brown.cs.pdtran.minesweep.metagame.Update;
import edu.brown.cs.pdtran.minesweep.move.Move;
import edu.brown.cs.pdtran.minesweep.player.AIPlayer;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.Room;
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
  protected String[][] colors;

  /**
   * The constructor that builds a Game by using the Session constructor
   * that it extends.
   * @param room The Room to be made into a Game.
   */
  protected Game(Room room) {
    super(room.getName(), room.getSpecs());
    int[] dims = room.getSpecs().getBoardDims();
    colors = new String[dims[0]][dims[1]];
    for (int i = 0; i < dims[0]; i++) {
      for (int j = 0; j < dims[1]; j++) {
        colors[i][j] = "WHITE";
      }
    }
    teams = makeTeams(room.getTeams());
  }

  protected abstract ConcurrentMap<String, PlayerTeam> makeTeams(
      ConcurrentMap<String, TeamFormation> preteams);

  @Override
  public ConcurrentMap<String, PlayerTeam> getTeams() {
    return teams;
  }

  public JsonElement getBoardInfo(String teamId) {
    JsonArray colorsJson = new JsonArray();
    for (int i = 0; i < colors.length; i++) {
      JsonArray col = new JsonArray();
      for (int j = 0; j < colors[0].length; j++) {
        col.add(new JsonPrimitive(colors[i][j]));
      }
      colorsJson.add(col);
    }

    JsonObject boardInfo =
        teams.get(teamId).getBoardInfo().getAsJsonObject();
    boardInfo.add("colors", colorsJson);

    return boardInfo;
  }

  /**
   * Gets the AI players in the game.
   * @return A Map of team ids to lists of AI Players within each team.
   */
  public Map<String, List<AIPlayer>> getAis() {
    Map<String, List<AIPlayer>> ais =
        new HashMap<String, List<AIPlayer>>();
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
   * @param m A Move object that specifies that the team is doing.
   * @return A List of Updates for the board.
   */
  public abstract List<Update> makeMove(String teamId, Move m);

  /**
   * Gets the board corresponding to a given team.
   * @param teamId The unique string representing a team.
   * @return The Board object that the team is using.
   */
  public abstract Board getBoard(String teamId);

  /**
   * Gets the game data in a JSON element to be transferred to the front
   * end.
   * @return A JSON element with information needed in the front end.
   */
  public abstract JsonElement getGameData();

}
