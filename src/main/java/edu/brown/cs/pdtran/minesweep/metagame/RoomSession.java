package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.ServerSocket;

import edu.brown.cs.pdtran.minesweep.setup.Room;

public class RoomSession extends Session {

  private Room room;

  public RoomSession(ServerSocket s) {
    super(s);
  }

  public RoomInfo getRoomInfo() {
    return null;
  }

}
