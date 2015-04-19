package edu.brown.cs.pdtran.minesweep.board;

/**
 * This class constructs board and boards should be constructed using this
 * class.
 *
 * @author agokasla
 *
 */
public class BoardFactory {

  /**
   * The different board types you wish to make.
   *
   * @author agokasla
   *
   */
  public enum BoardType {
    DEFAULT, RECTANGULAR, TRIANGULAR;
  }

  private BoardFactory() {};

  /**
   * This is the intended constructor to make Boards.
   *
   * @param type The type of the board you wish to make.
   * @return The made board.
   */
  public Board makeBoard(BoardType type) {// TODO add additional parameters
    switch (type) {
      case DEFAULT:
        return new DefaultBoard();

      case RECTANGULAR:
        return new RectangularBoard();

      case TRIANGULAR:
        return new TriangularBoard();
    }
    return null;
  }
}
