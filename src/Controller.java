import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    protected Tournament tournament;
    protected boolean activeTournament = false;  // When true, current tournament name and numbers of teams are shown in the top of the welcome message.
    protected IO io = new IO();

    protected static ArrayList<Team> teams = new ArrayList<>(); // Database of all teams. Not necessarily a part of any tournaments yet.

    boolean goBack = false;

    // TODO: maybe just put it into the constructor of the controller class.
    public void mainApplication() {
        // Makes sure the application loads the database of previously added teams.
        teams = readTeamData();
        welcomeMessage();
    }


    // The menu system is divided into different Events: Tournament, Teams, Players. Each event has sub events aswell.
    public void welcomeMessage() {
        System.out.println("\n======================================");
        String activeName = activeTournament ? tournament.getTournamentName() : "No active tournament";
        String activeTeams = activeTournament ? tournament.teamsInTournament() : "";
        System.out.println("\nNext event: " + activeName + "\n" + activeTeams);

        System.out.println("\nWhat do you wish to do? ");
        String input = "";
        input = io.getUserInput("\n" +
                "1) Tournaments " + "\n" +
                "2) Teams " + "\n" +
                "3) Players " + "\n" +
                "4) Quit " + "\n" +

                "\nChoice action: ");
        System.out.println("\n======================================\n");
        getMainEvent(input);
    }

    public void getMainEvent(String input) {
        switch (input) {
            case "1":
                eventTournaments();
                break;
            case "2":
                eventTeams();
                break;
            case "3":
//                eventPlayers();
                System.out.println("Not implemented yet");

                break;
            case "4":
                System.out.println("QUIT!");
                ;
                break;
        }
    }

    // TODO Tournament events. What bout a cancel tournament feature?
    public void eventTournaments() {
        goBack = false;
        while (!goBack) {
            System.out.println("\n======================================");
            System.out.println("\nWhat do you wish to do? ");
            String input = "";
            input = io.getUserInput("\n" +
                    "1) Create new Tournament " + "\n" +
                    "2) Add teams " + "\n" +
                    "3) Overview of added teams " + "\n" +
                    "4) Start Tournament " + "\n" +
                    "5) back " + "\n" +

                    "\nChoice action: ");
            System.out.println("\n======================================\n");
            getTournamentEvent(input);
        }
    }

    public void getTournamentEvent(String input) {
        switch (input) {
            case "1":
                eventNewTournament();
                break;
            case "2":
                eventManageTournament();
                break;
            case "3":
                eventPrintTeams();
                break;
            case "4":
                eventStartTournament();
                break;
            case "5":
                goBack = true;
                welcomeMessage();
        }
    }

    public void eventNewTournament() {
        if (!activeTournament) {
            System.out.println("\nThere is no active tournament");
            String input = "";
            input = io.getUserInput("\nStart new Tournament? Y/N ").toLowerCase();
            if (input.equals("y")) {
                input = "";
                input = io.getUserInput("\nWhats the name of the tournament: ");
                tournament = new Tournament(16, input);
                activeTournament = true;
            } else if (input.equals("n")) {
                System.out.println("\n No new tournament created.");
            } else {
                System.out.println("\nWrong input");
                eventNewTournament();
            }
        } else {
            System.out.println("\nThere is already an active tournament.");
        }
    }

    public void eventManageTournament() {
        if (activeTournament) {
            System.out.println("Add team(s) to tournament, end with 0: ");

            goBack = false;
            while (!goBack) {
                int input = 0;
                input = io.getUserInputInteger("\nInput Team ID to add to tournament: ");
                if(input==0) {
                    goBack = true;
                    eventTournaments();
                }
                for (Team team : teams) {
                    if (team.getTeamID() == input) {
                        if (!team.isAddedToActiveTournament()) {
                            tournament.addTeamToTournament(team);
                            break;
                        } else {
                            System.out.println("\nAlready in the current tournament");
                        }
                    }
                }
            }
        } else {
            System.out.println("\nNo Active tournament, please Create a new first.");
        }
    }

    public void eventPrintTeams() {
        if (activeTournament) {
            tournament.printTeamsInTournament();
        } else {
            System.out.println("\nNo Active tournament, please Create a new first.");
        }
    }

    // TODO: When tournament start, app should create 8 new match objects and put them into an array
    public void eventStartTournament() {
        if (tournament.isTournamentFull() && !tournament.isTournamentStarted()) {   // booleans makes sure you cant start the tournament if there is not enough teams admitted.
            tournament.randomizerOrderOfArray();    // Created a method to randomize the order of the teams, so its unpredictable who will go up against each other
            tournament.setTournamentStarted(true); // preventing app to start the same tournament twice.
            for (Team team : tournament.getTeamsInTournament()) {
                System.out.println(team.getTeamName());
            }
        }
    }

    // TODO Team event
    public void eventTeams() {
        goBack = false;
        while (!goBack) {
            System.out.println("\n======================================");
            System.out.println("\nWhat do you wish to do? ");
            String input = "";
            input = io.getUserInput("\n" +
                    "1) Create new team " + "\n" +
                    "2) Print all teams " + "\n" +
                    "3) Back " + "\n" +

                    "\nChoice action: ");
            System.out.println("\n======================================\n");
            getTeamEvent(input);
        }

    }

    public void getTeamEvent(String input) {
        switch (input) {
            case "1":
                eventCreateTeams();
                break;
            case "2":
                eventManageTeams();
                break;
            case "3":
                goBack = true;
                welcomeMessage();
                break;
        }
    }

    public void eventCreateTeams() {
        teams.add(new Team()); // Creates a new team which then ask for details about team and player names
        io.save(); // Saves to data.txt everytime a new team is created.

    }

    // TODO: So far its only to print a list of all the teams. Maybe we should add an search and edit function
    public void eventManageTeams() {
        for (Team t : teams) {
            System.out.println(t.getTeamID() + ") " + t.getTeamName() + " (" + t.getPlayer1().getName() + " and " + t.getPlayer2().getName() + ")");
        }
    }


    public static ArrayList<Team> readTeamData() {
        ArrayList<Team> teamList = new ArrayList<>();

        File file = new File("src/data.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String[] commaSeperatedValues = scanner.nextLine().split(",");
                String teamName = commaSeperatedValues[0];
                int teamID = Integer.parseInt(commaSeperatedValues[1]);
                String player1 = commaSeperatedValues[2];
                int player1ID = Integer.parseInt(commaSeperatedValues[3]);
                String player2 = commaSeperatedValues[4];
                int player2ID = Integer.parseInt(commaSeperatedValues[5]);

                // We created a new constructor inside the Team class, which assigns the data to the respectable variables.
                teamList.add(new Team(teamName, teamID, player1, player1ID, player2, player2ID));
            }
        }

        return teamList;
    }

    // TODO: Make another read data method with tournament data.
}