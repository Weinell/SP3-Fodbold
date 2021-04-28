import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {

    Controller ctr;
    DBConnector dbc;
    FileReader fr;

    boolean goBack = false;  // Used inside the menus.


    public UI(Controller ctr, DBConnector dbc, FileReader fr) {
        this.ctr = ctr;
        this.dbc = dbc;
        this.fr = fr;

    }

    // The menu system is divided into different Events: Tournament, Teams, Players. Each event has sub events as well.
    public void welcomeMessage() {
        System.out.println("\n======================================");
        String activeName = ctr.activeTournament ? ctr.tournament.getTournamentName() : "No active tournament";
        String activeTeams = ctr.activeTournament ? ctr.tournament.teamsInTournament() : "";
        System.out.println("\nNext event: " + activeName + "\n" + activeTeams);
        String tipForUser = ctr.activeTournament ? "Tip: Add existent teams (Press 1) or create new ones (Press 2)" : "Tip: Create new Tournament (Press 1)";
        System.out.println("\n" + tipForUser);

        System.out.println("\nWhat do you wish to do? ");
        String input = getUserInput("\n" +
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
            case "3" -> {
                dbc.teamSave("src/teamData.txt");
                dbc.playerSave(null);
                System.exit(0);
            }
        }
    }

    public void eventTournaments() {
        goBack = false;
        while (!goBack) {
            System.out.println("\n======================================");

            String tipForUser;
            if (!ctr.activeTournament) {
                tipForUser = "Tip: Create new Tournament (Press 1)";
            } else if (!ctr.tournament.isTournamentFull()) {
                tipForUser = "Tip: Add existing teams (Press 2) or go back and create new teams (Press 7)";
            } else if (ctr.tournament.isTournamentFull() && !ctr.tournament.isTournamentStarted()) {
                tipForUser = "Tip: Start tournament (Press 4)";
            } else if (ctr.tournament.isTournamentStarted()) {
                tipForUser = "Tip: Input the results of the matches (Press 6)";
            } else {
                tipForUser = "The tournament is over. Thank you to all participants";
            }

            System.out.println("\n" + tipForUser);

            System.out.println("\nWhat do you wish to do? ");
            String input = getUserInput("\n" +
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
        if (!ctr.activeTournament) {
            System.out.println("\nThere is no active tournament");
            String input = getUserInput("\nStart new Tournament? Y/N ").toLowerCase();
            if (input.equals("y")) {
                input = getUserInput("\nWhats the name of the tournament: ");
                ctr.tournament = new Tournament(16, input);
                ctr.activeTournament = true;
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
        if (ctr.activeTournament) {
            System.out.println("Add team(s) to tournament, end with 0: ");

            goBack = false;
            while (!goBack && !ctr.tournament.isTournamentFull()) {
                int input = getUserInputInteger("\nInput Team ID to add to tournament: ");
                if (input == 0) {
                    goBack = true;
                    eventTournaments();
                }
                for (Team team : ctr.teams) {
                    if (team.getTeamID() == input) {
                        if (!team.isAddedToActiveTournament()) {
                            ctr.tournament.addTeamToTournament(team);
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
        if (ctr.activeTournament) {
            ctr.tournament.printTeamsInTournament();
        } else {
            System.out.println("\nNo Active tournament, please Create a new first.");
        }
    }

    // TODO: When tournament start, app should create 8 new match objects and put them into an array
    public void eventStartTournament() {
        if (ctr.tournament != null) {
            if (ctr.tournament.isTournamentFull() && !ctr.tournament.isTournamentStarted()) {   // booleans makes sure you cant start the tournament if there is not enough teams admitted.
                ctr.tournament.randomizerOrderOfArray();    // Created a method to randomize the order of the teams, so its unpredictable who will go up against each other
                ctr.tournament.setTournamentStarted(true); // preventing app to start the same tournament twice.

                ctr.tournament.setMatches8(ctr.tournament.createRound(ctr.tournament.getTeamsInTournament()));
                eventPrintFixtures();

            }
        } else {
            System.out.println("\nNo Active tournament, please Create a new first.");
        }
    }

    public void eventPrintFixtures() {
        if (ctr.tournament != null) {
            if (ctr.tournament.tournamentStarted) {
                System.out.println("Games in 1st Round:\n");
                for (Match match : ctr.tournament.getMatches8()) {
                    System.out.println(match);
                }
            } else System.out.println("\nStart tournament first.");
        } else System.out.println("\nNo Active tournament, please Create a new first.");
    }

    private void eventResults() {
        if (ctr.tournament != null) {
            if (ctr.tournament.tournamentStarted) {
                while (!ctr.tournament.tournamentFinished) {
                    // first round
                    for (Match m : ctr.tournament.getMatches8()) {
                        System.out.println(m);
                        ctr.matches.add(m);
                        fr.matchSave("src/matchData.txt");
                    }
                    Team[] teamsQuarterFinal = ctr.tournament.resultOfMatch8();
                    ctr.tournament.matches4 = ctr.tournament.createRound(teamsQuarterFinal);
                    // Quarter Finals
                    System.out.println("\nQuarter Finals");
                    System.out.println("============");
                    for (Match m : ctr.tournament.getMatches4()) {
                        System.out.println(m);
                        ctr.matches.add(m);
                        fr.matchSave("src/matchData.txt");
                    }
                    Team[] teamsSemiFinal = ctr.tournament.resultOfMatch4();
                    ctr.tournament.matches2 = ctr.tournament.createRound(teamsSemiFinal);
                    // Semi Finals
                    System.out.println("\nSemiFinals");
                    System.out.println("============");
                    for (Match m : ctr.tournament.getMatches2()) {
                        System.out.println(m);
                        ctr.matches.add(m);
                        fr.matchSave("src/matchData.txt");
                    }
                    Team[] teamsFinal = ctr.tournament.resultOfMatch2();
                    ctr.tournament.finalMatch = ctr.tournament.createFinalRound(teamsFinal);
                    // Final
                    System.out.println("\nFinal");
                    System.out.println("============");
                    System.out.println(ctr.tournament.getFinalMatch());
                    ctr.matches.add(ctr.tournament.getFinalMatch());
                    fr.matchSave("src/matchData.txt");

                    Team winner = ctr.tournament.resultOfFinal();
                    fr.matchSave("src/matchData.txt");
                    System.out.println("\n" + winner.getTeamName() + " is the winner of the Tournament!!");
                    ctr.tournament.setTournamentFinished(true);
                }
            } else System.out.println("\nStart tournament first.");
        } else System.out.println("\nNo Active tournament, please Create a new first.");

    }

    public void currentRound() {


    }

    public void eventTeams() {
        goBack = false;
        while (!goBack) {
            System.out.println("\n======================================");

            String tipForUser;
            if (ctr.teams.size() == 0) {
                tipForUser = "Tip: We don't have any teams in our database. Remember you minimum need 16 teams to start a tournament.";
            } else {
                tipForUser = "Fact: We already have " + ctr.teams.size() + " teams in our database.";
            }
            System.out.println("\n" + tipForUser);

            System.out.println("\nWhat do you wish to do? ");
            String input = getUserInput("\n" +
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
        ctr.teams.add(new Team()); // Creates a new team which then ask for details about team and player names
        fr.teamSave("src/teamData.txt"); // Saves to teamData.txt everytime a new team is created.

    }

    // TODO: So far its only to print a list of all the teams. Maybe we should add an search and edit function
    public void eventManageTeams() {
        for (Team t : ctr.teams) {
            System.out.println(t.getTeamID() + ") "
                    + t.getTeamName() + " ("
                    + t.getPlayer1().getPlayerName() + " and "
                    + t.getPlayer2().getPlayerName() + ") Teams Goals: "
                    + t.getTeamGoals() + " Team Points: " + t.getTeamPoints());
        }
//        for (Player p : ctr.players) {
//            System.out.println(p);
//        }
    }

    public static ArrayList<Team> readTeamData() {
        ArrayList<Team> teamList = new ArrayList<>();

        File file = new File("src/teamData.txt");
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

    public static String getUserInput(String msg) {
        System.out.print(msg);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    // TODO: Couldn't get the getUserInput function to work for Integer. So i made this instead. Maybe find another solution.
    public static int getUserInputInteger(String msg) {
        System.out.print(msg);
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

}
