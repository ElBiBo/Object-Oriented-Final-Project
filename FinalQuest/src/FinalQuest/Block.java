
package FinalQuest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

/**
 * This is just a solid brick, used to build structures. 
 * Crashing into it is lethal. There are 18 colors of 16 different bricks
 * 
 * @author Marco Tacca
 */
public class Block extends Alien {
    private String brick;
    private int image_x, image_y, num;
    private BufferedImage image;
    /**
     * Constructor
     * Valid color strings are: dkbrown, mdbrown, ltbrown, dkgreen, mdgreen,
     * ltgreen, dkblue, mdblue, ltblue and f# where the # is any of those listed
     * strings
     * @param x starting x coordinate for the brick
     * @param y starting y coordinate for the brick
     * @param b a string used to define what color the brick will be
     * @param n a number used to define the brick pattern. should be an int 0 to 15
     */
    public Block(int x, int y, String b, int n) {
        super(x, y, "normal");
        sprite_type = "block";
        move_speed = 2;
        brick = b;
        num = n;
        if (n>15 || n <0)
        {
            num = ThreadLocalRandom.current().nextInt(1,16);
        }
        initBrick();
    }
    
    /**
     * Init our brick by assigning it an image and getting it's dimensions
     */
    private void initBrick() {
        health = 1;
        move_speed = 2;
        missiles = new ArrayList<>();
        loadImage("src/resources/blocks.png");
        getImageDimensions();
        getPlace();
    }
    
    /**
     * gets the starting coordinates of the brick for later drawing
     */
    private void getPlace()
    {
        int set_x;
        int set_y;
        switch (brick)
        {
            case "dkbrown":
            case "mdbrown":
            case "ltbrown":
            case "fdkbrown":
            case "fmdbrown":
            case "fltbrown":
            case "dkgreen":
            case "mdgreen":
            case "ltgreen":
            case "fdkgreen":
            case "fmdgreen":
            case "fltgreen":
            case "dkblue":
            case "mdblue":
            case "ltblue":
            case "fdkblue":
            case "fmdblue":
            case "fltblue":
                break;
            default:
                String[] choices = {"dkbrown", "mdbrown", "ltbrown", "dkgreen", "mdgreen", "ltgreen", "dkblue", "mdblue", "ltblue"};
                brick = choices[ThreadLocalRandom.current().nextInt(1,choices.length)];
                break;
        }
                
        switch (brick)
        {
            case "dkbrown":
                set_x = 16;
                set_y = 16;
                break;
            case "mdbrown":
                set_x = 160;
                set_y = 16;
                break;
            case "ltbrown":
                set_x = 304;
                set_y = 16;
                break;
            case "fdkbrown":
                set_x = 464;
                set_y = 16;
                break;
            case "fmdbrown":
                set_x = 608;
                set_y = 16;
                break;
            case "fltbrown":
                set_x = 752;
                set_y = 16;
                break;
            case "dkgreen":
                set_x = 16;
                set_y = 160;
                break;
            case "mdgreen":
                set_x = 160;
                set_y = 160;
                break;
            case "ltgreen":
                set_x = 304;
                set_y = 160;
                break;
            case "fdkgreen":
                set_x = 464;
                set_y = 160;
                break;
            case "fmdgreen":
                set_x = 608;
                set_y = 160;
                break;
            case "fltgreen":
                set_x = 752;
                set_y = 160;
                break;
            case "dkblue":
                set_x = 16;
                set_y = 304;
                break;
            case "mdblue":
                set_x = 160;
                set_y = 304;
                break;
            case "ltblue":
                set_x = 304;
                set_y = 304;
                break;
            case "fdkblue":
                set_x = 464;
                set_y = 304;
                break;
            case "fmdblue":
                set_x = 608;
                set_y = 304;
                break;
            case "fltblue":
                set_x = 752;
                set_y = 304;
                break;
            default:
                set_x = 16;
                set_y = 16;
                break;
                    
        }
        image_x = set_x +(num%4)*32;
        image_y = set_y +(num/4)*32;
    }
    
    /**
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    @Override
    public void move() {
        x -= move_speed;
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
    
    /**
     * Blocks can't be destroyed
     * @return the block's current health (always 1)
     */
    @Override
    public int damage(){
        SoundEffect.TENTACLE.play();
        
        return health;
    }
    
    /**
     * Get the height and width of an image (used for collision)
     */
    @Override
    protected void getImageDimensions() {
        
        width = 32;
        height = 32;
    }
    
    /**
     * Get the coordinates of the sprite's bounding box.
     * This is used to check for collision with other sprites.
     * @return  returns the bounding box for our sprite
     */
    @Override
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
     * Get the image currently assigned to our sprite. This flips through a
     * sprite sheet of images to allow for the asteroid's rotation.
     * @return returns the image currently assigned to our sprite
     */
    @Override
    public BufferedImage getImage() {
        
        return image.getSubimage(image_x, image_y, width,height);
    }
    
    /**
     * Blocks don't fire
     */
    public void fire() {}
        
}