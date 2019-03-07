/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package FinalQuest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * A tumbling meteor to get in the way of our ship.
 * It just flies across the screen and has so many hit points that it is just
 * about indestructible, but if it is somehow destroyed, it is worth a
 * whopping 5,000 points
 * @author Marco Tacca
 */
public class Asteroid extends Sprite {
    private final int INITIAL_X = 1280;
    private final int POINTS = 5000;
    private int health;
    private int move_speed;
    private List<Missile> missiles;
    private final String DIFFICULTY;
    private BufferedImage image;
    private int frame;
    private int delay;
    private int vert;
    private int direction;
    private final int max = 7;
    private final int min = 0;
    private String sprite_type;
    
    
    /**
     * Constructor
     * @param x starting x coordinate for the asteroid
     * @param y starting y coordinate for the asteroid
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Asteroid(int x, int y, String D) {
        super(x, y);
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = 6;
        vert = 0;
        if (Math.random() >= 0.5) {
            direction = -1;
        } else {
            direction = 1;
        }
        frame = (int)(Math.random()*((max-min)+1))+min;;
        initAsteroid();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the asteroid
     * @param y starting y coordinate for the asteroid
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the asteroid moves, adjusted for difficulty. default is 6
     */
    public Asteroid(int x, int y, String D, int s) {
        super(x, y);
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = s;
        vert = 0;
        if (Math.random() >= 0.5) {
            direction = -1;
        } else {
            direction = 1;
        }
        frame = (int)(Math.random()*((max-min)+1))+min;;
        initAsteroid();
        initAsteroid();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the asteroid
     * @param y starting y coordinate for the asteroid
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 6
     * @param vert  speed the asteroid moves vertically, default is 0
     */
    public Asteroid(int x, int y, String D, int s, int vert) {
        super(x, y);
        this.sprite_type = "enemy";
        DIFFICULTY = D;
        move_speed = s;
        this.vert = vert;
        if (Math.random() >= 0.5) {
            direction = -1;
        } else {
            direction = 1;
        }
        frame = (int)(Math.random()*((max-min)+1))+min;;
        initAsteroid();
        initAsteroid();
    }
    
    
    /**
     * Init our asteroid by assigning it an image and getting it's dimensions
     */
    private void initAsteroid() {
        if (DIFFICULTY == "normal")
        {
            health = 10; // how many times they can be hit before dying
            move_speed += 0; // how fast the asteroid moves
        }
        else if (DIFFICULTY == "hard")
        {
            health = 20; // how many times they can be hit before dying
            move_speed += 6; // how fast the asteroid moves
        }
        else if (DIFFICULTY == "unforgiving")
        {
            health = 30; // how many times they can be hit before dying
            move_speed += 12; // how fast the asteroid moves
        }
        
        missiles = new ArrayList<>();
        loadImage("src/resources/asteroid.png");
        getImageDimensions();
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health <= 0 it should be destroyed
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
     * This is just an accessor for the ship's score
     * @return  the number of points destroying the alien is worth
     */
    public int getPoints(){
        return POINTS;
    }
    
    
    /**
     *  Asteroid's AI
     * Just moves in a straight line and is destroyed if it goes off the screen
     */
    public void move() {
        x -= move_speed;
        y += vert;
        delay++;
        if (delay > 3)
        {
            frame += direction;
            delay = 0;
        }
        
        if (frame>=8)
            frame = 0;
        if (frame<0)
            frame = 7;
        
        //asteroid gets destroyed if it goes off the screen
        if ((x < 0-width) || (y < 0-height && vert < 0) || (y > 960 && vert > 0) ) 
            visible = false;
    }
    
    /**
     * Get the height and width of an image (used for collision)
     */
    protected void getImageDimensions() {
        
        width = 64;
        height = 64;
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
     * sprite sheet of images to allow for the asteroid's rotation.
     * @return returns the image currently assigned to our sprite
     */
    public BufferedImage getImage() {
        
        return image.getSubimage(frame*64, 0, width,height);
    }
    
    /**
     * see what type the sprite is
     * @return  a string containing the sprite's type
     */
    public String getType()
    {
        return sprite_type;
    }
}
