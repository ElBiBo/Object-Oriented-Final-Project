
package FinalQuest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * Behavior should be:
 * - fly in a straight line then do 3 loops before continuing in a straight 
 *   line again
 * - frequently fires a laser
 * - be destroyed with 1 hit
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 200 points if destroyed by the player
 * @author Marco Tacca
 */
public class Alien5 extends Sprite {
    
    private final int INITIAL_X = 1280;
    private final int POINTS = 200;
    private int laser_rate;
    private int fire_count;
    private int fire_rate;
    private int health;
    private int move_speed;
    private List<Missile> missiles;
    private int missile_speed;
    private final String DIFFICULTY;
    private int behavior;
    private final double ORIGIN_X;
    private final double ORIGIN_Y;
    private final double RADIUS;
    private double angle;
    private double direction;
    
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien5(int x, int y, String D) {
        super(x, y);
        DIFFICULTY = D;
        initAlien();
        ORIGIN_X = 640;
        RADIUS = 200;
        if (y<960/2)
        {
            ORIGIN_Y = y+RADIUS;
            angle = Math.PI /-2;
            direction = -1;
        }
        else
        {
            ORIGIN_Y = y-RADIUS;
            angle = Math.PI /2;
            direction = 1;
        }
        
        
    }
    
    /**
     * Constructor for bigger or smaller circles.
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param r is the radius you want the alien to circle. default is 200
     */
    public Alien5(int x, int y, String D, double r) {
        super(x, y);
        DIFFICULTY = D;
        initAlien();
        ORIGIN_X = 640;
        RADIUS = r;
        if (y<960/2)
        {
            ORIGIN_Y = y+RADIUS;
            angle = Math.PI /-2;
            direction = -1;
        }
        else
        {
            ORIGIN_Y = y-RADIUS;
            angle = Math.PI /2;
            direction = 1;
        }
        
        
    }
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        if (DIFFICULTY == "normal")
        {
            fire_rate = 100;  //how often lasers are fired
            health = 1; // how many times they can be hit before dying
            missile_speed = -8; // how fast their lasers move
            move_speed = 4; // how fast the alien moves
        }
        else if (DIFFICULTY == "hard")
        {
            fire_rate = 50;  //how often lasers are fired
            health = 1; // how many times they can be hit before dying
            missile_speed = -10; // how fast their lasers move
            move_speed = 6; // how fast the alien moves
        }
        else if (DIFFICULTY == "unforgiving")
        {
            fire_rate = 25;  //how often lasers are fired
            health = 2; // how many times they can be hit before dying
            missile_speed = -14; // how fast their lasers move
            move_speed = 8; // how fast the alien moves
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        behavior = 0;
        
        loadImage("src/resources/Bomber2C.png");
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
        
        missiles.add(new Missile(x-width, y + height / 2, missile_speed,0));
    }
    
    /**
     *  Alien's AI
     * Moves to the left until about halfway across the screen, does 3 loops,
     * and then flies off the screen. Has a pretty quick fire rate
     */
    public void move() {
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        
        switch(behavior){
            case 0:
                x -= move_speed;
                if (x<ORIGIN_X)
                {
                    behavior++;
                }
                break;
            case 1:
                angle +=(double) move_speed/200 * direction;
                x = (int) Math.round(ORIGIN_X + cos(angle)*RADIUS);
                y = (int) Math.round(ORIGIN_Y + sin(angle)*RADIUS);
                if (angle >= (6*Math.PI+Math.PI/2) || angle <= (6*Math.PI+Math.PI/2)*-1)
                {
                    behavior++;
                }
            default:
                x -= move_speed;
                break;
        }
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
}