package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.options.PlayerType;
import edu.brown.cs.pdtran.minesweep.options.SessionType;
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
    List<TeamInfo> teams = new ArrayList<TeamInfo>();
    for (TeamFormation team : room.getAllTeams()) {
      List<Gamer> gamers = team.getGamers();
      List<PlayerInfo> players = new ArrayList<PlayerInfo>();
      for (Gamer gamer : gamers) {
        players.add(new PlayerInfo(gamer.getUserName(), PlayerType.HUMAN)); // CHANGE
        // THIS
        // EVENTUALLY
      }
      teams.add(new TeamInfo("TEMPORARY TEAM", players));
    }
    return new RoomInfo(room.getRoomName(), SessionType.SETUP, room
      .getGameSpecs().getMode(), teams);
  }

  public PreRoom getRoom() {
    return room;
  }

}
