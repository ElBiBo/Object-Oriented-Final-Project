
package FinalQuest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * This can either serve as a laser barrier or sentry. A barrier simply blocks
 * the way with a laser until the base is destroyed. the sentry fires upon the
 * player. In either case, destroying it is worth 300 points
 *
 * @author Marco Tacca
 */
public class LaserBarrier extends Alien {
    private String type;
    private int image_x, image_y, frame, rate, direction, deployment, d_speed;
    private BufferedImage image;
    /**
     * Constructor
     * 
     * @param x starting x coordinate for the barrier
     * @param y starting y coordinate for the barrier
     * @param t Barrier type. "barrier" for a laser shield, "sentry" for a firing sentry
     */
    public LaserBarrier(int x, int y, String t) {
        super(x, y, "normal");
        sprite_type = "enemy";
        move_speed = 2;
        type = t;
        d_speed = 48;
        deployment = GetPos.justY(height);
        POINTS = 1000;
        frame = 0;
        direction = 1;
        initBarrier();
    }
    
    /**
     * Init our brick by assigning it an image and getting it's dimensions
     */
    private void initBarrier() {
        health = 10;
        move_speed = 2;
        missiles = new ArrayList<>();
        loadImage("src/resources/securityrobot.png");
        getImageDimensions();
        getPlace();
    }
    
    /**
     * gets the starting coordinates of the brick for later drawing
     */
    private void getPlace()
    {
        frame+=direction;
        
        switch (type)
        {
            case "barrier":
                rate = 7;
                image_x = 29;
                image_y = 33;
                if (frame/rate>4)
                {
                    frame = 0;
                }
                laser();
                break;
            case "deploy":
                rate = 10;
                image_x = 29;
                image_y = 63;
                if (frame/rate>4)
                {
                    frame = 0;
                }
                x += move_speed;
                y -= d_speed/4;
                if (y<=deployment || y <= 250)
                {
                    d_speed-= 2;
                }
                if (d_speed <=0)
                {
                    type = "sentry";
                }
                break;
            case "sentry":
                rate = 10;
                image_x = 29;
                image_y = 63;
                if (frame/rate>4)
                {
                    frame = 0;
                    type = "firing";
                }
                break;
            case "firing":
                rate = 10;
                image_x = 113;
                image_y = 63;
                if (frame/rate>3)
                {
                    direction = -1;
                    fire();
                    frame = 3*rate;
                }
                else if (frame/rate < 0)
                {
                    type = "sentry";
                    frame = 0;
                    direction = 1;
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
        if (x < -500-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
    
    /**
     * Get the height and width of an image (used for collision)
     */
    @Override
    protected void getImageDimensions() {
        width = 13;
        height = 28;
    }
    
    /**
     * Get the coordinates of the sprite's bounding box.
     * This is used to check for collision with other sprites.
     * @return  returns the bounding box for our sprite
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y+3, width, height-3);
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
        
        return image.getSubimage(image_x+15*(frame/rate), image_y, width,height);
    }
    
    /**
     * release the laser barrier!
     */
    public void laser() {
        if (x<1280)
        {
            missiles.add(new Beam(x, y, -1,-2,100));
            missiles.add(new Beam(x, y+height, 1,-2,100));
        }
    }
    
    /**
     * Turret fires a plasma bolt at the player
     */
    @Override
    public void fire() {
        if (x>0)
        {
            double dx,dy;
            dx = (Stage.spaceship.getX()+18)-(x+7);
            dy = Stage.spaceship.getY()+20-(y+14);
            double length = Math.sqrt((dx*dx)+(dy*dy));
            dx = dx / length;
            dy = dy / length;
            
            int speed = 5;
            int vx = (int)(dx * speed);
            int vy = (int)(dy * speed);
            missiles.add(new Missile(x, y+height/2, vx, vy, "src/resources/plasma_bolt.png"));
        }
    }
    
}