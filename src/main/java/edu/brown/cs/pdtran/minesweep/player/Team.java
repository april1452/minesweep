package edu.brown.cs.pdtran.minesweep.player;

import java.util.ArrayList;
import java.util.List;

public class Team {
  
  private List<Player> members;
  private int score;
  private int lives;
  private Boolean isWinner;
  private Boolean isLoser;
  
  public Team(List<Gamer> gamers, int lives) {
    members = new ArrayList<>();
    for (Gamer g: gamers) {
      members.add(PlayerFactory.getPlayer(g));
    }
    this.members = members;
    score = 0;
    this.lives = lives;
    isWinner = false;
    isLoser = false;
  }
  
  public void addPlayer(Player player) {
    members.add(player);
  }
  
  public void removePlayer(String username) {
    for (Player p: members) {
      if (p.getUsername().equals(username)) {
        members.remove(p);
      }
    }
  }
  
  public void updateScore() {
    int newScore = 0;
    for (Player p: members) {
      newScore += p.getScore();
    }
    score = newScore;
  }

  public void loseLife() {
    lives--;
  }
  
  public void setWinner() {
    isWinner = true;
  }
  
  public void setLoser() {
    isLoser = true;
  }
}
