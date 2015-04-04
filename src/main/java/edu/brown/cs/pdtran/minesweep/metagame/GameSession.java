package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.games.Game;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Player;

public class GameSession extends Session {

  private Game game;
  private Referee ref;

  public GameSession(RoomSession s) {
  }

  public boolean requestMove(Player p, Move m) {
    if (ref.validateMove(m)) {
      game.play(p, m);
      sendUpdate();
      return true;
    } else {
      return false;
    }
  }

  public void sendUpdate() {
  }
}
