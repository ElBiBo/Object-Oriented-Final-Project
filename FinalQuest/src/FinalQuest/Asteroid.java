/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalQuest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.transform.Transform;

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
    }

    
    /**
     * Init our asteroid by assigning it an image and getting it's dimensions
    */
    private void initAsteroid() {
        if (DIFFICULTY == "normal")
        {
            health = 20; // how many times they can be hit before dying
            move_speed += 0; // how fast the asteroid moves
        }
        else if (DIFFICULTY == "hard")
        {
            health = 40; // how many times they can be hit before dying 
            move_speed += 6; // how fast the asteroid moves            
        }
        else if (DIFFICULTY == "unforgiving")
        {
            health = 60; // how many times they can be hit before dying
            move_speed += 12; // how fast the asteroid moves
        }
        
        missiles = new ArrayList<>();
        loadImage("src/resources/asteroid1.png");
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
        Transform.rotate(90, 200, 700);
        x -= move_speed;
        
        if (x < 0-width) //alien gets destroyed if it goes off the screen
            visible = false;
    }
}