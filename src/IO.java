import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {


    public String getUserInput(String msg) {
        System.out.print(msg);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public int getUserInputInteger(String msg) {
        System.out.print(msg);
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }



    public void save() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/data.txt");
            for(Team teams : Controller.teams)
                fw.write(teams.toString()+"\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Couldn't save");
        }
    }
}