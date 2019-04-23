package FinalQuest;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.*;
/**
 * Used to manage and store our high scores
 * @author Nicholas Gacharich
 */

public class HighscoreManager {
    // An arraylist of the type "score" we will use to work with the scores inside the class
    private ArrayList<Score> scores;
    
    // The name of the file where the highscores will be saved
    File HIGHSCORE_FILE = new File("scores.dat");
    
    
    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;
    
    /**
     * The constructor for our list of high scores. The following 
     * names are default values and all references to famous science fiction
     * figures
     */
    public HighscoreManager() {
        //initialising the scores-arraylist.
        scores = new ArrayList<Score>();
        scores.add(new Score("Reynolds", 100000));  // Malcom Reynolds from Firefly
        scores.add(new Score("Adama", 90000));      // William "Husker" Adama from Battlestar Galactica
        scores.add(new Score("Solo", 80000));       // Han Solo from Star Wars
        scores.add(new Score("Picard", 70000));     // Jean-Luc Picard from Star Trek
        scores.add(new Score("Global", 60000));     // Bruno J Global from The Super Dimension Fortress Macross
        scores.add(new Score("Leela", 50000));      // Turanga Leela from Futurama
        scores.add(new Score("Bowman", 40000));     // David Bowman from 2001: a Space Odyssey
        scores.add(new Score("Beeblebrox", 30000)); // Zaphod Beeblebrox from Hitchhiker's Guide to the Galaxy
        scores.add(new Score("Robinson", 20000));   // John Robinson from Lost in Space
        scores.add(new Score("Adams", 10000));      // John J. Adams from Forbidden Planet
    }
    
    /**
     * returns a list of top scores from a file
     * @return  the list of scores
     */
    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }
    
    /**
     * Sorts the scores in order by top value first
     */
    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }
    
    /**
     * Adds a new score to the file
     * @param name  Name of our new score
     * @param score points scored for that person
     */
    public void addScore(String name, int score) {
        loadScoreFile();
        scores.add(new Score(name, score));
        updateScoreFile();
    }
    
    /**
     *  Loads up our score file
     */
    public void loadScoreFile() {
        
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            
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
     *  Once our score fill is loaded, we need to update it with new scores
     */
    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(scores);
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
    
    /**
     * Stringify our list of scores for drawing later
     * @return  a string containing all of our names and scores
     */
    public String getHighscoreString() {
        String highscoreString = "";
        int max = 10;
        
        ArrayList<Score> scores;
        scores = getScores();
        
        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highscoreString += (i + 1) + ".\t" + scores.get(i).getName() + "\t\t" + scores.get(i).getScore() + "\n";
            i++;
        }
        return highscoreString;
    }
}
