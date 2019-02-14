package FinalQuest;

/**
 * Controls the logic for our missiles
 * 
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class Missile extends Sprite {

    private final int BOARD_WIDTH = 1280;
    private final int MISSILE_SPEED = 4;

    /**
     * Constructor
     * 
     * @param x starting x coordinate for our missile
     * @param y starting y coordinate for our missile
     */
    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }
    
     /**
     * Initiate our missile 
     * 
     */
    private void initMissile() {
        
        loadImage("src/resources/missile.png"); // sprite image
        getImageDimensions(); // find the size of our image to use for collisions
    }

    public void move() {
        
        x += MISSILE_SPEED; //move missile straight to the right
        
        if (x > BOARD_WIDTH) //missile gets destroyed if it goes off the screen
            visible = false;
    }
}