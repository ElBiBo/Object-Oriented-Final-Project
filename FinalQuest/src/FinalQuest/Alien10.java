
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

/**
 * Barrier ship
 * Alien child of our sprite class. Controls the AI of our alien.
 * This alien forms a wide beam shield, similar to the laser barriers, however 
 * this alien flies rapidly forward while firing missiles. 
 * Behavior should be:
 * - fly in a straight line
 * - occasionally fire a laser
 * - create a large beam shield behind it
 * - be destroyed with 5 hits
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 800 points if destroyed by the player
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien10 extends Alien {
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien10(int x, int y, String D) {
        super(x, y, D);
        POINTS = 800;
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
    public Alien10(int x, int y, String D, int s) {
        super(x, y, D);
        POINTS = 800;
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
                fire_rate = 250;  //how often lasers are fired
                health = 5; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "hard":
                fire_rate = 150;  //how often lasers are fired
                health = 10; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "unforgiving":
                fire_rate = 75;  //how often lasers are fired
                health = 15; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                break;
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Fighter3.png");
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
        laser();
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
    
    /**
     * Fire missiles!
     */
    @Override
    public void fire() {
        
        missiles.add(new Missile(x-width, y + height / 2, missile_speed,0));
    }
    
    /**
     * release the laser barrier!
     */
    public void laser() {
        if (x<1280)
        {
            missiles.add(new Beam(x+50, y, -1,-6,150));
            missiles.add(new Beam(x+50, y+height, 1,-6,150));
        }
    }
}