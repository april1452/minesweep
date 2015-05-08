package edu.brown.cs.pdtran.minesweep.websockets;

import java.util.List;

public interface UpdateSender {

  public void sendUpdates(List<Update> updates);

}
