package edu.brown.cs.pdtran.minesweep.player;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HumanPlayerTest {

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

}
