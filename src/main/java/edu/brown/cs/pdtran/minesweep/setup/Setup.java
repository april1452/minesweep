package edu.brown.cs.pdtran.minesweep.setup;

import java.util.ArrayList;
import java.util.List;

/**
 * Receives setup information from URL created by GUI 'Create Game' button.
 * Setup information: game mode (classic, layers, territory, path, FSU), number
 * of matches, board shape & dimensions, teams, players and player info (AI &
 * difficulty vs.human), and each team's lives. Returns all setup information as
 * a Room, which is processed to become a game.
 *
 * @author pdtran
 */
public class Setup {
  /**
   * Set up game specifications and return a room representing those options.
   * Room will be used to create a game.
   *
   * @param specsURL
   *          String URL representing game specifications
   * @return Room representing game specifications
   */
  public static PreRoom setup(String roomName, String hostId, String hostName,
    GameSpecs specs) {
    // Host will always be 1st player on 1st team
    List<TeamFormation> teams = new ArrayList<TeamFormation>();

    for (int i = 0; i < specs.getNumTeams(); i++) {
      List<Gamer> gamers = new ArrayList<Gamer>();
      if (i == 0) {
        gamers.add(new HumanGamer(hostId, hostName));
      }
      TeamFormation team = new TeamFormation(gamers, specs.getTeamLives());
      teams.add(team);
    }

    return new PreRoom(roomName, specs, teams);
  }
}
