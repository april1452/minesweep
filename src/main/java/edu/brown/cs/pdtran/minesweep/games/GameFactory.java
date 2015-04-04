package edu.brown.cs.pdtran.minesweep.games;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.player.Player;

public class GameFactory {

  public enum GameType {
    CLASSIC, LAYERS, TERRITORY;
  }

  private GameFactory() {}

  public Game generateGame(GameType game, Board board, Player[] player) {
    switch (game) {

      case CLASSIC:
        return new ClassicGame(board, player);


      case TERRITORY:
        return new ClassicGame(board, player);
    }
    return null;
  }

}
