package edu.brown.cs.pdtran.minesweep.types;

/**
 * Details the type of update sent back to players.
 * @author Clayton Sanford
 */
public enum UpdateType {
  TEAM_ASSIGNMENT, ROOM_UPDATE, BOARD_UPDATE, INIT_BOARD, INIT_INFO,
  INFO_UPDATE, MOVE, VICTORY, DEFEAT, SESSION_DISBAND, ERROR
}
