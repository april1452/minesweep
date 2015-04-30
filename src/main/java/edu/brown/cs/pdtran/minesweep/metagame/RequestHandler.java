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
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;

/**
 * Keeps records of games, sessions, users, and rooms by storing maps that
 * relate id stirngs to the corresponding information.
 * @author Clayton Sanford
 */
public class RequestHandler {

  private ConcurrentMap<String, Boolean> userIds;
  private ConcurrentMap<String, Session> sessions;
  private ConcurrentMap<String, PreRoom> rooms;
  private ConcurrentMap<String, Game> games;

  /**
   * Constructs a RequestHandler.
   * @throws IOException Thrown if the input is invalid.
   */
  public RequestHandler() throws IOException {
    userIds = new ConcurrentHashMap<String, Boolean>();
    sessions = new ConcurrentHashMap<String, Session>();
    rooms = new ConcurrentHashMap<String, PreRoom>();
    games = new ConcurrentHashMap<String, Game>();
  }

  /**
   * Gets the rooms currently being used by the server.
   * @return A list of map entries that relate unique room id strings to
   *         the corresponding room information.
   */
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

  /**
   * Returns a game object corresponding to the specified id.
   * @param id A string unique to each game.
   * @return The Game object that contains the id.
   * @throws NoSuchSessionException Thrown when the game does not exist.
   */
  public Game getGame(String id) throws NoSuchSessionException {
    Game game = games.get(id);
    if (game == null) {
      throw new NoSuchSessionException();
    }
    return game;
  }

  /**
   * Adds a gamer to a room in the event that the gamer is not already in
   * the room.
   * @param sessionId The unique string corresponding to the session.
   * @param userId The unique string corresponding to the user.
   * @param name The intended name for the human.
   * @return Returns the string corresponding to the room with one more
   *         gamer added.
   * @throws NoSuchSessionException Thrown when the requested session does
   *         not exist.
   */
  public String joinIfAbsent(String sessionId, String gamerId, Gamer g)
      throws NoSuchSessionException {
    PreRoom room = getRoom(sessionId);
    return room.addGamer(gamerId, g);
  }

  /**
   * For an object, generates an id corresponding to it and adds the object
   * to the map that relates strings to corresponding objects.
   * @param map A map between unique id strings and the objects the same
   *        type as the inputted value.
   * @param value The object to be added to the map.
   * @return The map with the new value added if it was not already present
   *         with a unique id string.
   */
  public static <T> String addAndGetKey(ConcurrentMap<String, T> map, T value) {
    while (true) {
      String id = UUID.randomUUID().toString();
      T previous = map.putIfAbsent(id, value);
      if (previous == null) {
        return id;
      }
    }
  }

  /**
   * Gets the room info corresponding to the room specified by an id.
   * @param id The unique id that corresponds to a room.
   * @return The RoomInfo object corresponding to the same room.
   * @throws NoSuchSessionException Thrown when the the room is in does not
   *         exist.
   */
  public RoomInfo getRoomInfo(String id) throws NoSuchSessionException {
    PreRoom room = getRoom(id);
    return room.getRoomInfo();
  }

  /**
   * Creates a game from a room id. Removes the id corresponding to that
   * room from the Rooms map and creates a new Game with the specified
   * information in the Games map.
   * @param id The unique string corresponding to a room.
   * @throws NoSuchSessionException Thrown when there is no session
   *         corresponding to the given room.
   */
  public void startGame(String id) throws NoSuchSessionException {
    PreRoom room = rooms.remove(id);
    if (room == null || sessions.remove(id) == null) {
      throw new NoSuchSessionException();
    }
    Game game = GameFactory.generateGame(room);
    games.put(id, game);
    sessions.put(id, game);
  }

  /**
   * Generates an id for a room and adds the room to the map of ids to
   * rooms.
   * @param room A PreRoom object with information needed to define the
   *        kind of room we want.
   * @return The id genereated for the inputted room.
   */
  public String addRoom(PreRoom room) {
    String id = addAndGetKey(sessions, room);
    rooms.put(id, room);
    return id;
  }

  /**
   * Adds a user to the RequestHandler and generates an id for it.
   * @return The unique string generated for the new user.
   */
  public String getUserId() {
    return addAndGetKey(userIds, true);
  }

  /**
   * Gets all of the users in a certain room.
   * @param sessionId The id corresponding to a certain session.
   * @return A list of unique user ids that are contained in the room.
   * @throws NoSuchSessionException Thrown when the requested session does
   *         not exist.
   */
  public List<String> getUsers(String sessionId) throws NoSuchSessionException {
    PreRoom room = getRoom(sessionId);
    return room.getUsers();
  }
}
