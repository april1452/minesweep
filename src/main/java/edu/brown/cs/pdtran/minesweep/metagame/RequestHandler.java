package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RequestHandler {

  private Set<Session> sessions;
  private Map<UUID, RoomSession> rooms;
  private Map<UUID, GameSession> games;

  public RequestHandler() {
    rooms = new ConcurrentHashMap<UUID, RoomSession>();
    games = new ConcurrentHashMap<UUID, GameSession>();
  }

  public Map<Integer, RoomSession> getRooms() {
    return sessions;
  }

  public RoomSession getRoom(String id) {
    return rooms.get(id);
  }

  public GameSession getGame(String id) {
    return games.get(id);
  }

  public int addGame() {

  }

}
