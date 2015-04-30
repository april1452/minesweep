package edu.brown.cs.pdtran.minesweep.player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.games.BoardData;
import edu.brown.cs.pdtran.minesweep.metagame.Team;
import edu.brown.cs.pdtran.minesweep.setup.Gamer;
import edu.brown.cs.pdtran.minesweep.setup.TeamFormation;

/**
 * Represents a Team of Players that works together in a game of Minesweep.
 *
 * @author Clayton
 *
 */
public class PlayerTeam extends Team implements BoardData {

  private ConcurrentMap<String, GamePlayer> players;
  private int score;
  private int lives;
  private Boolean isWinner;
  private Boolean isLoser;
  private List<Board> boards;
  private int boardIndex;

  /**
   * Creates a new Team to last through a game.
   *
   * @param gamers
   *          A List of all the Gamer objects from the pre-game that are to made
   *          into Player objects within the team.
   * @param lives
   *          The total number of lives for the team and its players.
   * @param id
   *          A unique string for the given team.
   * @param (DEFAULT BOARD) board2 The Board object that the team holds.
   */
  public PlayerTeam(TeamFormation tf, int lives, List<Board> boards) {
    super(tf.getName());
    this.lives = lives;
    this.boards = boards;
    boardIndex = 0;
    players = new ConcurrentHashMap<String, GamePlayer>();
    for (Map.Entry<String, Gamer> entry : tf.getPlayers().entrySet()) {
      players.put(entry.getKey(), entry.getValue().toGamePlayer(this));
    }

    score = 0;
    isWinner = false;
    isLoser = false;
  }

  @Override
  public Board getCurrentBoard() {
    return boards.get(boardIndex);
  }

  /**
   * Searches for the id for a player and removes that player from the team and
   * from play.
   *
   * @param id
   *          A unique string for a player on the team.
   */
  public void removePlayer(String id) {
    players.remove(id);
  }

  /**
   * Gets the team's score.
   *
   * @return An integer representing the total score of all the players on a
   *         team.
   */
  public int getScore() {
    return score;
  }

  /**
   * Updates the team's score by iterating through all the players and adding
   * their individual scores.
   */
  public void updateScore() {
    int newScore = 0;
    for (GamePlayer p : players.values()) {
      newScore += p.getScore();
    }
    score = newScore;
  }

  /**
   * Subtracts one life from the team when they land on a mine.
   */
  public void loseLife() {
    lives--;
    if (lives == 0) {
      setIsLoser();
    }
  }

  /**
   * Gets the number of lives remaining.
   *
   * @return An int representing the number of lives left.
   */
  public int getLives() {
    return lives;
  }

  /**
   * Makes the team into the winning team and ends gameplay for all of the its
   * Players.
   */
  public void setIsWinner() {
    isWinner = true;
    for (GamePlayer p : players.values()) {
      p.endPlay();
    }
  }

  /**
   * Checks if the team is the winning team.
   *
   * @return A Boolean that is true if the team is the winner and false if the
   *         team is the loser or if the game is not over.
   */
  public Boolean getIsWinner() {
    return isWinner;
  }

  /**
   * Makes the team into the winning team and ends gameplay for all of the its
   * Players.
   */
  public void setIsLoser() {
    isLoser = true;
    for (GamePlayer p : players.values()) {
      p.endPlay();
    }
  }

  /**
   * Checks if the team is the losing team.
   *
   * @return A Boolean that is true if the team is the loser and false if the
   *         team is the winner or if the game is not over.
   */
  public Boolean getIsLoser() {
    return isLoser;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Map<String, GamePlayer> getPlayers() {
    return players;
  }
}
