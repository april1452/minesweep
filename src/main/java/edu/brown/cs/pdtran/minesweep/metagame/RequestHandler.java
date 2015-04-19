package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import edu.brown.cs.pdtran.minesweep.setup.PreRoom;

public class RequestHandler {

  private Map<String, Boolean> userIds;
  private Map<Session, String> sessions;
  private Map<String, RoomSession> rooms;
  private Map<String, GameSession> games;

  public RequestHandler() {
    userIds = new ConcurrentHashMap<String, Boolean>();
    sessions = new ConcurrentHashMap<Session, Boolean>();
    rooms = new ConcurrentHashMap<String, RoomSession>();
    games = new ConcurrentHashMap<String, GameSession>();
  }

  public Map<Session, String> getRooms() {
    return sessions;
  }

  public RoomSession getRoom(String id) {
    return rooms.get(id);
  }

  public GameSession getGame(String id) {
    return games.get(id);
  }

  private static <T> String addAndGetKey(Map<String, T> map, T value) {
    while (true) {
      String id = UUID.randomUUID().toString();
      T previous = map.putIfAbsent(id, value);
      if (previous == null) {
        return id;
      }
    }
  }

  public String addRoom(PreRoom room) {
    return addAndGetKey(rooms, room);
  }

  public String getUserId() {
    return addAndGetKey(userIds, true);
  }
}
