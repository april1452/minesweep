package edu.brown.cs.pdtran.minesweep.player;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brown.cs.pdtran.minesweep.setup.HumanGamer;

public class HumanPlayerTest {

  @Test
  /**
   * Verifies that making a new HumanPlayer created from a HumanGamer has all
   * the characteristics expected of the Gamer, like username.
   */
  public void newHumanTest() {
    HumanGamer myGamer = new HumanGamer("id", "Clay");
    HumanPlayer myPlayer = new HumanPlayer(myGamer);
    assertTrue(myGamer.getUserName().equals(myPlayer.getUsername()));
  }
  
  @Test
  /**
   * Verifies that a simple change in score is recorded and that the updated
   * score reflects it.
   */
  public void scoreTester() {
    HumanPlayer p = new HumanPlayer("Clay");
    assertTrue(p.getScore() == 0);
    p.changeScore(100);
    assertTrue(p.getScore() == 100);
    p.changeScore(-150);
    assertTrue(p.getScore() == -50);
    p.changeScore(200);
    assertTrue(p.getScore() == 150);
  }
  
  @Test
  /**
   * This test checks that the player can make a move and see that same move be
   * registered on the board.
   */
  public void moveTester() {
    fail("Not yet implemented");
  }

}
