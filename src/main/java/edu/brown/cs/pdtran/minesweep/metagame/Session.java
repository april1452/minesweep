package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.options.SessionType;
import edu.brown.cs.pdtran.minesweep.setup.GameSpecs;

public abstract class Session {

  protected String name;
  protected GameSpecs gameSpecs;
  protected ConcurrentMap<String, ? extends Team> teams;

  public abstract SessionType getSessionType();

  public RoomInfo getRoomInfo() {
    List<TeamInfo> teamsInfo = new ArrayList<TeamInfo>();
    for (Team team : teams.values()) {
      teamsInfo.add(team.getTeamInfo());
    }
    return new RoomInfo(name, getSessionType(), room.getGameSpecs().getMode(),
      teams);
  }
}
