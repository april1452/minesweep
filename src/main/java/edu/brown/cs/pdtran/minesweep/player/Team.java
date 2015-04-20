package edu.brown.cs.pdtran.minesweep.player;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.pdtran.minesweep.board.Board;
import edu.brown.cs.pdtran.minesweep.board.DefaultBoard;
import edu.brown.cs.pdtran.minesweep.setup.AIGamer;
import edu.brown.cs.pdtran.minesweep.setup.Gamer;
import edu.brown.cs.pdtran.minesweep.setup.HumanGamer;

/**
 * Represents a Team of Players that works together in a game of Minesweep.
 *
 * @author Clayton
 *
 */
public class Team {

  private List<Player> members;
  private int score;
  private int lives;
  private Boolean isWinner;
  private Boolean isLoser;
  String name;
  Board board;

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
  public Team(List<Gamer> gamers, int lives, String name, Board board) {
    members = new ArrayList<>();
    for (Gamer g : gamers) {
      if (g instanceof HumanGamer) {
        HumanGamer h = (HumanGamer) g;
        members.add(new HumanPlayer(h));
      } else if (g instanceof AIGamer) {
        AIGamer a = (AIGamer) g;
        members.add(new AIPlayer(a, (DefaultBoard) board)); // TEMPORARY
      }
    }
    score = 0;
    this.lives = lives;
    isWinner = false;
    isLoser = false;
    this.name = name;
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }

  /**
   * Adds a new Player to the List of Players in the Team.
   *
   * @param player
   *          The object for a Player that is to be added to the team.
   */
  public void addPlayer(Player player) {
    members.add(player);
  }

  /**
   * Searches for the username for a player and removes that player from the
   * team and from play.
   *
   * @param username
   *          A unique string for a player on the team.
   */
  public void removePlayer(String username) {
    for (Player p : members) {
      if (p.getUsername().equals(username)) {
        members.remove(p);
      }
    }
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
    for (Player p : members) {
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
    for (Player p : members) {
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
    for (Player p : members) {
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

  public List<Player> getPlayers() {
    return members;
  }

  public String getName() {
    return name;
  }
}
