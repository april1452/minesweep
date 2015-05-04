package edu.brown.cs.pdtran.minesweep.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.board.BoardFactory;
import edu.brown.cs.pdtran.minesweep.player.PlayerTeam;
import edu.brown.cs.pdtran.minesweep.setup.PreRoom;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;

/**
 * An object that represents the data held by a Territory game, where teams
 * compete to claim the most territorial squares on the same board.
 * @author Clayton Sanford
 */
public class TerritoryGame extends Game {

  /**
   * Constructs a TerritoryGame.
   * @param room A PreRoom object that contains the specifications needed
   *        for the game.
   */
  public TerritoryGame(PreRoom room) {
    super(room);
    List<Board> boardsToPlay = new ArrayList<>();
    int[] dims = specs.getBoardDims();
    boardsToPlay.add(BoardFactory.makeBoard(specs.getBoardType(), dims[0],
        dims[1], specs.getNumMines()));
    teams = new ConcurrentHashMap<String, PlayerTeam>();
    for (Map.Entry<String, TeamFormation> entry : room.getTeams().entrySet()) {
      teams.put(entry.getKey(),
          new PlayerTeam(entry.getValue(), specs.getTeamLives(), boardsToPlay));
    }
  }

}
