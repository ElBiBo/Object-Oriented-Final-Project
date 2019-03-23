
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

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
public class Alien6 extends Alien {
    
    private int behavior;
    private final double ORIGIN_X;
    private final double ORIGIN_Y;
    private final double RADIUS = 200;
    private final double angle;
    private final double direction;
    private final SpaceShip ship;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s
     */
    public Alien6(int x, int y, String D, SpaceShip s) {
        super(x, y, D);
        POINTS = 500;
        this.sprite_type = "enemy";
        this.ship = s;
        DIFFICULTY = D;
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
        initAlien();
        
    }
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                fire_rate = 100;  //how often lasers are fired
                health = 3; // how many times they can be hit before dying
                missile_speed = -8; // how fast their lasers move
                move_speed = 4; // how fast the alien moves
                break;
            case "hard":
                fire_rate = 50;  //how often lasers are fired
                health = 4; // how many times they can be hit before dying
                missile_speed = -10; // how fast their lasers move
                move_speed = 6; // how fast the alien moves
                break;
            case "unforgiving":
                fire_rate = 25;  //how often lasers are fired
                health = 5; // how many times they can be hit before dying
                missile_speed = -14; // how fast their lasers move
                move_speed = 8; // how fast the alien moves
                break;
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        behavior = 0;
        
        loadImage("src/resources/BomberC.png");
        getImageDimensions();
        
    }
    
    /**
     *  Alien's AI
     * currently moves it to the left until it reaches the end of the screen
     * then pops it back to the right side of the screen
     * We will program some more interesting AI here
     */
    @Override
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
                    y += move_speed / 3;
                }
                else
                {
                    y -= move_speed / 3;
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