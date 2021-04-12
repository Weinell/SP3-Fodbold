public class Team {

    protected String teamName;
    protected int teamID;
    static int counter = 1;
    IO io = new IO();

    protected boolean addedToActiveTournament = false;

    protected Player player1, player2;

    public Team() {
        this.teamID = counter;
        counter++;

        createTeams();
    }

    public Team(String teamName, int teamID, String player1, int player1ID, String player2, int player2ID) {
        this.teamName = teamName;
        this.teamID = teamID;
        this.player1 = new Player(player1, player1ID);
        this.player2 = new Player(player2, player2ID);


    }

    public void createTeams(){
        String name = io.getUserInput("Insert Team name: ");
        if(!name.equals("")) {
            this.teamName = name;
        } else {
            this.teamName = "Team " + teamID;
        }

        System.out.println("\nTeam: " + teamName);
        this.player1 = new Player("Member 1: ");
        this.player2 = new Player("Member 2: ");
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    @Override
    public String toString() {
        return  teamName +
                "," + teamID +
                "," + player1 +
                "," + player2;

    }

    public boolean isAddedToActiveTournament() {
        return addedToActiveTournament;
    }

    public void setAddedToActiveTournament(boolean addedToActiveTournament) {
        this.addedToActiveTournament = addedToActiveTournament;
    }
}