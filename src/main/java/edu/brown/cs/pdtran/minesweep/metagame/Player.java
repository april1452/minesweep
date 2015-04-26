package edu.brown.cs.pdtran.minesweep.metagame;

import org.java_websocket.WebSocket;

import edu.brown.cs.pdtran.minesweep.options.PlayerType;

public abstract class Player {

  protected String name;
  protected WebSocket connection;
  protected PlayerType type;

  public String getName() {
    return name;
  }

  public void sendMessage(String message) {
    connection.send(message);
  }

  public PlayerInfo getPlayerInfo() {
    return new PlayerInfo(name, type);
  }
}
