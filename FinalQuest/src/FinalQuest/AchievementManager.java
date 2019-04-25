package FinalQuest;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.io.*;
/**
 * This class stores all the achievements the player has scored. It also 
 * is used to display a small icon and chime when an achievement is scored
 * @author Nicholas Gacharich
 */
public class AchievementManager {
    private int[] counter;
    private final int time = 60;
    private final Achievement a = new Achievement(1200, 50,0);
    
    // An arraylist of the type Boolean we will use to work with the achievements inside the class
    private boolean[] achievements = new boolean[16];
    
    // The name of the file where the achievements will be saved
    File ACHIEVEMENT_FILE = new File("achievements.dat");
    
    
    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;
    
    /**
     * Constructor
     */
    public AchievementManager() {
        
        counter = new int[16];
        for (int i = 0; i< counter.length;i++)
        {
            counter[i] = -1;
        }
        
    }
    
    /**
     */
    public boolean[] getAchievements() {
        loadAchievementFile();
        return achievements;
    }
    
    /**
     * Used to draw achievements onto the screen when scored
     * @param g screen we are drawing on
     * @param i which achievement we are scoring
     * @return  True if the achievement should still be shown, false if time is up
     */
    public boolean popupAchievement(Graphics g, int i)
    {
        if (counter[i] < 0)
        {
            return false;
        }
        else if (counter[i]== time)
        {
            SoundEffect.ACHIEVEMENT.play();
        }
        a.getPlace(1200, 50, i);
        g.drawImage(a.getImage(), a.getX(), a.getY(), 67,67, null);
        counter[i]--;
        
        return counter[i] >0;
    }
    
    /**
     * Used when an achievement is scored to update the achievement list
     * @param i the achievement that was scored (0-15)
     */
    public void addAchievement(int i) {
        if (!achievements[i])
        {
            loadAchievementFile();
            achievements[i] = true;
            counter[i] = time;
            updateAchievementFile();
        }
    }
    
    /**
     * Loads up the file used for the achievement. Creates one if none exists.
     */
    public void loadAchievementFile() {
        
        try {
            inputStream = new ObjectInputStream(new FileInputStream(ACHIEVEMENT_FILE));
            achievements =  (boolean[]) inputStream.readObject();
        } catch (FileNotFoundException e) {
            //System.out.println("[Laad] FNF Error: " + e.getMessage());
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
    
    /**
     * 
     */
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
