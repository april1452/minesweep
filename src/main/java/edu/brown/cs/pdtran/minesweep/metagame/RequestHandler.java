package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestHandler {

  private Map<Integer, RoomSession> rooms;
  private Map<Integer, GameSession> games;

  public RequestHandler() {
    sessions = new HashMap<Integer, Session>();
  }

  public Iterator<RoomSession> getRooms() {
    return rooms.values().iterator();
  }

  public Session getSession(int id) {
    return
  }

}
