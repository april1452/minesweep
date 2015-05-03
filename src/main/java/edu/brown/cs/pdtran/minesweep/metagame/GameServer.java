package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.brown.cs.pdtran.minesweep.move.Move;
import edu.brown.cs.pdtran.minesweep.setup.AIGamer;
import edu.brown.cs.pdtran.minesweep.types.AiDifficulty;
import edu.brown.cs.pdtran.minesweep.types.RequestType;

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
    try {
      JsonObject messageJson = parser.parse(message).getAsJsonObject();

      String userId = messageJson.get("minesweepId").getAsString();
      String sessionId = messageJson.get("minesweepRoomId").getAsString();

      String requestTypeString = messageJson.get("requestType").getAsString();
      RequestType requestType = RequestType.valueOf(requestTypeString);
      switch (requestType) {
        case INITIALIZE:
          try {
            clients.put(userId, conn);

            String name = messageJson.get("name").getAsString();

            Update update = handler.humanJoinIfAbsent(sessionId, userId, name);

            sendUpdate(update);
          } catch (NoSuchSessionException e) {
            System.out.println("Could not find room.");
          }
          break;
        case SWITCH_TEAM:
          try {
            String teamId = messageJson.get("minesweepTeamId").getAsString();
            String newTeamId = messageJson.get("newTeamId").getAsString();

            Update update =
                handler.humanSwitch(sessionId, teamId, userId, newTeamId);

            sendUpdate(update);
          } catch (NoSuchSessionException e) {
            System.out.println("Could not find room.");
          }
          break;
        case ADD_AI:
          try {
            String difficultyString =
                messageJson.get("difficulty").getAsString();
            AiDifficulty aiDifficulty = AiDifficulty.valueOf(difficultyString);
            String teamId = messageJson.get("minesweepTeamId").getAsString();
            String aiId = handler.getUserId();
            AIGamer gamer = new AIGamer("John Jabbotti", aiDifficulty);

            Update update = handler.aiJoin(sessionId, teamId, aiId, gamer);

            sendUpdate(update);
          } catch (NoSuchSessionException e) {
            System.out
                .println("Could not find room (perhaps it was already started?).");
          }
          break;
        case START_GAME:
          // try {
          // // Map<String, List<AIPlayer>> aisToStart =
          // // handler.startGame(sessionId);
          // //
          // // for (Entry<String, List<AIPlayer>> entry :
          // // aisToStart.entrySet()) {
          // // for (AIPlayer player : entry.getValue()) {
          // // new Thread(new AIRunnable(sessionId, entry.getKey(),
          // player,
          // // this)).start();
          // // }
          // // }
          // //
          // // Game game = handler.getGame(sessionId);
          // //
          // // JsonObject gameData = new JsonObject();
          // // gameData.addProperty("type", "gameData");
          // // for (Entry<String, PlayerTeam> entry :
          // // game.getTeams().entrySet()) {
          // // PlayerTeam team = entry.getValue();
          // // gameData.addProperty("data",
          // // team.getCurrentBoard().toJson());
          // // updateTeam(team.getHumans(), gameData.toString());
          // // gameData.remove("data");
          // // }
          // } catch (NoSuchSessionException e) {
          // System.out
          // .println("Could not find room (perhaps it was already started?).");
          // }
          break;
        case MAKE_MOVE:
          // try {
          // // String teamId =
          // // messageJson.get("minesweepTeamId").getAsString();
          // // int row = messageJson.get("row").getAsInt();
          // // int col = messageJson.get("col").getAsInt();
          // //
          // // Move move = new CheckTile(col, row);
          // //
          // // System.out.println(col + " " + row);
          // //
          // // makeMove(sessionId, teamId, move);
          // //
          // } catch (NoSuchSessionException e) {
          // System.out.println("Could not find game.");
          // }
          break;
        default:
          System.out.println("No known types reached.");
      }
    } catch (Exception e) {
      System.out.println("An unknown exception occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void makeMove(String sessionId, String teamId, String playerId, Move m)
      throws NoSuchSessionException {
    // Game game = handler.getGame(sessionId);
    // List<Update> updates = game.makeMove(teamId, playerId, m);
    //
    // JsonObject gameData = new JsonObject();
    // if (board == null) {
    // for (PlayerTeam team : game.getTeams().values()) {
    // for (GamePlayer player : team.getPlayers().values()) {
    // player.endPlay();
    // }
    // }
    // gameData.addProperty("type", "victory");
    // gameData.addProperty("teamId", teamId);
    // for (PlayerTeam team : game.getTeams().values()) {
    // updateTeam(team.getHumans(), gameData.toString());
    // }
    // } else {
    // gameData.addProperty("type", "gameData");
    // gameData.addProperty("data", board.toJson());
    // updateTeam(game.getTeams().get(teamId).getHumans(),
    // gameData.toString());
    // }
  }

  private void sendUpdates(List<Update> updates) throws NoSuchSessionException {
    for (Update update : updates) {
      sendUpdate(update);
    }
  }

  private void sendUpdate(Update update) {
    String message = update.getMessage();
    for (String id : update.getUsersToUpdate()) {
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
