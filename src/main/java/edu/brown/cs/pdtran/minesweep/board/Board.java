package edu.brown.cs.pdtran.minesweep.board;

public interface Board {

  void makeMove(int row, int col);

  boolean isWinningBoard();

  boolean isLosingBoard();

  boolean isGameOver();
}
