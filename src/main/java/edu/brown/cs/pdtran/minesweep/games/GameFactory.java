package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.options.GameMode;
import edu.brown.cs.pdtran.minesweep.player.Team;

public class GameFactory {

  private GameFactory() {
  }

  public static Game generateGame(GameMode game, Team[] teams) {
    switch (game) {
      case CLASSIC:
        return new ClassicGame(teams);

      case TERRITORY:
        return new ClassicGame(teams);

      case LAYERS:
        return new ClassicGame(teams);

      default:
        return null;
    }
  }
}
