package edu.brown.cs.pdtran.minesweep.metagame;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.JsonPrimitive;
import edu.brown.cs.pdtran.minesweep.games.Game;
import edu.brown.cs.pdtran.minesweep.games.GameFactory;
import edu.brown.cs.pdtran.minesweep.move.Move;
import edu.brown.cs.pdtran.minesweep.player.AIPlayer;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.AIGamer;
import edu.brown.cs.pdtran.minesweep.setup.HumanGamer;
import edu.brown.cs.pdtran.minesweep.setup.Room;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;
import edu.brown.cs.pdtran.minesweep.types.UpdateType;

/**
 * Keeps records of games, sessions, users, and rooms by storing maps that
 * relate id stirngs to the corresponding information.
 * @author Clayton Sanford
 */
public class RequestHandler {

  private ConcurrentMap<String, Boolean> userIds;
  private ConcurrentMap<String, Session> sessions;
  private ConcurrentMap<String, Room> rooms;
  private ConcurrentMap<String, Game> games;

  /**
   * Constructs a RequestHandler.
   * @throws IOException Thrown if the input is invalid.
   */
  public RequestHandler() throws IOException {
    userIds = new ConcurrentHashMap<String, Boolean>();
    sessions = new ConcurrentHashMap<String, Session>();
    rooms = new ConcurrentHashMap<String, Room>();
    games = new ConcurrentHashMap<String, Game>();
  }

  /**
   * Gets the rooms currently being used by the server.
   * @return A list of map entries that relate unique room id strings to
   *         the corresponding room information.
   */
  public List<Entry<String, SessionInfo>> getSessions() {
    List<Entry<String, SessionInfo>> sessionsInfo =
        new ArrayList<Entry<String, SessionInfo>>();
    for (Entry<String, Session> entry : sessions.entrySet()) {
      sessionsInfo
      .add(new AbstractMap.SimpleImmutableEntry<String, SessionInfo>(
          entry.getKey(), entry.getValue().getRoomInfo()));
    }
    return sessionsInfo;
  }

  private Session getSession(String id) throws NoSuchSessionException {
    Session session = sessions.get(id);
    if (session == null) {
      throw new NoSuchSessionException();
    }
    return session;
  }

