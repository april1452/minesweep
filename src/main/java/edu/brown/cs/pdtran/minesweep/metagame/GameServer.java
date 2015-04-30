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

          List<String> users = handler.getUsers(sessionId);
          for (String id : users) {
            clients.get(id).send(update.toString());
          }
        } catch (NoSuchSessionException e) {
          System.out.println("Could not find room.");
        }
        break;
      case "startGame":
        try {
          handler.startGame(sessionId);
        } catch (NoSuchSessionException e) {
          System.out
          .println("Could not find room (perhaps it was already started?).");
        }
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
  }
}
