
package FinalQuest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Boss of level 2 - Giant space station
 * moves around the border of the screen
 * fires plasma bolts in every direction
 * occasionally releases alien2's (rate increases with damage)
 * @author Marco Tacca
 */
public class Boss2 extends Alien {
    
    private int max_health;
    private String attack_mode = "entry";
    private String fire_mode = "regular";
    private int direction = 1;
    private int step = 0;
    private int delay;
    private int reinforce;
    private List<Sprite> reinforcement_list = new ArrayList<>();
    private int count = 0;
    private String status;
    private Explosion boom;
    private int frame;
    private BufferedImage image;
    private int threat;
    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Boss2(int x, int y, String D) {
        super(x, y, D);
        POINTS = 4000;
        this.threat = 0;
        this.frame = 0;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
        DIFFICULTY = D;
        move_speed = 2;
        initAlien();
    }
    
    /**
     * Constructor, to adjust the speed, if needed
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for difficulty. default is 2
     */
    public Boss2(int x, int y, String D, int s) {
        super(x, y, D);
        POINTS = 4000;
        this.threat = 0;
        this.frame = 0;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
        DIFFICULTY = D;
        move_speed = s;
        initAlien();
    }
    
    
    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                fire_rate = 10;  //how often lasers are fired
                health = 90; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                delay = 180; // how long it takes for the boss to charge
                reinforce = 200;
                break;
            case "hard":
                fire_rate = 5;  //how often lasers are fired
                health = 120; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed+3)*-1; // how fast their lasers move
                delay = 120;
                reinforce = 100;
                break;
            case "unforgiving":
                fire_rate = 1;  //how often lasers are fired
                health = 180; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed+6)*-1; // how fast their lasers move
                delay = 60;
                reinforce = 50;
                break;
        }
        max_health = health;
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate-100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss2.png");
        getImageDimensions();
    }
    
    /**
     * This damages the alien and then returns its current health
     * if health <= 0 it should be destroyed
     * otherwise it makes a sound and takes some damage
     * @return  the alien's current health
     */
    public int damage(){
        if (attack_mode != "shields" && attack_mode != "entry")
        {
            health -= 1;
            SoundEffect.ALIEN_HIT.play();
        }
        
        if (health <= 0)
        {
            status = "exploding";
            
        }
        return health;
    }
    
    /**
     * Create a missile when activated. No more than num_missiles
     * missiles can be fired without one of the previous missiles being
     * destroyed first
     */
    public void fire() {
        
        missiles.add(new Missile(x+width-50, y+105, 4,-3,"src/resources/plasma_bolt.png"));
        missiles.add(new Missile(x+width-50, y+height-115, 4,3,"src/resources/plasma_bolt.png"));
        missiles.add(new Missile(x+width-337, y+15, -1,-4,"src/resources/plasma_bolt.png"));
        missiles.add(new Missile(x+width-337, y+height-25, -1,4,"src/resources/plasma_bolt.png"));
        missiles.add(new Missile(x+15, y+height/2-5, -4,0,"src/resources/plasma_bolt.png"));
        if (DIFFICULTY == "hard")
        {
            missiles.add(new Missile(x, y -40 + height / 2, missile_speed,-1));
            missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,1));
        }
        else if (DIFFICULTY == "unforgiving")
        {
            missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-4));
            missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,4));
            missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,-2));
            missiles.add(new Missile(x-width+80, y -2 + height / 2, missile_speed,2));
        }
        
    }
    
    
    /**
     *  Alien's AI
     * moves to the left until it reaches the end of the screen
     * then is destroyed. It also has a laser it fires.
     */
    public void move()
    {
        count++;
        switch (attack_mode){
            case "entry":
                entry();
                break;
            case "circle":
                circle();
                break;
            case "shields":
                shields();
                break;
            case "blowup":
                blowup();
                break;
        }
        
        if (health > max_health/3*2)
        {
            threat = 0;
        }
        else if (health > max_health / 3)
        {
            if (threat == 0)
            {
                count = 0;
                attack_mode = "shields";
            }
            threat = 1;
        }
        else
        {
            if (threat == 1)
            {
                count = 0;
                attack_mode = "shields";
            }
            threat = 2;
        }
        
        // this is used to send out enemy ships as backup
        update_reinforcements();
        
        
    }
    
    /**
     * The boss enters the screen
     */
    public void entry(){
        x -= move_speed;
        if (x<1280-width)
        {
            attack_mode = "circle";
        }
    }
    
    /**
     * The boss doesn't move, is immune to damage and launches a barrage of
     * ships at the player
     */
    public void shields()
    {
        frame = 1;
        int xpos,ypos;
        if (step == 0 || step ==2)
        {
            if (y>= 980-height || y <= 30)
            {
                step += direction;
            }
        }
        else if (step == 1 || step == 3)
        {
            if (x>= 1280-width || x <= 300)
            {
                step += direction;
            }
        }
        if (step <0)
        {
            step = 3;
        }
        if (step > 3)
        {
            step = 0;
        }
        switch (step){
            case 0:
                y+=move_speed+threat-1;
                break;
            case 1:
                x -= move_speed+threat-1;
                break;
            case 2:
                y-=move_speed+threat-1;
                break;
            case 3:
                x += move_speed+threat-1;
                break;
        }
        if (count % 30 == 0)
        {
            int diff;
            switch (DIFFICULTY) {
                case "normal":
                    diff = 1;
                    break;
                case "hard":
                    diff = 2;
                    break;
                default:
                    diff = 3;
                    break;
            }
            for (int i = 0; i<diff; i++)
            {
                xpos = x+width/2+ThreadLocalRandom.current().nextInt(-120, 120);
                ypos = y+height/2+ThreadLocalRandom.current().nextInt(-120, 120);
                reinforcement_list.add(new Alien2(xpos, ypos, DIFFICULTY,move_speed+3+threat-1));
            }           
        }
        if (count % 300 == 0)
        {
            attack_mode = "circle";
        }
    }
    
    /**
     * The boss moves up and down, firing it's missiles
     */
    public void circle(){
        fire_count +=1;
        frame = 0;
        if (fire_count % fire_rate == 0)
        {
            fire();
        }
        if (step == 0 || step ==2)
        {
            if (y>= 980-height || y <= 30)
            {
                step += direction;
            }
        }
        else if (step == 1 || step == 3)
        {
            if (x>= 1280-width || x <= 300)
            {
                step += direction;
            }
        }
        if (step <0)
        {
            step = 3;
        }
        if (step > 3)
        {
            step = 0;
        }
        switch (step){
            case 0:
                y+=move_speed+threat-1;
                break;
            case 1:
                x -= move_speed+threat-1;
                break;
            case 2:
                y-=move_speed+threat-1;
                break;
            case 3:
                x += move_speed+threat-1;
                break;
        }
        if (threat == 2 && count % 600 == 0)
        {
            count = 0;
            attack_mode = "shields";
        }
        
    }
    
    private void blowup()
    {
        step++;
        if (step <= 300)
        {
            if (step%3 == 0)
            {
                y+= 5*direction;
                direction = direction * -1;
            }
            if (step%5 == 0)
            {
                
                makeBoom();
            }
        }
        else
        {
            visible = false;
        }
    }
    
    private void makeBoom()
    {
        SoundEffect.ALIEN_EXPLODE.play();
        int y_pos = ThreadLocalRandom.current().nextInt(y+80, y+height-140);
        int x_pos = ThreadLocalRandom.current().nextInt(x+80, x+width-140);
        boom = new Explosion(x_pos,y_pos);
    }
    
    @Override
    public Explosion getBoom()
    {
        if (boom == null)
        {
            return null;
        }
        else
        {
            Explosion tmp = boom;
            boom = null;
            return tmp;
        }
    }
    
    /**
     *
     */
    public void update_reinforcements()
    {
        int xpos = x+width/2+ThreadLocalRandom.current().nextInt(-100, 100);
        int ypos = y+height/2+ThreadLocalRandom.current().nextInt(-100, 100);
        if (count % (reinforce) == 0){
            switch (DIFFICULTY) {
                case "normal":
                    reinforcement_list.add(new Alien2(xpos, ypos, DIFFICULTY,move_speed+2+threat-1));
                    break;
                case "hard":
                    reinforcement_list.add(new Alien2(xpos, ypos, DIFFICULTY,move_speed+2+threat-1));
                    xpos = x+width/2+ThreadLocalRandom.current().nextInt(-100, 100);
                    ypos = y+height/2+ThreadLocalRandom.current().nextInt(-100, 100);
                    reinforcement_list.add(new Alien2(xpos, ypos, DIFFICULTY,move_speed+2+threat-1));
                    break;
                case "unforgiving":
                    reinforcement_list.add(new Alien2(xpos, ypos, DIFFICULTY,move_speed+2+threat-1));
                    xpos = x+width/2+ThreadLocalRandom.current().nextInt(-100, 100);
                    ypos = y+height/2+ThreadLocalRandom.current().nextInt(-100, 100);
                    reinforcement_list.add(new Alien2(xpos, ypos, DIFFICULTY,move_speed+2+threat-1));
                    xpos = x+width/2+ThreadLocalRandom.current().nextInt(-100, 100);
                    ypos = y+height/2+ThreadLocalRandom.current().nextInt(-100, 100);
                    reinforcement_list.add(new Alien2(xpos, ypos, DIFFICULTY,move_speed+2+threat-1));
                    break;
            }
        }
        if (count % (reinforce/2) == 0)
        {
            switch (DIFFICULTY) {
                case "normal":
                    ypos = ThreadLocalRandom.current().nextInt(50, 890);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10+threat-1));
                    break;
                case "hard":
                    ypos = ThreadLocalRandom.current().nextInt(50, 420);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10+threat-1));
                    ypos = ThreadLocalRandom.current().nextInt(470, 890);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10+threat-1));
                    break;
                case "unforgiving":
                    ypos = ThreadLocalRandom.current().nextInt(50, 280);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10+threat-1));
                    ypos = ThreadLocalRandom.current().nextInt(330, 600);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10+threat-1));
                    ypos = ThreadLocalRandom.current().nextInt(650, 890);
                    reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY,10+threat-1));
                    reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY,10+threat-1));
                    break;
            }
        }
    }
    
    @Override
    public Sprite checkReinforcements()
    {
        if (reinforcement_list.size() <= 0)
        {
            return null;
        }
        else
        {
            return reinforcement_list.remove(0);
        }
    }
    
    /**
     * sets status to exploding
     */
    @Override
    public void explode()
    {
        status = "gone";
        attack_mode = "blowup";
        step = 0;
    }
    
    /**
     * mostly used to make things explode
     * @return  the current status of the sprite
     */
    @Override
    public String getStatus()
    {
        return status;
    }
    
    /**
     * Get the coordinates of the sprite's bounding box.
     * This is used to check for collision with other sprites.
     * @return  returns the bounding box for our sprite
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+80, y+80, width-160, height-160);
    }
    
    /**
     * Get the height and width of an image (used for collision)
     */
    @Override
    protected void getImageDimensions() {
        
        width = 512;
        height = 512;
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
        
        return image.getSubimage(frame*512, 0, width,height);
    }
    
    
}