package edu.brown.cs.pdtran.minesweep.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
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
import edu.brown.cs.pdtran.minesweep.websockets.UpdateSender;

public class TimerGame extends Game {
  UpdateSender updateSender;
  private Timer timer;
  private ConcurrentMap<String, PlayerTimer> timers;

  private static final long INIT_TIME_MILLIS = 60000;
  private static final long MINE_LOSS_MILLIS = 20000;
  private static final long EXPLORE_GAIN_MILLIS = 5000;


  /**
   * A constructor for a ClassicGame.
   * @param room Uses a room with game information to generate the game
   *        object.
   */
  public TimerGame(Room room, UpdateSender updateSender) {
    super(room);
    this.updateSender = updateSender;
    timers = new ConcurrentHashMap<String, PlayerTimer>();
    timer = new Timer();

    for (String teamId : getTeams().keySet()) {
      PlayerTimer playerTimer =
          new PlayerTimer(this, teamId, System.currentTimeMillis(),
              INIT_TIME_MILLIS);
      timer.schedule(playerTimer, INIT_TIME_MILLIS);
      timers.put(teamId, playerTimer);
    }
  }

  @Override
  public synchronized List<Update> makeMove(String teamId, Move m) {
    List<Update> updates = new ArrayList<>();
    PlayerTeam team = teams.get(teamId);
    MoveResponse response = team.makeMove(m);
    if (response == MoveResponse.MINE) {
      PlayerTimer oldTimer = timers.get(teamId);
      oldTimer.cancel();
      long elapsedTime =
          System.currentTimeMillis() - oldTimer.getStartTime();
      long remainingTime = oldTimer.getDelay() - elapsedTime;
      long newDelay = remainingTime - MINE_LOSS_MILLIS;
      if (newDelay <= 0) {
        updates.addAll(getLossUpdate(teamId));
      } else {
        PlayerTimer newTimer =
            new PlayerTimer(this, teamId, System.currentTimeMillis(),
                newDelay);
        timer.schedule(newTimer, newDelay);
        timers.put(teamId, newTimer);
      }
    } else if (response == MoveResponse.NOT_MINE) {
      PlayerTimer oldTimer = timers.get(teamId);
      oldTimer.cancel();
      long elapsedTime =
          System.currentTimeMillis() - oldTimer.getStartTime();
      long remainingTime = oldTimer.getDelay() - elapsedTime;
      long newDelay = remainingTime + EXPLORE_GAIN_MILLIS;
      PlayerTimer newTimer =
          new PlayerTimer(this, teamId, System.currentTimeMillis(),
              newDelay);
      timer.schedule(newTimer, newDelay);
      timers.put(teamId, newTimer);

      Board board = team.getCurrentBoard();

      if (board.isWinningBoard()) {
        timer.purge();
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

  public void timerLoss(String teamId) {
    updateSender.sendUpdates(getLossUpdate(teamId));
  }

  private List<Update> getLossUpdate(String teamId) {
    List<Update> updates = new ArrayList<Update>();
    PlayerTeam team = getTeams().get(teamId);
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
      timer.purge();
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
      PlayerTimer timer = timers.get(entry.getKey());
      long elapsedTime =
          System.currentTimeMillis() - timer.getStartTime();
      long remainingTime = timer.getDelay() - elapsedTime;
      teamJson.addProperty("time", remainingTime);
      gameData.add(entry.getKey(), teamJson);
    }
    return gameData;
  }
}
