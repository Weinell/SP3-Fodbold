import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tournament {

    private String tournamentName;
    private int tournamentID;
    private static int counter = 1;

    protected Team[] teamsInTournament; // Changed from teams
    protected boolean tournamentFull, tournamentStarted, tournamentFinished; // Important booleans to make sure user don't tries to access unavailable stages of the application
    protected int numberTeamsInTournament = 0;  // When this hits the max size of the tournament is sets tournamentFull to true

    // Each round have an array of matches
    protected Match[] matches8, matches4, matches2;  // Round 1, Quarter Final, Semi Final
    protected Match finalMatch;
    private int round; // 4 rounds in total

    public Tournament(int numbTeams, String tournamentName) {
        this.tournamentName = tournamentName;
        this.tournamentID = counter;
        counter++;
        teamsInTournament = new Team[numbTeams];
        tournamentFull = false;
        tournamentStarted = false;
        tournamentFinished = false;
        round = 1;
        this.matches8 = new Match[8];
        this.matches4 = new Match[4];
        this.matches2 = new Match[2];
        this.finalMatch = new Match(new Team("first", 0), new Team("second", 0));
    }

    // Will be used for loading from DBC
    public Tournament(int id, int match_id, int team_id1, int team_id2, int score, int points) {

    }

    // Get team from controller class, and checks if there is room in the tournament before adding it.
    public void addTeamToTournament(Team team) {
        for (int i = 0; i < teamsInTournament.length; i++) {
            if (!tournamentFull) {
                if (teamsInTournament[i] == null) {
                    teamsInTournament[i] = team;
                    numberTeamsInTournament++;
                    System.out.println(team.getTeamName() + " Added. Add more teams or end with 0");
                    team.setAddedToActiveTournament(true);
                    break;
                }
            }
        }
        // Checks after each adding if the tournament is full then.
        if (numberTeamsInTournament == teamsInTournament.length) {
            tournamentFull = true;
        }
    }

    public void randomizerOrderOfArray() {
        List<Team> randomList = Arrays.asList(teamsInTournament);
        Collections.shuffle(randomList);
        randomList.toArray(teamsInTournament);
    }

    // Monitoring the number of participants of this tournament
    public String teamsInTournament() {
        return numberTeamsInTournament + " Teams out of " + teamsInTournament.length;
    }

    public void printTeamsInTournament() {
        System.out.println("Teams in " + tournamentName + ":");
        for (int i = 0; i < numberTeamsInTournament; i++) {
            if (teamsInTournament[i].getTeamName() != null) {
                System.out.println(
                        teamsInTournament[i].getTeamID() + ") " +
                                teamsInTournament[i].getTeamName() + " (" +
                                teamsInTournament[i].getPlayer1().getPlayerName() + " and " +
                                teamsInTournament[i].getPlayer2().getPlayerName() + ")");
            }
        }
    }

    // This should work for any round
    public Match[] createRound(Team[] teams) {
        Match[] newMatches = new Match[teams.length / 2];
        for (int k = 0; k < teams.length / 2; k++) {
            newMatches[k] = new Match();
        }
        int i = 0;
        int j = 1;
        for (Match match : newMatches) {
            match.setTeam1(teams[i]);
            match.setTeam2(teams[j]);
            i += 2;
            j += 2;
        }
        return newMatches;
    }

    public Match createFinalRound(Team[] teams) {
        Match newMatches = new Match();
        newMatches.setTeam1(teams[0]);
        newMatches.setTeam2(teams[1]);
        return newMatches;
    }

    public Team[] resultOfMatch8() {
        Team[] tempWinners = new Team[8];
        for (int i = 0; i < matches8.length; i++) {
            System.out.println("\n" + matches8[i].getTeam1().getTeamName() + " vs. " + matches8[i].getTeam2().getTeamName() + "\n");
            int s1 = UI.getUserInputInteger("Enter " + matches8[i].getTeam1().getTeamName() + " goals: ");
            int s2 = UI.getUserInputInteger("Enter " + matches8[i].getTeam2().getTeamName() + " goals: ");
            matches8[i].setScore1(s1);
            matches8[i].getTeam1().increaseTeamGoals(s1);
            matches8[i].setScore2(s2);
            matches8[i].getTeam2().increaseTeamGoals(s2);
            System.out.println(matches8[i].getScore1() + " - " + matches8[i].getScore2());
            if (s1 > s2) {
                tempWinners[i] = matches8[i].getTeam1();
                matches8[i].getTeam1().increaseTeamPoints(2);
            } else {
                tempWinners[i] = matches8[i].getTeam2();
                matches8[i].getTeam2().increaseTeamPoints(2);
            }
        }
        return tempWinners;
    }

    public Team[] resultOfMatch4() {
        Team[] tempWinners = new Team[4];
        for (int i = 0; i < matches4.length; i++) {
            System.out.println("\n" + matches4[i].getTeam1().getTeamName() + " vs. " + matches4[i].getTeam2().getTeamName() + "\n");
            int s1 = UI.getUserInputInteger("Enter " + matches4[i].getTeam1().getTeamName() + " goals: ");
            int s2 = UI.getUserInputInteger("Enter " + matches4[i].getTeam2().getTeamName() + " goals: ");
            matches4[i].setScore1(s1);
            matches4[i].getTeam1().increaseTeamGoals(s1);
            matches4[i].setScore2(s2);
            matches4[i].getTeam2().increaseTeamGoals(s2);
            System.out.println(matches4[i].getScore1() + " - " + matches4[i].getScore2());
            if (s1 > s2) {
                tempWinners[i] = matches4[i].getTeam1();
                matches4[i].getTeam1().increaseTeamPoints(2);
            } else {
                tempWinners[i] = matches4[i].getTeam2();
                matches4[i].getTeam2().increaseTeamPoints(2);
            }
        }
        return tempWinners;
    }

    public Team[] resultOfMatch2() {
        Team[] tempWinners = new Team[2];
        for (int i = 0; i < matches2.length; i++) {
            System.out.println("\n" + matches2[i].getTeam1().getTeamName() + " vs. " + matches2[i].getTeam2().getTeamName() + "\n");
            int s1 = UI.getUserInputInteger("Enter " + matches2[i].getTeam1().getTeamName() + " goals: ");
            int s2 = UI.getUserInputInteger("Enter " + matches2[i].getTeam2().getTeamName() + " goals: ");
            matches2[i].setScore1(s1);
            matches2[i].getTeam1().increaseTeamGoals(s1);
            matches2[i].setScore2(s2);
            matches2[i].getTeam2().increaseTeamGoals(s2);
            System.out.println(matches2[i].getScore1() + " - " + matches2[i].getScore2());
            if (s1 > s2) {
                tempWinners[i] = matches2[i].getTeam1();
                matches2[i].getTeam1().increaseTeamPoints(2);
            } else {
                tempWinners[i] = matches2[i].getTeam2();
                matches2[i].getTeam2().increaseTeamPoints(2);
            }
        }
        return tempWinners;
    }

    public Team resultOfFinal() {
        Team tempWinners = null;
        System.out.println("\n" + finalMatch.getTeam1().getTeamName() + " vs. " + finalMatch.getTeam2().getTeamName() + "\n");
        int s1 = UI.getUserInputInteger("Enter " + finalMatch.getTeam1().getTeamName() + " goals: ");
        int s2 = UI.getUserInputInteger("Enter " + finalMatch.getTeam2().getTeamName() + " goals: ");
        finalMatch.setScore1(s1);
        finalMatch.getTeam1().increaseTeamGoals(s1);
        finalMatch.setScore2(s2);
        finalMatch.getTeam2().increaseTeamGoals(s2);
        System.out.println(finalMatch.getScore1() + " - " + finalMatch.getScore2());
        if (s1 > s2) {
            tempWinners = finalMatch.getTeam1();
            finalMatch.getTeam1().increaseTeamPoints(2);
        } else {
            tempWinners = finalMatch.getTeam2();
            finalMatch.getTeam2().increaseTeamPoints(2);
        }

        return tempWinners;
    }

    public void clearTeamsInActiveTournament() {
        for(Team t : teamsInTournament) {
            t.setAddedToActiveTournament(false);
        }
    }


    public boolean isTournamentFull() {
        return tournamentFull;
    }

    public boolean isTournamentStarted() {
        return tournamentStarted;
    }

    public void setTournamentStarted(boolean tournamentStarted) {
        this.tournamentStarted = tournamentStarted;
    }

    public boolean isTournamentFinished() {
        return tournamentFinished;
    }

    public void setTournamentFinished(boolean tournFinished) {
        this.tournamentFinished = tournFinished;
    }


    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public Team[] getTeamsInTournament() {
        return teamsInTournament;
    }

    public void setTeamsInTournament(Team[] teams) {
        this.teamsInTournament = teams;
    }

    public Match[] getMatches8() {
        return matches8;
    }

    public void setMatches8(Match[] matches8) {
        this.matches8 = matches8;
    }

    public Match[] getMatches4() {
        return matches4;
    }

    public void setMatches4(Match[] matches4) {
        this.matches4 = matches4;
    }

    public Match[] getMatches2() {
        return matches2;
    }

    public void setMatches2(Match[] matches2) {
        this.matches2 = matches2;
    }

    public Match getFinalMatch() {
        return finalMatch;
    }

    public void setFinalMatch(Match finalMatch) {
        this.finalMatch = finalMatch;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
