
package FinalQuest;

import java.util.List;

/**
 * Alien child of our sprite class. 
 * All alien behavior is based on this class
 * 
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public abstract class Alien extends Sprite {
    
    /**
     * POINTS are the value of the alien
     */
    protected int POINTS;
    private final int INITIAL_X = 1280;

    /**
     * Game difficulty affects speed and toughness of the aliens
     */
    protected String DIFFICULTY;

    /**
     * Type of sprite: boss, enemy, powerup, missile or player
     */
    protected String sprite_type;

    /**
     * how often the sprite fires (if it fires at all)
     */
    protected int laser_rate;

    /**
     * counter to determine when a sprite will fire
     */
    protected int fire_count;

    /**
     * how quickly they shoot
     */
    protected int fire_rate;

    /**
     * how much damage the alien can take before being destroyed
     */
    protected int health;

    /**
     * how fast the alien moves
     */
    protected int move_speed;

    /**
     *list of missiles the sprite has fired
     */
    protected List<Missile> missiles;

    /**
     * how fast the sprite's missiles are
     */
    protected int missile_speed;

    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D
     */
    public Alien(int x, int y, String D) {
        super(x, y);
        this.POINTS = 100;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        initAlien();
    }

    /**
     * Init our alien by assigning it an image and getting it's dimensions
    */
    private void initAlien() {

        loadImage("src/resources/Bomber.png");
        getImageDimensions();
    }
    
    /**
     * This is just an accessor for the alien's point value
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
     * see what type the sprite is
     * @return  a string containing the sprite's type
     */
    @Override
    public String getType()
    {
        return sprite_type;
    }
    
    /**
     * Create a missile when activated. 
     */
    @Override
    public void fire() {
        
        missiles.add(new Missile(x-width, y + height / 2, missile_speed,0));
    }
    
    /**
     *  Alien's AI
     * currently moves it to the left until it reaches the end of the screen
     * then pops it back to the right side of the screen
     * Mostly just a filler as our other aliens have more interesting AI
     */
    @Override
    public void move() {

        if (x < 0  - width) {
            x = INITIAL_X;
        }

        x -= 2;
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health <= 0 it should be destroyed
     * otherwise it makes a sound and takes some damage
     * @return  the alien's current health
     */
    @Override
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
    
}