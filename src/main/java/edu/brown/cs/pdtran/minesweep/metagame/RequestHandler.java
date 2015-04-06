package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestHandler {

  private Map<Integer, RoomSession> rooms;
  private Map<Integer, GameSession> games;

  public RequestHandler() {
    rooms = new HashMap<Integer, RoomSession>();
    games = new HashMap<Integer, GameSession>();
  }

  public Iterator<RoomSession> getRooms() {
    return rooms.values().iterator();
  }

  public RoomSession getRoom(int id) {
    return rooms.get(id);
  }

  public GameSession getGame(int id) {
    return games.get(id);
  }
  
  public int addGame() {
    
  }
  
  

}
