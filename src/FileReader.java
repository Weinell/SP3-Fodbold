import java.io.FileWriter;
import java.util.Scanner;




public class FileReader implements IO {

        public String getUserInput(String msg) {
            System.out.print(msg);
            Scanner scan = new Scanner(System.in);
            return scan.nextLine();
        }

        // TODO: Couldn't get the getUserInput function to work for Integer. So i made this instead. Maybe find another solution.
        public int getUserInputInteger(String msg) {
            System.out.print(msg);
            Scanner scan = new Scanner(System.in);
            return scan.nextInt();
        }

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
                for(Match match : Controller.match)
                    fw.write(match.matchDataToString()+"\n");
                fw.close();
            } catch (Exception e) {
                System.out.println("Couldn't save");
            }
        }
    }

