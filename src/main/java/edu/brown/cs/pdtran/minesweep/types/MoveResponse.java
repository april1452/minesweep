package edu.brown.cs.pdtran.minesweep.types;

/**
 * Represents the response from the server to the players regarding the
 * effects of the given move.
 * @author Clayton Sanford
 */
public enum MoveResponse {
  MINE, NOT_MINE, FLAG, INVALID
}
