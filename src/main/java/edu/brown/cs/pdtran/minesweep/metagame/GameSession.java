package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.board.BoardFactory;
import edu.brown.cs.pdtran.minesweep.games.Game;
import edu.brown.cs.pdtran.minesweep.games.GameFactory;
import edu.brown.cs.pdtran.minesweep.options.BoardType;
import edu.brown.cs.pdtran.minesweep.options.GameMode;
import edu.brown.cs.pdtran.minesweep.options.PlayerType;
import edu.brown.cs.pdtran.minesweep.options.SessionType;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Player;
import edu.brown.cs.pdtran.minesweep.player.Team;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;

public class GameSession implements Session {

  private Game game;

  private Referee ref;

  public GameSession(RoomSession roomSession) {
    PreRoom room = roomSession.getRoom();

    Board firstboard = BoardFactory.makeBoard(BoardType.DEFAULT);
    Board secondBoard = BoardFactory.makeBoard(BoardType.DEFAULT);

    List<TeamFormation> formation = room.getAllTeams();

    int teamLives = room.getGameSpecs().getTeamLives();

    Team teamOne =
      new Team(formation.get(0).getGamers(), teamLives, "Team One", firstboard);

    Team teamTwo =
      new Team(formation.get(1).getGamers(), teamLives, "Team Two", secondBoard);

    Team[] teams = { teamOne, teamTwo };

    ref = new TestReferee();

    game = GameFactory.generateGame(GameMode.CLASSIC, teams);
  }

  public void requestMove(int team, Move m) {
    if (ref.validateMove(m)) {
      game.play(team, m);
    }
  }

  @Override
  public RoomInfo getRoomInfo() {
    List<TeamInfo> teams = new ArrayList<TeamInfo>();
    for (Team team : game.getTeams()) {
      List<Player> players = team.getPlayers();
      List<PlayerInfo> playersInfo = new ArrayList<PlayerInfo>();
      for (Player player : players) {
        playersInfo.add(new PlayerInfo(player.getUsername(), PlayerType.HUMAN));
        // CHANGE
        // THIS
        // EVENTUALLY
      }
      teams.add(new TeamInfo(team.getName(), playersInfo));
    }

    List<String> playerNames = new ArrayList<String>();
    playerNames.add("temp1");
    playerNames.add("temp2");

    return new RoomInfo("temp2", SessionType.IN_GAME, GameMode.CLASSIC, teams);
  }

  public Board getBoard(int team) {
    return game.getTeams()[team].getBoard();
  }
}
