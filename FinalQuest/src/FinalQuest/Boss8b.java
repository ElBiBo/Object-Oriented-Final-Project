
package FinalQuest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 8's boss. 
 * This serves as protection for the main ship. It circles around and fires 
 * lasers
 * @author Marco Tacca
 */
public class Boss8b extends Boss {
    
    private final int POINTS = 3000;
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
    private String attack_mode = "moving";
    private String fire_mode = "regular";
    private int direction = 1;
    private int step = 0;
    private int delay;
    private int count = 0;
    private String sprite_type;
    private String status;
    private Explosion boom;
    private Boss main_ship;
    private double ORIGIN_X;
    private double ORIGIN_Y;
    private double RADIUS;
    private double angle;
    
    
    /**
     * Constructor
     * @param ship Used to get info about the boss ship and coordinate movement
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Boss8b(Boss ship, String D, double a) {
        super(ship.getX(), ship.getY()+20, D);
        this.p_direction = 1;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "miniboss";
        main_ship = ship;
        DIFFICULTY = D;
        move_speed = 8;
        angle = Math.toRadians(a);
        RADIUS = 150;
        initAlien();
    }
    
    
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                fire_rate = 80;  //how often lasers are fired
                health = 10; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                delay = 90; // how long it takes for the boss to charge
                
                break;
            case "hard":
                fire_rate = 60;  //how often lasers are fired
                health = 20; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                delay = 60;
                break;
            case "unforgiving":
                fire_rate = 40;  //how often lasers are fired
                health = 40; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                delay = 30;
                break;
        }
        curve = 0;
        max_health = health;
        crash_health = max_health/3*2;
        fire_count = 0;
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss8b.png");
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
        fire_count +=1;
        /*if (fire_count % (fire_rate*5) == 0)
        {
            reinforcement_list.add(new Alien1(x+width/2, y+height/2, DIFFICULTY,"launch"));
        }*/
        
        switch (fire_mode)
        {
            case "regular":
                if (fire_count % (fire_rate/3) == 0)
                {
                    missiles.add(new Missile(x+15, y +70 , missile_speed,0));
                    missiles.add(new Missile(x+15, y+height-74 , missile_speed,0));
                    missiles.add(new Missile(x+20, y +30 , missile_speed,0));
                    missiles.add(new Missile(x+20, y+height-34 , missile_speed,0));
                }
                if (fire_count % fire_rate*10 == 0)
                {
                    attack_mode = "moving";
                }
                break;
            case "spread":
                missiles.add(new Missile(x+15, y+height/2-4 , -5,curve,"src/resources/plasma_bolt.png"));
                curve+=p_direction;
                if ((curve>5 && p_direction >0)|| (curve<-5 && p_direction <0))
                {
                    p_direction*=-1;
                }
                if (fire_count % fire_rate*10 == 0)
                {
                    attack_mode = "moving";
                }
                break;
            case "seek":
                if (fire_count % fire_rate == 0)
                {
                    missiles.add(new Seeker(x+15,y+height/2-4,Stage.spaceship));
                }
                if (fire_count % fire_rate*2 == 0)
                {
                    attack_mode = "moving";
                }
                break;
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
            case "moving":
                moving();
                break;
            case "fire":
                //fire();
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
     * The boss moves up and down, firing it's missiles
     */
    public void moving(){
        ORIGIN_X = main_ship.getX() + 64 - (width/2);
        ORIGIN_Y = main_ship.getY() + 72 - (width/2);
        
        angle +=(double) move_speed/200;
        x = (int) Math.round(ORIGIN_X + cos(angle)*RADIUS);
        y = (int) Math.round(ORIGIN_Y + sin(angle)*RADIUS);
    }
    
    public void attack_mode()
    {
        attack_mode = "fire";
        int attack  = ThreadLocalRandom.current().nextInt(5);
        switch (attack)
        {
            case 0: case 3:
                fire_mode = "regular";
                break;
            case 1: case 4:
                fire_mode = "spread";
                break;
            case 2:
                fire_mode = "seek";
                break;
                        
        }
    }
    
    /**
     *  boss zooms across the screen, attempting to crash into the player
     */
    public void crash()
    {
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
            y = 480-height/2;
            attack_mode = "entry";
            step = 0;
            fire_count = 0;
        }
    }
    
    public void blowup()
    {
        step++;
        if (step <= 25)
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
        int y_pos = ThreadLocalRandom.current().nextInt(y, y+height-16);
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
     * sets status to exploding
     */
    public void explode()
    {
        status = "gone";
        attack_mode = "blowup";
        sprite_type = "exploding"; 
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