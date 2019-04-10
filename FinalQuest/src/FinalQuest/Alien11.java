
package FinalQuest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Alien child of our sprite class. Controls the AI of our alien.
 * It's basically a freakier looking and slightly animated version of Alien 1. 
 * It is otherwise identical
 * Behavior should be:
 * - fly in a straight line
 * - occasionally fire a laser
 * - be destroyed with 1 hit
 * - be destroyed if it flies off the screen (player gets no points though)
 * - 100 points if destroyed by the player
 * - If player collides with it, player is destroyed
 * @author Marco Tacca
 */
public class Alien11 extends Alien {
    private String behavior;
    private int thrust_adjust, image_x, image_y, frame, rate;
    private final int direction;
    private BufferedImage image;
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Alien11(int x, int y, String D) {
        super(x, y, D);
        this.direction = 0;
        this.thrust_adjust = 10;
        POINTS = 200;
        sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = 3;
        behavior = "normal";
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 2
     */
    public Alien11(int x, int y, String D, int s) {
        super(x, y, D);
        this.direction = 0;
        this.thrust_adjust = 30;
        POINTS = 200;
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = s;
        behavior = "normal";
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed. Also special behavior
     * when being launched from other ships (bosses)
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param B enter "launch" as a string to have the alien randomly launch from a spot
     */
    public Alien11(int x, int y, String D, String B) {
        super(x, y, D);
        direction = ThreadLocalRandom.current().nextInt(-4,5);
        this.thrust_adjust = 10;
        POINTS = 200;
        this.sprite_type = "enemy";
        DIFFICULTY = D;       
        behavior = B;
        if (behavior == "launch")
        {
            move_speed = 10;
        }
        else
        {
            move_speed = 2;
        }
        initAlien();
    }
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                fire_rate = 200;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "hard":
                fire_rate = 100;  //how often lasers are fired
                health = 1; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                break;
            case "unforgiving":
                fire_rate = 75;  //how often lasers are fired
                health = 2; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                break;
        }
        frame = 5;
        rate = 5;
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/ships_biomech.png");
        getImageDimensions();
    }
    private void spriteAdjust()
    {
        image_x = 673;
        image_y = 0;
        
        frame++;
    }
    /**
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    @Override
    public void move() {
        fire_count +=1;
        spriteAdjust();
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        switch (behavior){
            case "normal":
                x -= move_speed;
                break;
            case "launch":
                x += (thrust_adjust/5);
                thrust_adjust--;
                y += direction;
                if (thrust_adjust/5 <= -10)
                {
                    behavior = "normal";
                }
                break;
        }
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
    /**
     * Get the height and width of an image (used for collision)
     */
    @Override
    protected void getImageDimensions() {
        width = 30;
        height = 16;
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
        
        return image.getSubimage(image_x, image_y+16*((frame/rate)%4), width,height);        
    }
}