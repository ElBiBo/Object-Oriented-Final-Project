
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
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien1 extends Sprite {

    private final int INITIAL_X = 1280;
    private final int POINTS = 100;
    private int laser_rate;
    private int fire_count;
    private int fire_rate;
    private int health;
    private int move_speed;
    private List<Missile> missiles;
    private int missile_speed;
    private final String DIFFICULTY;
    

    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien1(int x, int y, String D) {
        super(x, y);
        DIFFICULTY = D;
        initAlien();
    }

    /**
     * Init our alien by assigning it an image and getting it's dimensions
    */
    private void initAlien() {
        if (DIFFICULTY == "normal")
        {
            fire_rate = 300;  //how often lasers are fired
            health = 1; // how many times they can be hit before dying 
            missile_speed = -5; // how fast their lasers move
            move_speed = 2; // how fast the alien moves
        }
        else if (DIFFICULTY == "hard")
        {
            fire_rate = 250;  //how often lasers are fired
            health = 1; // how many times they can be hit before dying 
            missile_speed = -7; // how fast their lasers move
            move_speed = 4; // how fast the alien moves
        }
        else if (DIFFICULTY == "unforgiving")
        {
            fire_rate = 100;  //how often lasers are fired
            health = 2; // how many times they can be hit before dying 
            missile_speed = -12; // how fast their lasers move
            move_speed = 6; // how fast the alien moves
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Bomber.png");
        getImageDimensions();
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health <= 0 it should be destroyed
     * otherwise it makes a sound and takes some damage
     * @return  the alien's current health
     */
    public int damage(){
        health -= 1;
        if (health > 0)
        {
            SoundEffect.ALIEN_HIT.play();
        }
        else
        {
            SoundEffect.ALIEN_EXPLODE.play();
        }
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
        
        missiles.add(new Missile(x-width, y -2 + height / 2, missile_speed,0));
    }
    
    /**
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    public void move() {
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        x -= move_speed;
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
}