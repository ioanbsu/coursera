import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 11/17/13
 * Time: 4:24 PM
 *
 * @author ioanbsu
 */

public class BaseballElimination {

    private static final String CONFIG_STRING_SPLITTER = " ";
    private int[][] gamesLeftMatrix;

    private Team[] teams;

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    /**
     * @param filename create a baseball division from given filename in format specified below
     */
    public BaseballElimination(String filename) {
        In in = new In(new File(filename));
        if (in.isEmpty()) {
            throw new IllegalArgumentException("Illegal file structure");
        }
        int teamsSize = in.readInt();
        gamesLeftMatrix = new int[teamsSize][teamsSize];
        teams = new Team[teamsSize];

        int winLossMatrixRow = 0;
        in.readLine();
        while (!in.isEmpty()) {
            teams[winLossMatrixRow] = new Team(in.readString(), in.readInt(), in.readInt(), in.readInt());
            for (int winLossMatrixCol = 0; winLossMatrixCol < teamsSize; winLossMatrixCol++) {
                gamesLeftMatrix[winLossMatrixRow][winLossMatrixCol] = in.readInt();
            }
            winLossMatrixRow++;
        }
    }

    /**
     * @return number of teams
     */
    public int numberOfTeams() {
        return teams.length;
    }


    /**
     * @return all teams
     */
    public Iterable<String> teams() {
        List<String> teamsArray = new ArrayList<String>();
        for (Team team : teams) {
            teamsArray.add(team.getTeamName());
        }
        return teamsArray;
    }

    /**
     * @param team the team name
     * @return number of wins for given team
     */
    public int wins(String team) {
        return getTeamByName(team).getWins();
    }

    /**
     * @param team the team name
     * @return number of losses for given team
     */
    public int losses(String team) {
        return getTeamByName(team).getLoss();
    }

    /**
     * @param team the team name
     * @return number of remaining games for given team
     */
    public int remaining(String team) {
        return getTeamByName(team).getLeft();
    }

    /**
     * @param team1 the first team name
     * @param team2 the second team name
     * @return number of remaining games between team1 and team2
     */
    public int against(String team1, String team2) {
        int team1Index = getTeamIndexInArray(team1);
        int team2Index = getTeamIndexInArray(team2);
        return gamesLeftMatrix[team1Index][team2Index];
    }

    /**
     * @param team the team name
     * @return is given team eliminated?
     */
    public boolean isEliminated(String team) {
        int teamIndexInArray = getTeamIndexInArray(team);
        int numberOfTeamsButOne = numberOfTeams() - 1;
        int gamesBetweenTeams = (numberOfTeamsButOne - 1) * numberOfTeamsButOne / 2;
        int totalVertices = 2 + gamesBetweenTeams + numberOfTeamsButOne;
        FlowNetwork teamsFlowNetwork = getTeamsFlowNetwork(teamIndexInArray, numberOfTeamsButOne, gamesBetweenTeams, totalVertices);
        new FordFulkerson(teamsFlowNetwork, 0, totalVertices - 1);
        for (FlowEdge flowEdge : teamsFlowNetwork.adj(0)) {
            if (flowEdge.capacity() != flowEdge.flow()) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param teamStr the team name
     * @return subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String teamStr) {
        if (!isEliminated(teamStr)) {
            return null;
        }
        int teamIndexInArray = getTeamIndexInArray(teamStr);
        int numberOfTeamsButOne = numberOfTeams() - 1;
        int gamesBetweenTeams = (numberOfTeamsButOne - 1) * numberOfTeamsButOne / 2;
        int totalVertices = 2 + gamesBetweenTeams + numberOfTeamsButOne;
        FlowNetwork teamsFlowNetwork = getTeamsFlowNetwork(teamIndexInArray, numberOfTeamsButOne, gamesBetweenTeams, totalVertices);
        List<String> certificateOfElimination = new ArrayList<String>();

        for (FlowEdge flowEdge : teamsFlowNetwork.adj(0)) {
            if (flowEdge.capacity() != 0) {

            }
        }
        for (int i = 0; i < teams.length; i++) {
            if (!teams[i].getTeamName().equals(teamStr)) {
                certificateOfElimination.add(teams[i].getTeamName());
            }
        }
        return certificateOfElimination;
    }


    private int getTeamIndexInArray(String teamName) {
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getTeamName().equals(teamName)) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    private Team getTeamByName(String teamName) {
        int teamIndex = getTeamIndexInArray(teamName);

        if (teamIndex != -1) {
            return teams[teamIndex];
        }
        return null;
    }


    private FlowNetwork getTeamsFlowNetwork(int teamIndexInArray, int numberOfTeamsButOne, int gamesBetweenTeams, int totalVertices) {
        FlowNetwork teamsFlowNetwork = new FlowNetwork(totalVertices);

        int nextVertexIndex = 1;
        for (int i = 0; i < numberOfTeamsButOne; i++) {
            int matrixI = i >= teamIndexInArray ? i + 1 : i;
            for (int j = i + 1; j < numberOfTeamsButOne; j++) {
                int matrixJ = j >= teamIndexInArray ? j + 1 : j;
                teamsFlowNetwork.addEdge(new FlowEdge(0, nextVertexIndex, gamesLeftMatrix[matrixI][matrixJ]));
                teamsFlowNetwork.addEdge(new FlowEdge(nextVertexIndex, gamesBetweenTeams + i + 1, Double.POSITIVE_INFINITY));
                teamsFlowNetwork.addEdge(new FlowEdge(nextVertexIndex, gamesBetweenTeams + j + 1, Double.POSITIVE_INFINITY));
                nextVertexIndex++;
            }
        }
        for (int secondLevelIndex = 0; secondLevelIndex < numberOfTeamsButOne; secondLevelIndex++) {
            int rightTeamIndex = secondLevelIndex >= teamIndexInArray ? secondLevelIndex + 1 : secondLevelIndex;
            int edgeCapacity = Math.max(0, teams[teamIndexInArray].getWins() + teams[teamIndexInArray].getLeft() - teams[rightTeamIndex].getWins());
            teamsFlowNetwork.addEdge(new FlowEdge(gamesBetweenTeams + 1 + secondLevelIndex, totalVertices - 1, edgeCapacity));
        }
        return teamsFlowNetwork;
    }

    private class Team {
        private String teamName;

        private int wins;

        private int loss;

        private int left;

        private Team(String teamName, int wins, int loss, int left) {
            this.teamName = teamName;
            this.wins = wins;
            this.loss = loss;
            this.left = left;
        }

        public String getTeamName() {
            return teamName;
        }

        public int getWins() {
            return wins;
        }

        public int getLoss() {
            return loss;
        }

        public int getLeft() {
            return left;
        }
    }
}
