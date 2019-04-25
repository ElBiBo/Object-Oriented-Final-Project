package FinalQuest;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.*;
/**
 * This class stores and manages the game's high scores
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
     * constructor
     * default names are based on famous scifi captains
     */
    public HighscoreManager() {
        //initialising the scores-arraylist
        scores = new ArrayList<Score>();
        scores.add(new Score("Reynolds", 100000));  // Malcolm Reynolds (Firefly)
        scores.add(new Score("Adama", 90000));      // William "Husker" Adama (New Battlestar Galactica)
        scores.add(new Score("Solo", 80000));       // Han Solo (Star Wars)
        scores.add(new Score("Picard", 70000));     // Jean-Luc Picard (Star Trek: The Next Generation)
        scores.add(new Score("Global", 60000));     // Bruno J. Global (The Super Dimension Fortress Macross)
        scores.add(new Score("Leela", 50000));      // Turanga Leela (Futurama)
        scores.add(new Score("Bowman", 40000));     // David Bowman (2001: A Space Odyssey)
        scores.add(new Score("Beeblebrox", 30000)); // Zaphod Beeblebrox (Hitchhiker's Guide to the Galaxy)
        scores.add(new Score("Robinson", 20000));   // John Robinson (Lost in Space)
        scores.add(new Score("Adams", 10000));      // John J. Adams (Forbidden Planet)
}

    /**
     * get an array of all our scores
     * @return an array containing our scores
     */
    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
}
    
private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
}
     
    /**
     * Adds a new score to the list
     * @param name  name of the scorer
     * @param score the score scored
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
     * Changes the scores on our score file
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
     * Makes a string for printing onto our screen
     * @return  a string of high scores for the board
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
