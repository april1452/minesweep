package edu.brown.cs.pdtran.minesweep;

import java.io.IOException;

import edu.brown.cs.pdtran.minesweep.metagame.Metagame;

public class Main {

  public static void main(String[] args) {
    try {
      Metagame metagame = new Metagame(4564);
    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }

}
