package edu.brown.cs.pdtran.minesweep.metagame;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.brown.cs.pdtran.minesweep.types.UpdateType;

public class Update {

  UpdateType updateType;
  JsonElement data;
  List<String> usersToUpdate;

  public Update(UpdateType updateType, JsonElement data,
      List<String> usersToUpdate) {
    this.updateType = updateType;
    this.data = data;
    this.usersToUpdate = usersToUpdate;
  }

  public String getMessage() {
    JsonObject message = new JsonObject();
    message.addProperty("updateType", updateType.toString());
    message.add("data", data);
    return message.toString();
  }

  public List<String> getUsersToUpdate() {
    return usersToUpdate;
  }

}
