package FinalQuest;

/**
 * Controls the logic for our missiles
 *
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class Missile extends Sprite {
    
    private final int BOARD_WIDTH = 1280;
    private int missile_speed;
    private int dy;
    private String sprite_type;
    private String m_img;
    
    
    public Missile(int x, int y) {
        super(x, y);
    }
    /**
     * Constructor
     *
     * @param x starting x coordinate for our missile
     * @param y starting y coordinate for our missile
     * @param speed the speed of our missile. Should normally be 4
     * @param direction the direction of our missile. 0 is straight positive is down diagonal and negative is up diagonal
     */
    public Missile(int x, int y, int speed, int direction) {
        super(x, y);
        this.sprite_type = "missile";
        missile_speed = speed;
        dy = direction;
        
        if (missile_speed > 0)// sprite image
        {
            m_img = "src/resources/missile.png";
        }
        else
        {
            m_img ="src/resources/enemy_missile.png";
        }
        initMissile();
    }
    
    /**
     * Constructor
     *
     * @param x starting x coordinate for our missile
     * @param y starting y coordinate for our missile
     * @param speed the speed of our missile. Should normally be 4
     * @param direction the direction of our missile. 0 is straight positive is down diagonal and negative is up diagonal
     * @param img   image desired for the missile, if not default
     */
    public Missile(int x, int y, int speed, int direction, String img) {
        super(x, y);
        this.sprite_type = "missile";
        this.m_img = img;
        missile_speed = speed;
        dy = direction;
        initMissile();
    }
    
    /**
     * Initiate our missile
     *
     */
    private void initMissile() {
        loadImage(m_img);
        getImageDimensions(); // find the size of our image to use for collisions
    }
    
    /**
     * Move that missile!
     */
    @Override
    public void move() {
        
        x += missile_speed; //move missile straight to the right
        y += dy;
        
        if (x > BOARD_WIDTH) //missile gets destroyed if it goes off the screen
            visible = false;
    }
    
    /**
     * Certain missiles have special conditions for destruction
     * @return  regular missiles don't. always return False
     */
    public boolean destroy()
    {
        return false;
    }
}