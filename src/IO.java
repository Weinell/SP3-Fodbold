import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public interface IO {
    public void playerSave();
    public ArrayList<Player> readPlayerData();
    public void teamSave();//Saves after new Teams is added
    public ArrayList<Team>  readTeamData();
    public void matchSave();//saves after each match
    public ArrayList<Match> readMatchData();
    public void tournySave(String filepath);
    public ArrayList<Tournament> readTournyData();
}