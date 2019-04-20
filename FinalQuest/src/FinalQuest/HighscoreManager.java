package FinalQuest;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.*;
/**
 *
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

public HighscoreManager() {
        //initialising the scores-arraylist
        scores = new ArrayList<Score>();
        scores.add(new Score("Reynolds", 100000));
        scores.add(new Score("Adama", 90000));
        scores.add(new Score("Solo", 80000));
        scores.add(new Score("Picard", 70000));
        scores.add(new Score("Global", 60000));
        scores.add(new Score("Leela", 50000));
        scores.add(new Score("Bowman", 40000));
        scores.add(new Score("Beeblebrox", 30000));
        scores.add(new Score("Robinson", 20000));
        scores.add(new Score("Keys", 10000));
}

public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
}
    
private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
}
     
public void addScore(String name, int score) {
        loadScoreFile();
        scores.add(new Score(name, score));
        updateScoreFile();
}
      
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
