package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

/**
 * Targeting Alien
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
    private final SpaceShip ship;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien6(int x, int y, String D) {
        super(x, y, D);
        this.POINTS = 500;
        this.sprite_type = "enemy";
        this.DIFFICULTY = D;
        this.move_speed = 4;
        this.ship = Stage.spaceship;
        initAlien();
        
    }
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s adjust the speed of the ship
     */
    public Alien6(int x, int y, String D, int s) {
        super(x, y, D);
        this.POINTS = 500;
        this.sprite_type = "enemy";
        this.DIFFICULTY = D;
        this.move_speed = s;
        this.ship = Stage.spaceship;
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
                move_speed = move_speed+0; // how fast the alien moves
                missile_speed = -4-move_speed; // how fast their lasers move
                break;
            case "hard":
                fire_rate = 50;  //how often lasers are fired
                health = 4; // how many times they can be hit before dying
                move_speed = move_speed+2; // how fast the alien moves
                missile_speed = -4-move_speed; // how fast their lasers move
                break;
            case "unforgiving":
                fire_rate = 25;  //how often lasers are fired
                health = 5; // how many times they can be hit before dying
                move_speed = move_speed+4; // how fast the alien moves
                missile_speed = -8-move_speed; // how fast their lasers move
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
     * Moves from right to left, tracking the player's vertical position as it
     * fires.
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
                    if (move_speed/3 == 0)
                        y++;
                }
                else
                {
                    y -= move_speed / 3;
                    if (move_speed/3 == 0)
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