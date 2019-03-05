
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * Behavior should be:
 * - fly in a straight line
 * - occasionally fire a laser
 * - be destroyed with 1 hit
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 100 points if destroyed by the player
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Boss1 extends Sprite {
    
    private final int INITIAL_X = 1280;
    private final int POINTS = 3000;
    private int laser_rate;
    private int fire_count;
    private int fire_rate;
    private int health;
    private int max_health;
    private int move_speed;
    private List<Missile> missiles;
    private int missile_speed;
    private final String DIFFICULTY;
    private String attack_mode = "entry";
    private String fire_mode = "regular";
    private int direction = 1;
    private int step = 0;
    private int delay;
    private int reinforce;
    private List<Sprite> reinforcement_list = new ArrayList<>();
    private int count = 0;
    private String sprite_type;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Boss1(int x, int y, String D) {
        super(x, y);
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = 4;
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 2
     */
    public Boss1(int x, int y, String D, int s) {
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
            fire_rate = 75;  //how often lasers are fired
            health = 50; // how many times they can be hit before dying
            move_speed += 0; // how fast the alien moves
            missile_speed = (move_speed+3)*-1; // how fast their lasers move
            delay = 180; // how long it takes for the boss to charge
            reinforce = 400;
        }
        else if (DIFFICULTY == "hard")
        {
            fire_rate = 30;  //how often lasers are fired
            health = 100; // how many times they can be hit before dying
            move_speed += 2; // how fast the alien moves
            missile_speed = (move_speed+3)*-1; // how fast their lasers move
            delay = 120;
            reinforce = 200;
        }
        else if (DIFFICULTY == "unforgiving")
        {
            fire_rate = 10;  //how often lasers are fired
            health = 150; // how many times they can be hit before dying
            move_speed += 4; // how fast the alien moves
            missile_speed = (move_speed+6)*-1; // how fast their lasers move
            delay = 60;
            reinforce = 100;
        }
        max_health = health;
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss1.png");
        getImageDimensions();
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health <= 0 it should be destroyed
     * otherwise it makes a sound and takes some damage
     * @return  the alien's current health
     */
    public int damage(){
        if (attack_mode != "crash" && attack_mode != "entry")
        {
            health -= 1;
        }
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
        if (fire_mode == "regular")
        {
            missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,0));
            if (DIFFICULTY == "hard")
            {
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-1));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,1));
            }
            else if (DIFFICULTY == "unforgiving")
            {
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-4));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,4));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-2));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,2));
            }
        }
        else if (fire_mode == "double time")
        {
            if (DIFFICULTY == "normal")
            {
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,0));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-1));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,1));
            }
            else if (DIFFICULTY == "hard")
            {
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,0));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-1));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,1));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-2));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,2));
            }
            else if (DIFFICULTY == "unforgiving")
            {
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-4));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,4));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-2));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,2));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-6));
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,6));
            }
        }
    }
    
    
    /**
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    public void move() 
    {
        count++;
        switch (attack_mode){
            case "entry":
                entry();
                break;
            case "slow fire sweep":
                slowFireSweep();
                break;
            case "med fire sweep":
                medFireSweep();
                break;
            case "fast fire sweep":
                fastFireSweep();
                break;
            case "crash":
                crash();
                break;
        }
        // this is used to send out enemy ships as backup
        if (count % reinforce == 0)
        {
            update_reinforcements();
        }
            
    }
    
    /**
     * The boss enters the screen
     */
    public void entry(){
        x -= move_speed;
        if (x<=950)
        {
            
            if (health <= max_health/3)
            {
                attack_mode = "fast fire sweep";
                if (direction <0)
                {
                    direction = -3;
                }
                else
                {
                    direction = 3;
                }
            }
            else if (health <= max_health*2/3)
            {
                attack_mode = "med fire sweep";
                if (direction <0)
                {
                    direction = -2;
                }
                else
                {
                    direction = 2;
                }
            }
            else
            {
                attack_mode = "slow fire sweep";
            }
        }
    }
    
    /**
     * The boss moves up and down, firing it's missiles
     */
    public void slowFireSweep(){
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        y += move_speed*direction;
        if (y < 50)
        {
            direction = 1;
        }
        if (y > 750)
        {
            direction = -1;
        }
        if (health <= max_health/3*2)
        {
            attack_mode = "crash";
            fire_mode = "double time";
            fire_rate = fire_rate/3*2;
        }
    }
    
    /**
     *  boss zooms across the screen, attempting to crash into the player
     */
    public void crash()
    {
        step++;
        if (step <= delay)
        {
            if (step%3 == 0)
            {
                y+= 5*direction;
                direction = direction * -1;
            }
        }
        else if (x>0-width)
        {
            x -= move_speed*4;
        }
        else
        {
            x = 1300;
            attack_mode = "entry";
            step = 0;
        }
    }
    
    /**
     * The boss moves up and down, firing it's missiles a little faster
     */
    public void medFireSweep(){
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        y += move_speed*direction;
        if (y < 50)
        {
            direction = 2;
        }
        else if (y > 750)
        {
            direction = -2;
        }
        if (health <= max_health/3)
        {
            attack_mode = "crash";
            fire_rate = fire_rate/2;
            delay = delay/2;
        }
    }
    
    /**
     *  The boss moves up and down, firing it's missiles a lot faster
     */
    public void fastFireSweep(){
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        y += move_speed*direction;
        if (y < 50)
        {
            direction = 3;
        }
        if (y > 750)
        {
            direction = -3;
        }
        if (count % reinforce/3*2 == 0)
        {
            attack_mode = "crash";
        }
    }
    
    public void update_reinforcements()
    {
        if (DIFFICULTY == "normal")
        {
            int ypos = ThreadLocalRandom.current().nextInt(50, 890);
            reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
        }
        else if (DIFFICULTY == "hard")
        {
            int ypos = ThreadLocalRandom.current().nextInt(50, 420);
            reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
            ypos = ThreadLocalRandom.current().nextInt(470, 890);
            reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
        }
        else if (DIFFICULTY == "unforgiving")
        {
            int ypos = ThreadLocalRandom.current().nextInt(50, 280);
            reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
            ypos = ThreadLocalRandom.current().nextInt(330, 600);
            reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
            ypos = ThreadLocalRandom.current().nextInt(650, 890);
            reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
            reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
        }
    }
    
    public Sprite checkReinforcements()
    {
        if (reinforcement_list.size() <= 0)
        {
            return null;
        }
        else
        {
            return reinforcement_list.remove(0);
        }
    }
}