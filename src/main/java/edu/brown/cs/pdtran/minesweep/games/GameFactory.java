package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.setup.PreRoom;

public class GameFactory {

  private GameFactory() {
  }

  public static Game generateGame(PreRoom room) {
    switch (room.getSpecs().getMode()) {
      case CLASSIC:
        return new ClassicGame(room);

      case TERRITORY:
        return new TerritoryGame(room);

      case LAYERS:
        return new ClassicGame(room);

      default:
        return null;
    }
  }
}
