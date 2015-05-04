package edu.brown.cs.pdtran.minesweep.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.board.BoardFactory;
import edu.brown.cs.pdtran.minesweep.metagame.Update;
import edu.brown.cs.pdtran.minesweep.move.Move;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.Room;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;
import edu.brown.cs.pdtran.minesweep.types.MoveResponse;
import edu.brown.cs.pdtran.minesweep.types.SessionType;
import edu.brown.cs.pdtran.minesweep.types.UpdateType;

/**
 * The class that represents code needed for the layers game mode.
 * <p>
 * In this game mode, each team solves a stack of copies of the same
 * boards, and the winning team either is the team that lasts the longest
 * or is the team that finishes all boards fist.
 * @author Clayton Sanford
 */
public class LayersGame extends Game {

  private ConcurrentMap<String, Integer> lives;
  private static final int LAYERS_COUNT = 5;

  /**
   * A constructor for a Layers Game.
   * @param room Uses a room with game information to generate the game
   *        object.
   */
  public LayersGame(Room room) {
    super(room);
    System.out.println("MADE LAYERS GAME");
    lives = new ConcurrentHashMap<String, Integer>();
    int teamLives = getSpecs().getTeamLives();
    for (String teamId : getTeams().keySet()) {
      lives.put(teamId, teamLives);
    }
  }

  @Override
  public List<Update> makeMove(String teamId, Move m) {
    List<Update> updates = new ArrayList<>();
    PlayerTeam team = teams.get(teamId);
    MoveResponse response = team.makeMove(m);
    if (response == MoveResponse.MINE) {
      int newLives = lives.get(teamId) - 1;
      lives.put(teamId, newLives);

      if (newLives <= 0) {
        team.setIsLoser();
        updates.add(new Update(UpdateType.DEFEAT,
            new JsonPrimitive(teamId),
            team.getHumans()));

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
              updates.add(new Update(UpdateType.VICTORY,
                  new JsonPrimitive(
                      entry.getKey()), otherTeam.getHumans()));
            }
          }
        }
      }
    } else if (response == MoveResponse.NOT_MINE) {
      Board board = team.getCurrentBoard();

      if (board.isWinningBoard() && !team.nextBoard()) {
        if (!team.nextBoard()) {
          team.setIsWinner();
          updates.add(new Update(UpdateType.VICTORY, new JsonPrimitive(
              teamId),
              team.getHumans()));
          for (Entry<String, PlayerTeam> entry : getTeams().entrySet()) {
            if (entry.getKey() != teamId) {
              entry.getValue().setIsLoser();
              updates.add(new Update(UpdateType.DEFEAT, new JsonPrimitive(
                  entry
                  .getKey()), entry.getValue().getHumans()));
            }
          }
        }
      }
    }

    if (response != MoveResponse.INVALID) {
      List<String> allHumans = new ArrayList<>();
      for (PlayerTeam tempTeam : getTeams().values()) {
        allHumans.addAll(tempTeam.getHumans());
      }
      updates.add(new Update(UpdateType.BOARD_UPDATE, team.getBoardInfo(),
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
    for (int i = 0; i < LAYERS_COUNT; i++) {
      boardsToPlay.add(BoardFactory.makeBoard(getSpecs().getBoardType(),
          dims[0], dims[1], specs.getNumMines()));
    }

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
