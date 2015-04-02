package edu.brown.cs.pdtran.minesweep.board;

public class BoardFactory {

  public enum BoardType {
    DEFAULT, RECTANGULAR, TRIANGULAR;
  }

  private BoardFactory() {};

  public Board makeBoard(BoardType type) {
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
