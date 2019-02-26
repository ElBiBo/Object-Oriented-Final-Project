/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package FinalQuest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.transform.Transform;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import sun.awt.image.ToolkitImage;

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
    private BufferedImage big_image;
    private BufferedImage image;
    private int frame;
    private int delay;
    private int vert;
    
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Asteroid(int x, int y, String D) {
        super(x, y);
        DIFFICULTY = D;
        initAsteroid();
        move_speed = 6;
        vert = 0;
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 2
     */
    public Asteroid(int x, int y, String D, int s) {
        super(x, y);
        DIFFICULTY = D;
        initAsteroid();
        move_speed = s;
        vert = 0;
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 2
     */
    public Asteroid(int x, int y, String D, int s, int vert) {
        super(x, y);
        DIFFICULTY = D;
        initAsteroid();
        move_speed = s;
        this.vert = vert;
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
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    public void move() {
        x -= move_speed;
        y += vert;
        delay++;
        if (delay > 3)
        {
            frame++;
            delay = 0;
        }
        
        if (frame>=8)
            frame = 0;
        if (x < 0-width) //alien gets destroyed if it goes off the screen
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
    
}
