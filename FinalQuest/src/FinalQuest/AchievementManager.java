package FinalQuest;

import java.io.File;
import java.io.IOException;
import java.io.*;
/**
 *
 * @author Gach56
 */
public class AchievementManager {
    
    // An arraylist of the type Boolean we will use to work with the achievements inside the class
    private boolean[] achievements = new boolean[16];
    
    // The name of the file where the achievements will be saved
    File ACHIEVEMENT_FILE = new File("achievements.dat");
    
    
    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;
    
    public AchievementManager() {
        
        
        
    }
    
    public boolean[] getAchievements() {
        loadAchievementFile();
        return achievements;
    }
    
    
    public void addAchievement() {
        loadAchievementFile();
        updateAchievementFile();
    }
    
    public void loadAchievementFile() {
        
        try {
            inputStream = new ObjectInputStream(new FileInputStream(ACHIEVEMENT_FILE));
            
            
            
            achievements =  (boolean[]) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Laad] IO Error: " + e.getMessage());
            }
        }
    }
    
    public void updateAchievementFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(ACHIEVEMENT_FILE));
            outputStream.writeObject(achievements);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }
    
    
}
