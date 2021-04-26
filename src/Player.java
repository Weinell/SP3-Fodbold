public class Player {

    protected String playerName;
    protected int playerID;
    static int counter = 1;

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

    public void createPlayer(String member) {
        String name = fr.getUserInput(member);
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
}
