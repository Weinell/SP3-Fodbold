public class Player {


    protected String playerName;
    protected int playerID;
    static int counter = 1;


    public Player(String name)   {

        this.playerName = name;
        this.playerID = counter;
        counter++;

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
        return "Player = '" + playerName + '\'' +
                ", Player ID = " + playerID;
    }
}
