package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo.SessionType;
import edu.brown.cs.pdtran.minesweep.setup.Gamer;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;

public class RoomSession implements Session {

  private PreRoom room;

  public RoomSession(PreRoom room) {
    this.room = room;
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
    return new RoomInfo(room.getRoomName(), SessionType.SETUP, room
      .getGameSpecs().getMode(), playerNames);
  }

  public PreRoom getRoom() {
    return room;
  }

}
