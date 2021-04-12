import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {

 /*   public void registerPlayers(){
        String input="";
        System.out.println("Skriv navnene på spillets deltagere");
        System.out.println("tast Q for quitte");
        while(Main.players.size() < maxPlayers ){ //Tjekker om brugeren har tastet Q eller om der er registret 6 spillere
            input = getUserInput("Skriv navnet på spiller nr "+(Main.players.size()+1));

            if(input.toUpperCase().equals("Q")) {
                break;
            }else{
                Player player = new Player(input, startBalance);
                Main.players.add(player);
            }

        }
        System.out.println("Tak, spillets deltagere er registeret");
    }

  */

    //Team team1 = new Team();

    //Save game data
    /*
    publicstaticvoidsaveGameData(){
        FileWriterwriter=null;
        try{
            writer=newFileWriter("data.txt");
            writer.write(getGameDataFromSession());
        }catch(IOExceptione){
            System.out.println("Couldn'tinstantiatetheFileWriterinsaveGameData()");
            e.printStackTrace();
        }finally{
            try{
                writer.close();
            }catch(NullPointerException|IOExceptione){
                System.out.println("Couldn'tclosetheFileWriterinsaveGameData()");
                e.printStackTrace();
            }
        }
    }

     */
/*
public static ArrayList<Player> readGameData() {
    ArrayList<Player> playerList = new ArrayList<Player>();
    Player.counter = 0; // Just to be safe

    File file = new File("data.txt");
    Scanner scanner = null;
    try {
        scanner = new Scanner(file);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    if (scanner != null) {
        while (scanner.hasNextLine()) {
            String[] colonSeperatedValues = scanner.nextLine().split(":");
            String name = colonSeperatedValues[0];
            int balance = Integer.parseInt(colonSeperatedValues[1]);
            playerList.add(new Player(name, balance));
        }
    }

    return playerList;
}
*/

    public String getUserInput(String msg){
        System.out.print(msg);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

/*
    public Team createTeams()   {

        System.out.println("Write team name and name of team members ");
        System.out.println(getUserInput("Team name: "));
        Team.player1 = getUserInput("Member 1: ");
        getUserInput("Member 2: ");



    }
*/

}
