package edu.brown.cs.pdtran.minesweep.games;

import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

public class GameFactory {

  private GameFactory() {
  }

  public static Game generateGame(String name, GameSpecs specs,
    ConcurrentMap<String, PlayerTeam> teams) {
    switch (specs.getMode()) {
      case CLASSIC:
        return new ClassicGame(name, specs, teams);

      case TERRITORY:
        return new ClassicGame(name, specs, teams);

      case LAYERS:
        return new ClassicGame(name, specs, teams);

      default:
        return null;
    }
  }
}
