import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public interface IO {
    public void playerSave(String filepath);
    public ArrayList<Player> readPlayerData();
    public void teamSave(String filepath);//Saves after new Teams is added
    public ArrayList<Team>  readTeamData(String path);
    public void matchSave(String filepath);//saves after each match
    public ArrayList<Match> readMatchData();
    public void tournySave(String filepath);
    public ArrayList<Tournament> readTournyData();
}