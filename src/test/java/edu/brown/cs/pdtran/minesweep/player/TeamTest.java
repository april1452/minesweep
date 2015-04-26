package edu.brown.cs.pdtran.minesweep.player;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.pdtran.minesweep.board.DefaultBoard;
import edu.brown.cs.pdtran.minesweep.tile.Tile;

public class TeamTest {
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
   * Verifies that the constructor for a Team works as planned. Creates a Team
   * and checks that all the inputs specified in the constructor are present in
   * the Team object.
   */
  public void constructorTest() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * Verifies that the score counter adjusts to the changes in Players' scores.
   */
  public void scoreTest() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * Verifies that a team can add or remove players and that the team's
   * contained players change with it.
   */
  public void addRemovePlayerTest() {
    fail("Not yet implemented");
  }
  
  @Test
  /**
   * Verifies that a team from the start is neither a winner not a loser until
   * it is specifically changed.
   */
  public void winnerLoserTest() {
    fail("Not yet implemented");
  }

}
