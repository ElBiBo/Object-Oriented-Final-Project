
package FinalQuest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

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
public class Alien5 extends Alien {
    
    private int behavior;
    private final double ORIGIN_X;
    private final double ORIGIN_Y;
    private final double RADIUS;
    private double angle;
    private final double direction;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien5(int x, int y, String D) {
        super(x, y, D);
        POINTS = 200;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        ORIGIN_X = 640;
        move_speed = 4;
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
        initAlien();
    }
    
    /**
     * Constructor for bigger or smaller circles.
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param r is the radius you want the alien to circle. default is 200
     */
    public Alien5(int x, int y, String D, double r) {
        super(x, y, D);
        POINTS = 200;
        this.sprite_type = "enemy";
        move_speed = 4;
        DIFFICULTY = D;
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
        initAlien();
    }
    
    /**
     * Constructor for bigger or smaller circles.
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param r is the radius you want the alien to circle. default is 200
     * @param spin  Default is to start spinning at about halfway across the screen, this can adjust that x point
     * @param speed move speed of the ship
     */
    public Alien5(int x, int y, String D, double r, int spin, int speed) {
        super(x, y, D);
        POINTS = 200;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        ORIGIN_X = spin;
        RADIUS = r;
        move_speed = speed;
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
        initAlien();
    }
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                fire_rate = 100;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                move_speed = move_speed; // how fast the alien moves
                missile_speed = -4-move_speed; // how fast their lasers move
                break;
            case "hard":
                fire_rate = 50;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                move_speed = move_speed+2; // how fast the alien moves
                missile_speed = -4-move_speed; // how fast their lasers move
                break;
            case "unforgiving":
                fire_rate = 25;  //how often lasers are fired
                health = 2; // how many times they can be hit before dying
                move_speed = move_speed+4; // how fast the alien moves
                missile_speed = -4-move_speed; // how fast their lasers move
                break;
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        behavior = 0;
        
        loadImage("src/resources/Bomber2C.png");
        getImageDimensions();
        
    }
    
    /**
     *  Alien's AI
     * Moves to the left until about halfway across the screen, does 3 loops,
     * and then flies off the screen. Has a pretty quick fire rate
     */
    @Override
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