
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 4's boss. 
 * This boss relies less on shooting and more on maneuvering.
 * It will fly up and down, firing off a plasma spread whenever it is in 
 * the middle of the screen. When it gets to the top or bottom edges it will
 * fly forward. It's delay before flying forward is much lower than previous
 * bosses. It will also have far more enemy ships coming through, which are a
 * mixture of alien1's and alien2's. It will also occasionally deploy a random powerup
 * @author Marco Tacca
 */
public class Boss4 extends Boss {
    
    private final int INITIAL_X = 1280;
    private final int POINTS = 6000;
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
    public Boss4(int x, int y, String D) {
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
    public Boss4(int x, int y, String D, int s) {
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
        if (DIFFICULTY == "normal")
        {
            fire_rate = 20;  //how often lasers are fired
            health = 60; // how many times they can be hit before dying
            move_speed += 0; // how fast the alien moves
            missile_speed = (move_speed+3)*-1; // how fast their lasers move
            delay = 90; // how long it takes for the boss to charge
            reinforce = 400;
        }
        else if (DIFFICULTY == "hard")
        {
            fire_rate = 10;  //how often lasers are fired
            health = 90; // how many times they can be hit before dying
            move_speed += 2; // how fast the alien moves
            missile_speed = (move_speed+3)*-1; // how fast their lasers move
            delay = 60;
            reinforce = 200;
        }
        else if (DIFFICULTY == "unforgiving")
        {
            fire_rate = 5;  //how often lasers are fired
            health = 120; // how many times they can be hit before dying
            move_speed += 4; // how fast the alien moves
            missile_speed = (move_speed+6)*-1; // how fast their lasers move
            delay = 30;
            reinforce = 100;
        }
        curve = 0;
        max_health = health;
        crash_health = max_health/3*2;
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss4.png");
        getImageDimensions();
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
     * This is just an accessor for the alien's point value
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
        fire_count +=1;
        
        if (fire_mode == "regular" && attack_mode == "crash")// && step > delay)
        {
            if (fire_count % fire_rate == 0)
            {
                missiles.add(new Missile(x-10, y +96 , missile_speed*4,0));
                missiles.add(new Missile(x-10, y+height -103 , missile_speed*4,0));
            }
        }
        else if (fire_mode == "plasma")
        {
            missiles.add(new Missile(x-width+120, y+height/2-4 , -5,curve,"src/resources/plasma_bolt.png"));
            curve+=p_direction;
            if ((curve>5 && p_direction >0)|| (curve<-5 && p_direction <0))
            {
                p_direction*=-1;
            }
        }
    }
    
    
    /**
     *  Alien's AI
     * Swaps between moving around to avoid the player, to crashing into the player
     * and firing at the player
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
        fire_mode = "plasma";
        if (x<=1150)
        {
            attack_mode = "moving";
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
    public void moving(){
        fire();
        if (fire_mode == "regular")
        {
            y += move_speed*direction;
            if (y < 0 && direction < 0)
            {
                attack_mode = "crash";
                direction *= -1;
            }
            else if (y > 650 && direction > 0)
            {
                direction *= -1;
                attack_mode = "crash";
            }
        }
        else if (fire_mode == "plasma")
        {
            if (fire_count == 100)
            {
                fire_mode = "regular";
            }
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
    public void crash()
    {
        fire_mode = "regular";
        fire();
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
            y = 300;
            attack_mode = "entry";
            step = 0;
            fire_count = 0;
        }
    }
    
     /**
     * Explosion animation for the boss
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
     * generates little explosions all over the boss when it dies
     */
    private void makeBoom()
    {
        SoundEffect.ALIEN_EXPLODE.play();
        int y_pos = ThreadLocalRandom.current().nextInt(y, y+height-32);
        int x_pos = ThreadLocalRandom.current().nextInt(x, x+width-32);
        boom = new Explosion(x_pos,y_pos);
    }
    
    /**
     * lets board know that the boss is exploding for purposes of status
     * change. Returns an explosion to draw if it is exploding, null otherwise
     * @return null or an explosion
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
     /**
     * Aliens are randomly generated to act as backup for the boss
     */
    public void update_reinforcements()
    {
        int ypos;
        int alien_type;
        int aliens = 0;
        if (DIFFICULTY == "normal")
        {
            aliens = 3;
        }
        else if (DIFFICULTY == "hard")
        {
            aliens = 6;
        }
        else if (DIFFICULTY == "unforgiving")
        {
            aliens = 9;
        }
        for (int i = 0; i<aliens; i++)
        {
            ypos = ThreadLocalRandom.current().nextInt(50, 890);
            alien_type = ThreadLocalRandom.current().nextInt(2);
            if (alien_type == 0)
            {
                reinforcement_list.add(new Alien1(1300+i*100, ypos, DIFFICULTY,10));
            }
            else
            {
                reinforcement_list.add(new Alien2(1300+i*100, ypos, DIFFICULTY,10));
            }
        }
        if (count % (reinforce*5) == 0)
        {
            ypos = ThreadLocalRandom.current().nextInt(50, 890);
            reinforcement_list.add(new PowerUp(1200, ypos));
        }
    }
    /**
     * When aliens have been generated, this lets Board know they are ready to go
     * returns an alien if there is an alien, otherwise it returns null
     * @return An alien sprite, or null
     */
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
}