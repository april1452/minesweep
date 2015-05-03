package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.games.Game;
import edu.brown.cs.pdtran.minesweep.player.AIPlayer;
import edu.brown.cs.pdtran.minesweep.player.CheckTile;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.AIGamer;
import edu.brown.cs.pdtran.minesweep.setup.HumanGamer;
import edu.brown.cs.pdtran.minesweep.types.AiDifficulty;

/**
 * This class extends WebSocketServer to create a server that multiple
 * games can be run through at once.
 * @author Clayton
 */
public class GameServer extends WebSocketServer implements MoveHandler {

  private JsonParser parser;
  private ConcurrentMap<String, WebSocket> clients;
  private RequestHandler handler;

  /**
   * Constructs a GameServer.
   * @param port An integer representing the port to use.
   * @param handler A RequestHandler for the server to use.
   * @throws UnknownHostException Thrown when there is no known host.
   */
  public GameServer(int port, RequestHandler handler)
      throws UnknownHostException {
    super(new InetSocketAddress(port));
    this.handler = handler;
    parser = new JsonParser();
    clients = new ConcurrentHashMap<String, WebSocket>();
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    System.out.println("A client connected.");
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean arg3) {
    System.out.println("A client has left.");
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    JsonObject messageJson = parser.parse(message).getAsJsonObject();
    String userId = messageJson.get("minesweepId").getAsString();
    String sessionId = messageJson.get("minesweepRoomId").getAsString();
    String messageType = messageJson.get("type").getAsString();
    switch (messageType) {
      case "init":
        try {
          JsonObject update = new JsonObject();
          update.addProperty("type", "init");
          update.add("data", handler.getRoomInfo(sessionId).toJson());

          conn.send(update.toString());
        } catch (NoSuchSessionException e) {
          System.out.println("Could not find room.");
        }
        break;
      case "joinRoom":
        try {
          String name = messageJson.get("name").getAsString();
          clients.put(userId, conn);
          String teamId = messageJson.get("minesweepTeamId").getAsString();
          HumanGamer gamer = new HumanGamer(name);
          Map<String, List<String>> usersToUpdate =
              handler.humanJoinIfAbsent(sessionId, teamId, userId, gamer);

          JsonObject update = new JsonObject();
          update.addProperty("type", "update");
          update.add("data", handler.getRoomInfo(sessionId).toJson());

          updateSession(usersToUpdate, update.toString());
        } catch (NoSuchSessionException e) {
          System.out.println("Could not find room.");
        }
        break;
      case "addAIPlayer":
        try {
          String difficultyString = messageJson.get("difficulty").getAsString();
          AiDifficulty aiDifficulty = AiDifficulty.valueOf(difficultyString);
          String teamId = messageJson.get("minesweepTeamId").getAsString();
          String aiId = handler.getUserId();
          AIGamer gamer = new AIGamer(aiDifficulty);
          Map<String, List<String>> usersToUpdate =
              handler.aiJoinIfAbsent(sessionId, teamId, aiId, gamer);

          JsonObject update = new JsonObject();
          update.addProperty("type", "update");
          update.add("data", handler.getRoomInfo(sessionId).toJson());

          updateSession(usersToUpdate, update.toString());

          break;
        } catch (NoSuchSessionException e) {
          System.out
          .println("Could not find room (perhaps it was already started?).");
        }
      case "startGame":
        try {
          Map<String, List<AIPlayer>> aisToStart = handler.startGame(sessionId);

          for (Entry<String, List<AIPlayer>> entry : aisToStart.entrySet()) {
            for (AIPlayer player : entry.getValue()) {
              new Thread(
                  new AIRunnable(sessionId, entry.getKey(), player, this))
              .start();
            }
          }

          Game game = handler.getGame(sessionId);

          JsonObject gameData = new JsonObject();
          gameData.addProperty("type", "gameData");
          for (Entry<String, PlayerTeam> entry : game.getTeams().entrySet()) {
            PlayerTeam team = entry.getValue();
            gameData.addProperty("data", team.getCurrentBoard().toJson());
            updateTeam(team.getHumans(), gameData.toString());
            gameData.remove("data");
          }
        } catch (NoSuchSessionException e) {
          System.out
          .println("Could not find room (perhaps it was already started?).");
        }
        break;
      case "makeMove":
        try {
          String teamId = messageJson.get("minesweepTeamId").getAsString();
          int row = messageJson.get("row").getAsInt();
          int col = messageJson.get("col").getAsInt();

          Move move = new CheckTile(col, row);

          System.out.println(col + " " + row);

          makeMove(sessionId, teamId, move);

        } catch (NoSuchSessionException e) {
          System.out.println("Could not find game.");
        }
        break;
      default:
        System.out.println("No known types reached.");
    }
  }

  @Override
  public void makeMove(String sessionId, String teamId, Move m)
      throws NoSuchSessionException {
    Game game = handler.getGame(sessionId);
    Board board = game.makeMove(teamId, m);

    JsonObject gameData = new JsonObject();
    if (board == null) {
      for (PlayerTeam team : game.getTeams().values()) {
        for (GamePlayer player : team.getPlayers().values()) {
          player.endPlay();
        }
      }
      gameData.addProperty("type", "victory");
      gameData.addProperty("teamId", teamId);
      for (PlayerTeam team : game.getTeams().values()) {
        updateTeam(team.getHumans(), gameData.toString());
      }
    } else {
      gameData.addProperty("type", "gameData");
      gameData.addProperty("data", board.toJson());
      gameData.addProperty("lives", game.getTeams().get(teamId).getLives());
      gameData.addProperty("score", game.getTeams().get(teamId).getScore());
      updateTeam(game.getTeams().get(teamId).getHumans(), gameData.toString());
    }
  }

  private void updateSession(Map<String, List<String>> usersToUpdate,
      String message) throws NoSuchSessionException {
    for (Entry<String, List<String>> entry : usersToUpdate.entrySet()) {
      updateTeam(entry.getValue(), message);
    }
  }

  private void updateTeam(List<String> usersToUpdate, String message) {
    for (String id : usersToUpdate) {
      WebSocket conn = clients.get(id);
      if (conn.isOpen()) {
        conn.send(message);
      }
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
  }
}
