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
        initMissile();
    }
    
    /**
     * Initiate our missile
     *
     */
    private void initMissile() {
        
        if (missile_speed > 0)// sprite image
        {
            loadImage("src/resources/missile.png");
        }
        else
        {
            loadImage("src/resources/enemy_missile.png");
        }
        getImageDimensions(); // find the size of our image to use for collisions
    }
    
    public void move() {
        
        x += missile_speed; //move missile straight to the right
        y += dy;
        
        if (x > BOARD_WIDTH) //missile gets destroyed if it goes off the screen
            visible = false;
    }
}