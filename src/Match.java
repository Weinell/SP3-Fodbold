public class Match {
    protected Team team1, team2;
    protected int matchID;
    protected static int counter = 1;

    public Match() {
        this.team1 = null;
        this.team2 = null;
        this.matchID = counter;
        counter++;
    }

    public Match(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;

    }

    @Override
    public String toString() {
        return "Match " + matchID + " " + team1.getTeamName() + " - " + team2.getTeamName();
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }
}
