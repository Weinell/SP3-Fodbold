public class Match {
    private Team team1, team2;  // TODO: change to Team[] = new Team[6]
    private int score1, score2;
    private int matchID;
    private static int counter = 1;
    private int matchRound;
    private boolean matchPlayed;

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

    public Match(int matchID, Team team1, Team team2, int score1, int score2) {
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;

    }

    public Match(int id, int round, boolean played) {

        this.matchID = id;
        this.matchRound = round;
        this.matchPlayed = played;

    }


    @Override
    public String toString() {
        return "Match " + matchID + " " + team1.getTeamName() + " - " + team2.getTeamName();
    }

    public String matchDataToString()   {
        return matchID+","+team1.getTeamName()+","+team1.getTeamID()+","+team2.getTeamName()+","+team2.getTeamID()+ "," + score1 + "," + score2;

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

    public int getMatchRound() {
        return matchRound;
    }

    public boolean getMatchPlayed() {
        return matchPlayed;
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
