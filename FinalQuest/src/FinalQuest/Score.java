
package FinalQuest;
import java.io.Serializable;

/**
 * an object used to contain a score and name for high scores
 * @author Nicholas Gacharich
 */
public class Score  implements Serializable{    
    private static final long serialVersionUID = 1L;
    private int score;
    private String name;

    /**
     * getter for the player's score
     * @return their score
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for the player's name
     * @return their name
     */
    public String getName() {
        return name;
    }

    /**
     * constructor
     * @param name  name of the player
     * @param score player score
     */
    public Score(String name, int score) {
        this.score = score;
        this.name = name;
    }    
}

