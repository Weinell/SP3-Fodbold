public class Match {
    protected Team team1, team2;
    protected int score1, score2;
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

    public Match(int matchID, Team team1, Team team2) {
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;

    }


    @Override
    public String toString() {
        return "Match " + matchID + " " + team1.getTeamName() + " - " + team2.getTeamName();
    }

    public String matchDataToString()   {
        return matchID+","+team1.getTeamName()+","+team1.getTeamID()+","+team2.getTeamName()+","+team2.getTeamID();

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

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }
}
