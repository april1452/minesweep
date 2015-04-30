package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.setup.PreRoom;

/**
 * An object that represents the data held by a Territory game, where teams
 * compete to claim the most territorial squares on the same board.
 * @author Clayton Sanford
 */
public class TerritoryGame extends ClassicGame {

  /**
   * Constructs a TerritoryGame.
   * @param room A PreRoom object that contains the specifications needed for
   *        the game.
   */
  public TerritoryGame(PreRoom room) {
    super(room);
  }

}
