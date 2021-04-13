public class Team {

    protected String teamName;
    protected int teamID;
    static int counter = 1;
    IO io = new IO();

    protected boolean addedToActiveTournament = false;  // Teams can only be added once because of this bool.

    protected Player player1, player2;

    // Constructer for new team.
    public Team() {
        this.teamID = counter;
        counter++;

        createTeams();
    }

    // Constructor for loaded data.
    public Team(String teamName, int teamID, String player1, int player1ID, String player2, int player2ID) {
        this.teamName = teamName;
        this.teamID = teamID;
        this.player1 = new Player(player1, player1ID);
        this.player2 = new Player(player2, player2ID);
        counter++;   // This solved our ID index issue.


    }

    // If the game manager dont assign a team/player name, the app while then assign a default "Team x".
    // This function also automatically creates two new player objects and assing them as player 1 and 2.
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