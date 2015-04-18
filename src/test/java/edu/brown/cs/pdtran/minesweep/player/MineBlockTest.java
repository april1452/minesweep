package edu.brown.cs.pdtran.minesweep.player;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

public class MineBlockTest {

  @Test
  /**
   * Verifies that the data set with the constructor matches the data grabbed
   * by getters.
   */
  public void constructorTest() {
    Tile tile1 = new Tile(false, 3, false, 4, 5);
    Tile tile2 = new Tile(false, 2, false, 5, 5);
    Tile tile3 = new Tile(false, 1, false, 6, 5);
    Tile tile4 = new Tile(false, 1, false, 6, 6);
    Set<Tile> tiles = new TreeSet<>();
    tiles.add(tile1);
    tiles.add(tile2);
    tiles.add(tile3);
    tiles.add(tile4);
    MineBlock block1 = new MineBlock(tiles, 2);
    assertTrue(block1.getNumMines() == 2);
    assertTrue(block1.getTiles().equals(tiles));
  }
  
  @Test
  /**
   * Verifies that the contains method works when all points of one set of
   * tiles are contained in another.
   */
  public void containsTest() {
    Tile tile1 = new Tile(false, 3, false, 4, 5);
    Tile tile2 = new Tile(false, 2, false, 5, 5);
    Tile tile3 = new Tile(false, 1, false, 6, 5);
    Tile tile4 = new Tile(false, 1, false, 6, 6);
    Set<Tile> tiles1 = new TreeSet<>();
    tiles1.add(tile1);
    tiles1.add(tile2);
    tiles1.add(tile3);
    MineBlock block1 = new MineBlock(tiles1, 2);
    Set<Tile> tiles2 = new TreeSet<>();
    tiles2.add(tile2);
    tiles2.add(tile3);
    MineBlock block2 = new MineBlock(tiles2, 1);
    Set<Tile> tiles3 = new TreeSet<>();
    tiles3.add(tile3);
    tiles3.add(tile2);
    MineBlock block3 = new MineBlock(tiles3, 1);
    Set<Tile> tiles4 = new TreeSet<>();
    tiles4.add(tile2);
    tiles4.add(tile3);
    tiles4.add(tile4);
    MineBlock block4 = new MineBlock(tiles4, 1);
    assertTrue(block1.contains(block2));
    assertTrue(!block2.contains(block1));
    assertTrue(block2.contains(block3));
    assertTrue(block3.contains(block2));
    assertTrue(block4.contains(block3));
    assertTrue(block3.contains(block4));
    assertTrue(!block1.contains(block4));
    assertTrue(!block4.contains(block1));
  }

}