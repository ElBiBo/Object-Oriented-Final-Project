
package FinalQuest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * A black tentacle erupts from the ground, attacking the player. 
 * It is indestructable
 * 
 * @author Marco Tacca
 */
public class Tentacle extends Alien {
    private int image_x, image_y, frame, delay, t_height, rate, step;
    private BufferedImage image;
    /**
     * Constructor
     * 
     * @param x starting x coordinate for the barrier
     * @param y starting y coordinate for the barrier
     */
    public Tentacle(int x, int y) {
        super(x, y, "normal");
        sprite_type = "enemy";
        move_speed = 1;
        POINTS = 1000;
        frame = 0;
        delay = 0;
        rate = 4;
        step = 0;
        t_height = 360;
        initTentacle();
    }
    
    /**
     * Init our brick by assigning it an image and getting it's dimensions
     */
    private void initTentacle() {
        health = 10;
        missiles = new ArrayList<>();
        loadImage("src/resources/tentacles.png");
        getImageDimensions();
        getPlace();
    }
    
    /**
     * gets the starting coordinates of the brick for later drawing
     */
    private void getPlace()
    {
        switch (step)
        {
            case 0:
                frame = 0;
                image_x = 90*6;
                image_y = 0;
                if (x < 1200)
                {
                    if (y > 930-height)
                    {
                        y--;
                    }
                    else
                    {
                        step++;
                    }
                }
                    
                break;
            case 1:
                t_height = 360;
                frame += 1;
                image_x = 0;
                image_y = 0;
                if (frame/rate > 4)
                {
                    frame = 4*rate;
                    delay++;
                    if (delay > 15)
                    {
                        frame = 0;
                        step++;
                        delay = 0;
                        image_x = 0;
                        image_y = 360;
                    }
                }
                break;
            case 2:
                image_x = 0;
                image_y = 360;
                frame++;
                t_height = 360 - ((360/8)*(frame/rate));
                if (frame/rate > 7)
                {
                    frame = 0;
                    image_x = 0;
                    image_y = 720;
                    step++;  
                }
                break;
            case 3:
                image_x = 0;
                image_y = 720;
                t_height = 0;
                frame++;
                if (frame/rate > 7)
                {
                    frame = 0;
                    
                }
                break;
        }
    }
    
    /**
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    @Override
    public void move() {
        
        getPlace();
        x -= move_speed;
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
    
    /**
     * Get the height and width of an image (used for collision)
     */
    @Override
    protected void getImageDimensions() {
        width = 100;
        height = 360;
    }
    
    /**
     * Get the coordinates of the sprite's bounding box.
     * This is used to check for collision with other sprites.
     * @return  returns the bounding box for our sprite
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+30, y+t_height+40, width-60, height-t_height-40);
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
        
        return image.getSubimage(image_x+100*(frame/rate), image_y, width,height);
        
    }
    
    /**
     * Tentacles can't be destroyed
     * @return  returns a health value, but it effectively has infinite health
     */
    @Override
    public int damage(){
        SoundEffect.TENTACLE.play();
        return health;
    }
}