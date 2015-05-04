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
  private int difficulty;
  private static final int DIFFICULTY_MULTIPLIER = 32;

  /**
   * Create game specifications.
   * @param mode game mode (c, fsu, lay, path, terr)
   * @param matches number of matches/rounds to be played
   * @param shape board shape
   * @param dims board dimensions
   * @param numTeams number of teams
   * @param numTeamPlayers number of players per team
   * @param teamLives lives per team
   * @param difficulty density of mines on the board
   */
  public GameSpecs(GameMode mode, BoardType shape, int matches, int numTeams,
      int numTeamPlayers, int teamLives, int[] dims, int difficulty) {
    this.mode = mode;
    this.boardType = shape;
    this.numTeams = numTeams;
    this.numTeamPlayers = numTeamPlayers;
    this.teamLives = teamLives;
    this.numMatches = matches;
    this.boardDims = dims;
    this.difficulty = difficulty;
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

  public int getNumMines() {
    return difficulty * boardDims[0] * boardDims[1] / DIFFICULTY_MULTIPLIER;
  }
}
