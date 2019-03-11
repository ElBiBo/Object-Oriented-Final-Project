
package FinalQuest;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Power_up child of our sprite class. Controls the AI of power-ups.
 * Behavior should be:
 * - fly in a straight line
 * - occasionally fire a laser
 * - be destroyed with 1 hit and turns into an orb
 * - be destroyed if it flies off the screen (player gets no points though)
 * @author Gach5
 */
public class Power_up extends Sprite {

    private final int INITIAL_X = 1280;
    public int health = 2;
    private int invincibility_count;
    private int remaining_lives;
    /**
     * Constructor
     * @param x starting x coordinate for the power-up
     * @param y starting y coordinate for the power-up
     */
    public Power_up(int x, int y) {
        super(x, y);
        initPower_up();
       
    }

    /**
     * Init our power-up by assigning it an image and getting it's dimensions
    */
    public void initPower_up() {
       
        loadImage("src/resources/invinsibility_barrel.png");
        getImageDimensions();
    }
    
    /**
     * This damages the barrel and then returns its current health
     * if health == 1 it should turn to an orb
     * @return  the power-up current health
     */
    public int damage(){
        health -= 1;
        if(health == 1){
        loadImage("src/resources/invinsibility_orb.png");
        getImageDimensions();
        }
        
        return health;
    }

     /**
     * Invoked whenever the player touches orb. They gain invincibility.
     */
    private void powerup_pickup(){
        
    }
    
    /**
     * They receive a brief moment of invincibility
     * and respawn in an area where they would collide with an enemy.
     * This function counts down the time the player should be invincible
     * and returns a boolean to provide a visual flashing cue.
     * True means the ship is currently visible
     * False means the ship is currently invisible
     * @return  boolean to determine whether the ship is visible or not during invincibility
     */
    public boolean invincibilityFlash(){
        invincibility_count = 200;
        if (invincibility_count > 0)
        {
            invincibility_count -= 1;
        }
        return ((invincibility_count/5)%2) == 0;
    }
    
    
    /**
     *  A quick check to see if the player is invincible. True if they are, 
     * False if they are not.
     * @return  true if player is invincible, otherwise false
     */
    public boolean isInvincibile(){
        return invincibility_count > 0;
    }
    
    /**
     *  Power-up AI
     * currently moves it to the left until it reaches the end of the screen
     * then pops it back to the right side of the screen
     * We will program some more interesting AI here
     */
    public void move() {
        x -= 3;

        if (x < 0-width) //power-up gets destroyed if it goes off the screen
            visible = false;
    }
    
  
}
