public class Team {


    protected String teamName;
    protected int teamID;
    static int counter = 1;
    IO IO = new IO();

    Player player1, player2;


    public Team(String teamName, Player player1, Player player2)    {

        this.teamName = teamName;
        this.teamID = counter;
        counter++;

        this.player1 = player1;
        this.player2 = player2;
    }

    public Team(String teamName)   {

        this.teamName = teamName;
        this.teamID = counter;
        counter++;

        this.player1 = new Player(IO.getUserInput("Member 1: "));
        this.player2 = new Player(IO.getUserInput("Member 2: "));

    }


    @Override
    public String toString() {
        return  "teamName='" + teamName + '\'' +
                ", teamID = " + teamID +
                ", player1 = " + player1 +
                ", Player2 = " + player2;

    }
}
