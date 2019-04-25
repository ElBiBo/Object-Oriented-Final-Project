package FinalQuest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Mtacc
 */
public abstract class Boss extends Sprite {
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
    private String status;
    private Explosion boom;
    
    /**
     * Constructor
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param D game difficulty "normal", "hard" or "unforgiving"
     */
    public Boss(int x, int y, String D) {
        super(x, y);
        this.sprite_type = "boss";
        this.boom = null;
        this.status = "good";
        DIFFICULTY = D;
        initAlien();
    }
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        max_health = health;
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss1.png");
        getImageDimensions();
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health less than or equal to 0 it should be destroyed
     * otherwise it makes a sound and takes some damage
     * @return  the alien's current health
     */
    @Override
    public int damage(){
        if (attack_mode != "entry")
        {
            health -= 1;
        }
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
    @Override
    public int getPoints(){
        return POINTS;
    }
    
    /**
     * Returns a list of missiles that have been fired.
     * Used to find their coordinates for drawing them
     * @return  list of missiles
     */
    @Override
    public List<Missile> getMissiles() {
        return missiles;
    }
    
    /**
     * Create a missile when activated. No more than num_missiles
     * missiles can be fired without one of the previous missiles being
     * destroyed first
     */
    @Override
    public void fire() {
        switch (fire_mode) {
            case "regular":
                missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,0));
                switch (DIFFICULTY) {
                    case "hard":
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-1));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,1));
                        break;
                    case "unforgiving":
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-4));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,4));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-2));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,2));
                        break;
                }
                break;
            case "double time":
                switch (DIFFICULTY) {
                    case "normal":
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,0));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-1));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,1));
                        break;
                    case "hard":
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,0));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-1));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,1));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-2));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,2));
                        break;
                    case "unforgiving":
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-4));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,4));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-2));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,2));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-6));
                        missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,6));
                        break;
                }
                break;
        }
    }
    
    
    /**
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    @Override
    public void move()
    {
        count++;
        switch (attack_mode){
            case "entry":
                entry();
                break;
            case "crash":
                crash();
                break;
            case "blowup":
                blowup();
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
     * Animation for our boss exploding
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
     * Plays an explosion and makes a random explosion animation
     */
    private void makeBoom()
    {
        SoundEffect.ALIEN_EXPLODE.play();
        int y_pos = ThreadLocalRandom.current().nextInt(y, y+height-32);
        int x_pos = ThreadLocalRandom.current().nextInt(x, x+width-32);
        boom = new Explosion(x_pos,y_pos);
    }
    
    /**
     * See if there is an explosion sprite ready to go. Returns null if
     * there isn't or an explosion sprite if there is. Once the explosion sprite
     * is taken, this returns null
     * 
     * @return Null or an explosion sprite
     */
    @Override
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
    
    /**
     * Occasionally ships show up to help the boss. 
     * This creates them
     */
    public void update_reinforcements()
    {
        switch (DIFFICULTY) {
            case "normal":
                {
                    int ypos = ThreadLocalRandom.current().nextInt(50, 890);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
                    break;
                }
            case "hard":
                {
                    int ypos = ThreadLocalRandom.current().nextInt(50, 420);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
                    ypos = ThreadLocalRandom.current().nextInt(470, 890);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10));
                    break;
                }
            case "unforgiving":
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
                    break;
                }
        }
    }
    
    /**
     * Checks to see if any reinforcements are ready to go
     * if there are any reinforcements ready to go, it returns the latest
     * alien. Otherwise it returns null.
     * @return an alien or null
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
    @Override
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
    @Override
    public String getStatus()
    {
        return status;
    }
    
    /**
     * see what type the sprite is
     * @return  a string containing the sprite's type
     */
    @Override
    public String getType()
    {
        return sprite_type;
    }
}

