package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.brown.cs.pdtran.minesweep.types.UpdateType;

/**
 * Sends information to players based on events that occur in the scope of
 * the game.
 * @author Clayton Sanford
 */
public class Update {

  UpdateType updateType;
  JsonElement data;
  List<String> usersToUpdate;

  /**
   * Constructs an update to be send out.
   * @param updateType An enum representing the type of update.
   * @param data The JSON data to be carried by the update.
   * @param usersToUpdate The List of user IDs who need to receive the
   *        update.
   */
  public Update(UpdateType updateType, JsonElement data,
      List<String> usersToUpdate) {
    this.updateType = updateType;
    this.data = data;
    this.usersToUpdate = usersToUpdate;
  }

  /**
   * Retrieves the message corresponding a an update to be sent.
   * @return The message as a string to be added, which is a JSON.
   */
  public String getMessage() {
    JsonObject message = new JsonObject();
    message.addProperty("updateType", updateType.toString());
    message.add("data", data);
    return message.toString();
  }

  /**
   * Gets the users that need to be updated by an Update.
   * @return A list of strings corresponding to each user.
   */
  public List<String> getUsersToUpdate() {
    return usersToUpdate;
  }

}
