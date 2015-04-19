package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo.SessionType;
import edu.brown.cs.pdtran.minesweep.setup.Gamer;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;

public class RoomSession implements Session {

  private PreRoom room;
  private Map<String, Socket> connections;

  public RoomSession(PreRoom room) {
    this.room = room;
    connections = new ConcurrentHashMap<String, Socket>();
  }

  @Override
  public RoomInfo getRoomInfo() {
    List<String> playerNames = new ArrayList<String>();
    for (TeamFormation team : room.getAllTeams()) {
      List<Gamer> gamers = team.getGamers();
      for (Gamer gamer : gamers) {
        playerNames.add(gamer.getUserName());
      }
    }
    return new RoomInfo(SessionType.SETUP, room.getGameSpecs().getMode(),
      playerNames);
  }

  public PreRoom getRoom() {
    return room;
  }

}
