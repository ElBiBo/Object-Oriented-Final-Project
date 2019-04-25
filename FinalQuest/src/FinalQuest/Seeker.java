package FinalQuest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Special seeker missile, follows the player around until it eventually runs out of power
 *
 * @author Marco Tacca 
 */
public class Seeker extends Missile {
    
    private int dx;
    private int dy;
    private int frame;
    private final int MAX_SPEED = 5;
    private double limit; //how far it can go before the seeker runs out of gas
    private String sprite_type;
    private String m_img;
    private BufferedImage image;
    private SpaceShip ship;
    
    /**
     * Constructor
     *
     * @param x starting x coordinate for our missile
     * @param y starting y coordinate for our missile
     * @param s Our spaceship object, used for finding their location
     
     */
    public Seeker(int x, int y, SpaceShip s) {
        super(x, y);
        this.sprite_type = "missile";
        dx = 5;
        dy = 0;
        limit = 0;
        ship = s;
        frame = 0;
        m_img = "src/resources/seeker.png";
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
    
    public void move() {
        if (x+dx < ship.getX()-15)
        {
            dx++;
        }
        else if (x-dx > ship.getX()+51)
        {
            dx--;
        }
        if (y+dx < ship.getY()-15)
        {
            dy++;
        }
        else if (y-dy > ship.getY()+54)
        {
            dy--;
        }
        int adjust = 5;
        if (dx > MAX_SPEED*adjust)
        {
            dx = MAX_SPEED*adjust;
        }
        if (dy > MAX_SPEED*adjust)
        {
            dy = MAX_SPEED*adjust;
        }
        if (dx < MAX_SPEED*adjust*-1)
        {
            dx = MAX_SPEED*adjust*-1;
        }
        if (dy < MAX_SPEED*adjust*-1)
        {
            dy = MAX_SPEED*adjust*-1;
        }
        x += dx/adjust; 
        y += dy/adjust;
        
        limit += Math.sqrt(((dx/adjust*dx/adjust)+(dy/adjust*dy/adjust)));
        frame++;
        
    }
    
    /**
     * This destroys our seeker after it's gone too far
     * @return 
     */
    @Override
    public boolean destroy()
    {
        return limit > 2000;
    }
    
    /**
     * Get the height and width of an image (used for collision)
     */
    @Override
    protected void getImageDimensions() {
        
        width = 16;
        height = 16;
    }
    
    /**
     * Assigns an image to our sprite
     * @param imageName name and path of the image to be used
     */
    @Override
    protected void loadImage(String imageName) {
        
        try
        {
            image = ImageIO.read(new File(imageName));
        }
        catch (IOException e)
        {
            System.out.print("Bad Image");
        }
    }
    
    /**
     * Get the image currently assigned to our sprite. This flips through a
     * sprite sheet of images to make the missile look like it is flashing.
     * @return returns the image currently assigned to our sprite
     */
    public BufferedImage getImage() {
        
        return image.getSubimage(((frame/5)%4)*16, 0, width,height);
    }
}