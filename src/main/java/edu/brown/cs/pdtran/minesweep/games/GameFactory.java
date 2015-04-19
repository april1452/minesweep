package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.options.GameMode;
import edu.brown.cs.pdtran.minesweep.player.Player;

public class GameFactory {

  private GameFactory() {
  }

  public static Game generateGame(GameMode game, Board board, Player[] player) {
    switch (game) {
      case CLASSIC:
        return new ClassicGame(board, player);

      case TERRITORY:
        return new ClassicGame(board, player);

      case LAYERS:
        return new ClassicGame(board, player);

      default:
        return null;
    }
  }
}
