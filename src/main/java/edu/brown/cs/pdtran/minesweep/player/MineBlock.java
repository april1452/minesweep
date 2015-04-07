package edu.brown.cs.pdtran.minesweep.player;

import java.util.Set;
import java.util.TreeSet;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

/**
 * This class represents a block of tiles that are known to contain a certain
 * number of mines. As such, it contains a set of tiles and an integer of mines
 * contained. These sets can then have other sets be "subtracted" from it to
 * simplify the blocks and localize the mines.
 * @author Clayton Sanford
 *
 */
public class MineBlock {
  
  private Set<Tile> tiles;
  private int numMines;
  
  /**
   * Creates a MineBlock that represents a certain number of mines contained in
   * a set of tiles.
   * @param tiles A Set containing Tile objects that have the possibility of
   * having a mine.
   * @param numMines The total number of mines contained between the tiles.
   */
  public MineBlock(Set<Tile> tiles, int numMines) {
    this.tiles = tiles;
    this.numMines = numMines;
  }
  
  /**
   * Gets the set of tiles surrounding that the mines could be in.
   * @return A copy of the set of tiles.
   */
  public Set<Tile> getTiles() {
    Set<Tile> toReturn = new TreeSet<>();
    toReturn.addAll(tiles);
    return toReturn;
  }
  
  /**
   * Gets the number of mines within the block.
   * @return An integer representing the number of mines in the block.
   */
  public int getNumMines() {
    return numMines;
  }
  
  /**
   * Checks if the current MineBlock "contains" another MineBlock, which is
   * true if all elements of the second MineBlock are in the first one.
   * @param block2 A MineBlock to check if it is contained in the current one.
   * @return A Boolean that evaluates to true if block2 is contained in the
   * current block.
   */
  public Boolean contains(MineBlock block2) {
    for (Tile t: block2.getTiles()) {
      if (!tiles.contains(t)) {
        return false;
      }
    }
    return block2.getNumMines() <= numMines;
  }
  
  /**
   * "Subtracts" a block that is contained in the current block from the
   * current one by removing all common elements from the current block and
   * subtracting the number of mines by the number in block2.
   * @param block2 A MineBlock that is contained in the current block.
   */
  public void subtract(MineBlock block2) {
    for (Tile t: block2.getTiles()) {
      tiles.remove(t);
    }
    numMines -= block2.getNumMines();
  }

}
