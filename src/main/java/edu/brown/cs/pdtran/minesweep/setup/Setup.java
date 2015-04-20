package edu.brown.cs.pdtran.minesweep.setup;

import java.util.ArrayList;
import java.util.List;

/**
 * Receives setup information created by GUI 'Create Game' button. Setup
 * information: game mode (classic, layers, territory, path, FSU), number of
 * matches, board shape & dimensions, teams, players and player info (AI &
 * difficulty vs.human), and each team's lives. Returns all setup information as
 * a Room, which is processed to become a game.
 * @author pdtran
 */
public class Setup {
  /**
   * Set up game specifications and return a room representing those options.
   * Room will be used to create a game.
   * @param specsURL String URL representing game specifications
   * @return Room representing game specifications
   */
  // public static PreRoom setup(String[] specsArr) {
  // // hostID-mode-matches-shape-width-height-lives-[team#][AI/h][name/diff]...
  //
  // int[] dims = {Integer.parseInt(specsArr[4]),
  // Integer.parseInt(specsArr[5])};
  // GameSpecs specs = new GameSpecs(specsArr[1], Integer.parseInt(specsArr[2]),
  // specsArr[3], dims);
  //
  // // Host will always be 1st player on 1st team
  // int hostID = Integer.parseInt(specsArr[0]);
  // List<TeamFormation> teams = createTeams(hostID, specsArr[6], specsArr[7]);
  //
  // return new PreRoom(hostID, specs, teams);
  // }
  //
  // private static List<TeamFormation> createTeams(int host, String lives,
  // String teamSpecs) {
  // List<TeamFormation> teams = new ArrayList<>();
  //
  // // TODO process team spec URL string to create teams
  //
  // return teams;
  public static PreRoom setup(String roomName, GameSpecs specs) {
    List<TeamFormation> teams = new ArrayList<TeamFormation>();

    for (int i = 0; i < specs.getNumTeams(); i++) {
      List<Gamer> gamers = new ArrayList<Gamer>();
      TeamFormation team = new TeamFormation(gamers, specs.getTeamLives());
      teams.add(team);
    }

    return new PreRoom(roomName, specs, teams);
  }
}
