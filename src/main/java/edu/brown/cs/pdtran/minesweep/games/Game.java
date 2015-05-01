package edu.brown.cs.pdtran.minesweep.games;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.metagame.Session;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

/**
 * An Abstract class representing a Game.
 * <p>
 * This is implemented by the different types of games (Classic, FSU,
 * etc.).
 * @author Clayton
 */
public abstract class Game extends Session {

  /**
   * The constructor that builds a Game by using the Session constructor
   * that it extends.
   * @param name The string representing the name of the game.
   * @param specs The GameSpecs object that represents the settins for the
   *        game.
   */
  public Game(String name, GameSpecs specs) {
    super(name, specs);
  }

  @Override
  public abstract ConcurrentMap<String, PlayerTeam> getTeams();

  /**
   * Gets the ids for the players contained in a team.
   * @param teamId The unique string id corresponding to a team.
   * @return A collection of strings representing the id of each player in
   *         that team.
   */
  public abstract Collection<String> getPlayers(String teamId);

  /**
   * Gets the score of a player given the player's GamePlayer object.
   * @param player A GamePlayer object representing a certain player.
   * @return An integer representing that player's score.
   */
  public abstract int getGameScore(GamePlayer player);

  /**
   * Makes a move to the board on behalf of a team.
   * @param teamId The unique string corresponding to a team.
   * @param m A Move object that specifes that the team is doing.
   */
  public abstract Board makeMove(String teamId, Move m);

  /**
   * Implements the move a team uses.
   * @param team An integer representing the number of a team.
   * @param m A Move object that specifies that the team is doing.
   * @return Returns true if the move is executed.
   */
  public abstract boolean play(int team, Move m);

  /**
   * Gets the board corresponding to a given team.
   * @param teamId The unique string representing a team.
   * @return The Board object that the team is using.
   */
  public abstract Board getBoard(String teamId);

}
