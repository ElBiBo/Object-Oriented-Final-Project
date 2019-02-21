
package FinalQuest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * Behavior should be:
 * - fly to the left, steering towards the player so long as the player is in front of it
 * - Frequently fires a laser
 * - be destroyed with 3 hits
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 500 points if destroyed by the player
 * @author Marco Tacca
 */
public class Alien6 extends Sprite {
    
    private final int INITIAL_X = 1280;
    private final int POINTS = 500;
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
    private final double RADIUS = 200;
    private double angle;
    private double direction;
    private SpaceShip ship;
    
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param d is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien6(int x, int y, String D, SpaceShip s) {
        super(x, y);
        ship = s;
        DIFFICULTY = D;
        initAlien();
        ORIGIN_X = 640;
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
            health = 3; // how many times they can be hit before dying
            missile_speed = -8; // how fast their lasers move
            move_speed = 4; // how fast the alien moves
        }
        else if (DIFFICULTY == "hard")
        {
            fire_rate = 50;  //how often lasers are fired
            health = 4; // how many times they can be hit before dying
            missile_speed = -10; // how fast their lasers move
            move_speed = 6; // how fast the alien moves
        }
        else if (DIFFICULTY == "unforgiving")
        {
            fire_rate = 25;  //how often lasers are fired
            health = 5; // how many times they can be hit before dying
            missile_speed = -14; // how fast their lasers move
            move_speed = 8; // how fast the alien moves
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        behavior = 0;
        
        loadImage("src/resources/BomberC.png");
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
        int[] pos = ship.getPos();
        switch(behavior){
            case 0:
                x -= move_speed;
                if (y< pos[1])
                {
                    y++;
                }
                else
                {
                    y--;
                }
                if (x<pos[0])
                {
                    behavior++;
                }
                break;
            default:
                x -= move_speed;
                break;
        }
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
}