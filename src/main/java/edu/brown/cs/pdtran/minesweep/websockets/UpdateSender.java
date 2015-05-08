package edu.brown.cs.pdtran.minesweep.websockets;

import java.util.List;

/**
 * An interface. that manages the sending of updates to players through
 * websockets.
 * @author Clayton Sanford
 */
public interface UpdateSender {

  /**
   * Sends Updates objects the players who need to receive them.
   * @param updates Updates with specified messages and recipients.
   */
  void sendUpdates(List<Update> updates);

}
