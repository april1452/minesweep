package edu.brown.cs.pdtran.minesweep.player;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.pdtran.minesweep.board.DefaultBoard;
import edu.brown.cs.pdtran.minesweep.tile.Tile;

public class AIPlayerTest {
  private DefaultBoard simpleBoard;
  private Tile a1;
  private Tile a2;
  private Tile a3;
  private Tile a4;
  private Tile b1;
  private Tile b2;
  private Tile b3;
  private Tile b4;
  private Tile c1;
  private Tile c2;
  private Tile c3;
  private Tile c4;
  private Tile d1;
  private Tile d2;
  private Tile d3;
  private Tile d4;
  
  
  @Before
  public void setUp() throws Exception {
    a1 = new Tile(true, 0, false, 0, 0);
    a2 = new Tile(false, 1, true, 1, 0);
    a3 = new Tile(false, 0, true, 2, 0);
    a4 = new Tile(false, 0, true, 3, 0);
    b1 = new Tile(false, 3, false, 0, 1);
    b2 = new Tile(false, 3, true, 1, 1);
    b3 = new Tile(false, 1, true, 2, 1);
    b4 = new Tile(false, 0, true, 3, 1);
    c1 = new Tile(true, 1, false, 0, 2);
    c2 = new Tile(true, 2, false, 1, 2);
    c3 = new Tile(false, 2, true, 2, 2);
    c4 = new Tile(false, 1, true, 3, 2);
    d1 = new Tile(false, 2, false, 0, 3);
    d2 = new Tile(false, 3, false, 1, 3);
    d3 = new Tile(true, 1, false, 2, 3);
    d4 = new Tile(false, 1, false, 3, 3);
    Tile[][] tileArray = {{a1, a2, a3, a4}, {b1, b2, b3, b4},
        {c1, c2, c3, c4}, {d1, d2, d3, d4}};
    simpleBoard = new DefaultBoard(tileArray);
  }

  @Test
  /**
   * Uses several pre-established AIGamers and shows that the AIPlayers created
   * by them have the same characteristics (username, difficulty)
   */
  public void newAIPlayerTest() {
    AIPlayer c = new AIPlayer("Clay", 5, simpleBoard);
    assertTrue(c.getUsername().equals("Clay"));
    assertTrue(c.getScore() == 0);
  }
  
  @Test
  /**
   * Verifies that a simple change in score is recorded and that the updated
   * score reflects it.
   */
  public void scoreTester() {
    AIPlayer p = new AIPlayer("Clay", 5, simpleBoard);
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
    AIPlayer p = new AIPlayer("Clay", 5, simpleBoard);
    MovePossibility mpA1 = new MovePossibility(a1, .5);
    MovePossibility mpB1 = new MovePossibility(b1, .5);
    MovePossibility mpC1 = new MovePossibility(c1, 1);
    MovePossibility mpC2 = new MovePossibility(c2, 1);
    MovePossibility mpD2 = new MovePossibility(d2, 0);
    MovePossibility mpD3 = new MovePossibility(d3, .5);
    MovePossibility mpD4 = new MovePossibility(d4, .5);
    /*for (MovePossibility m: p.getCertainMine()) {
      System.out.println(m.getXCoord() + " " + m.getYCoord() + " " + m.getMineProbability());
    }
    for (MovePossibility m: p.getCertainNotMine()) {
      System.out.println(m.getXCoord() + " " + m.getYCoord() + " " + m.getMineProbability());
    }
    for (MovePossibility m: p.getUncertain()) {
      System.out.println(m.getXCoord() + " " + m.getYCoord() + " " + m.getMineProbability());
    }*/
    assertTrue(p.getCertainMine().contains(mpC1));
    assertTrue(p.getCertainMine().contains(mpC2));
    assertTrue(p.getCertainNotMine().contains(mpD2));
    assertTrue(p.getUncertain().contains(mpA1));
    assertTrue(p.getUncertain().contains(mpB1));
    assertTrue(p.getUncertain().contains(mpD3));
    assertTrue(p.getUncertain().contains(mpD4));
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
