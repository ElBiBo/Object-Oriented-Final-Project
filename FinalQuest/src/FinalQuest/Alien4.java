
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * Behavior should be:
 * - fly in a straight line, zag back then continue on, in a Z pattern
 * - occasionally fire 3 shots rapidly
 * - be destroyed with 1 hit
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 200 points if destroyed by the player
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien4 extends Alien {
    
    private int behavior;
    private final int INITIAL_Y;
    private final int bend;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien4(int x, int y, String D) {
        super(x, y, D);
        POINTS = 200;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        
        INITIAL_Y = y;
        bend = 380;
        initAlien();
    }
    
    /**
     * Constructor to change the size of the z pattern
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param b the distance the ship should zag back. default is 380
     */
    public Alien4(int x, int y, String D, int b) {
        super(x, y, D);
        POINTS = 200;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        INITIAL_Y = y;
        bend = b;
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
                missile_speed = -8; // how fast their lasers move
                move_speed = 4; // how fast the alien moves
                break;
            case "hard":
                fire_rate = 150;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                missile_speed = -10; // how fast their lasers move
                move_speed = 6; // how fast the alien moves
                break;
            case "unforgiving":
                fire_rate = 50;  //how often lasers are fired
                health = 2; // how many times they can be hit before dying
                missile_speed = -14; // how fast their lasers move
                move_speed = 8; // how fast the alien moves
                break;
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        behavior = 0;
        
        loadImage("src/resources/Bomber2B.png");
        getImageDimensions();
        
    }
    
    /**
     *  Alien's AI
     * Moves in a z pattern going left, then diagonally back and finally left
     * again (and off the screen). Rapidly fires 3 times in a row between cooldowns
     */
    @Override
    public void move() {
        fire_count +=1;
        if (fire_count % fire_rate == 0 || fire_count % fire_rate == 4 || fire_count % fire_rate == 8)
        {
            fire();
        }
        
        switch(behavior){
            case 0:
                x -= move_speed;
                if (x<500)
                {
                    if (y < 960/2)
                    {
                        behavior = 1;
                    }
                    else
                    {
                        behavior = 2;
                    }
                }
                break;
            case 1:
                x += move_speed;
                y += move_speed;
                if (y >= INITIAL_Y + bend)
                {
                    behavior = 3;
                }
                break;
            case 2:
                x += move_speed;
                y -= move_speed;
                if (y <= INITIAL_Y - bend)
                {
                    behavior = 3;
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