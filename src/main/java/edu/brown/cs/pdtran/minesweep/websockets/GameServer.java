package edu.brown.cs.pdtran.minesweep.websockets;

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
import edu.brown.cs.pdtran.minesweep.metagame.RequestHandler;
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
public class GameServer extends WebSocketServer implements MoveHandler,
    UpdateSender {

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

    System.out.println(message);

    try {
      JsonObject messageJson = parser.parse(message).getAsJsonObject();

      String userId = messageJson.get("minesweepId").getAsString();
      String sessionId = messageJson.get("minesweepRoomId").getAsString();

      String requestTypeString =
          messageJson.get("requestType").getAsString();

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
        case LEAVE_ROOM:
          leaveRoom(sessionId, messageJson.get("minesweepTeamId")
              .getAsString(), userId);
          break;
        case ADD_AI:
          addAi(sessionId, messageJson.get("minesweepTeamId")
              .getAsString(),
              userId, messageJson.get("difficulty").getAsString());
          break;
        case REMOVE_AIS:
          removeAis(sessionId, messageJson.get("minesweepTeamId")
              .getAsString(), userId);
          break;
        case DISBAND_ROOM:
          disbandRoom(sessionId, userId);
          break;
        case START_GAME:
          startGame(sessionId, userId);
          break;
        case MAKE_MOVE:
          makeMove(sessionId, messageJson.get("minesweepTeamId")
              .getAsString(),
              MoveFactory.makeMove(messageJson.get("col").getAsInt(),
                  messageJson.get("row").getAsInt(),
                  MoveType.valueOf(messageJson.get("moveType")
                      .getAsString())));
          break;
        default:
          System.out.println("No known types reached.");
      }
    } catch (Exception e) {
      System.out.println("An unknown exception occurred: "
          + e.getMessage());
      e.printStackTrace();
    }
  }

  private void disbandRoom(String sessionId, String userId) {
    List<Update> updates = handler.disbandRoom(sessionId, userId);

    sendUpdates(updates);
  }

  /**
   * Creates a room with the first player.
   * @param sessionId The unique ID of the session.
   * @param userId The unique ID of the first user.
   * @param name The name of the first user.
   */
  public void initialize(String sessionId, String userId, String name) {
    List<Update> updates =
        handler.humanJoinIfAbsent(sessionId, userId, name);

    sendUpdates(updates);
  }

  /**
   * Moves a player to another team.
   * @param sessionId The unique ID of the session.
   * @param teamId The unique ID of the team being transferred from.
   * @param userId The unique ID for the user.
   * @param newTeamId The unique ID for the new team.
   */
  public void switchTeam(String sessionId,
      String teamId,
      String userId,
      String newTeamId) {
    List<Update> updates =
        handler.humanSwitch(sessionId, teamId, userId, newTeamId);

    sendUpdates(updates);
  }

  private void leaveRoom(String sessionId, String teamId, String userId) {
    List<Update> updates =
        handler.removeHuman(sessionId, teamId, userId);

    sendUpdates(updates);
  }

  /**
   * Adds an AI to a team.
   * @param sessionId The unique ID for the session.
   * @param teamId The unique ID for a team.
   * @param userId The unique ID for the user adding the AI.
   * @param difficultyString A string representing the AI's difficulty:
   *        "EASY", "MEDIUM", or "HARD".
   */
  public void addAi(String sessionId,
      String teamId,
      String userId,
      String difficultyString) {
    String aiId = handler.getUserId();

    AiDifficulty aiDifficulty = AiDifficulty.valueOf(difficultyString);
    AIGamer gamer = new AIGamer(aiDifficulty);

    List<Update> update =
        handler.aiJoin(sessionId, teamId, userId, aiId, gamer);

    sendUpdates(update);
  }

  private void removeAis(String sessionId,
      String teamId,
      String requesterId) {
    List<Update> updates =
        handler.removeAis(sessionId, teamId, requesterId);

    sendUpdates(updates);
  }

  /**
   * Initiates the game when the "Start Game" button is pressed.
   * @param sessionId The unique id for each session.
   * @param userId The unique id for each user.
   */
  public void startGame(String sessionId, String userId) {
    // TODO do something with team id?
    List<Update> updates =
        handler.startGame(this, sessionId, userId, this);

    sendUpdates(updates);
  }

  @Override
  public void makeMove(String sessionId, String teamId, Move move)
      throws NoSuchSessionException {
    List<Update> updates = handler.makeMove(sessionId, teamId, move);

    sendUpdates(updates);
  }

  @Override
  public void sendUpdates(List<Update> updates) {
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
