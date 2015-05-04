package edu.brown.cs.pdtran.minesweep.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.board.BoardFactory;
import edu.brown.cs.pdtran.minesweep.move.Move;
import edu.brown.cs.pdtran.minesweep.player.GamePlayer;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;
import edu.brown.cs.pdtran.minesweep.types.SessionType;

/**
 * The class that represents code needed for the classic game mode.
 * <p>
 * In this game mode, each team solves a copy of the same board, and the
 * winning team either is the team that lasts the longest or is the team
 * that finishes its board fist.
 * @author Clayton Sanford
 */
public class ClassicGame extends Game {

  protected ConcurrentMap<String, PlayerTeam> teams;

  /**
   * A constructor for a ClassicGame.
   * @param room Uses a room with game information to generate the game
   *        object.
   */
  public ClassicGame(PreRoom room) {
    super(room);
  }

  @Override
  public Board makeMove(String teamId, Move m) {
    PlayerTeam team = teams.get(teamId);
    Board board = team.getCurrentBoard();
    Boolean loseLife = board.makeMove(m.getYCoord(), m.getXCoord());
    if (loseLife) {
      team.loseLife();
    }
    if (board.isWinningBoard()) {
      return null;
    } else {
      return board;
    }
  }

  /**
   * Gets the number of moves remaining.
   * @param player A GamePlayer object that represents a player in the
   *        game.
   * @return This will calculate how many moves are left.
   */
  @Override
  public int getGameScore(GamePlayer player) {
    // TODO?
    return 0;
  }

  @Override
  public SessionType getSessionType() {
    return SessionType.IN_GAME;
  }

  @Override
  public ConcurrentMap<String, PlayerTeam> getTeams() {
    return teams;
  }

  @Override
  public Board getBoard(String teamId) {
    return teams.get(teamId).getCurrentBoard();
  }

  @Override
  protected ConcurrentMap<String, PlayerTeam> makeTeams(ConcurrentMap<String, TeamFormation> preTeams) {
    ConcurrentMap<String, PlayerTeam> teams =
        new ConcurrentHashMap<String, PlayerTeam>();
    List<Board> boardsToPlay = new ArrayList<>();
    int[] dims = specs.getBoardDims();
    boardsToPlay.add(BoardFactory.makeBoard(getSpecs().getBoardType(), dims[0],
        dims[1], 40));
    for (Map.Entry<String, TeamFormation> entry : preTeams.entrySet()) {
      List<Board> copy = new ArrayList<>();
      for (Board board : boardsToPlay) {
        copy.add(board.clone());
      }
      teams.put(entry.getKey(),
          new PlayerTeam(entry.getValue(), specs.getTeamLives(), copy));
    }
    return teams;
  }

}
