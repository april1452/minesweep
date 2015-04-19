package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.brown.cs.pdtran.minesweep.setup.PreRoom;

public class RoomSession implements Session {

  private String id;
  private PreRoom room;
  private Map<String, Socket> connections;

  public RoomSession(String id, PreRoom room) {
    this.id = id;
    this.room = room;
    connections = new ConcurrentHashMap<String, Socket>();
  }

  @Override
  public RoomInfo getRoomInfo() {
    return new RoomInfo(id, room.get, id, null);
  }

  @Override
  public String getId() {
    return id;
  }

}
