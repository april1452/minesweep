package edu.brown.cs.pdtran.minesweep.metagame;

import edu.brown.cs.pdtran.minesweep.games.GameFactory;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Player;

public class GameSession implements Session {

  public GameSession(RoomSession s) {

  }

  private GameFactory game;

  private Referee ref;

  // public GameSession(RoomSession s) {
  // }

  public boolean requestMove(Player p, Move m) {
    // if (ref.validateMove(m)) {
    // game.play(p, m);
    // sendUpdate();
    // return true;
    // } else {
    // return false;
    // }
    return false;
  }

  public void sendUpdate() {
  }

  @Override
  public RoomInfo getRoomInfo() {
    // TODO Auto-generated method stub
    return null;
  }
}
