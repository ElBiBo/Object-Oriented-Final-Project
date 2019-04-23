
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 3's boss. he flies up and down, speeding up as he takes damage
 * At first he fires a double laser, tries to crash into the player, after which
 * comes a massive plasma spread
 * a trio of Alien2's come through to help from time to time
 * @author Marco Tacca
 */
public class Boss3 extends Boss {
    
    private final int INITIAL_X = 1280;
    private final int POINTS = 5000;
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
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Boss3(int x, int y, String D) {
        super(x, y, D);
        this.p_direction = 1;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
        DIFFICULTY = D;
        move_speed = 4;
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 4
     */
    public Boss3(int x, int y, String D, int s) {
        super(x, y, D);
        this.p_direction = 1;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
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
                fire_rate = 20;  //how often lasers are fired
                health = 60; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                delay = 180; // how long it takes for the boss to charge
                reinforce = 400;
                break;
            case "hard":
                fire_rate = 10;  //how often lasers are fired
                health = 90; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                delay = 120;
                reinforce = 200;
                break;
            case "unforgiving":
                fire_rate = 5;  //how often lasers are fired
                health = 120; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                delay = 60;
                reinforce = 100;
                break;
        }
        curve = 0;
        max_health = health;
        crash_health = max_health/3*2;
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss3.png");
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
            status = "exploding";
            
        }
        return health;
    }
    
    /**
     * This is just an accessor for the boss's point value
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
     * Create a missile when activated. Sometimes fires regular missiles. 
     * Sometimes fires plasma sprays
     */
    @Override
    public void fire() {
        fire_count +=1;
        switch (fire_mode) {
            case "regular":
                if (fire_count % fire_rate == 0)
                {
                    missiles.add(new Missile(x-width+90, y +16 , missile_speed,0));
                    missiles.add(new Missile(x-width+90, y+height -20 , missile_speed,0));
                }   break;
            case "plasma":
                missiles.add(new Missile(x-width+120, y+height/2-4 , -5,curve,"src/resources/plasma_bolt.png"));
                curve+=p_direction;
                if ((curve>5 && p_direction >0)|| (curve<-5 && p_direction <0))
                {
                    p_direction*=-1;
                }   
                break;
        }
    }
    
    
    /**
     *  Alien's AI
     * Alternates between moving up and down, shooting and trying to crash into the player
     */
    @Override
    public void move()
    {
        count++;
        switch (attack_mode){
            case "entry":
                entry();
                break;
            case "gunning":
                gunning();
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
    @Override
    public void entry(){
        x -= move_speed;
        fire_mode = "plasma";
        if (x<=1150)
        {
            attack_mode = "gunning";
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
     * The boss moves up and down, firing it's missiles
     */
    public void gunning(){
        fire();
        switch (fire_mode) {
            case "regular":
                y += move_speed*direction;
                if (y < 50)
                {
                    direction *= -1;
                }   if (y > 750)
                {
                    direction *= -1;
                }   break;
            case "plasma":
                if (fire_count == 100)
                {
                    fire_mode = "regular";
                }   break;
        }
        if (crash_health> health)
        {
            attack_mode = "crash";
            crash_health -= 15;
        }
        if (health < max_health/3 && fire_mode == "regular")
        {
            if (1 == ThreadLocalRandom.current().nextInt(1, 400))
            {
                int choice = ThreadLocalRandom.current().nextInt(1, 4);
                if (choice == 1)
                    attack_mode = "crash";
                else
                {
                    fire_mode = "plasma";
                    fire_count = 0;
                }
            }
        }
    }
    
    /**
     *  boss zooms across the screen, attempting to crash into the player
     */
    @Override
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
            y = 450;
            attack_mode = "entry";
            step = 0;
            fire_count = 0;
        }
    }
    
     /**
     * Explosion animation for the boss
     */
    @Override
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
     * generates explosions for the boss
     */
    private void makeBoom()
    {
        SoundEffect.ALIEN_EXPLODE.play();
        int y_pos = ThreadLocalRandom.current().nextInt(y, y+height-32);
        int x_pos = ThreadLocalRandom.current().nextInt(x, x+width-32);
        boom = new Explosion(x_pos,y_pos);
    }
    
    /**
     * When the ship explodes, a series of explosions are made. This returns
     * them.
     * @return  null if there are no explosions. An explosion otherwise
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
     * whenever new reinforcements for the boss are needed, this creates them
     */
    @Override
    public void update_reinforcements()
    {
        switch (DIFFICULTY) {
            case "normal":
            {
                int ypos = ThreadLocalRandom.current().nextInt(50, 890);
                reinforcement_list.add(new Alien2(1300, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1400, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1500, ypos, DIFFICULTY,10));
                break;
            }
            case "hard":
            {
                int ypos = ThreadLocalRandom.current().nextInt(50, 420);
                reinforcement_list.add(new Alien2(1300, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1400, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1500, ypos, DIFFICULTY,10));
                ypos = ThreadLocalRandom.current().nextInt(470, 890);
                reinforcement_list.add(new Alien2(1300, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1400, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1500, ypos, DIFFICULTY,10));
                break;
            }
            case "unforgiving":
            {
                int ypos = ThreadLocalRandom.current().nextInt(50, 280);
                reinforcement_list.add(new Alien2(1300, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1400, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1500, ypos, DIFFICULTY,10));
                ypos = ThreadLocalRandom.current().nextInt(330, 600);
                reinforcement_list.add(new Alien2(1300, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1400, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1500, ypos, DIFFICULTY,10));
                ypos = ThreadLocalRandom.current().nextInt(650, 890);
                reinforcement_list.add(new Alien2(1300, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1400, ypos, DIFFICULTY,10));
                reinforcement_list.add(new Alien2(1500, ypos, DIFFICULTY,10));
                break;
            }
        }
    }
    
    /**
     * Used to add reach reinforcement individually to an array on board.
     * Assuming there are any to return, of course
     * @return  null if there are no reinforcements, otherwise the next alien
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