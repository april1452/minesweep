package edu.brown.cs.pdtran.minesweep.metagame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RequestHandler {

  private ConcurrentMap<String, Boolean> userIds;
  private ConcurrentMap<String, RoomSession> rooms;
  private ConcurrentMap<String, GameSession> games;

  public RequestHandler() throws IOException {
    userIds = new ConcurrentHashMap<String, Boolean>();
    rooms = new ConcurrentHashMap<String, RoomSession>();
    games = new ConcurrentHashMap<String, GameSession>();
  }

  public List<Map.Entry<String, ? extends Session>> getRooms() {
    List<Map.Entry<String, ? extends Session>> entries =
      new ArrayList<Map.Entry<String, ? extends Session>>();
    entries.addAll(rooms.entrySet());
    entries.addAll(games.entrySet());
    return entries;
  }

  public RoomSession getRoom(String id) {
    return rooms.get(id);
  }

  public GameSession getGame(String id) {
    return games.get(id);
  }

  private static <T> String addAndGetKey(ConcurrentMap<String, T> map, T value) {
    while (true) {
      String id = UUID.randomUUID().toString();
      T previous = map.putIfAbsent(id, value);
      if (previous == null) {
        return id;
      }
    }
  }

  public void convert(String id) {
    RoomSession room = rooms.get(id);
    rooms.remove(id);
    games.put(id, new GameSession(room));
  }

  public String addRoom(RoomSession session) {
    return addAndGetKey(rooms, session);
  }

  public String getUserId() {
    return addAndGetKey(userIds, true);
  }
}
