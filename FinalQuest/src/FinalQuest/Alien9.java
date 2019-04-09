
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * These "aliens" are actually surface defense missiles fired from the alien
 * planet at our would-be hero.
 * Behavior should be:
 * - Rapidly fly upwards
 * - be destroyed with 1 hit
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 100 points if destroyed by the player
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien9 extends Alien {
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien9(int x, int y, String D) {
        super(x, y, D);
        POINTS = 200;
        sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = 6;
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 2
     */
    public Alien9(int x, int y, String D, int s) {
        super(x, y, D);
        POINTS = 100;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = s;
        initAlien();
    }
    
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                health = 1; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                break;
            case "hard":
                health = 1; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                break;
            case "unforgiving":
                health = 2; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                break;
        }
        
        missiles = new ArrayList<>();
        loadImage("src/resources/Fighter4.png");
        getImageDimensions();
    }
    
    /**
     *  Alien's AI
     * All the missile does is fly straight up. Simple, but effective
     */
    @Override
    public void move() {
        y-=move_speed;
        if (y < 0-height) //alien gets destroyed if it goes off the screen
            visible = false;
    }
    
}