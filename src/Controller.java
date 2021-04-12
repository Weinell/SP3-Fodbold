import java.util.ArrayList;

public class Controller {

    protected Tournament tournament;
    protected boolean activeTournament = false;
    protected IO io = new IO();

    protected static ArrayList<Team> teams = new ArrayList<>();

    public void mainApplication() {
        welcomeMessage();
    }

    public void welcomeMessage() {
        System.out.println("\n======================================");
        String activeName = activeTournament ? tournament.getTournamentName() : "No active tournament";
        String activeTeams = activeTournament ? tournament.teamsInTournament() : "";
        System.out.println("\nNext event: " + activeName + "\n" + activeTeams);

        System.out.println("\nWhat do you wish to do? ");
        String input = "";
        input = io.getUserInput("\n" +
                "1) Start new Tournament " + "\n" +
                "2) Manage Tournament " + "\n" +
                "3) Create new Teams " + "\n" +
                "4) Manage Teams " + "\n" +
                "5) Start Tournament " + "\n" +
                "6) Print Tree " + "\n" +

                "\nChoice action: ");
        System.out.println("\n======================================");
        getEvent(input);
    }


    public void getEvent(String input) {
        switch (input) {
            case "1":
                eventNewTournament();
                break;
            case "2":
                eventManageTournament();
                break;
            case "3":
                eventCreateTeams();
                break;
            case "4":
                eventManageTeams();
                break;
            case "5":
                eventStartTournament();
                break;
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
//                    System.out.println("\n" + t.teamsInTournament());
            } else if (input.equals("n")) {
                System.out.println("Something else");
            } else {
                System.out.println("Wrong input");
            }
        } else {
            System.out.println("\nThere is already an active tournament.");
        }
    }

    public void eventManageTournament() {
        if (activeTournament) {
            System.out.println("Add team to tournament: ");

            int input = 0;
            input = io.getUserInputInteger("\nInput Team ID to add to tournament: ");
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
        } else {
            System.out.println("\nNo Active tournament, please Create a new first.");
        }
    }

    public void eventCreateTeams() {
        teams.add(new Team());
        io.save();

    }

    public void eventManageTeams() {
        for (Team t : teams) {
            System.out.println(t.getTeamID() + ") " + t.getTeamName() + " (" + t.getPlayer1().getName() + " and " + t.getPlayer2().getName() + ")");
        }
    }

    public void eventStartTournament() {
        if (tournament.isTournFull() && tournament.isTournamentStarted()) {
            tournament.randomizerOrderOfArray();
            tournament.setTournamentStarted(true);
            for (Team team : tournament.getTeams()) {
                System.out.println(team.getTeamName());
            }
        }
    }
}