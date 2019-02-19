
package FinalQuest;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * 
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class Alien extends Sprite {

    private final int INITIAL_X = 1280;
    private final String DIFFICULTY;

    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     */
    public Alien(int x, int y, String D) {
        super(x, y);
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
     *  Alien's AI
     * currently moves it to the left until it reaches the end of the screen
     * then pops it back to the right side of the screen
     * We will program some more interesting AI here
     */
    public void move() {

        if (x < 0  - width) {
            x = INITIAL_X;
        }

        x -= 2;
    }
}