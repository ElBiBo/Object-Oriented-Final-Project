
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
    private int fire_count;
    private int p_direction;
    private int fire_rate;
    private int health;
    private int move_speed;
    private List<Missile> missiles;
    private int missile_speed;
    private final String DIFFICULTY;
    private String attack_mode = "moving";
    private String fire_mode = "regular";
    private int direction = 1;
    private int step = 0;
    private int delay;
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
     * @param a the starting angle of the miniboss relative to the main boss
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
                fire_rate = 40;  //how often lasers are fired
                health = 10; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "hard":
                fire_rate = 20;  //how often lasers are fired
                health = 20; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "unforgiving":
                fire_rate = 10;  //how often lasers are fired
                health = 40; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                break;
        }
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
     * Fire a fairly continuous stream of shots
     */
    public void fire() {
        fire_count +=1;
        if (fire_count % fire_rate == 0)
        {
            missiles.add(new Missile(x-10, y+5 , missile_speed,0));
        }
    }
    
    /**
     *  Alien's AI
     * just circles around the boss and fires, or blows up. pretty simple
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
     * The miniboss circles around the main boss, providing covering fire and
     * protection
     */
    public void moving(){
        fire();
        ORIGIN_X = main_ship.getX() + 64 - (width/2);
        ORIGIN_Y = main_ship.getY() + 72 - (width/2);
        
        angle +=(double) move_speed/200;
        x = (int) Math.round(ORIGIN_X + cos(angle)*RADIUS);
        y = (int) Math.round(ORIGIN_Y + sin(angle)*RADIUS);
    }
    
    
    /**
     * Rapidly explode animation
     */
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
    
    /**
     * finds a random place to place an explosion
     */
    private void makeBoom()
    {
        SoundEffect.ALIEN_EXPLODE.play();
        int y_pos = ThreadLocalRandom.current().nextInt(y, y+height-16);
        int x_pos = ThreadLocalRandom.current().nextInt(x, x+width-32);
        boom = new Explosion(x_pos,y_pos);
    }
    
    /**
     * gets the latest explosion to return to the board
     * @return an explosion, if there is one, otherwise null
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