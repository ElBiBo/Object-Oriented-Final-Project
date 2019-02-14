package FinalQuest;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Spaceship child of our sprite class. Controls the player's spaceship.
 * 
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
    private int num_missiles = 5;
    private List<Missile> missiles;

    /**
     * Constructor
     * @param x starting x coordinate for the player
     * @param y starting y coordinate for the player
     */
    public SpaceShip(int x, int y) {
        super(x, y);

        initCraft();
    }

    /**
     * Init our player by assigning it an image and getting it's dimensions
     * Also create a list for keeping track of our ship's missiles
     */
    private void initCraft() {
        
        missiles = new ArrayList<>();
        loadImage("src/resources/Assaultboat.png");
        getImageDimensions();
    }

    /**
     * Control the movement of our spaceship. 
     * Also prevents the player from moving off the screen
     */
    public void move() {

        x += dx;
        y += dy;

        //left border
        if (x < 1) {
            x = 1;
        }
        //right border
        if (x > 1280-width) {
            x = 1280-width;
        }
        // top border
        if (y < 1) {
            y = 1;
        }
        // bottom border
        if (y > 960-height) {
            y = 960-height;
        }
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
     * Check for key press events so the player can control the space ship
     * @param e the last key pressed
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) { // fire missile on space
            fire();
        }

        if (key == KeyEvent.VK_LEFT) { // move left
            dx = -3;
        }

        if (key == KeyEvent.VK_RIGHT) { // move right
            dx = 3;
        }

        if (key == KeyEvent.VK_UP) { // move up
            dy = -3;
        }

        if (key == KeyEvent.VK_DOWN) { // move down
            dy = 3;
        }
    }

    /**
     * Create a missile when activated. No more than num_missiles
     * missiles can be fired without one of the previous missiles being 
     * destroyed first
     */
    public void fire() {
        if (missiles.size() < num_missiles)
        {
            missiles.add(new Missile(x + width, y + height / 2));
        }
    }

    /**
     * Check for key release events so the player can control the space ship
     * I am thinking of having a "charging" event for the laser so if you
     * hold it down for a few seconds then release it, it fires off some 
     * cooler missile. Idea for later!
     * @param e the last key pressed
     */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) { //stop moving left when you release left
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) { //stop moving right when you release right
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) { //stop moving up when you release up
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) { //stop moving down when you release down
            dy = 0;
        }
    }
}