package FinalQuest;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

        
        
/**
 * Our general sprite parent class
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class Sprite{
    
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    private final int POINTS = 100;
    private List<Missile> missiles = new ArrayList<>();
    public int health;
    private int missile_speed;
    private String sprite_type;
    private String status;
    
    
    /**
     * Constructor
     * @param x starting x coordinate for the sprite
     * @param y starting y coordinate for the sprite
     */
    public Sprite(int x, int y) {
        this.status = "good";
        this.sprite_type = "sprite";
        this.x = x;
        this.y = y;
        visible = true;
        health = 1;
        missile_speed = 0;
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
        
        try
        {
            //image = ImageIO.read(getClass().getResource(imageName));
        
            image = ImageIO.read(new File(imageName));
        }
        catch (IOException e)
        {
            System.out.print("Bad Image");
        }       
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
    
    /**
     * Gets a list of all missiles that have been fired
     * @return  that list
     */
    public List<Missile> getMissiles()
    {
        return missiles;
    }
    
    /**
     * Create a missile when activated.
     */
    public void fire() {
        
        missiles.add(new Missile(x, y + height / 2, missile_speed,0));
    }
    
    /**
     *  Used for movement
     */
    public void move()
    {
        
    }

    /**
     * get the point value for this sprite, if any
     * @return  points as an int
     */
    public int getPoints(){
        return POINTS;
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health less than or equal to it should be destroyed
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
            SoundEffect.ALIEN_EXPLODE.play();
        }
        return health;
    }
    
    /**
     * Some sprites (usually bosses) have reinforcements that help them out
     * This is used to return sprites, or lists of sprites to Board
     * @return  returns a single sprite if a sprite is waiting to be added to a list, otherwise null
     */
    public Sprite checkReinforcements()
    {
        Sprite temp = null;
        return temp;
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
     * sets status to exploding 
     */
    public void explode()
    {
        status = "gone";
    }
    
    /**
     * see what type the sprite is
     * @return  a string containing the sprite's type
     */
    public String getType()
    {
        return sprite_type;
    }

    /**
     * mostly used for bosses, generates an explosion. not used in this class
     * @return  null
     */
    public Explosion getBoom()
    {
        return null;
    }
}
