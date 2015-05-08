package edu.brown.cs.pdtran.minesweep.board;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.brown.cs.pdtran.minesweep.tile.Tile;

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

  @Test
  public void dumbTriangularBoard() {
    TriangularBoard board =
        new TriangularBoard(Arrays.copyOf(dumbBoard, dumbBoard.length));
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


  @Test
  public void dumbHexagonalBoard() {
    HexagonalBoard board =
        new HexagonalBoard(Arrays.copyOf(dumbBoard, dumbBoard.length));
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

  @Test
  public void lossTest() {
    DefaultBoard board = new DefaultBoard();
    checkLoss(board);
  }

  private void checkLoss(DefaultBoard board) {
    Tile t = findBomb(board);
    board.makeMove(t.getRow(), t.getColumn());
    assertTrue(board.isGameOver());
    assertTrue(board.isLosingBoard());
    assertFalse(board.isWinningBoard());
  }

  @Test
  public void lossTestRect() {
    RectangularBoard board = new RectangularBoard();
    checkLoss(board);
  }

  @Test
  public void lossTestTri() {
    TriangularBoard board = new TriangularBoard();
    checkLoss(board);
  }

  @Test
  public void lossTestEngtangledBoard() {
    EntangledBoard board = new EntangledBoard();
    checkLoss(board);
  }

  @Test
  public void invalidMove() {
    DefaultBoard board = new DefaultBoard();
    checkInvalidMove(board);
  }

  @Test
  public void invalidMoveRect() {
    DefaultBoard board = new RectangularBoard();
    checkInvalidMove(board);
  }

  @Test
  public void invalidMoveTri() {
    DefaultBoard board = new TriangularBoard();
    checkInvalidMove(board);
  }

  @Test
  public void invalidMoveEntangled() {
    EntangledBoard board = new EntangledBoard();
    checkInvalidMove(board);
  }

  @Test
  public void cloneTest() {
    DefaultBoard board = new DefaultBoard();
    DefaultBoard board2 = board.clone();
    assertTrue(board != board2);
    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        assertTrue(board.getTile(row, col) != board2.getTile(row, col));
      }
    }
  }

  @Test
  public void cloneTestRec() {
    RectangularBoard board = new RectangularBoard();
    RectangularBoard board2 = board.clone();
    assertTrue(board != board2);
    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        assertTrue(board.getTile(row, col) != board2.getTile(row, col));
      }
    }
  }

  @Test
  public void cloneTestTri() {
    TriangularBoard board = new TriangularBoard();
    TriangularBoard board2 = board.clone();
    assertTrue(board != board2);
    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        assertTrue(board.getTile(row, col) != board2.getTile(row, col));
      }
    }
  }

  @Test
  public void cloneEntangledBoard() {
    EntangledBoard board = new EntangledBoard();
    EntangledBoard board2 = board.clone();
    assertTrue(board != board2);
    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        assertTrue(board.getTile(row, col) != board2.getTile(row, col));
      }
    }
  }

  private void checkInvalidMove(DefaultBoard board) {
    board.makeMove(-1, -1);
    board.makeMove(board.getWidth() * 2, board.getHeight() * 2);
    assertFalse(board.isWithinBoard(-1, -1));
    assertFalse(board
        .isWithinBoard(board.getWidth() + 1, board.getHeight() + 1));
  }

  private Tile findBomb(DefaultBoard board) {
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        Tile t = board.getTile(i, j);
        if (t.isBomb()) {
          return t;
        }
      }
    }
    return null;
  }
}
