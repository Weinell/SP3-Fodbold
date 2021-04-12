public class Main {


    static boolean isRunning = true;
    static Controller c = new Controller();

    public static void main(String[] args) {






        while (isRunning) {
            c.mainApplication();
        }




        //TESTING
//        Tournament tour = new Tournament(4,"DM");
//        tour.addTeamToTournament(new Team());
//        tour.addTeamToTournament(new Team());
//        tour.addTeamToTournament(new Team());
//        tour.addTeamToTournament(new Team());
//
//
//        for(Team team : tour.getTeams()) {
//            System.out.println(team.getTeamName());
//        }
//
//        tour.randomizerOrderOfArray();
//
//        for(Team team : tour.getTeams()) {
//            System.out.println(team.getTeamName());
//        }
    }
}
