public class Player {


    protected String playerName;
    protected int playerID;
    static int counter = 1;

    IO io = new IO();


    public Player(String member)   {
        this.playerID = counter;
        counter++;

        createPlayer(member);

    }
    public Player(String name, int playerID)   {
        this.playerID = playerID;
        this.playerName = name;
    }


    public void createPlayer(String member) {
        String name = io.getUserInput(member);
        if(!name.equals("")) {
            this.playerName = name;
        } else {
            this.playerName = "Unknown name";
        }

    }

    public String getName() {
        return playerName;
    }

    public void setName(String name) {
        this.playerName = name;
    }

    public int getID() {
        return playerID;
    }

    public void setID(int ID) {
        this.playerID = ID;
    }


    @Override
    public String toString() {
        return playerName +
                "," + playerID;
    }
}
