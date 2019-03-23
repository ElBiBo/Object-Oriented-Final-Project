
package FinalQuest;

import java.util.Random;

/**
 * PowerUp child of our sprite class. Controls the AI of power-ups.
 * Behavior should be:
 * - fly in a straight line
 * - occasionally fire a laser
 * - be destroyed with 1 hit and turns into an orb
 * - be destroyed if it flies off the screen (player gets no points though)
 * @author Gach5
 */
public class PowerUp extends Sprite {
    
    private int health = 2;
    private String sprite_type;
    private String power_type;
    
    
    /**
     * Constructor, sets the powerup to random
     * @param x starting x coordinate for the power-up
     * @param y starting y coordinate for the power-up
     */
    public PowerUp(int x, int y) {
        super(x, y);
        this.sprite_type = "capsule";
        this.power_type = "";
        checkPower();
        initPower_up();        
    }
    
    /**
     * Constructor, allows you to choose what powerup you want
     * @param x starting x coordinate for the power-up
     * @param y starting y coordinate for the power-up
     * @param power Choose "invincibility", "bullet", "fast", "rapid", "spread". otherwise the powerup is random
     */
    public PowerUp(int x, int y, String power) {
        super(x, y);
        this.sprite_type = "capsule";
        this.power_type = power;
        checkPower();
        initPower_up();        
    }
    /**
     * Init our power-up by assigning it an image and getting it's dimensions
     */
    public void initPower_up() {
        
        loadImage("src/resources/powerup.png");
        getImageDimensions();
    }
    
    /**
     * Ensure that the powerup is a valid one. If it is not a valid one, the 
     * powerup is randomly chosen from those that are valid
     */
    private void checkPower()
    {
        String[] powers={"invincibility", "bullet", "fast", "rapid", "spread","life","points"};
        
        switch (power_type){
            case "invincibility": //player can't be hurt                
            case "bullet": // number of bullets the player can fire goes up
            case "fast": // ship speed goes up
            case "spread": // spread mode
            case "rapid": // bullets move faster
            case "life": // an extra life!
            case "points": // 5,000 points!
                break;
            default:
                Random r=new Random();
                int i=r.nextInt(powers.length);
                power_type = powers[i];
                break;
        }
    }
    
    /**
     * This damages the barrel and then returns its current health
     * if health == 1 it should turn to an orb
     * @return  the power-up current health
     */
    public int damage(){
        health -= 1;
        if(health == 1){
            loadImage("src/resources/powerup_"+power_type+".png");
            this.sprite_type = "powerup";
        }
        return health;
    }
    
    /**
     * Invoked whenever the player touches orb. They gain invincibility.
     */
    public void fire(){
        sprite_type = power_type;
    }
    
    
    /**
     *  Power-up AI
     * Powerup moves to the left and gets destroyed if it goes off screen
     */
    public void move() {
        x -= 3;
        
        if (x < 0-width) //power-up gets destroyed if it goes off the screen
            visible = false;
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
