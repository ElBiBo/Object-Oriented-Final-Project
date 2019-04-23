
package FinalQuest;

import static java.lang.Math.sin;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

/**
 * Wave Alien
 * Alien child of our sprite class. Controls the AI of our alien.
 * Behavior should be:
 * - swerve up and down as it flies to the left
 * - occasionally fire a laser
 * - be destroyed with 1 hit
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 300 points if destroyed by the player
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien3 extends Alien {
    
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
    public Alien3(int x, int y, String D) {
        super(x, y, D);
        POINTS = 300;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
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
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 4
     */
    public Alien3(int x, int y, String D, int s) {
        super(x, y, D);
        POINTS = 300;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = s;
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
     * Constructor, to adjust the speed, if needed, as well as the sin wave
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 4
     * @param r adjust the radius of the sin wave for tighter or looser curves. default is 200
     */
    public Alien3(int x, int y, String D, int s, double r) {
        super(x, y, D);
        POINTS = 300;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = s;
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
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                fire_rate = 250;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+4)*-1; // how fast their lasers move
                break;
            case "hard":
                fire_rate = 150;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+4)*-1; // how fast their lasers move
                break;
            case "unforgiving":
                fire_rate = 50;  //how often lasers are fired
                health = 2; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                break;
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        
        loadImage("src/resources/Bomber2.png");
        getImageDimensions();
        
    }
    
    /**
     *  Alien's AI
     * swerves up and down along a sin wave as it moves across the screen to the left
     * also fires a laser
     */
    @Override
    public void move() {
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        x -= move_speed;
        if (x <= 1280)
        {
            angle +=(double) move_speed/80* direction /(RADIUS/200);
            y = (int) Math.round(ORIGIN_Y + sin(angle)*RADIUS);
        }
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
    
}