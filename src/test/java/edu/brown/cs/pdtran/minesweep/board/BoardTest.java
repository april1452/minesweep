package edu.brown.cs.pdtran.minesweep.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

  private Tile[][] dumbBoard;

  @Before
  public void setup() {
    Tile[][] boardTiles = new Tile[10][10];
    for (int i = 0; i < boardTiles.length; i++) {
      for (int j = 0; j < boardTiles[0].length; j++) {
        boardTiles[i][j] = new Tile(false, 0, false, i, j);
      }
    }
    dumbBoard = boardTiles;
  }

  @Test
  public void testDumbBoard() {
    DefaultBoard board =
        new DefaultBoard(Arrays.copyOf(dumbBoard, dumbBoard.length));
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        assertFalse(board.getTile(i, j).hasBeenVisited());
        assertFalse(board.getTile(i, j).isBomb());
      }
    }
    board.makeMove(board.getHeight() / 2, board.getWidth() / 2);
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        assertTrue(board.getTile(i, j).hasBeenVisited());
      }
    }

  }

  @Test
  public void dumbRectangularBoard() {
    RectangularBoard board =
        new RectangularBoard(Arrays.copyOf(dumbBoard, dumbBoard.length));
    System.out.println(Arrays.toString(dumbBoard));
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        assertFalse(board.getTile(i, j).isBomb());
        assertFalse(board.getTile(i, j).hasBeenVisited());
      }
    }
    board.makeMove(board.getHeight() / 2, board.getWidth() / 2);
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        assertTrue(board.getTile(i, j).hasBeenVisited());
      }
    }
  }
}
