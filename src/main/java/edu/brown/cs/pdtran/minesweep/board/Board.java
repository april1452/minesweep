package edu.brown.cs.pdtran.minesweep.board;

public interface Board extends Cloneable {

  void makeMove(int row, int col);

  boolean isWinningBoard();

  boolean isLosingBoard();

  boolean isGameOver();
}
