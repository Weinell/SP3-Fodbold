import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tournament {

    protected String tournamentName;
    protected int tournamentID;
    protected static int counter = 1;


    protected Team[] teams;
    protected boolean tournFull;
    protected int numbTournTeams = 0;

    protected boolean tournamentStarted;
    protected boolean tournFinished;

    public Tournament(int numbTeams, String tournamentName) {
        this.tournamentName = tournamentName;
        this.tournamentID = counter;
        counter++;
        teams = new Team[numbTeams];
        tournFull = false;
        tournamentStarted = false;
        tournFinished = false;

    }

    public void addTeamToTournament(Team team) {

        for (int i = 0; i < teams.length; i++) {
            if (!tournFull) {
                if (teams[i] == null) {
                    teams[i] = team;
                    numbTournTeams++;
                    System.out.println(team.getTeamName() + " Added");
                    team.setAddedToActiveTournament(true);
                    break;
                }
            }

        }
        if (numbTournTeams == teams.length) {
            tournFull = true;
        }

    }

    public Team[] randomizerOrderOfArray() {
        List<Team> randomList = Arrays.asList(teams);

        Collections.shuffle(randomList);

        return randomList.toArray(teams);
    }




    // Monitoring the number of participants of this tournament
    public String teamsInTournament() {
        return numbTournTeams + " Teams out of " + teams.length;

    }

    public boolean isTournFull() {
        return tournFull;
    }

    public boolean isTournamentStarted() {
        return tournamentStarted;
    }

    public void setTournamentStarted(boolean tournamentStarted) {
        this.tournamentStarted = tournamentStarted;
    }

    public boolean isTournFinished() {
        return tournFinished;
    }

    public void setTournFinished(boolean tournFinished) {
        this.tournFinished = tournFinished;
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

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }
}
