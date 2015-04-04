package edu.brown.cs.pdtran.minesweep.player;

import static org.junit.Assert.*;

import org.junit.Test;

public class AIPlayerTest {

  @Test
  /**
   * Uses several pre-established AIGamers and shows that the AIPlayers created
   * by them have the same characteristics (username, difficulty)
   */
  public void newAIGamerTest() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * Verifies that a simple change in score is recorded and that the updated
   * score reflects it.
   */
  public void scoreTester() {
    AIPlayer p = new AIPlayer("Clay", 5);
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
   * Using a simple partially-solved board, this test verifies that the AI can
   * find tiles guaranteed to have or not to have mines.
   */
  public void basicMoveGeneratorCheck() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * This test shows that if there are no squares on the board that for sure
   * have no mines, that the AI will find the space with the lowest probability
   * of a mine.
   */
  public void moveEstimateCheck() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * This test checks similar things as the above tests, but for a tesslated
   * board.
   */
  public void tesselatedMoveGeneratorCheck() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * This test checks similar things as the above tests, but for a rectangular
   * board with uneven tile sizes.
   */
  public void rectangularMoveGeneratorCheck() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * This test checks that the AI can also handle moving its opponent's mines
   * in FSU mode.
   */
  public void FSUMoveGeneratorCheck() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * This test checks that the AI can also handle moving through each level of
   * boards in a layers game.
   */
  public void layersMoveGeneratorCheck() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * This test checks that the AI can also working to maximize its "territory"
   * on a board shared with other players.
   */
  public void territoryMoveGeneratorCheck() {
    fail("Not yet implemented");
  }

  @Test
  /**
   * This test checks that the AI can also handle searching for mines to "race"
   * opponents across a long and skinny board.
   */
  public void raceMoveGeneratorCheck() {
    fail("Not yet implemented");
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
