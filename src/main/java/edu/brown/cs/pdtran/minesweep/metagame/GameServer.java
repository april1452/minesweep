package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    String messageType = messageJson.get("type").getAsString();
    switch (messageType) {
      case "joinRoom":
        String userId = messageJson.get("minesweepId").getAsString();
        String sessionId = messageJson.get("minesweepRoomId").getAsString();
        String name = messageJson.get("name").getAsString();
        clients.put(userId, conn);
        try {
          String teamId = handler.joinIfAbsent(sessionId, userId, name);
          conn.send(teamId);
          List<String> users = handler.getUsers(sessionId);
          for (String id : users) {
            clients.get(id).send(roomInfoToString(handler.getRoomInfo(id)));
          }
        } catch (NoSuchSessionException e) {
          System.out.println("Could not find room.");
        }
        break;
      case "test":
        System.out.println("success");
        // case "startGame":
    }
  }

  private static String roomInfoToString(RoomInfo info) {
    JsonObject roomJson = new JsonObject();
    roomJson.addProperty("roomName", info.getRoomName());
    String gameModeString;
    switch (info.getGameSpecs().getMode()) {
      case CLASSIC:
        gameModeString = "Classic";
        break;
      default:
        gameModeString = "";
    }
    roomJson.addProperty("gameMode", gameModeString);

    JsonArray teamsJson = new JsonArray();
    for (TeamInfo team : info.getTeams()) {
      JsonObject teamJson = new JsonObject();
      JsonArray playersJson = new JsonArray();
      for (PlayerInfo player : team.getPlayers()) {
        JsonObject playerJson = new JsonObject();
        playerJson.addProperty("name", player.getName());
        playersJson.add(playerJson);
      }
      teamJson.addProperty("name", team.getName());
      teamJson.add("players", playersJson);
      teamsJson.add(teamJson);
    }

    roomJson.add("teams", teamsJson);

    return roomJson.toString();
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
  }
}
