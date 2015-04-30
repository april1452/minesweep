package edu.brown.cs.pdtran.minesweep.setup;

import edu.brown.cs.pdtran.minesweep.types.BoardType;
import edu.brown.cs.pdtran.minesweep.types.GameMode;

/**
 * Specificiations for a game: game mode (classic, layers, territory, path,
 * FSU), number of matches, board shape, board dimensions. Used to create a
 * game.
 * @author pdtran
 */
public class GameSpecs {
  private GameMode mode;
  private int numTeams;
  private int numTeamPlayers;
  private int teamLives;
  private int numMatches;
  private BoardType boardType;
  private int[] boardDims;

  /**
   * Create game specifications.
   * @param mode game mode (c, fsu, lay, path, terr)
   * @param matches number of matches/rounds to be played
   * @param shape board shape
   * @param dims board dimensions
   */
  public GameSpecs(GameMode mode, int matches, BoardType shape, int[] dims) {
    this.mode = mode;
    this.numTeams = 2;
    this.numTeamPlayers = 3;
    this.teamLives = 2;
    this.numMatches = matches;
    this.boardType = shape;
    this.boardDims = dims;
  }

  /**
   * Gets the number of lives each team starts with.
   * @return An integer representing the number of lives.
   */
  public int getTeamLives() {
    return teamLives;
  }

  /**
   * Gets the number of allowed teams for the game.
   * @return An integer representing total number of possible teams.
   */
  public int getNumTeams() {
    return numTeams;
  }

  /**
   * Gets the number of players per team.
   * @return An integer representing the number of players allowed on a
   *         team.
   */
  public int getNumTeamPlayers() {
    return numTeamPlayers;
  }

  /**
   * Return game mode: c, fsu, lay, path, terr.
   * @return game mode
   */
  public GameMode getMode() {
    return mode;
  }

  /**
   * Return number of matches/rounds.
   * @return number of matches
   */
  public int getNumMatches() {
    return numMatches;
  }

  /**
   * Return board shape: def, rect, tri.
   * @return board shape
   */
  public BoardType getBoardType() {
    return boardType;
  }

  /**
   * Return board dimensions.
   * @return board dimensions
   */
  public int[] getBoardDims() {
    return boardDims;
  }
}
