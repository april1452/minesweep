package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.board.Board;

public class DummyBoardData implements BoardData {

  private Board board;

  public DummyBoardData(Board board) {
    this.board = board;
  }

  @Override
  public Board getCurrentBoard() {
    return board;
  }

}
