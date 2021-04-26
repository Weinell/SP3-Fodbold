public class Team {

    protected String teamName;
    protected int teamID, teamGoals, teamPoints ;
    static int counter = 1;

    protected FileReader fr = new FileReader();
    protected DBConnector dbc = new DBConnector();

    protected boolean addedToActiveTournament = false;  // Teams can only be added once because of this bool.

    protected Player player1, player2;

    // Constructor for new team.
    public Team() {
        this.teamID = counter;
        counter++;

        createTeams();
        this.teamGoals = 0;
        this.teamPoints = 0;
    }

    // Only used for creating empty teams for the final.
    public Team(String teamName, int teamID) {
        this.teamName = teamName;
        this.teamID = teamID;
    }

    // Constructor for loaded data.
    public Team(String teamName, int teamID, String player1, int player1ID, String player2, int player2ID) {
        this.teamName = teamName;
        this.teamID = teamID;
        this.player1 = new Player(player1, player1ID);
        this.player2 = new Player(player2, player2ID);
        counter++;   // This solved our ID index issue.

        this.teamGoals = 0;
        this.teamPoints = 0;
    }


    public Team(int teamID, String teamName, int teamGoals, int teamPoints){
        this.teamID = teamID;
        this.teamName = teamName;
        this.teamGoals = teamGoals;
        this.teamPoints = teamPoints;
        this.player1 = new Player("player1", 1);
        this.player2 = new Player("player2", 2);

        counter++;
        System.out.println("new team created");
    }

    // If the game manager don't assign a team/player name, the app will then assign a default "Team x".
    // This function also automatically creates two new player objects and assign them as player 1 and 2.
    public void createTeams(){
        String name = fr.getUserInput("Insert Team name: ");
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

    public int getTeamGoals() { return teamGoals; }

    public int getTeamPoints() { return teamPoints; }

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