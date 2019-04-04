
package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 8's boss. 
 * This serves as the main cannon of the boss. It fires pretty constantly and
 * can be destroyed on its own.
 * @author Marco Tacca
 */
public class Boss8a extends Boss {
    
    private final int POINTS = 3000;
    private int laser_rate;
    private int fire_count;
    private int curve;
    private int p_direction;
    private int fire_rate;
    private int health;
    private int move_speed;
    private int direction = 1;
    private int step = 0;
    private List<Missile> missiles;
    private int missile_speed;
    private final String DIFFICULTY;
    private String attack_mode = "moving";
    private String sprite_type;
    private String status;
    private Explosion boom;
    private Boss8 main_ship;
    
    /**
     * Constructor
     * @param ship Used to get info about the boss ship and coordinate movement
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Boss8a(Boss8 ship, String D) {
        super(ship.getX(), ship.getY()+20, D);
        this.p_direction = 1;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "miniboss";
        main_ship = ship;
        DIFFICULTY = D;
        move_speed = 3;
        initAlien();
    }
    
    
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                health = 10; // how many times they can be hit before dying
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "hard":
                health = 20; // how many times they can be hit before dying
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "unforgiving":
                health = 30; // how many times they can be hit before dying
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                break;
        }
        fire_rate = 100;
        curve = 0;
        fire_count = 0;
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss8a.png");
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
     * Fires a spray of plasma
     */
    public void fire() {
        fire_count +=1;
        missiles.add(new Missile(x, y+height/2-4 , -5,curve,"src/resources/plasma_bolt.png"));
        curve+=p_direction;
        if ((curve>5 && p_direction >0)|| (curve<-5 && p_direction <0))
        {
            p_direction*=-1;
        }
        if (fire_count % fire_rate == 0)
        {
            main_ship.attackComplete();
        }        
    }
    
    
    /**
     *  Alien's AI
     * Basically just sticks to the front of the ship, serves as a gun and 
     * blows up when shot
     */
    public void move()
    {
        switch (attack_mode){
            case "moving":
                moving();
                break;
            case "blowup":
                blowup();
                break;
        }
        
    }
    
    /**
     * The miniboss stays right on the tip of the main boss, serving as a main
     * gun until destroyed.
     */
    public void moving(){
        x = main_ship.getX() - 40;
        y = main_ship.getY() + 40;
        if ("fire".equals(main_ship.getAttackMode()))
        {
            fire();
        }
    }
    
    /**
     * Rapidly explode animation
     */
    public void blowup()
    {
        x = main_ship.getX() - 40;
        y = main_ship.getY() + 40;
        step++;
        if (step <= 75)
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
        int y_pos = ThreadLocalRandom.current().nextInt(y, y+height-16);
        int x_pos = ThreadLocalRandom.current().nextInt(x, x+width-32);
        boom = new Explosion(x_pos,y_pos);
    }
    
    /**
     * Once explosions are randomly made, they can be found here for use
     * returns null if there are no explosions to be had
     * @return null if there are no explosions, otherwise an explosion sprite
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
     * sets status to exploding
     */
    @Override
    public void explode()
    {
        main_ship.toggleGuns();
        status = "gone";
        attack_mode = "blowup";
        sprite_type = "exploding"; 
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