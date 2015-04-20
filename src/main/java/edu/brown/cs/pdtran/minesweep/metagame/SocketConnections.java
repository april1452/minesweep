package edu.brown.cs.pdtran.minesweep.metagame;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.stream.JsonReader;

public class SocketConnections implements Runnable {

  private ServerSocket server;
  private ConcurrentMap<String, Socket> connections;

  public SocketConnections(int port) throws IOException {
    server = new ServerSocket(port);
    connections = new ConcurrentHashMap<String, Socket>();
  }

  public Socket getSocket(String id) {
    Socket conn = connections.get(id);
    while (conn == null) {
      conn = connections.get(id);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    return conn;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Socket connection = server.accept();
        JsonReader reader =
          new JsonReader(new InputStreamReader(connection.getInputStream()));
        String id = reader.nextString();
        connections.put(id, connection);
      } catch (IOException e) {
        System.out.println("ERROR:" + e.getMessage());
      }
    }
  }

}
