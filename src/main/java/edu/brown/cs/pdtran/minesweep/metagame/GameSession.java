package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.metagame.RoomInfo.SessionType;
import edu.brown.cs.pdtran.minesweep.setup.Gamer;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.board.BoardFactory;
import edu.brown.cs.pdtran.minesweep.games.Game;
import edu.brown.cs.pdtran.minesweep.games.GameFactory;
import edu.brown.cs.pdtran.minesweep.options.BoardType;
import edu.brown.cs.pdtran.minesweep.options.GameMode;
import edu.brown.cs.pdtran.minesweep.player.Move;
import edu.brown.cs.pdtran.minesweep.player.Team;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;

public class GameSession implements Session {

  private Game game;

  private Referee ref;

  public GameSession(RoomSession roomSession) {
    PreRoom room = roomSession.getRoom();

    Board board = BoardFactory.makeBoard(BoardType.DEFAULT);
    Board secondBoard = board.clone();

    List<TeamFormation> formation = room.getAllTeams();

    int teamLives = room.getGameSpecs().getTeamLives();

    Team teamOne =
      new Team(formation.get(0).getGamers(), teamLives, "Team One", board);

    Team teamTwo =
      new Team(formation.get(1).getGamers(), teamLives, "Team Two", secondBoard);

    Team[] teams = { teamOne, teamTwo };

    game = GameFactory.generateGame(GameMode.CLASSIC, teams);
  }

  public void requestMove(int team, Move m) {
    if (ref.validateMove(m)) {
      game.play(team, m);
    }
  }

  @Override
  public RoomInfo getRoomInfo() {
    //    List<String> playerNames = new ArrayList<String>();
    //    for (TeamFormation team : room.getAllTeams()) {
    //      List<Gamer> gamers = team.getGamers();
    //      for (Gamer gamer : gamers) {
    //        playerNames.add(gamer.getUserName());
    //      }
    //    }

    List<String> playerNames = new ArrayList<String>();
    playerNames.add("temp1");
    playerNames.add("temp2");


    return new RoomInfo("temp", SessionType.IN_GAME, GameMode.CLASSIC), playerNames);
  }
}
