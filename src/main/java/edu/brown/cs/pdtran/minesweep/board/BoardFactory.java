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
   * @param width Width of the board
   * @param height Height of the board
   * @param mines Number of mines on the board
   * @return The made board.
   */
  public static Board makeBoard(BoardType type, int width, int height, int mines) {// TODO
    // add
    // additional
    // parameters
    // ADDING 5 BOMBS TEMPORARILY
    switch (type) {
      case DEFAULT:
        return new DefaultBoard(width, height, mines); // width, height, 5
      case RECTANGULAR:
        return new RectangularBoard(); // width, height, 5

      case TRIANGULAR:
        return new TriangularBoard(width, height, mines);
      case HEXAGONAL:
        return new HexagonalBoard();
    }
    return null;
  }
}
