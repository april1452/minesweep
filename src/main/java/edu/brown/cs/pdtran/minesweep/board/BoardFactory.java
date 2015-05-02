package edu.brown.cs.pdtran.minesweep.board;

import edu.brown.cs.pdtran.minesweep.types.BoardType;

/**
 * This class constructs board and boards should be constructed using this
 * class.
 * @author agokasla
 */
public class BoardFactory {

  private BoardFactory() {};

  /**
   * This is the intended constructor to make Boards.
   * @param type The type of the board you wish to make.
   * @return The made board.
   */
  public static Board makeBoard(BoardType type, int width, int height) {// TODO
    // add
    // additional
    // parameters
    // ADDING 5 BOMBS TEMPORARILY
    switch (type) {
      case DEFAULT:
        return new DefaultBoard(width, height, 40);

      case RECTANGULAR:
        return new RectangularBoard(); // width, height, 5

      case TRIANGULAR:
        return new TriangularBoard();
      case HEXAGONAL:
        return new HexagonalBoard();
    }
    return null;
  }
}
