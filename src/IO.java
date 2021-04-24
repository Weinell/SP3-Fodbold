import java.io.FileWriter;
import java.util.Scanner;

public interface IO {
    public void teamSave(String filepath);//Saves after new Teams is added
    public void matchSave(String filepath);//saves after each match
}