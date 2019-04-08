
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * Kamikaze alien!
 * Behavior should be:
 * - Rapidly fly towards the player
 * - be destroyed with 3 hits
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 300 points if destroyed by the player
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien8 extends Alien {
    private int y_speed;
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien8(int x, int y, String D) {
        super(x, y, D);
        POINTS = 300;
        sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = 6;
        y_speed =0;
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 2
     */
    public Alien8(int x, int y, String D, int s) {
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
                fire_rate = 500;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "hard":
                fire_rate = 400;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "unforgiving":
                fire_rate = 300;  //how often lasers are fired
                health = 2; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                break;
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Fighter2.png");
        getImageDimensions();
    }
    
    /**
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    @Override
    public void move() {
        x-=move_speed;
        y -= y_speed/5;
        if (Stage.spaceship.getY()+ 19 < y +10)
        {
            y_speed++;
            if (y_speed > 25)
            {
                y_speed = 25;
            }
        }
        else
        {
            y_speed--;
            if (y_speed < -25)
            {
                y_speed = -25;
            }
        }
        
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
    
}