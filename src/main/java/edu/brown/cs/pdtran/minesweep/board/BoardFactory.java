package edu.brown.cs.pdtran.minesweep.board;

import edu.brown.cs.pdtran.minesweep.types.BoardType;

/**
 * This class constructs board and boards should be constructed using this
 * class.
 * @author agokasla
 */
public final class BoardFactory {

  private BoardFactory() {

  };

  /**
   * This is the intended constructor to make Boards.
   * @param type The type of the board you wish to make.
   * @param width An integer representing the width in tiles.
   * @param height An integer representing the height in tiles.
   * @param mines The number of mines on the board.
   * @return The made board.
   */
  public static Board makeBoard(BoardType type,
      int width,
      int height,
      int mines) {
    switch (type) {
      case DEFAULT:
        return new DefaultBoard(width, height, mines);
      case RECTANGULAR:
        return new RectangularBoard(width, height, mines);
      case TRIANGULAR:
        return new TriangularBoard(width, height, mines);
      case HEXAGONAL:
        return new HexagonalBoard(width, height, mines);
      default:
        return null;
    }
  }
}
