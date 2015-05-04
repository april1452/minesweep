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
import edu.brown.cs.pdtran.minesweep.move.MoveFactory;
import edu.brown.cs.pdtran.minesweep.setup.AIGamer;
import edu.brown.cs.pdtran.minesweep.types.AiDifficulty;
import edu.brown.cs.pdtran.minesweep.types.MoveType;
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
    for (WebSocket client : clients.values()) {
      if (conn == client) {
        System.out.println("WE HAVE HIM CAPTAIN");
      }
    }
  }

  @Override
  public void onMessage(WebSocket conn, String message) {

    System.out.println(message);

    try {
      JsonObject messageJson = parser.parse(message).getAsJsonObject();

      String userId = messageJson.get("minesweepId").getAsString();
      String sessionId = messageJson.get("minesweepRoomId").getAsString();

      String requestTypeString = messageJson.get("requestType").getAsString();

      RequestType requestType = RequestType.valueOf(requestTypeString);
      switch (requestType) {
        case INITIALIZE:
          clients.put(userId, conn);
          initialize(sessionId, userId, messageJson.get("minesweepName")
              .getAsString());
          break;
        case SWITCH_TEAM:
          switchTeam(sessionId, messageJson.get("minesweepTeamId")
              .getAsString(), userId, messageJson.get("newTeamId")
              .getAsString());
          break;
        case ADD_AI:
          addAi(sessionId, messageJson.get("minesweepTeamId").getAsString(),
              userId, messageJson.get("difficulty").getAsString());
          break;
        case START_GAME:
          startGame(sessionId, userId);
          break;
        case MAKE_MOVE:
          makeMove(sessionId, messageJson.get("minesweepTeamId").getAsString(),
              MoveFactory.makeMove(messageJson.get("row").getAsInt(),
                  messageJson.get("col").getAsInt(),
                  MoveType.valueOf(messageJson.get("moveType").getAsString())));
          break;
        default:
          System.out.println("No known types reached.");
      }
    } catch (Exception e) {
      System.out.println("An unknown exception occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void initialize(String sessionId, String userId, String name) {
    List<Update> updates = handler.humanJoinIfAbsent(sessionId, userId, name);

    sendUpdates(updates);
  }

  public void switchTeam(String sessionId,
      String teamId,
      String userId,
      String newTeamId) {
    List<Update> updates =
        handler.humanSwitch(sessionId, teamId, userId, newTeamId);

    sendUpdates(updates);
  }

  public void addAi(String sessionId,
      String teamId,
      String userId,
      String difficultyString) {
    String aiId = handler.getUserId();

    AiDifficulty aiDifficulty = AiDifficulty.valueOf(difficultyString);
    AIGamer gamer = new AIGamer(aiDifficulty);

    Update update = handler.aiJoin(sessionId, teamId, userId, aiId, gamer);

    sendUpdate(update);
  }

  public void startGame(String sessionId, String userId) {
    // TODO do something with team id?
    List<Update> updates = handler.startGame(sessionId, userId, this);

    sendUpdates(updates);
  }

  @Override
  public void makeMove(String sessionId, String teamId, Move move)
      throws NoSuchSessionException {
    List<Update> updates = handler.makeMove(sessionId, teamId, move);

    sendUpdates(updates);
  }

  private void sendUpdates(List<Update> updates) {
    for (Update update : updates) {
      sendUpdate(update);
    }
  }

  private void sendUpdate(Update update) {
    String message = update.getMessage();
    System.out.println(message);
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
