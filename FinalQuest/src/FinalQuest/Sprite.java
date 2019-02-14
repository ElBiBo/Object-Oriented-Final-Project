package FinalQuest;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Our general sprite parent class
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    /**
     * Constructor
     * @param x starting x coordinate for the sprite
     * @param y starting y coordinate for the sprite
     */
    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
    }

    /**
     * Get the height and width of an image (used for collision)
     */
    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    /**
     * Assigns an image to our sprite
     * @param imageName name and path of the image to be used
     */
    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    /**
     * Get the image currently assigned to our sprite
     * @return returns the image currently assigned to our sprite
     */
    public Image getImage() {
        return image;
    }

    /**
     * Get the x coordinate of our sprite
     * @return  the x coordinate of our sprite
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y coordinate of our sprite
     * @return  the y coordinate of our sprite
     */
    public int getY() {
        return y;
    }

    /**
     * Check whether our sprite is visible or not. Sprites that are not visible
     * are usually set to be destroyed
     * @return  True if visible, false if not
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Change the visibility of our sprite. Sprites that are not visible are
     * usually set to be destroyed
     * @param visible   a boolean variable, true if you want the sprite visible
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    /**
     * Get the coordinates of the sprite's bounding box. 
     * This is used to check for collision with other sprites. 
     * @return  returns the bounding box for our sprite
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}