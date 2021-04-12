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

    public Tournament(int numbTeams, String tournamentName) {
        this.tournamentName = tournamentName;
        this.tournamentID = counter;
        counter++;
        teamsInTournament = new Team[numbTeams];
        tournamentFull = false;
        tournamentStarted = false;
        tournamentFinished = false;

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

    public Team[] randomizerOrderOfArray() {
        List<Team> randomList = Arrays.asList(teamsInTournament);

        Collections.shuffle(randomList);

        return randomList.toArray(teamsInTournament);
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
}
