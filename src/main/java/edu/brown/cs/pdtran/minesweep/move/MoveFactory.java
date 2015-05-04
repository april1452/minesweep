package edu.brown.cs.pdtran.minesweep.move;

import edu.brown.cs.pdtran.minesweep.types.MoveType;

public class MoveFactory {

  private MoveFactory() {};

  /**
   * This is the intended constructor to make Moves.
   * @param xCoord The x location of the move.
   * @param yCoord The y location of the move.
   * @param moveType The type of the move you wish to make.
   * @return The made move.
   */
  public static Move makeMove(int xCoord, int yCoord, MoveType moveType) {
    switch (moveType) {
      case CHECK:
        return new CheckTile(xCoord, yCoord);
      case FLAG:
        return new FlagTile(xCoord, yCoord);
    }
    return null;
  }

}
