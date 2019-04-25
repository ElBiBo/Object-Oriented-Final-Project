
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 8's boss. 
 * The final boss is actually a combination of 4 ships. Two protect 
 * the main central ship by circling it while one serves as the ship's
 * main weapon with occasional plasma sprays. The main ship fires regular laser
 * blasts unless it no longer has any defenses remaining. Then it resorts to
 * a heavy barrage of seeker shots.
 * @author Marco Tacca
 */
public class Boss8 extends Boss {
    
    private final int POINTS = 10000;
    private int laser_rate;
    private int fire_count;
    private int curve;
    private int p_direction;
    private int fire_rate;
    private int health;
    private int max_health;
    private int crash_health;
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
    private String status;
    private Explosion boom;
    boolean center = false;
    private double num_ships;
    private boolean gunless = false;
    private boolean toggle = false;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Boss8(int x, int y, String D) {
        super(x, y, D);
        this.p_direction = 1;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
        DIFFICULTY = D;
        this.y = 480-height/2;
        move_speed = 3;
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 4
     */
    public Boss8(int x, int y, String D, int s) {
        super(x, y, D);
        this.p_direction = 1;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
        DIFFICULTY = D;
        this.y = 480-height/2;
        move_speed = s;        
        initAlien();
    }
    
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        
        switch (DIFFICULTY) {
            case "normal":
                fire_rate = 100;  //how often lasers are fired
                health = 60; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                reinforce = 2000;
                num_ships = 2.0;
                break;
            case "hard":
                fire_rate = 50;  //how often lasers are fired
                health = 90; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                reinforce = 1000;
                num_ships = 4.0;
                break;
            case "unforgiving":
                fire_rate = 25;  //how often lasers are fired
                health = 120; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                reinforce = 800;
                num_ships = 8.0;
                break;
        }
        curve = 0;
        max_health = health;
        crash_health = max_health/3*2;
        fire_count = 0;
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss8.png");
        getImageDimensions();
        update_reinforcements();
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health less than or equal to 0 it should be destroyed
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
            status = "exploding";
            
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
     * Fires seekers if no other weapon is available. Also starts launching ships
     * too 
     */
    public void fire() {
        fire_count +=1;
        if (fire_count % (fire_rate/2) == 0)
        {
            reinforcement_list.add(new Alien11(x+width/2, y+height/2, DIFFICULTY,"launch"));
        }
        
        if (fire_count % fire_rate == 0 && attack_mode == "fire" && !toggle)
        {
            missiles.add(new Seeker(x,y+height/2-4,Stage.spaceship));
            toggle = !toggle;
        }
        else if (fire_count % fire_rate == 0 && toggle)
        {
            attack_mode = "moving";
            toggle = !toggle;
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
            case "moving":
                moving();
                break;
            case "fire":
                if (gunless)
                {
                    fire();
                }
                break;
            case "crash":
                crash();
                break;
            case "blowup":
                blowup();
                break;
        }
    }
    
    /**
     * The boss enters the screen
     */
    @Override
    public void entry(){
        x -= move_speed;
        if (x<=1200-width)
        {
            attack_mode = "moving";
        }
    }
    
    /**
     * The boss moves up and down, pausing in the center, bottom and top of the 
     * screen to fire
     */
    public void moving(){
        if (gunless)
        {
            fire();
        }
        y += move_speed*direction;
        if (direction < 0)
        {
            if (y < 130 && !center) 
            {
                direction *= -1;
                center = true;
                attack_mode = "fire";
            }
            else if (y < 480-height/2 && center)
            {
                
                if (ThreadLocalRandom.current().nextInt(2) == 0)
                {
                    direction *= -1;
                }
                center = false;
                attack_mode = "fire";
            }
        }
        else if (direction > 0)
        {
            if (y > 750 && !center)
            {   
                direction *= -1;
                center = true;
                attack_mode = "fire";
            }
            else if (y > 480-height/2 && center)
            {
                if (ThreadLocalRandom.current().nextInt(2) == 0)
                {
                    direction *= -1;
                }
                center = false;
                attack_mode = "fire";
            }
        }
        if (health <= max_health/3)
        {
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
            if (direction <0)
            {
                direction = -2;
            }
            else
            {
                direction = 2;
            }
        }
    }
    
    /**
     * Extended explosion animation!
     */
    public void blowup()
    {
        step++;
        if (step <= 300)
        {
            if (step%3 == 0)
            {
                y+= 5*direction;
                direction = direction * -1;
            }
            if (step%5 == 0)
            {
                
                makeBoom();
            }
        }
        else
        {
            visible = false;
        }
    }
    
    /**
     * Makes random explosions 
     */
    private void makeBoom()
    {
        SoundEffect.ALIEN_EXPLODE.play();
        int y_pos = ThreadLocalRandom.current().nextInt(y, y+height-32);
        int x_pos = ThreadLocalRandom.current().nextInt(x, x+width-32);
        boom = new Explosion(x_pos,y_pos);
    }
    
     /**
     * Once explosions are randomly made, they can be found here for use
     * returns null if there are no explosions to be had
     * @return null if there are no explosions, otherwise an explosion sprite
     */
    public Explosion getBoom()
    {
        if (boom == null)
        {
            return null;
        }
        else
        {
            Explosion tmp = boom;
            boom = null;
            return tmp;
        }
    }
    
    public void update_reinforcements()
    {
        reinforcement_list.add(new Boss8a(this, DIFFICULTY));
        for (double i = 0;i < num_ships;i++)
        {
            reinforcement_list.add(new Boss8b(this, DIFFICULTY, i*(360/num_ships)));
        }
        
    }
    
    /**
     * check to see if any reinforcements should be arriving
     * @return null or the latest reinforcement 
     */
    @Override
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
    
    /**
     * sets status to exploding
     */
    public void explode()
    {
        status = "gone";
        attack_mode = "blowup";
        step = 0;
    }
    
    /**
     * mostly used to make things explode
     * @return  the current status of the sprite
     */
    public String getStatus()
    {
        return status;
    }
    
    /**
     * see what type the sprite is
     * @return  a string containing the sprite's type
     */
    public String getType()
    {
        return sprite_type;
    }
    
    /**
     * Used by boss8a to signal when it is destroyed so the main boss
     * knows when it no longer has a main gun
     */
    public void toggleGuns()
    {
        gunless = !gunless;
    }
    
    /**
     * Used by support ships to synchronize attacks
     * @return  This ship's current attack mode
     */
    public String getAttackMode()
    {
        return attack_mode;
    }
    
    /**
     * used by support ships to indicate their attacks are complete
     */
    public void attackComplete()
    {
        attack_mode = "moving";
    }
}