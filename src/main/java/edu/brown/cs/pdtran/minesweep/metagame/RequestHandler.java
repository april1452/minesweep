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
  private ConcurrentMap<String, Session> sessions;

  public RequestHandler() throws IOException {
    userIds = new ConcurrentHashMap<String, Boolean>();
    sessions = new ConcurrentHashMap<String, Session>();
  }

  public List<Map.Entry<String, ? extends Session>> getRooms() {
    List<Map.Entry<String, ? extends Session>> entries =
      new ArrayList<Map.Entry<String, ? extends Session>>();
    entries.addAll(sessions.entrySet());
    return entries;
  }

  public boolean joinIf(String sessionId, String userId) {
    if(sessions.putIfAbsent(sessionId, new RoomSession())
  }

  public RoomSession getRoom(String id) {
    return rooms.get(id);
  }

  public GameSession getGame(String id) {
    return games.get(id);
  }

  publ

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
