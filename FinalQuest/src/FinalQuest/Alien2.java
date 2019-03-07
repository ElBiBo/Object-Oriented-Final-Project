/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package FinalQuest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Another alien. Slightly tougher. Behavior includes:
 * - Moves in a straight line from right to left
 * - Fires 3 shots to the left (diagonal up/down and straight ahead), once every x seconds
 * - If hit once, it makes a damage sound
 * - If hit twice, it is destroyed (player gains 300 points)
 * - Will fly off the screen to the left if not destroyed
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien2 extends Sprite {
    
    private final int INITIAL_X = 1280;
    private final int POINTS = 300;
    private int laser_rate;
    private int fire_count;
    private int fire_rate;
    private int health;
    private int move_speed;
    private List<Missile> missiles;
    private int missile_speed;
    private int vert_adjust;
    private final String DIFFICULTY;
    private String sprite_type;
    
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien2(int x, int y, String D) {
        super(x, y);
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
        super(x, y);
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = s;
        initAlien();
    }
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        if (DIFFICULTY == "normal")
        {
            fire_rate = 400;  //how often lasers are fired
            health = 2; // how many times they can be hit before dying
            move_speed += 0; // how fast the alien moves
            missile_speed = (move_speed+3)*-1; // how fast their lasers move
            vert_adjust = 1; // how much the missile spreads
        }
        else if (DIFFICULTY == "hard")
        {
            fire_rate = 300;  //how often lasers are fired
            health = 2; // how many times they can be hit before dying
            move_speed += 2; // how fast the alien moves
            missile_speed = (move_speed+3)*-1; // how fast their lasers move
            vert_adjust = 2; // how much the missile spreads
        }
        else if (DIFFICULTY == "unforgiving")
        {
            fire_rate = 100;  //how often lasers are fired
            health = 3; // how many times they can be hit before dying
            move_speed += 4; // how fast the alien moves
            missile_speed = (move_speed+6)*-1; // how fast their lasers move
            vert_adjust = 3;// how much the missile spreads
        }
        
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/BomberB.png");
        getImageDimensions();
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health <= 0 it should be destroyed
     * otherwise it makes a sound and takes some damage
     * @return  the alien's current health
     */
    public int damage(){
        health -= 1;
        if (health > 0)
        {
            SoundEffect.ALIEN_HIT.play();
        }
        else
        {
            SoundEffect.ALIEN_EXPLODE.play();
        }
        return health;
    }
    
    /**
     * This is just an accessor for the ship's score
     * @return  the number of points destroying the alien is worth
     */
    public int getPoints(){
        return POINTS;
    }
    
    /**
     * Returns a list of missiles that have been fired.
     * Used to find their coordinates for drawing them
     * @return  list of missiles
     */
    public List<Missile> getMissiles() {
        return missiles;
    }
    
    /**
     * Create a missile when activated. No more than num_missiles
     * missiles can be fired without one of the previous missiles being
     * destroyed first
     */
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
    
    /**
     * see what type the sprite is
     * @return  a string containing the sprite's type
     */
    public String getType()
    {
        return sprite_type;
    }
}

