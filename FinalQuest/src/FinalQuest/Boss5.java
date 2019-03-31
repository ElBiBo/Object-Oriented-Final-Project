
package FinalQuest;

import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 5's boss. 
 * This boss relies less on shooting and more on maneuvering.
 * It will fly up and down, firing off a plasma spread whenever it is in 
 * the middle of the screen. When it gets to the top or bottom edges it will
 * fly forward. It's delay before flying forward is much lower than previous
 * bosses. It will also have far more enemy ships coming through, which are a
 * mixture of alien1's and alien2's. It will also occasionally deploy a random powerup
 * @author Marco Tacca
 */
public class Boss5 extends Boss {
    
    private final int INITIAL_X = 1280;
    private final int POINTS = 7000;
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
    private int seekers;
    private List<Sprite> reinforcement_list = new ArrayList<>();
    private int count = 0;
    private String sprite_type;
    private String status;
    private Explosion boom;
    private SpaceShip ship;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Boss5(int x, int y, String D) {
        super(x, y, D);
        this.seekers = 3;
        ship = Stage.spaceship;
        this.p_direction = 1;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
        DIFFICULTY = D;
        move_speed = 4;
        initAlien();
    }
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        if (DIFFICULTY == "normal")
        {
            fire_rate = 100;  //how often lasers are fired
            health = 60; // how many times they can be hit before dying
            move_speed += 0; // how fast the alien moves
            missile_speed = (move_speed+3)*-1; // how fast their lasers move
            delay = 90; // how long it takes for the boss to charge
            reinforce = 400;
        }
        else if (DIFFICULTY == "hard")
        {
            fire_rate = 50;  //how often lasers are fired
            health = 90; // how many times they can be hit before dying
            move_speed += 2; // how fast the alien moves
            missile_speed = (move_speed+3)*-1; // how fast their lasers move
            delay = 60;
            reinforce = 200;
        }
        else if (DIFFICULTY == "unforgiving")
        {
            fire_rate = 25;  //how often lasers are fired
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
        loadImage("src/resources/Boss5.png");
        getImageDimensions();
    }
  
        /**
     * Get the coordinates of the sprite's bounding box.
     * This is used to check for collision with other sprites.
     * @return  returns the bounding box for our sprite
     */
    public Rectangle getBounds() {
        return new Rectangle(x+80, y, width-80, height);
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
     * Fire the guns!
     */
    public void fire() {
        fire_count +=1;
        if (fire_mode == "regular")// && attack_mode == "crash")// && step > delay)
        {
            if (fire_count % fire_rate == 0)
            {
                missiles.add(new Missile(x+65, y +134 , missile_speed,0));
                missiles.add(new Missile(x+65, y+height -141 , missile_speed,0));
                missiles.add(new Missile(x+135, y +108 , missile_speed,0));
                missiles.add(new Missile(x+135, y+height -115 , missile_speed,0));
            }
        }
        else if (fire_mode == "seeker" && fire_count % 30 == 0)
        {
            missiles.add(new Seeker(x+50, y+height/2-4, ship));
            if (fire_count >= seekers*30)
            {
                fire_count = 0;
                fire_mode = "regular";
                attack_mode = "moving";
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
            case "moving":
                moving();
                break;
            case "seeking":
                seeking();
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
            attack_mode = "moving";
            
        }
    }
    
    /**
     * The ship moves towards the player and unleashes 3 seeker missiles
     */
    public void seeking()
    {
        if (y+height/2 < ship.getY() && y < 960-height)
        {
            y += move_speed;
        }
        else if (y+height/2>ship.getY()+39 && y > 0)
        {
            y -= move_speed;
        }
        else
        {
            fire();
        }
    }
    
    /**
     * The boss moves up and down, firing it's missiles
     */
    public void moving(){
        fire();
            
        if (fire_count < 300 || 1 != ThreadLocalRandom.current().nextInt(1, 500))
        {
            y += move_speed*direction;
            if (y < 0 && direction < 0)
            {
                direction *= -1;
            }
            else if (y > 960-height && direction > 0)
            {
                direction *= -1;
            }
        }
        else 
        {
            fire_mode = "seeker";
            attack_mode = "seeking";
            fire_count = 0;
            
        }
        if (health <= max_health/3)
            {
                seekers = 5;
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
                seekers = 4;
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
    
    private void makeBoom()
    {
        SoundEffect.ALIEN_EXPLODE.play();
        int y_pos = ThreadLocalRandom.current().nextInt(y, y+height-32);
        int x_pos = ThreadLocalRandom.current().nextInt(x, x+width-32);
        boom = new Explosion(x_pos,y_pos);
    }
    
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
     * Sends in alien reinforcements
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
            alien_type = ThreadLocalRandom.current().nextInt(3);
            if (alien_type == 0)
            {
                reinforcement_list.add(new Alien1(1300+i*100, ypos, DIFFICULTY,10));
            }
            else if (alien_type == 1)
            {
                reinforcement_list.add(new Alien2(1300+i*100, ypos, DIFFICULTY,10));
            }
            else
            {
                reinforcement_list.add(new Alien6(1300+i*100, ypos, DIFFICULTY,ship));
            }
        }
        if (count % (reinforce*5) == 0)
        {
            ypos = ThreadLocalRandom.current().nextInt(50, 890);
            reinforcement_list.add(new PowerUp(1200, ypos));
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