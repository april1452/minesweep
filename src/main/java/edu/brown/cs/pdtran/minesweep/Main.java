package edu.brown.cs.pdtran.minesweep;

import java.io.IOException;

import edu.brown.cs.pdtran.minesweep.metagame.Metagame;

/**
 * The entry-point to the program.
 * @author Clayton Snaford
 */
public final class Main {

  private static final int HTTP_PORT = 4686;
  private static final int WEB_SOCKET_PORT = 7777;

  private Main() {
  };

  /**
   * Enters the program.
   * @param args
   *          Commandline arguments.
   */
  public static void main(String[] args) {
    try {
      Metagame metagame = new Metagame(HTTP_PORT, WEB_SOCKET_PORT);
    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }

}
