package FinalQuest;

import java.awt.Rectangle;

/**
 * a short range laser beam
 *
 * @author Marco Tacca
 */
public class Beam extends Missile {
    
    private int missile_speed;
    private int dy,dx,d;
    private String sprite_type;
    private String m_img;
    
    /**
     * Constructor
     *
     * @param x starting x coordinate for our missile
     * @param y starting y coordinate for our missile
     * @param y_direction make the beam go in the y direction (1,0,-1)
     * @param x_direction make the beam go in the x direction (1,0,-1)
     * @param distance distance the beam should travel
     */
    public Beam(int x, int y, int y_direction, int x_direction, int distance) {
        super(x, y);
        this.sprite_type = "missile";
        dx = x_direction;
        dy = y_direction;
        d = distance;
        
        initBeam();
    }
    
    /**
     * Initiate our missile
     *
     */
    private void initBeam() {
        loadImage("src/resources/plasma_bolt.png");
        getImageDimensions(); // find the size of our image to use for collisions
    }
    
    /**
     * Move that beam!
     */
    @Override
    public void move() {
        
        x += dx;
        y += dy;
        d--;
        if (d < 0) //missile gets destroyed if it goes off the screen
            visible = false;
    }
    
    /**
     * Certain missiles have special conditions for destruction
     * @return  regular missiles don't. always return False
     */
    public boolean destroy()
    {
        return false;
    }
    
}