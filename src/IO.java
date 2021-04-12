import java.util.Scanner;

public class IO {



    //Team team1 = new Team();



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
