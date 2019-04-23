package FinalQuest;
import java.util.Comparator;

/**
 * A comparator used for sorting our scores
 * @author Nicholas Gacharich
 */
public class ScoreComparator implements Comparator<Score>{
    @Override
    public int compare(Score score1, Score score2) {
        
        int sc1 = score1.getScore();
        int sc2 = score2.getScore();
        
        if (sc1 > sc2){
            return -1;
        }else if (sc1 < sc2){
            return +1;
        }else{
            return 0;
        }
    }
}