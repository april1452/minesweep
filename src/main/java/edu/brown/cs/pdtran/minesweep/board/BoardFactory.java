package edu.brown.cs.pdtran.minesweep.board;

public class BoardFactory {

  public enum BoardType {
    DEFAULT, RECTANGULAR;
  }

  private BoardFactory() {};

  public Board makeBoard(BoardType type) {
    switch (type) {
      case DEFAULT:
        return new DefaultBoard();

      case RECTANGULAR:
        return new RectangularBoard();
    }
    return null;
  }
}
