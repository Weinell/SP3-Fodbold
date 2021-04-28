public class Player {

    protected String playerName;
    protected int playerID;
    static int counter = 1;
    protected int team_id;

    protected FileReader fr = new FileReader();

    public Player(String member)   {
        this.playerID = counter;
        counter++;

        createPlayer(member);
    }

    public Player(String name, int playerID)   {
        this.playerID = playerID;
        this.playerName = name;
        counter++;
    }

    public Player(int id, int team_id, String playerName)   {

        this.playerID = id;
        this.team_id = team_id;
        this.playerName = playerName;

    }

    public void createPlayer(String member) {
        String name = UI.getUserInput(member);
        if(!name.equals("")) {
            this.playerName = name;
        } else {
            this.playerName = "Unknown name";  // Unknown if nothing is selected. We don't want empty fields in our data files.
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int ID) {
        this.playerID = ID;
    }

    @Override
    public String toString() {
        return playerName +
                "," + playerID;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }


}
