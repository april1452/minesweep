package edu.brown.cs.pdtran.minesweep.setup;

/**
 * Specificiations for a game: game mode (classic, layers, territory, path,
 * FSU), number of matches, board shape, board dimensions. Used to create a
 * game.
 * @author pdtran
 */
public class GameSpecs {
  private String mode;
  private int numMatches;
  private String boardShape;
  private int[] boardDims;

  /**
   * Create game specifications.
   * @param mode game mode (c, fsu, lay, path, terr)
   * @param matches number of matches/rounds to be played
   * @param shape board shape
   * @param dims board dimensions
   */
  public GameSpecs(String mode, int matches, String shape, int[] dims) {
    this.mode = mode;
    this.numMatches = matches;
    this.boardShape = shape;
    this.boardDims = dims;
  }

  /**
   * Return game mode: c, fsu, lay, path, terr.
   * @return game mode
   */
  public String getMode() {
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
  public String getBoardShape() {
    return boardShape;
  }

  /**
   * Return board dimensions.
   * @return board dimensions
   */
  public int[] getboardDims() {
    return boardDims;
  }
}
