package edu.brown.cs.pdtran.minesweep.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.board.BoardFactory;
import edu.brown.cs.pdtran.minesweep.move.Move;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.Room;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;
import edu.brown.cs.pdtran.minesweep.types.MoveResponse;
import edu.brown.cs.pdtran.minesweep.types.SessionType;
import edu.brown.cs.pdtran.minesweep.types.UpdateType;
import edu.brown.cs.pdtran.minesweep.websockets.Update;

/**
 * The class that represents code needed for the classic game mode.
 * <p>
 * In this game mode, each team solves a copy of the same board, and the
 * winning team either is the team that lasts the longest or is the team
 * that finishes its board fist.
 * @author Clayton Sanford
 */
public class ClassicGame extends Game {

  private ConcurrentMap<String, Integer> lives;


  /**
   * A constructor for a ClassicGame.
   * @param room Uses a room with game information to generate the game
   *        object.
   */
  public ClassicGame(Room room) {
    super(room);
    lives = new ConcurrentHashMap<String, Integer>();
    int teamLives = getSpecs().getTeamLives();
    for (String teamId : getTeams().keySet()) {
      lives.put(teamId, teamLives);
    }

  }

  @Override
  public synchronized List<Update> makeMove(String teamId, Move m) {
    List<Update> updates = new ArrayList<>();
    PlayerTeam team = teams.get(teamId);
    MoveResponse response = team.makeMove(m);
    if (response == MoveResponse.MINE) {
      int newLives = lives.get(teamId) - 1;
      lives.put(teamId, newLives);

      if (newLives <= 0) {
        team.setIsLoser();
        updates.add(new Update(UpdateType.DEFEAT,
            new JsonPrimitive(teamId), team.getHumans()));

        int numPlaying = teams.size();
        for (Entry<String, PlayerTeam> entry : getTeams().entrySet()) {
          if (entry.getValue().getIsLoser()) {
            numPlaying--;
          }
        }
        if (numPlaying == 1) {
          for (Entry<String, PlayerTeam> entry : getTeams().entrySet()) {
            PlayerTeam otherTeam = entry.getValue();
            if (!otherTeam.getIsLoser()) {
              otherTeam.setIsWinner();
              updates
              .add(new Update(UpdateType.VICTORY, new JsonPrimitive(
                  entry.getKey()), otherTeam.getHumans()));
            }
          }
        }
      }
    } else if (response == MoveResponse.NOT_MINE) {
      Board board = team.getCurrentBoard();

      if (board.isWinningBoard()) {
        team.setIsWinner();
        updates.add(new Update(UpdateType.VICTORY, new JsonPrimitive(
            teamId), team.getHumans()));
        for (Entry<String, PlayerTeam> entry : getTeams().entrySet()) {
          if (entry.getKey() != teamId) {
            entry.getValue().setIsLoser();
            updates.add(new Update(UpdateType.DEFEAT, new JsonPrimitive(
                entry.getKey()), entry.getValue().getHumans()));
          }
        }
      }
    }

    if (response != MoveResponse.INVALID) {
      List<String> allHumans = new ArrayList<>();
      for (PlayerTeam tempTeam : getTeams().values()) {
        allHumans.addAll(tempTeam.getHumans());
      }

      JsonArray colorsJson = new JsonArray();
      for (int i = 0; i < colors.length; i++) {
        JsonArray col = new JsonArray();
        for (int j = 0; j < colors[0].length; j++) {
          col.add(new JsonPrimitive(colors[i][j]));
        }
        colorsJson.add(col);
      }

      JsonObject boardInfo = team.getBoardInfo().getAsJsonObject();
      boardInfo.add("colors", colorsJson);

      updates.add(new Update(UpdateType.BOARD_UPDATE, boardInfo,
          team
          .getHumans()));
      updates.add(new Update(UpdateType.INFO_UPDATE, getGameData(),
          allHumans));

    }

    return updates;
  }

  /**
   * Gets the number of moves remaining.
   * @param player A GamePlayer object that represents a player in the
   *        game.
   * @return This will calculate how many moves are left.
   */
  @Override
  public int getGameScore(GamePlayer player) {
    // TODO?
    return 0;
  }

  @Override
  public SessionType getSessionType() {
    return SessionType.IN_GAME;
  }

  @Override
  public ConcurrentMap<String, PlayerTeam> getTeams() {
    return teams;
  }

  @Override
  public Board getBoard(String teamId) {
    return teams.get(teamId).getCurrentBoard();
  }

  @Override
  protected ConcurrentMap<String, PlayerTeam> makeTeams(
      ConcurrentMap<String, TeamFormation> preTeams) {
    ConcurrentMap<String, PlayerTeam> teams =
        new ConcurrentHashMap<String, PlayerTeam>();
    List<Board> boardsToPlay = new ArrayList<>();
    int[] dims = specs.getBoardDims();
    boardsToPlay.add(BoardFactory.makeBoard(getSpecs().getBoardType(),
        dims[0], dims[1], specs.getNumMines()));
    for (Map.Entry<String, TeamFormation> entry : preTeams.entrySet()) {
      List<Board> copy = new ArrayList<>();
      for (Board board : boardsToPlay) {
        copy.add(board.clone());
      }
      teams.put(entry.getKey(),
          new PlayerTeam(entry.getValue(), specs.getTeamLives(), copy));
    }
    return teams;
  }

  @Override
  public JsonElement getGameData() {
    JsonObject gameData = new JsonObject();
    for (Entry<String, PlayerTeam> entry : getTeams().entrySet()) {
      PlayerTeam team = entry.getValue();
      JsonObject teamJson = new JsonObject();
      teamJson.addProperty("name", team.getName());
      teamJson.addProperty("lives", lives.get(entry.getKey()));
      gameData.add(entry.getKey(), teamJson);
    }
    return gameData;
  }
}
