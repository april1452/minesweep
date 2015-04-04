package edu.brown.cs.pdtran.minesweep.metagame;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

abstract class Session {

  private ServerSocket s;
  private List<Socket> clients;

  public Session(ServerSocket s) {
    this.s = s;
  }
}
