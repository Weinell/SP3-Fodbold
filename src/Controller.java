import java.util.ArrayList;

public class Controller {


    protected Tournament tournament;
    protected boolean activeTournament = false;  // When true, current tournament name and numbers of teams are shown in the top of the welcome message.
    public IO io;
    public UI ui;

    protected static ArrayList<Player> players = new ArrayList<>();
    protected static ArrayList<Team> teams = new ArrayList<>(); // Database of all teams. Not necessarily a part of any tournaments yet.
    protected static ArrayList<Match> matches = new ArrayList<>();
    protected static ArrayList<Tournament> tournaments = new ArrayList<>();


    //TODO: localDateTime when starting tournament. Intervals of 15-30 mins per match.

    //TODO: return name of players on winning team. "SELECT Players FROM WinningTeam IN Tournament"

    //ENUM
    enum Datasource{
        DATABASE,
        CSVFILE
    }
    private static Datasource src = Datasource.DATABASE;

    public Controller() {
        // Makes sure the application loads the database of previously added teams.
        loadData();
        ui = new UI(this, io);

        // Distribute the loaded players into their correct teams.
        distributePlayers();

        // Starting the tournament user interface
        ui.welcomeMessage();
    }

    public void loadData(){
        io = getIO();// new FileReader();
        players = io.readPlayerData();
        teams = io.readTeamData();
//        matches = io.readMatchData();   // TODO: Right now readMatchData returns null, which means the loop breaks in ui.eventResult.
    }

    public IO getIO() {
        if(src == Datasource.DATABASE){
            return new DBConnector();
        }else if (src == Datasource.CSVFILE){
            return new FileReader();
        }
        return null;
    }


    // Static so they can get accessed in the loading and saving classes.
    public static Team getTeamByID(int id) {
        for (Team t : teams) {
            if (t.getTeamID() == id) {
                return t;
            }
        }
        System.out.println("There was no team found");
        return null;
    }


    public static Player getPlayerByID(int id) {
        for (Player p : players) {
            if (p.getPlayerID() == id) {
                return p;
            }
        }
        System.out.println("There was no player found");
        return null;
    }


    public static int getTeamIDbyPlayer(int playerID) {
        for (Team t : teams){
            if(t.getPlayer1().getPlayerID()==playerID || t.getPlayer2().getPlayerID()==playerID) {
                return t.getTeamID();
            }
        }
        System.out.println("There was no team found for that player");
        return 0;
    }


    public static Match getMatchByID(int id) {
        for (Match m : matches) {
            if (m.getMatchID() == id) {
                return m;
            }
        }
        System.out.println("There was no match found");
        return null;
    }


    public static Tournament getTeamMatchesByID(int id) {
        for (Tournament t : tournaments) {
            if (t.getTournamentID() == id) {
                return t;
            }
        }
        System.out.println("There was no tournament found");
        return null;
    }


    public static void distributePlayers() {

        for (int i = 0; i < teams.size(); i++) {

            for (int j = 0; j < players.size(); j++) {

                if (teams.get(i).getTeamID() == players.get(j).getTeam_id()) {

                    if (players.get(j).getPlayerID() % 2 != 0) {
                        teams.get(i).setPlayer1(players.get(j));
                    } else {
                        teams.get(i).setPlayer2(players.get(j));
                    }
                }
            }
        }
    }
}