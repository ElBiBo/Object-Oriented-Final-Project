
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * Behavior should be:
 * - fly in a straight line
 * - occasionally fire a laser
 * - be destroyed with 1 hit
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 100 points if destroyed by the player
 * @author Marco Tacca
 */
public class Alien1 extends Sprite {

    private final int INITIAL_X = 1280;
    private final int POINTS = 100;
    private int laser_rate;
    private int fire_count;
    private int fire_rate;
    private int health;
    private List<Missile> missiles;
    private int missile_speed;
    

    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     */
    public Alien1(int x, int y) {
        super(x, y);
        initAlien();
    }

    /**
     * Init our alien by assigning it an image and getting it's dimensions
    */
    private void initAlien() {
        fire_rate = 300;
        fire_count = ThreadLocalRandom.current().nextInt(100, fire_rate + 1);
        health = 1;
        missile_speed = -5;
        missiles = new ArrayList<>();
        loadImage("src/resources/Bomber.png");
        getImageDimensions();
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health <= 0 it should be destroyed
     * @return  the alien's current health
     */
    public int damage(){
        health -= 1;
        return health;
    }

    /**
     * This is just an accessor for the ship's score 
     * @return  the number of points destroying the alien is worth
     */
    public int getPoints(){
        return POINTS;
    }
    
    /**
     * Returns a list of missiles that have been fired.
     * Used to find their coordinates for drawing them
     * @return  list of missiles
     */
    public List<Missile> getMissiles() {
        return missiles;
    }
    
    /**
     * Create a missile when activated. No more than num_missiles
     * missiles can be fired without one of the previous missiles being 
     * destroyed first
     */
    public void fire() {
        missiles.add(new Missile(x, y + height / 2, missile_speed,0));
    }
    
    /**
     *  Alien's AI
     * currently moves it to the left until it reaches the end of the screen
     * then pops it back to the right side of the screen
     * We will program some more interesting AI here
     */
    public void move() {
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        x -= 2;
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
}