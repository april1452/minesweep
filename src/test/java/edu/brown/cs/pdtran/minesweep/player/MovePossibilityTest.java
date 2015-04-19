package edu.brown.cs.pdtran.minesweep.player;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

public class MovePossibilityTest {

  @Test
  public void basicFunctionality() {
    Tile tile = new Tile(false, 2, false, 4, 3);
    MovePossibility mp = new MovePossibility(tile, .5);
    assertTrue(mp.getXCoord() == 4);
    assertTrue(mp.getYCoord() == 3);
    assertTrue(mp.getMineProbability() == .5);
    mp.setMineProbability(.6);
    assertTrue(mp.getMineProbability() == .6);
    assertTrue(mp.getTile().equals(tile));
    assertTrue(mp.equals(new MovePossibility(tile, .6)));
    assertTrue(!mp.equals(new MovePossibility(tile, .5)));
  }

}
