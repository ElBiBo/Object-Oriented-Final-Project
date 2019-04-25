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
     * A getter for the score
     * @return Returns the score as an int
     */
    public int getScore() {
        return score;
    }

    /**
     * A getter for the name
     * @return Returns the name as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Constructor for our object
     * @param name  Name of our high scorer
     * @param score Score of our high scorer
     */
    public Score(String name, int score) {
        this.score = score;
        this.name = name;
    }    
}

