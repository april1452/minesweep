package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.metagame.UpdateSender;
import edu.brown.cs.pdtran.minesweep.setup.Room;

/**
 * Builds Game objects according to the mode enum specified by each room.
 * @author Clayton Sanford
 */
public final class GameFactory {

  private GameFactory() {
    // Private constructor to avoid having public or default constructor
  }

  /**
   * Generates a Game object based on the mode enum assigned.
   * @param room A PreRoom object with information on the game's setup.
   * @return A Game object of the mode corresponding to the enum.
   */
  public static Game generateGame(Room room, UpdateSender updateSender) {
    switch (room.getSpecs().getMode()) {
      case CLASSIC:
        return new ClassicGame(room);

      case TERRITORY:
        return new TerritoryGame(room);

      case LAYERS:
        return new LayersGame(room);

      case TIMER:
        return new TimerGame(room, updateSender);

      default:
        return null;
    }
  }
}
