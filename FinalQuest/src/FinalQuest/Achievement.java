
package FinalQuest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This is just a solid brick, used to build structures. 
 * Crashing into it is lethal. There are 18 colors of 16 different bricks
 * 
 * @author Marco Tacca
 */
public class Achievement extends Sprite {
    private int image_x, image_y, num;
    private String[] message;
    private BufferedImage image;
    /**
     * Constructor
     * Must be given an x/y coordinate as well as a numerical code for what
     * achievement is being displayed. Anything outside of the 0-15 range is 
     * assumed to be 0
     * @param x starting x coordinate for the achievement icon
     * @param y starting y coordinate for the achievement icon
     * @param a achievement code, an int from 0 to 15
     */
    public Achievement(int x, int y, int a) {
        super(x, y);
        num = a;
        initAchievement();
    }
    
    /**
     * Init the achievement
     */
    private void initAchievement() {
        loadImage("src/resources/achievementicons.png");
        getImageDimensions();
        getPlace(x, y, num);
    }
    
    /**
     * gets the starting coordinates of the achievement for later drawing
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param a Achievement code [0-15]
     * @return a string message explaining how the achievement is scored
     */
    public String[] getPlace(int x, int y, int a)
    {
        this.x = x;
        this.y = y;
        this.num = a;
        int set_x;
        int set_y;
        switch (num)
        {
            case 0: // Beat level 1
                image_x = 3;
                image_y = 6;
                message = new String[] {"You beat the first level!"};
                break;
            case 1: // Beat level 2
                image_x = 0;
                image_y = 8;
                message = new String[] {"You beat the second level!"};
                break;
            case 2: // Beat level 3
                image_x = 2;
                image_y = 8;
                message = new String[] {"You beat the third level!"};
                break;
            case 3: // Beat level 4
                image_x = 4;
                image_y = 8;
                message = new String[] {"You beat the fourth level!"};
                break;
            case 4: // Beat level 5
                image_x = 6;
                image_y = 7;
                message = new String[] {"You beat the fifth level!"};
                break;
            case 5: // Beat level 6
                image_x = 7;
                image_y = 7;
                message = new String[] {"You beat the sixth level!"};
                break;
            case 6: // Beat level 7
                image_x = 8;
                image_y = 7;
                message = new String[] {"You beat the seventh level!"};
                break;
            case 7: // Beat level 8
                image_x = 3;
                image_y = 9;
                message = new String[] {"You beat the eighth level!"};
                break;
            case 8: // beat normal difficulty
                image_x = 0;
                image_y = 0;
                message = new String[] {"You beat the game on normal difficultyl!"};
                break;
            case 9: // beat hard difficulty
                image_x = 3;
                image_y = 0;
                message = new String[] {"You beat the game on hard difficulty!"};
                break;
            case 10: // beat unforgiving difficulty
                image_x = 7;
                image_y = 5;
                message = new String[] {"You beat the game on unforgiving difficulty! Impressive!!"};
                break; 
            case 11: // earn top score
                image_x = 4;
                image_y = 9;
                message = new String[] {"You earned the top high score!"};
                break;
            case 12: // beat a level without firing a shot or losing a life
                image_x = 3;
                image_y = 4;
                message = new String[] {"How sneaky! You got through an entire level without firing a shot!"};
                break;
            case 13: // gain a life
                image_x = 7;
                image_y = 6;
                message = new String[] {"You gained a life during the game."};
                break;
            case 14: // get a powerup
                image_x = 4;
                image_y = 6;
                message = new String[] {"You acquired a powerup during the game!"};
                break;
            case 15: // destroy an asteroid
                image_x = 1;
                image_y = 3;
                message = new String[] {"You destroyed an asteroid. Wow!"};
                break;                
            default:
                image_x = 3;
                image_y = 6;
                break;
        }
    
        image_y = image_y*150+16;
        image_x = image_x*150;
        return message;
    }
    
    /**
     *  Achievements possess no AI, unlike other sprites. 
     */
    @Override
    public void move() {
    }
    
    /**
     * Get the height and width of an image (used for finding the image on an image map)
     */
    @Override
    protected void getImageDimensions() {
        
        width = 134;
        height = 134;
    }
    
    /**
     * Get the coordinates of the sprite's bounding box.
     * This is used to check for collision with other sprites.
     * Not really used for the achievements but required since it is a sprite
     * @return  returns the bounding box for our sprite
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
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
     * Get the image currently assigned to our sprite from a sprite map
     * @return returns the image currently assigned to our sprite
     */
    @Override
    public BufferedImage getImage() {
        
        return image.getSubimage(image_x, image_y, width,height);
    }
}