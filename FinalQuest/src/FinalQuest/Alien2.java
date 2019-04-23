package FinalQuest;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Tough shot alien
 * Another alien. Slightly tougher. Behavior includes:
 * - Moves in a straight line from right to left
 * - Fires 3 shots to the left (diagonal up/down and straight ahead), once every x seconds
 * - If hit once, it makes a damage sound
 * - If hit twice, it is destroyed (player gains 300 points)
 * - Will fly off the screen to the left if not destroyed
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien2 extends Alien {
    
    private int vert_adjust;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien2(int x, int y, String D) {
        super(x, y, D);
        POINTS = 300;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = 2;
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 2
     */
    public Alien2(int x, int y, String D, int s) {
        super(x, y, D);
        POINTS = 300;
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
                fire_rate = 400;  //how often lasers are fired
                health = 2; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                vert_adjust = 1; // how much the missile spreads
                break;
            case "hard":
                fire_rate = 300;  //how often lasers are fired
                health = 2; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                vert_adjust = 2; // how much the missile spreads
                break;
            case "unforgiving":
                fire_rate = 100;  //how often lasers are fired
                health = 3; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                vert_adjust = 3;// how much the missile spreads
                break;
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/BomberB.png");
        getImageDimensions();
    }
    
    /**
     * Create a missile when activated.
     */
    @Override
    public void fire() {
        
        missiles.add(new Missile(x-width, y-2 + height / 2, missile_speed,0));
        missiles.add(new Missile(x-width, y-2 + height / 2, missile_speed,vert_adjust));
        missiles.add(new Missile(x-width, y-2 + height / 2, missile_speed,vert_adjust*-1));
    }
    
    /**
     *  Alien's AI
     * Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires a triple shot of.
     */
    @Override
    public void move() {
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        x -= move_speed;
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
}

