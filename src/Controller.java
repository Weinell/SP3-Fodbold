import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    DBConnector dbc = new DBConnector();

    protected Tournament tournament;
    protected boolean activeTournament = false;  // When true, current tournament name and numbers of teams are shown in the top of the welcome message.
    public static IO io;
    public UI ui;
    protected FileReader fr = new FileReader();


    public static ArrayList<Player> players = new ArrayList<>();
    protected static ArrayList<Team> teams = new ArrayList<>(); // Database of all teams. Not necessarily a part of any tournaments yet.
    protected static ArrayList<Match> matches = new ArrayList<>();
    protected static ArrayList<Tournament> tournaments = new ArrayList<>();




    //ENUM
    enum Datasource{
        DATABASE,
        CSVFILE
    }
    private static Datasource src = Datasource.DATABASE;
    private static String path;



    public void mainApplication() {
        ui = new UI(this, dbc, fr);
        // Makes sure the application loads the database of previously added teams.
        loadData();
        distributePlayers();

        matches = readMatchData();
        ui.welcomeMessage();
    }


    public static void loadData(){
        io = getIO();// new FileReader();
        players = io.readPlayerData();
        teams = io.readTeamData(path);
    }

    public static IO getIO() {
        if(src == Datasource.DATABASE){
            path = null;
            return new DBConnector();
        }else if (src == Datasource.CSVFILE){
            path = "teamData.txt";
            return new FileReader();
        }
        return null;
    }


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

    public static ArrayList<Match> readMatchData() {
        ArrayList<Match> matchList = new ArrayList<>();

        File file = new File("src/matchData.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String[] commaSeparatedValues = scanner.nextLine().split(",");
                int matchID = Integer.parseInt(commaSeparatedValues[0]);
                String matchTeam1Name = commaSeparatedValues[1];
                int matchTeam1ID = Integer.parseInt(commaSeparatedValues[2]);
                String matchTeam2Name = commaSeparatedValues[3];
                int matchTeam2ID = Integer.parseInt(commaSeparatedValues[4]);
                int matchScore1 = Integer.parseInt(commaSeparatedValues[5]);
                int matchScore2 = Integer.parseInt(commaSeparatedValues[6]);


                // We created a new constructor inside the Team class, which assigns the data to the respectable variables.
                matchList.add(new Match(matchID,new Team(matchTeam1Name,matchTeam1ID),new Team(matchTeam2Name,matchTeam2ID), matchScore1, matchScore2 ));
            }
        }

        return matchList;
    }
}