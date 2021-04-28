import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;




public class FileReader implements IO {


        public void teamSave(String filepath) {
            FileWriter fw = null;
            try {
                fw = new FileWriter(filepath);
                for(Team teams : Controller.teams)
                    fw.write(teams.toString()+"\n");
                fw.close();
            } catch (Exception e) {
                System.out.println("Couldn't save");
            }
        }

        public void matchSave(String filepath) {
            FileWriter fw = null;
            try {
                fw = new FileWriter(filepath);
                for(Match match : Controller.matches)
                    fw.write(match.matchDataToString()+"\n");
                fw.close();
            } catch (Exception e) {
                System.out.println("Couldn't save");
            }
        }

    @Override
    public ArrayList<Match> readMatchData() {
        return null;
    }

    @Override
    public void tournySave(String filepath) {

    }

    @Override
    public ArrayList<Tournament> readTournyData() {
        return null;
    }

    @Override
    public ArrayList<Team> readTeamData(String path) {
        ArrayList<Team> teamList = new ArrayList<>();

        File file = new File(path);
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

    @Override
    public void playerSave(String filepath) {

    }

    @Override
    public ArrayList<Player> readPlayerData() {
        return null;
    }
}

