import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    protected Tournament tournament;
    protected boolean activeTournament = false;  // When true, current tournament name and numbers of teams are shown in the top of the welcome message.
    protected IO io = new IO();

    protected static ArrayList<Team> teams = new ArrayList<>(); // Database of all teams. Not necessarily a part of any tournaments yet.

    boolean goBack = false;  // Used inside the menus.

    // TODO: maybe just put it into the constructor of the controller class.
    public void mainApplication() {
        // Makes sure the application loads the database of previously added teams.
        teams = readTeamData();
        welcomeMessage();
    }


    // The menu system is divided into different Events: Tournament, Teams, Players. Each event has sub events as well.
    public void welcomeMessage() {
        System.out.println("\n======================================");
        String activeName = activeTournament ? tournament.getTournamentName() : "No active tournament";
        String activeTeams = activeTournament ? tournament.teamsInTournament() : "";
        System.out.println("\nNext event: " + activeName + "\n" + activeTeams);
        String tipForUser = activeTournament ? "Tip: Add existent teams (Press 1) or create new ones (Press 2)" : "Tip: Create new Tournament (Press 1)";
        System.out.println("\n" + tipForUser);

        System.out.println("\nWhat do you wish to do? ");
        String input = io.getUserInput("\n" +
                "1) Tournaments " + "\n" +
                "2) Teams " + "\n" +
                "3) Quit " + "\n" +

                "\nChoose action: ");
        System.out.println("\n======================================\n");
        getMainEvent(input);
    }

    public void getMainEvent(String input) {
        switch (input) {
            case "1" -> eventTournaments();
            case "2" -> eventTeams();
            case "3" -> System.exit(0);

        }
    }

    public void eventTournaments() {
        goBack = false;
        while (!goBack) {
            System.out.println("\n======================================");

            String tipForUser;
            if (!activeTournament) {
                tipForUser = "Tip: Create new Tournament (Press 1)";
            } else if (!tournament.isTournamentFull()) {
                tipForUser = "Tip: Add existing teams (Press 2) or go back and create new teams (Press 7)";
            } else if (tournament.isTournamentFull() && !tournament.isTournamentStarted()) {
                tipForUser = "Tip: Start tournament (Press 4)";
            } else if (tournament.isTournamentStarted()) {
                tipForUser = "Tip: Input the results of the matches (Press 6)";
            } else {
                tipForUser = "The tournament is over. Thank you to all participants";
            }

            System.out.println("\n" + tipForUser);

            System.out.println("\nWhat do you wish to do? ");
            String input = io.getUserInput("\n" +
                    "1) Create new Tournament " + "\n" +
                    "2) Add teams " + "\n" +
                    "3) Overview of added teams " + "\n" +
                    "4) Start Tournament " + "\n" +
                    "5) Print fixtures " + "\n" +
                    "6) Results " + "\n" +
                    "7) Back " + "\n" +

                    "\nChoose action: ");
            System.out.println("\n======================================\n");
            getTournamentEvent(input);
        }
    }

    public void getTournamentEvent(String input) {
        switch (input) {
            case "1" -> eventNewTournament();
            case "2" -> eventManageTournament();
            case "3" -> eventPrintTeams();
            case "4" -> eventStartTournament();
            case "5" -> eventPrintFixtures();
            case "6" -> eventResults();
            case "7" -> {
                goBack = true;
                welcomeMessage();
            }
        }
    }


    // All Tournament events

    public void eventNewTournament() {
        if (!activeTournament) {
            System.out.println("\nThere is no active tournament");
            String input = io.getUserInput("\nStart new Tournament? Y/N ").toLowerCase();
            if (input.equals("y")) {
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
            while (!goBack && !tournament.isTournamentFull()) {
                int input = io.getUserInputInteger("\nInput Team ID to add to tournament: ");
                if (input == 0) {
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
        if (tournament != null) {
            if (tournament.isTournamentFull() && !tournament.isTournamentStarted()) {   // booleans makes sure you cant start the tournament if there is not enough teams admitted.
                tournament.randomizerOrderOfArray();    // Created a method to randomize the order of the teams, so its unpredictable who will go up against each other
                tournament.setTournamentStarted(true); // preventing app to start the same tournament twice.

                tournament.setMatches8(tournament.createRound(tournament.getTeamsInTournament()));
                eventPrintFixtures();

            }
        } else {
            System.out.println("\nNo Active tournament, please Create a new first.");
        }
    }

    public void eventPrintFixtures() {
        if (tournament != null) {
            if (tournament.tournamentStarted) {
                System.out.println("Games in 1st Round:\n");
                for (Match match : tournament.getMatches8()) {
                    System.out.println(match);
                }
            } else System.out.println("\nStart tournament first.");
        } else System.out.println("\nNo Active tournament, please Create a new first.");
    }

    private void eventResults() {
        if (tournament != null) {
            if (tournament.tournamentStarted) {
                while (!tournament.tournamentFinished) {
                    // first round
                    Team[] teamsQuarterFinal = tournament.resultOfMatch8();
                    tournament.matches4 = tournament.createRound(teamsQuarterFinal);
                    // Quarter Finals
                    System.out.println("\nQuarter Finals");
                    System.out.println("============");
                    for (Match match : tournament.getMatches4()) {
                        System.out.println(match);
                    }
                    Team[] teamsSemiFinal = tournament.resultOfMatch4();
                    tournament.matches2 = tournament.createRound(teamsSemiFinal);
                    // Semi Finals
                    System.out.println("\nSemiFinals");
                    System.out.println("============");
                    for (Match match : tournament.getMatches2()) {
                        System.out.println(match);
                    }
                    Team[] teamsFinal = tournament.resultOfMatch2();
                    tournament.finalMatch = tournament.createFinalRound(teamsFinal);
                    // Final
                    System.out.println("\nFinal");
                    System.out.println("============");
                    System.out.println(tournament.getFinalMatch());

                    Team winner = tournament.resultOfFinal();
                    System.out.println("\n" + winner.getTeamName() + " is the winner of the Tournament!!");
                    tournament.setTournamentFinished(true);
                }
            } else System.out.println("\nStart tournament first.");
        } else System.out.println("\nNo Active tournament, please Create a new first.");

    }

    public void eventTeams() {
        goBack = false;
        while (!goBack) {
            System.out.println("\n======================================");

            String tipForUser;
            if (teams.size() == 0) {
                tipForUser = "Tip: We don't have any teams in our database. Remember you minimum need 16 teams to start a tournament.";
            } else {
                tipForUser = "Fact: We already have " + teams.size() + " teams in our database.";
            }
            System.out.println("\n" + tipForUser);

            System.out.println("\nWhat do you wish to do? ");
            String input = io.getUserInput("\n" +
                    "1) Create new team " + "\n" +
                    "2) Print all existing teams from the database " + "\n" +
                    "3) Back " + "\n" +

                    "\nChoose action: ");
            System.out.println("\n======================================\n");
            getTeamEvent(input);
        }
    }

    // All team events
    public void getTeamEvent(String input) {
        switch (input) {
            case "1" -> eventCreateTeams();
            case "2" -> eventManageTeams();
            case "3" -> {
                goBack = true;
                welcomeMessage();
            }
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
                String[] commaSeparatedValues = scanner.nextLine().split(",");
                String teamName = commaSeparatedValues[0];
                int teamID = Integer.parseInt(commaSeparatedValues[1]);
                String player1 = commaSeparatedValues[2];
                int player1ID = Integer.parseInt(commaSeparatedValues[3]);
                String player2 = commaSeparatedValues[4];
                int player2ID = Integer.parseInt(commaSeparatedValues[5]);

                // We created a new constructor inside the Team class, which assigns the data to the respectable variables.
                teamList.add(new Team(teamName, teamID, player1, player1ID, player2, player2ID));
            }
        }

        return teamList;
    }

    // TODO: Make another read data method with tournament data.
}