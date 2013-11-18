/**
 * Date: 11/17/13
 * Time: 4:24 PM
 *
 * @author ioanbsu
 */

public class BaseballElimination {

    // create a baseball division from given filename in format specified below

    /**
     * @return number of teams
     */
    public int numberOfTeams() {
        return -1;
    }


    /**
     * @return all teams
     */
    public Iterable<String> teams() {
        return null;
    }

    /**
     * @param team the team name
     * @return number of wins for given team
     */
    public int wins(String team) {
        return -1;
    }

    /**
     * @param team the team name
     * @return number of losses for given team
     */
    public int losses(String team) {
        return -1;
    }

    /**
     * @param team the team name
     * @return number of remaining games for given team
     */
    public int remaining(String team) {
        return -1;
    }

    /**
     * @param team1 the first team name
     * @param team2 the second team name
     * @return number of remaining games between team1 and team2
     */
    public int against(String team1, String team2) {
        return -1;
    }

    /**
     * @param team the team name
     * @return is given team eliminated?
     */
    public boolean isEliminated(String team) {
        return false;
    }

    /**
     * @param team the team name
     * @return subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }
}