  private Room getRoom(String id) throws NoSuchSessionException {
    Room room = rooms.get(id);
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
   * Adds a human to a game if there is no human present.
   * @param sessionId The unique ID for the session.
   * @param gamerId The unique ID for the Gamer.
   * @param name The name of the human.
   * @return A List of Updates to be sent out to players.
   */
  public List<Update> humanJoinIfAbsent(String sessionId,
      String gamerId,
      String name) {
    try {
      List<Update> updates = new ArrayList<>();

      Room room = getRoom(sessionId);

      Map<String, TeamFormation> teams = room.getTeams();


      Entry<String, TeamFormation> smallestTeam = null;

      List<String> usersToUpdate = new ArrayList<>();

      for (Entry<String, TeamFormation> entry : teams.entrySet()) {
        if (smallestTeam == null) {
          smallestTeam = entry;
        } else {

          int smallestSize = smallestTeam.getValue().getSize();

          TeamFormation team = entry.getValue();
          if (team.getPlayers().containsKey(gamerId)) {
            usersToUpdate.add(gamerId);
            updates.add(new Update(UpdateType.ROOM_UPDATE, room
                .getRoomInfo()
                .toJson(), usersToUpdate));
            return updates;
          } else if (team.getSize() < smallestSize
              || (team.getSize() == smallestSize && team.getName()
              .compareTo(smallestTeam.getValue().getName()) < 0)) {
            smallestTeam = entry;
          }
        }
      }

      room.addHuman(smallestTeam.getKey(), gamerId, new HumanGamer(name));

      updates.add(getTeamAssignment(smallestTeam.getKey(), gamerId));

      updates.add(getRoomUpdate(room));

      return updates;
    } catch (NoSuchSessionException e) {
      List<Update> updates = new ArrayList<>();
      updates.add(getNoSessionError(gamerId));
      return updates;
    } catch (SessionFullException e) {
      List<Update> updates = new ArrayList<>();
      updates.add(getFullTeamError(gamerId));
      return updates;
    }
  }

  /**
   * Adds a gamer to a room in the event that the gamer is not already in
   * the room.
   * @param sessionId The unique string corresponding to the session.
   * @param teamId The id of the team the player is leaving.
   * @param gamerId The unique string corresponding to the user.
   * @param newTeamId The id of the player's new team.
   * @return Returns the updates generated by switching a player.
   */
  public List<Update> humanSwitch(String sessionId,
      String teamId,
      String gamerId,
      String newTeamId) {
    try {
      List<Update> updates = new ArrayList<>();

      Room room = getRoom(sessionId);
      room.switchTeam(teamId, gamerId, newTeamId);

      updates.add(getTeamAssignment(newTeamId, gamerId));
      updates.add(getRoomUpdate(room));

      return updates;
    } catch (NoSuchSessionException e) {
      List<Update> updates = new ArrayList<>();
      updates.add(getNoSessionError(gamerId));
      return updates;
    } catch (SessionFullException e) {
      List<Update> updates = new ArrayList<>();
      updates.add(getFullTeamError(gamerId));
      return updates;
    }
  }

  /**
   * Adds an AI to a Game.
   * @param sessionId The unique ID for a session.
   * @param teamId The unique ID for a team.
   * @param requesterId The unique ID for the requester.
   * @param aiId The unique ID for the AI.
   * @param g The AIGamer to be added.
   * @return An update that the AI has been added.
   */
  public Update aiJoin(String sessionId,
      String teamId,
      String requesterId,
      String aiId,
      AIGamer g) {

    try {
      Room room = getRoom(sessionId);
      room.addAi(teamId, aiId, g);
      return getRoomUpdate(room);
    } catch (NoSuchSessionException e) {
      return getNoSessionError(requesterId);
    } catch (SessionFullException e) {
      return getFullTeamError(requesterId);
    }
  }

  private Update getNoSessionError(String gamerId) {
    List<String> playersToUpdate = new ArrayList<>();
    playersToUpdate.add(gamerId);
    return new Update(UpdateType.ERROR, new JsonPrimitive(
        "The session could not be found."), playersToUpdate);
  }

  private Update getFullTeamError(String gamerId) {
    List<String> playersToUpdate = new ArrayList<>();
    playersToUpdate.add(gamerId);
    return new Update(UpdateType.ERROR, new JsonPrimitive(
        "The team you tried to join was full."), playersToUpdate);
  }

  private Update getTeamAssignment(String teamId, String gamerId) {
    List<String> playersToUpdate = new ArrayList<>();
    playersToUpdate.add(gamerId);
    return new Update(UpdateType.TEAM_ASSIGNMENT,
        new JsonPrimitive(teamId),
        playersToUpdate);

  }

  private Update getRoomUpdate(Room room) {
    List<String> playersToUpdate = getHumans(room);
    return new Update(UpdateType.ROOM_UPDATE, room.getRoomInfo().toJson(),
        playersToUpdate);
  }

  private List<Update> getInitBoardUpdate(Game game) {
    List<Update> updates = new ArrayList<>();
    for (Entry<String, PlayerTeam> entry : game.getTeams().entrySet()) {
      List<String> playersToUpdate = entry.getValue().getHumans();
      updates.add(new Update(UpdateType.INIT_BOARD, game
          .getBoardInfo(entry.getKey()),
          playersToUpdate));
    }
    return updates;
  }

  private Update getInitInfo(Game game) {
    List<String> playersToUpdate = getHumans(game);
    return new Update(UpdateType.INIT_INFO, game.getGameData(),
        playersToUpdate);
  }

  private List<String> getHumans(Session session) {
    List<String> humans = new ArrayList<>();
    for (Team team : session.getTeams().values()) {
      humans.addAll(team.getHumans());
    }
    return humans;
  }

  /**
   * For an object, generates an id corresponding to it and adds the object
   * to the map that relates strings to corresponding objects.
   * @param map A map between unique id strings and the objects the same
   *        type as the inputted value.
   * @param value The object to be added to the map.
   * @param <T> A value in the concurrent map.
   * @return The map with the new value added if it was not already present
   *         with a unique id string.
   */
  public static <T> String addAndGetKey(ConcurrentMap<String, T> map,
      T value) {
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
  public SessionInfo getSessionInfo(String id)
      throws NoSuchSessionException {
    Room room = getRoom(id);
    return room.getRoomInfo();
  }

  /**
   * Creates a game from a room id. Removes the id corresponding to that
   * room from the Rooms map and creates a new Game with the specified
   * information in the Games map.
   * @param sessionId The unique string corresponding to a room.
   * @param userId The string corresponding to the host.
   * @param handler The object that manages moves.
   * @return A list of updates to be sent to the board.
   */
  public List<Update> startGame(UpdateSender updateSender,
      String sessionId,
      String userId,
      MoveHandler handler) {
    try {
      Room room = rooms.remove(sessionId);
      if (room == null || sessions.remove(sessionId) == null) {
        throw new NoSuchSessionException();
      }
      Game game = GameFactory.generateGame(room, updateSender);
      games.put(sessionId, game);
      sessions.put(sessionId, game);

      for (Entry<String, PlayerTeam> entry : game.getTeams().entrySet()) {
        PlayerTeam team = entry.getValue();
        for (AIPlayer ai : team.getAis()) {
          new Thread(new AIRunnable(sessionId, team, entry.getKey(), ai,
              handler)).start();
        }
      }

      List<Update> updates = new ArrayList<>();
      updates.addAll(getInitBoardUpdate(game));
      updates.add(getInitInfo(game));

      return updates;
    } catch (NoSuchSessionException e) {
      List<Update> updates = new ArrayList<>();
      updates.add(getNoSessionError(userId));
      return updates;
    }
  }

  /**
   * Generates an id for a room and adds the room to the map of ids to
   * rooms.
   * @param room A PreRoom object with information needed to define the
   *        kind of room we want.
   * @return The id genereated for the inputted room.
   */
  public String addRoom(Room room) {
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
   * Makes a move and gets a list of updates to be sent out to various
   * players.
   * @param sessionId The unique ID for the given session.
   * @param teamId The unique ID for the given team.
   * @param move The Move object representing a given move.
   * @return A list of updates for the move to be sent out to players.
   */
  public List<Update> makeMove(String sessionId, String teamId, Move move) {
    try {
      Game game = getGame(sessionId);
      return game.makeMove(teamId, move);
    } catch (NoSuchSessionException e) {
      // not returning an error message in this case (maybe change?)
      return new ArrayList<Update>();
    }
  }

}
