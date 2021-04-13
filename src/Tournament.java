import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tournament {

    protected String tournamentName;
    protected int tournamentID;
    protected static int counter = 1;


    protected Team[] teamsInTournament; // Changed from teams
    protected boolean tournamentFull;
    protected int numberTeamsInTournament = 0;  // When this hits the max size of the tournament is sets tournamentFull to true

    // Used to lock or unlock certain functions.
    protected boolean tournamentStarted;
    protected boolean tournamentFinished;

    // Each round have an array of matches
    protected Match[] matches8, matches4, matches2;  // Round 1, Quarter Final, Semi Final
    protected Match finalMatch;

    public Tournament(int numbTeams, String tournamentName) {
        this.tournamentName = tournamentName;
        this.tournamentID = counter;
        counter++;
        teamsInTournament = new Team[numbTeams];
        tournamentFull = false;
        tournamentStarted = false;
        tournamentFinished = false;
        this.matches8 = new Match[8];
        this.matches4 = new Match[4];
        this.matches2 = new Match[2];
        this.finalMatch = null;

    }

    // Get team from controller class, and checks if there is room in the tournament before adding it.
    public void addTeamToTournament(Team team) {
        for (int i = 0; i < teamsInTournament.length; i++) {
            if (!tournamentFull) {
                if (teamsInTournament[i] == null) {
                    teamsInTournament[i] = team;
                    numberTeamsInTournament++;
                    System.out.println(team.getTeamName() + " Added");
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
        for(int i = 0; i < numberTeamsInTournament; i++) {
            if (teamsInTournament[i].getTeamName() != null) {
                System.out.println(
                        teamsInTournament[i].getTeamID() + ") " +
                        teamsInTournament[i].getTeamName() + " (" +
                        teamsInTournament[i].getPlayer1().getName() + " and " +
                        teamsInTournament[i].getPlayer2().getName() + ")");
            }
        }
    }

    public Match[] createRound(Team[] teams) {
        Match[] newMatches = new Match[teams.length/2];
        for (int k = 0; k < teams.length/2; k++) {
            newMatches[k] = new Match();
        }
        int i = 0;
        int j = 1;
        for(Match match : newMatches) {
            match.setTeam1(teams[i]);
            match.setTeam2(teams[j]);
            i+=2;
            j+=2;
        }
        return newMatches;
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
}
