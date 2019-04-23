
package FinalQuest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A little sprite to make explosion animations
 * @author Marco Tacca
 */
public class Explosion extends Sprite{
    private int count;
    private int frame;
    private BufferedImage image;

    public Explosion(int x, int y) {
        super(x, y);
        this.count = 0;
        loadImage("src/resources/explosion.png");
        getImageDimensions();
    }
    
    /**
     * Get the height and width of an image (used for collision)
     */
    protected void getImageDimensions() {
        width = 32;
        height = 32;
    }
    
    /**
     * Assigns an image to our sprite
     * @param imageName name and path of the image to be used
     */
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
     * sprite sheet of images to allow for the explosion's animation.
     * @return returns the image currently assigned to our sprite
     */
    public BufferedImage getImage() {
        count++;
        frame = count/5*32;
        if (frame >= 6*32)
        {
            frame = 6*32;
            visible = false;
        }
        return image.getSubimage(frame, 0, width,height);
    }
}
