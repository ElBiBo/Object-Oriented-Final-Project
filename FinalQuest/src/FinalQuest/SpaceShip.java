package FinalQuest;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Spaceship child of our sprite class. Controls the player's spaceship.
 * 
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
    private int num_missiles;
    private int missile_speed;
    private String firing_mode;
    private List<Missile> missiles;
    private int score;
    private final int START_X;
    private final int START_Y;
    private final int LIFE_POINTS = 100000;
    private final String DIFFICULTY;
    private int move_speed;
    private int invincibility_count;
    private int remaining_lives;
    private int respawn_count;
    private String game_mode;
    private int life_score;
    private String sprite_type;
    private int count;
    private int kills;
    private int dummy;
    private int powerup_count;
    
    /**
     * Constructor
     * @param x starting x coordinate for the player
     * @param y starting y coordinate for the player
     * @param d Game difficulty. "normal", "hard" or "unforgiving"
     */
    public SpaceShip(int x, int y, String d) {
        super(x, y);
        this.count = 0;
        this.sprite_type = "player";
        START_X = x;
        START_Y = y;
        DIFFICULTY = d;
        initCraft();   
    }

    /**
     * Init our player by assigning it an image and getting it's dimensions
     * Also create a list for keeping track of our ship's missiles
     */
    private void initCraft() {
        if (DIFFICULTY == "normal")
        {
            remaining_lives = 5;
            move_speed = 3;
        
        }
        else if (DIFFICULTY == "hard")
        {
            remaining_lives = 3;
            move_speed = 5;
        
        }
        else if (DIFFICULTY == "unforgiving")
        {
            remaining_lives = 1;
            move_speed = 7;
        
        }
        missiles = new ArrayList<>();
        firing_mode = "normal";
        num_missiles = 5;
        missile_speed = move_speed + (move_speed/3);
        score = 0;
        life_score = LIFE_POINTS;
        kills = 0;
        powerup_count = 30;
        startingMode();
        loadImage("src/resources/Player1.png");
        getImageDimensions();
    }

    /**
     * Control the movement of our spaceship. 
     * Also prevents the player from moving off the screen
     */
    public void move() {
        if (game_mode == "gametime")
        {
            x += dx;
            y += dy;

            //left border
            if (x < 1) {
                x = 1;
            }
            //right border
            if (x > 1280-width) {
                x = 1280-width;
            }
            // top border
            if (y < 32) {
                y = 32;
            }
            // bottom border
            if (y > 960-height) {
                y = 960-height;
            }
        }
        else if (game_mode == "dead"){
            respawn_count -= 1;
            if (respawn_count <= 0)
            {
                SoundEffect.PLAYER_RESPAWN.play();
                gametimeMode();
            }
        }
        else if (game_mode == "starting")
        {
            switch (count){
                case 0:
                    x = -200;
                    y = START_Y+110;
                    dummy = 25;
                    count++;
                    break;
                case 1:
                    if (x <= START_X+100)
                    {
                        x+= dummy/4;
                    }
                    else
                    {
                        count++;
                    }
                    break;
                case 2:
                    if (y > START_Y)
                    {
                        y -= 2;
                    }
                    if (x > START_X+100)
                    {
                        x+=dummy/4;
                        dummy--;
                        
                    }
                    else
                    {
                        count++;
                    }
                    break;
                case 3:
                    x -= 3;
                    if (x <= START_X)
                    {
                        count++;
                    }
                    break;
            }
            
        }
        else if (game_mode == "boss" || game_mode == "complete")
        {
            count++;
            if (x > START_X+5)
            {
                x -= 4;
            }
            else if (x < START_X-5)
            {
                x += 4;
            }
            if (y > START_Y+5)
            {
                y -= 4;
            }
            else if (y < START_Y-5)
            {
                y += 4;
            }
            if (count > 310 && game_mode == "complete")
            {
                count = 0;
                game_mode = "flyoff";
                Stage.setSong(MusicPlayer.VICTORY);
            }
        }
        else if (game_mode == "flyoff")
        {
            switch (count){
                case 0:
                    dummy = -25;
                    count++;
                    break;
                case 1:
                    if (dummy <=25)
                    {
                        dummy++;
                        y+= 3;
                        x+= dummy/4;
                    }
                    else
                    {
                        count++;
                    }
                    break;
                case 2:
                    if (x < 1700)
                    {
                        dummy++;
                        x+=dummy/4;
                    }
                    else
                    {
                        count++;
                    }
                    break;
                case 3:
                    game_mode = "level";
                    count = 0;
                    break;
            }
        }
    }
    
    /**
     * A boss is coming! WARNING!!!
     */
    public void bossMode()
    {
        count = 0;
            
        game_mode = "boss";
    }
    
    /**
     * level complete!
     */
    public void completeMode()
    {
        count = 0;
            
        game_mode = "complete";
    }
    
    /**
     * Adjusts mode to dead. This is when a player has died, but still has lives
     */
    public void deadMode()
    {
        count = 0;
        game_mode = "dead";
    }
    
    /**
     * Adjusts mode to gametime. This is our main game mode
     */
    public void gametimeMode()
    {
        count = 0;
        game_mode = "gametime";
        Stage.startSong();
    }
    
    /**
     * Adjusts mode to gameover. This is when a player run out of lives
     */
    public void gameoverMode()
    {
        count = 0;
        game_mode = "gameover";
    }
    
     /**
     * Adjusts mode to menu. We are back at our main menu!
     */
    public void menuMode()
    {
        count = 0;
        game_mode = "menu";
    }
    
    /**
     * Adjusts mode to pause. This pauses gameplay
     */
    public void pauseMode()
    {
        count = 0;
        game_mode = "pause";
        Stage.stopSong();
    }
    
     /**
     * Adjusts mode to starting. This is used at the beginning of a level
     */
    public void startingMode()
    {
        count = 0;
        game_mode = "starting";
        
    }
    
    /**
     * Returns a list of missiles that have been fired.
     * Used to find their coordinates for drawing them
     * @return  list of missiles
     */
    public List<Missile> getMissiles() {
        return missiles;
    }

    /**
     * Check the player's current score
     * @return  returns the player's score
     */
    public int getScore(){
        return score;
    }
    
    /**
     * Used to get the ship's position, if needed.
     * @return  an int array with the x,y coordinates of the ship
     */
    public int[] getPos(){
        return new int[]{x,y};
    }
        
    /**
     * Invoked whenever the player dies. The lose a life, if they have any
     * otherwise it's game over!
     * @return Returns the number of lives remaining after this loss 
     */
    public int die(){
        SoundEffect.PLAYER_EXPLODE.play();
        invincibility_count = 200;
        remaining_lives -=1;
        x = START_X;
        y = START_Y;
        
        if (remaining_lives <= 0)
            gameoverMode();
        else {
            respawn_count = 100;
            deadMode();
        }
        return remaining_lives;
    }
    
    /**
     * Returns the remaining lives of the player
     * @return  remaining lives
     */
    public int getLives(){
        return remaining_lives;
    }
    
    /**
     * When the player dies, they receive a brief moment of invincibility
     * in case they respawn in an area where they would collide with an enemy.
     * This function counts down the time the player should be invincible
     * and returns a boolean to provide a visual flashing cue.
     * True means the ship is currently visible
     * False means the ship is currently invisible
     * @return  boolean to determine whether the ship is visible or not during invincibility
     */
    public boolean invincibilityFlash(){
        if (invincibility_count > 0)
        {
            invincibility_count -= 1;
        }
        return ((invincibility_count/5)%2) == 0;
    }
    
    /**
     * Before the player appears again, there are a few seconds of down time
     * 
     * @return  returns true if the respawning period is finished, otherwise false
     */
    public boolean isRespawning(){
        return respawn_count > 0;
    }
    /**
     * Used by the game to check the current game status. Used for pausing, 
     * unpausing shifting between menus and entering the main game mode.
     * @return  returns a string describing the current game status
     */
    public String checkMode(){
        return game_mode;
    }
    
    /**
     * Toggles between pause and normal game play
     */
    public void pause()
    {
        if (game_mode == "gametime")
        {
            pauseMode();
        }
        else if (game_mode == "pause")
        {
            gametimeMode();
        }
    }
    
    /**
     *  A quick check to see if the player is invincible. True if they are, 
     * False if they are not.
     * @return  true if player is invincible, otherwise false
     */
    public boolean isInvincibile(){
        return ((invincibility_count > 0) || game_mode == "complete");
    }
    
    /**
     * Adds points to the player's score
     * @param newPoints the amount of points to be added to the player score
     */
    public void addPoints(int newPoints){
        score += newPoints;
        if (score >= life_score)
        {
            life_score += LIFE_POINTS;
            remaining_lives++;
            SoundEffect.LIFE_UP.play();
        }
    }
    
    
    
    public void powerup(String power)
    {
        SoundEffect.POWERUP.play();
        
        switch (power){
            case "invincibility": //player can't be hurt
                invincibility_count = 800;
                break;
            case "bullet": // number of bullets the player can fire goes up
                num_missiles++;
                if (num_missiles > 10)
                {
                    num_missiles = 10;
                }
                break;
            case "fast": // ship speed goes up
                move_speed++;
                if (move_speed > 10)
                {
                    move_speed = 10;
                }
                break;
            case "spread": // spread mode
                spreadFire();
                break;
            case "rapid": // bullets move faster
                missile_speed++;
                if (missile_speed > 20)
                {
                    missile_speed = 20;
                }
                break;
            case "life": // an extra life!
                remaining_lives++;
                break;
             case "points": // 5,000 points
                score += 5000;
                break;
            default:
                break;
        }          
    }

    /**
     * counts the number of ships you've destroyed
     */
    public void killCount()
    {
        kills++;
    }
    
    /**
     * Accessor for the player's kills
     * @return int containing the number of aliens you've killed
     */
    public int getKills()
    {
        return kills;
    }
    
    /**
     * Decides when to release a powerup based on the number of aliens killed
     * @return  True if it is time to release a powerup. False if it is not
     */
    public boolean powerupCheck()
    {
        return kills >= powerup_count;
    }
    
    /**
     * Whenever a powerup has been released (in the stage class), this class 
     * should be invoked to reset the count to 0
     */
    public void deployPowerup()
    {
        powerup_count = kills + 30;
    }
    
    /**
     * Create a missile when activated. No more than num_missiles
     * missiles can be fired without one of the previous missiles being 
     * destroyed first
     */
    public void fire() {
        switch (firing_mode){
            case "normal":
                if (missiles.size() < num_missiles)
                {
                    SoundEffect.LASER.play();
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,0));
                }
                break;
            case "spread":
                if (missiles.size() < num_missiles*3)
                {
                    SoundEffect.LASER.play();
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,0));
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,1));
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,-1));
                }
                break;
            case "double spread":
                if (missiles.size() < num_missiles*5)
                {
                    SoundEffect.LASER.play();
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,0));
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,1));
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,-1));
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,2));
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,-2));
                }
                break;
            default:
                if (missiles.size() < num_missiles)
                {
                    SoundEffect.LASER.play();
                    missiles.add(new Missile(x + width, y + height / 2, missile_speed,0));
                }
                break;            
        }
    }

    /**
     * Activates spread fire mode for the spaceship 
     */
    public void spreadFire() {
        switch (firing_mode){
            case "normal":
                firing_mode = "spread";
                break;
            case "spread":
                firing_mode = "double spread";
                break;
            default:
                firing_mode = "double spread";
                break;
        }           
    }
    
    /**
     * Activates normal fire mode for the spaceship 
     */
    public void normalFire() {
        firing_mode = "normal";
    }
    
    /**
     * Check for key press events so the player can control the space ship
     * @param e the last key pressed
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if (game_mode == "level")
        {
            Stage.setLevelSong();
            startingMode();
            
        }
            
        if (key == KeyEvent.VK_SPACE && game_mode == "gametime") { // fire missile on space
            fire();            
        }
        if (key == KeyEvent.VK_S && game_mode == "gametime") { // enable spread fire mode (DEBUG PURPOSES, DELETE LATER)
            spreadFire();
        }
        if (key == KeyEvent.VK_P) { // Pause the game
            pause();
        }
        if (key == KeyEvent.VK_LEFT && game_mode == "gametime") { // move left
            dx = move_speed * -1;
        }

        if (key == KeyEvent.VK_RIGHT && game_mode == "gametime") { // move right
            dx = move_speed;
        }

        if (key == KeyEvent.VK_UP && game_mode == "gametime") { // move up
            dy = move_speed * -1;
        }

        if (key == KeyEvent.VK_DOWN && game_mode == "gametime") { // move down
            dy = move_speed;
        }
    }
    /**
     * Check for key release events so the player can control the space ship
     * I am thinking of having a "charging" event for the laser so if you
     * hold it down for a few seconds then release it, it fires off some 
     * cooler missile. Idea for later!
     * @param e the last key pressed
     */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) { //stop moving left when you release left
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) { //stop moving right when you release right
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) { //stop moving up when you release up
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) { //stop moving down when you release down
            dy = 0;
        }
    }
}