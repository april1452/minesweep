package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.NotYetConnectedException;
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
import edu.brown.cs.pdtran.minesweep.player.CheckTile;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;

public class GameServer extends WebSocketServer {

  private JsonParser parser;
  private ConcurrentMap<String, WebSocket> clients;
  private RequestHandler handler;

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
      case "joinRoom":
        String name = messageJson.get("name").getAsString();
        clients.put(userId, conn);
        try {
          String teamId = handler.joinIfAbsent(sessionId, userId, name);
          JsonObject joinResponse = new JsonObject();
          joinResponse.addProperty("type", "joinResponse");
          joinResponse.addProperty("data", teamId);
          conn.send(joinResponse.toString());

          JsonObject update = new JsonObject();
          update.addProperty("type", "update");
          update.add("data", handler.getRoomInfo(sessionId).toJson());

          updateUsers(sessionId, update.toString());
        } catch (NoSuchSessionException e) {
          System.out.println("Could not find room.");
        }
        break;
      case "startGame":
        try {
          handler.startGame(sessionId);
          updateBoards(sessionId);
        } catch (NoSuchSessionException e) {
          System.out
            .println("Could not find room (perhaps it was already started?).");
        }
      case "makeMove":
        try {
          String teamId = messageJson.get("minesweepTeamId").getAsString();
          int row = messageJson.get("row").getAsInt();
          int col = messageJson.get("col").getAsInt();

          Move move = new CheckTile(col, row);

          Game game = handler.getGame(sessionId);

          game.makeMove(teamId, move);

          updateBoards(sessionId);
        } catch (NoSuchSessionException e) {
          System.out.println("Could not find game.");
        }
    }
  }

  private void updateUsers(String sessionId, String message)
    throws NoSuchSessionException {
    List<String> users = handler.getUsers(sessionId);
    for (String id : users) {
      clients.get(id).send(message);
    }
  }

  private void updateBoards(String sessionId) throws NotYetConnectedException,
  NoSuchSessionException {
    JsonObject gameData = new JsonObject();
    gameData.addProperty("type", "gameData");
    for (Entry<String, PlayerTeam> teamEntry : handler.getGame(sessionId)
      .getTeams().entrySet()) {
      Board board = teamEntry.getValue().getCurrentBoard();
      gameData.addProperty("data", board.toJson());
      Map<String, GamePlayer> players = teamEntry.getValue().getPlayers();

      assert (players != null);

      for (String playerId : players.keySet()) {
        clients.get(playerId).send(gameData.toString());
      }
      gameData.remove("data");
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
  }
}
