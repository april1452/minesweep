package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GameServer extends WebSocketServer {

  private JsonParser parser;
  private ConcurrentMap<String, WebSocket> clients;

  public GameServer(int port) throws UnknownHostException {
    super(new InetSocketAddress(port));
    parser = new JsonParser();
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
      case "makeOrJoin":
      case "leave":
      case "getData":
      case "startGame":
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
  }
}
