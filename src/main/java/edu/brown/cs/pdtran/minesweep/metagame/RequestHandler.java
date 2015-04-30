package edu.brown.cs.pdtran.minesweep.metagame;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.games.Game;
import edu.brown.cs.pdtran.minesweep.games.GameFactory;
import edu.brown.cs.pdtran.minesweep.setup.Gamer;
import edu.brown.cs.pdtran.minesweep.setup.HumanGamer;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;

public class RequestHandler {

  private ConcurrentMap<String, Boolean> userIds;
  private ConcurrentMap<String, Session> sessions;
  private ConcurrentMap<String, PreRoom> rooms;
  private ConcurrentMap<String, Game> games;

  public RequestHandler() throws IOException {
    userIds = new ConcurrentHashMap<String, Boolean>();
    sessions = new ConcurrentHashMap<String, Session>();
    rooms = new ConcurrentHashMap<String, PreRoom>();
    games = new ConcurrentHashMap<String, Game>();
  }

  public List<Map.Entry<String, RoomInfo>> getRooms() {
    List<Map.Entry<String, RoomInfo>> roomsInfo =
      new ArrayList<Map.Entry<String, RoomInfo>>();
    for (Map.Entry<String, Session> entry : sessions.entrySet()) {
      roomsInfo.add(new AbstractMap.SimpleImmutableEntry<String, RoomInfo>(
        entry.getKey(), entry.getValue().getRoomInfo()));
    }
    return roomsInfo;
  }

  private PreRoom getRoom(String id) throws NoSuchSessionException {
    PreRoom room = rooms.get(id);
    if (room == null) {
      throw new NoSuchSessionException();
    }
    return room;
  }

  public String joinIfAbsent(String sessionId, String userId, String name)
    throws NoSuchSessionException {
    PreRoom room = getRoom(sessionId);
    Gamer g = new HumanGamer(name);
    return room.addGamer(userId, g);
  }

  public static <T> String addAndGetKey(ConcurrentMap<String, T> map, T value) {
    while (true) {
      String id = UUID.randomUUID().toString();
      T previous = map.putIfAbsent(id, value);
      if (previous == null) {
        return id;
      }
    }
  }

  public RoomInfo getRoomInfo(String id) throws NoSuchSessionException {
    PreRoom room = getRoom(id);
    return room.getRoomInfo();
  }

  public void startGame(String id) throws NoSuchSessionException {
    PreRoom room = rooms.remove(id);
    if (room == null || sessions.remove(id) == null) {
      throw new NoSuchSessionException();
    }
    Game game = GameFactory.generateGame(room);
    games.put(id, game);
    sessions.put(id, game);
  }

  public String addRoom(PreRoom room) {
    String id = addAndGetKey(sessions, room);
    rooms.put(id, room);
    return id;
  }

  public String getUserId() {
    return addAndGetKey(userIds, true);
  }

  public List<String> getUsers(String sessionId) throws NoSuchSessionException {
    PreRoom room = getRoom(sessionId);
    return room.getUsers();
  }
}
