package FinalQuest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * This class primarily handles drawing various objects to the screen
 * It also keeps track of how many objects are on the screen, updates their
 * positions
 *
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class Board extends JPanel implements ActionListener {
    
    private Timer timer;
    private static SpaceShip spaceship;
    private List<Sprite> aliens;
    private List<Sprite> power_ups;
    private List<Explosion> explosions;
    private List<Background> background;
    private Background GUI_bar, GUI_bar_player, warning, menu, arrow;
    private final int B_WIDTH = 1280;
    private final int B_HEIGHT = 960;
    private final int ICRAFT_X = 200;
    private final int ICRAFT_Y = B_HEIGHT/2;
    private final int DELAY = 15;
    private int stage_count = 0;
    private int level = 1;
    private String game_mode = "mainmenu";
    private String difficulty;
    private int wave_count = 0;
    private Stage stage;
    private int num_players;
    private String name = "";
    private boolean enter_score = false;
    private boolean ship_input;
    private HighscoreManager hm = new HighscoreManager();
    private int menu_option = 0;
    
    /**
     * Constructor
     */
    public Board() {
        this.difficulty = "normal";
        this.num_players = 1;
        
        initBoard();
    }
    
    /**
     * Initialize our board
     */
    private void initBoard() {
        
        addKeyListener(new TAdapter()); //check for key input
        setFocusable(true); // pay attention to this window
        setBackground(Color.BLACK);
        
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        explosions = new ArrayList<>();
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y, difficulty);
        ship_input = false;
        GUI_bar = new Background(400,0,"src/resources/GUIbar.png");
        GUI_bar_player = new Background(0,0,"src/resources/GUIbarplayer.png");
        menu = new Background(0,0,"src/resources/menu_image.png");
        warning = new Background(0,0,"src/resources/warning.png");
        arrow = new Background(0,0,"src/resources/blueArrow.png");
        //Stage stage = new Stage(difficulty, spaceship);
        
        //initBG();
        //initAliens();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    /**
     * Initiate all our background sprites
     */
    public void initBG(){
        background = new ArrayList<>();
        
        Background tmp = stage.background();
        
        while (tmp != null)
        {
            background.add(tmp);
            tmp = stage.background();
        }
        
    }
    /**
     * Initialize our aliens and drop them all into our board
     */
    public void initAliens() {
        if (stage.getWave() == 11)
        {
            spaceship.bossMode();
        }
        aliens = new ArrayList<>();
        aliens =  stage.sendWave();
    }
    
    public void initPower_ups(){
        power_ups = new ArrayList<>();
        power_ups = stage.sendWave();
    }
    /**
     * Ignore the default swing paintComponent function. This is where
     * we decide what screen we are on. It will also be what we have to edit
     * to put our menus and possibly extra levels in
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game_mode != "starting" && spaceship.checkMode() == "starting")
        {
            initAliens();
            initBG();
        }
        if (ship_input)
        {
            game_mode = spaceship.checkMode();
        }
        switch (game_mode) {
            case "dead":
                drawObjects(g);
                ship_input = true;
                break;
            case "starting":
                drawStart(g);
                ship_input = true;
                break;
            case "gametime":
                drawObjects(g);
                ship_input = true;
                break;
            case "level":
                drawLevelComplete(g);
                ship_input = true;
                break;
            case "pause":
                drawObjects(g);
                drawPause(g);
                ship_input = true;
                break;
            case "gameover":
                drawGameOver(g);
                ship_input = false;
                break;
            case "highscore":
                drawHighscore(g);
                ship_input = false;
                break;
            case "mainmenu":
                drawMainMenu(g);
                ship_input = false;
                break;
            case "boss":
                drawObjects(g);
                drawWarning(g);
                ship_input = true;
                break;
            case "complete": case "flyoff":
                drawObjects(g);
                ship_input = true;
                break;
            case "credits":
                drawCredits(g);
                ship_input = false;
                break;
            case "instructions":
                drawInstructions(g);
                ship_input = false;
                break;
            case "achievements":
                drawAchievements(g);
                ship_input = false;
                break;
            case "options":
                drawOptions(g);
                ship_input = false;
                break;
            default:
                game_mode = "mainmenu";
                ship_input = false;
                break;
        }
        
    }
    
    /**
     * Draw all the active objects on our board
     */
    private void drawObjects(Graphics g) {
        for (Background bg : background)
        {
            g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
        }
        
        if (spaceship.isVisible() && spaceship.invincibilityFlash() && !spaceship.isRespawning()) { // draw our spaceship first
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),this);
        }
        
        List<Missile> ms = spaceship.getMissiles(); // then our spaceship's shots
        
        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(),
                        missile.getY(), this);
            }
        }
        
        List<Missile> ems;
        
        for (Sprite alien : aliens) { // then the aliens
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
                ems = alien.getMissiles(); // then our aliens' shots
                for (Missile missile : ems) {
                    if (missile.isVisible())
                    {
                        g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
                    }
                }
            }
        }
        drawExplosions(g);
        drawGUI(g);
    }
    
    private void drawExplosions(Graphics g)
    {
        for (Explosion e : explosions){
            if (e.isVisible())
            {
                g.drawImage(e.getImage(), e.getX(), e.getY(), this);
            }
        }
    }
    
    private void drawWarning(Graphics g)
    {
        stage_count++;
        if (!(stage_count/20%4 == 0))
        {
            g.drawImage(warning.getImage(), warning.getX(), warning.getY(),this);
        }
        if (stage_count >= 390)
        {
            stage_count = 0;
            spaceship.gametimeMode();
        }
        if ((stage_count+80) % 81 == 0)
        {
            SoundEffect.ALERT.play();
        }
    }
    
    private void drawLevelComplete(Graphics g)
    {
        String msg = "Level complete!";
        Font small = new Font("Impact", Font.BOLD, 40);
        FontMetrics fm = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
        
        drawGUI(g);
    }
    
    private void drawGUI(Graphics g)
    {
        int high_score = hm.getScores().get(0).getScore();
        if (spaceship.getScore() > high_score)
        {
            high_score = spaceship.getScore();
        }
        g.drawImage(GUI_bar.getImage(), GUI_bar.getX(), GUI_bar.getY(),this);
        g.drawImage(GUI_bar_player.getImage(), GUI_bar_player.getX(), GUI_bar_player.getY(),this);
        
        Font small = new Font("Impact", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(small);
        g.setFont(small);
        g.setColor(Color.black);
        g.drawString("Player 1: " + spaceship.getScore(), 14, 22);
        g.drawString("Lives: " + spaceship.getLives(), 299, 22);
        
        g.drawString("High Score: " + high_score, 410, 22);
        g.drawString("Level: " + stage.getLevel(), 710, 22);
        
        if (num_players == 2) //just a place holder for now, but we may add 2 player mode later
        {
            g.drawImage(GUI_bar_player.getImage(), 880, GUI_bar_player.getY(),this);
            g.drawString("Player 2: " + spaceship.getScore(), 894, 22);
            g.drawString("Lives: " + spaceship.getLives(), 1179, 22);
        }
        if (difficulty == "normal")
            g.setColor(Color.white);
        else if (difficulty == "hard")
            g.setColor(Color.yellow);
        else if (difficulty == "unforgiving")
            g.setColor(Color.red);
        
        g.drawString("Player 1: " + spaceship.getScore(), 15, 23);
        g.drawString("Lives: " + spaceship.getLives(), 300, 23);
        g.drawString("High Score: " + high_score, 411, 23);
        g.drawString("Level: " + stage.getLevel(), 711, 23);
        if (num_players == 2) //just a place holder for now, but we may add 2 player mode later
        {
            g.drawString("Player 2: " + spaceship.getScore(), 895, 23);
            g.drawString("Lives: " + spaceship.getLives(), 1180, 23);
        }
    }
    
    /**
     * Draws the ready sequence at the beginning of the level
     */
    private void drawStart(Graphics g) {
        stage_count++;
        String msg;
        for (Background bg : background)
        {
            g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
        }
        if (stage_count < 180)
        {
            msg = "Ready...";
            if (stage_count/20%3 == 0)
            {
                msg = "";
            }
        }
        else
        {
            msg = "GO!";
        }
        if (stage_count > 300)
        {
            spaceship.gametimeMode();
            stage_count = 0;
        }
        Font small = new Font("Impact", Font.BOLD, 40);
        FontMetrics fm = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
        
        g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),this);
        
        drawGUI(g);
    }
    
    /**
     * You're dead, draw game over
     */
    private void drawGameOver(Graphics g) {
        
        Stage.playOnce(MusicPlayer.GAMEOVER);
        String msg = "Game Over";
        Font small = new Font("Impact", Font.BOLD, 40);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
        int low =  hm.getScores().get(9).getScore();
        if (spaceship.getScore() > low)
        {
            enter_score = true;
            msg = "Congratulations! You are in the top 10! Enter your name:";
            small = new Font("Impact", Font.BOLD, 20);
            fm = getFontMetrics(small);
            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2+40);
            msg = name+"_";
            g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2+60);
            
        }
        
    }
    
    /**
     * This draws out our main menu
     */
    private void drawMainMenu(Graphics g) {
        Stage.setSong(MusicPlayer.MENU);
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        String title = "Final Quest";
        Font small = new Font("Impact", Font.BOLD, 80);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.gray);
        g.setFont(small);
        g.drawString(title, (B_WIDTH - fm.stringWidth(title)) / 2+2, B_HEIGHT / 7+2);
        g.setColor(Color.white);
        g.drawString(title, (B_WIDTH - fm.stringWidth(title)) / 2, B_HEIGHT / 7);
        title = "Journey for a Passing Grade";
        small = new Font("Impact", Font.BOLD, 31);
        fm = getFontMetrics(small);
        g.setColor(Color.gray);
        g.setFont(small);
        g.drawString(title, (B_WIDTH - fm.stringWidth(title)) / 2+2, B_HEIGHT / 7+32);
        g.setColor(Color.white);
        g.drawString(title, (B_WIDTH - fm.stringWidth(title)) / 2, B_HEIGHT / 7+30);
        
        int x = 540;
        int y = 300;
        int spacing = 50;
        String[] msg = {"Play", "Instructions", "High Scores", "Achievements", "Options", "Credits", "Exit"};
        small = new Font("Impact", Font.BOLD, 40);
        fm = getFontMetrics(small);
        g.setFont(small);
        for (int i = 0;i < msg.length;i++)
        {
            g.setColor(Color.gray);
            g.drawString(msg[i], x+2, y+2+i*spacing);
            if (menu_option == i)
            {
                g.setColor(Color.blue);
            }
            else
            {
                g.setColor(Color.white);
            }
            
            g.drawString(msg[i], x, y+i*spacing);
        }
        
        g.drawImage(arrow.getImage(), x-40, y+menu_option*spacing-25, null);
        
    }
    
    /**
     * This draws a list of the top 10 scores to the screen
     */
    private void drawHighscore(Graphics g) {
        Stage.setSong(MusicPlayer.MENU);
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        String msg = "Top 10";
        Font small = new Font("Impact", Font.BOLD, 40);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.gray);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2+2, B_HEIGHT / 7+2);
        g.setColor(Color.white);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 7);
        
        small = new Font("Impact", Font.BOLD, 30);
        fm = getFontMetrics(small);
        g.setFont(small);
        ArrayList<Score> scores;
        scores = hm.getScores();
        Rectangle2D bounds;
        int adjust;
        Color color;
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        
        for (int i = 0; i<10;i++)
        {
            color = new Color(255-i*20,255-i/2*20,255-i/2*20);
            msg = scores.get(i).getName();
            bounds = fm.getStringBounds(msg, g);
            adjust = (int) bounds.getWidth();
            g.setColor(Color.gray);
            g.drawString(msg, B_WIDTH/ 2-adjust-20+2, B_HEIGHT / 7+2+90+40*i);
            g.setColor(color);
            g.drawString(msg, B_WIDTH/ 2-adjust-20, B_HEIGHT / 7+90+40*i);
            msg = myFormat.format(scores.get(i).getScore());
            g.setColor(Color.gray);
            g.drawString(msg, B_WIDTH/ 2+20+2, B_HEIGHT / 7+90+2+40*i);
            g.setColor(color);
            g.drawString(msg, B_WIDTH/ 2+20, B_HEIGHT / 7+90+40*i);
            
        }
        
    }
    
    /**
     * quick instructions for the player
     */
    private void drawInstructions(Graphics g) {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg = {"#", "Instructions", "!", 
            "The goal of the game is simple. Navigate your ship through space, avoiding", 
            "enemies along the way. Destroy enemies for points. Every 30,000 points earns",
            "you a spare life.",
            " ", "#", "Controls", "!", 
            "Use the arrow keys to navigate and the space bar to fire. P pauses the game. ", 
            " ", "#", "Powerups", "!", 
            "Occasionally barrels appear on the screen if you've destroyed enough enemies.",
            "These barrels contain powerups! Destroy the barrel and touch the powerup to  ", 
            "improve your ship.                                                                           "};
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        int y = B_HEIGHT/10;
        int x = B_WIDTH/16;
        drawText(g,msg,x,y);
        Sprite p = new SpaceShip(0,0,"normal");
        g.drawImage(p.getImage(), 270, 290,this);
        p = new PowerUp(0,0);
        g.drawImage(p.getImage(), 280, 470,this);
        p.damage();
        
        String[] powers={"invincibility", "bullet", "fast", "rapid", "spread","life","points"};
        String[] msg1 = {"Temporary invincibility", "More bullets", "Move faster", "Faster bullets", "Spread fire", "Bonus life", "5,000 points!"};
        for (int i = 0; i < 7; i++)
        {
            x = 150 + (i%2)*500;
            y = 650 + (i/2) * 50;
            g.drawString(msg1[i], x+40, y+20);
            p = new PowerUp(0,0,powers[i]);
            p.damage();
            if (i==5)
            {
                x-=5;
                y-=10;
            }
            else if (i==6)
            {
                x-=10;
                y-=10;
            }
            g.drawImage(p.getImage(), x, y,this);
        }
        
        
    }
    
    /**
     * This the game's system options
     */
    private void drawOptions(Graphics g) {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg = {"#", "Options", "!", "To be included later"};
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        int y = B_HEIGHT/7;
        drawCenteredText(g,msg,y);
        
    }
    
    /**
     * Any achievements the player has acquired are displayed here
     */
    private void drawAchievements(Graphics g) {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg = {"#", "Achievements", "!", "To be included later"};
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        int y = B_HEIGHT/7;
        drawCenteredText(g,msg,y);
        
    }
    
    /**
     * This the game's credits
     */
    private void drawCredits(Graphics g) {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg = {"#", "Programmers", "!", "Marco Tacca", "Nicholas Gacharich", 
            " ", "#", "Game Assets", "!", "OpenGameArt.org", 
            " ", "#", "Special Thanks", "!", "Dr. David Jaramillo"};
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        int y = B_HEIGHT/7;
        drawCenteredText(g,msg,y);
        
    }
    
    /**
     * Used for drawing centered text to the screen
     * @param g the screen to be drawn on
     * @param msg   an array of strings. "#" sets size to large, "!" to small and " " skips a line
     * @param y     y coordinate for the top of the text
     */
    private void drawCenteredText(Graphics g, String[] msg, int y)
    {
        Stage.setSong(MusicPlayer.MENU);
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        int adjust = 40;
        Font size = new Font("Impact", Font.BOLD, adjust);
        FontMetrics fm = getFontMetrics(size);
        g.setFont(size);
        for (String msg1 : msg) {
            switch (msg1) {
                case "#":
                    adjust = 40;
                    size = new Font("Impact", Font.BOLD, adjust);
                    fm = getFontMetrics(size);
                    g.setFont(size);
                    break;
                case "!":
                    adjust = 30;
                    size = new Font("Impact", Font.BOLD, adjust);
                    fm = getFontMetrics(size);
                    g.setFont(size);
                    break;
                case " ":
                    y += 100;
                    break;
                default:
                    g.setColor(Color.gray);
                    g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2, y);
                    g.setColor(Color.white);
                    g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2 + 2, y+2);
                    y+=adjust;
                    break;
            }
        }
    }
    
    /**
     * Used for drawing left aligned text to the screen
     * @param g the screen to be drawn on
     * @param msg   an array of strings. "#" sets size to large, "!" to small and " " skips a line
     * @param y     y coordinate for the top of the text
     */
    private void drawText(Graphics g, String[] msg, int x , int y)
    {
        Stage.setSong(MusicPlayer.MENU);
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        int adjust = 40;
        Font size = new Font("Impact", Font.BOLD, adjust);
        FontMetrics fm = getFontMetrics(size);
        g.setFont(size);
        for (String msg1 : msg) {
            switch (msg1) {
                case "#":
                    adjust = 40;
                    size = new Font("Impact", Font.BOLD, adjust);
                    fm = getFontMetrics(size);
                    g.setFont(size);
                    break;
                case "!":
                    adjust = 30;
                    size = new Font("Impact", Font.BOLD, adjust);
                    fm = getFontMetrics(size);
                    g.setFont(size);
                    break;
                case " ":
                    y += 100;
                    break;
                default:
                    g.setColor(Color.gray);
                    g.drawString(msg1, x, y);
                    g.setColor(Color.white);
                    g.drawString(msg1, x + 2, y+2);
                    y+=adjust;
                    break;
            }
        }
    }
    
    /**
     * The game is paused, draw a big pause in the middle of the screen
     */
    private void drawPause(Graphics g) {
        
        String msg = "PAUSED!";
        Font small = new Font("Impact", Font.BOLD, 100);
        FontMetrics fm = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
    
    /*
    * Update all our objects and repaint
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        inGame();
        if (game_mode == "gametime" || game_mode == "dead" || game_mode == "complete")
        {
            updateBackground();
            updateShip();
            updateMissiles();
            updateAliens();
            
            checkCollisions();
        }
        else if (game_mode == "starting" || game_mode == "boss" || game_mode == "flyoff")
        {
            updateBackground();
            updateShip();
        }
        repaint();
    }
    
    /**
     * Stop our game timer if we are not currently playing
     */
    private void inGame() {
        
        if (game_mode != "gametime") {
            //timer.stop();
        }
    }
    
    /**
     * Background images should move
     */
    private void updateBackground() {
        for (Background bg : background)
        {
            bg.move();
        }
    }
    
    /**
     * Move our ship, if necessary
     */
    private void updateShip() {
        if (spaceship.isVisible()) {
            spaceship.move();
        }
    }
    
    /**
     * move or destroy our player's missiles
     */
    private void updateMissiles() {
        
        List<Missile> ms = spaceship.getMissiles();
        
        for (int i = 0; i < ms.size(); i++) {
            
            Missile m = ms.get(i);
            
            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
        
        for (int i = 0; i < explosions.size(); i++) {
            
            Explosion m = explosions.get(i);
            
            if (m.isVisible()) {
                m.move();
            } else {
                explosions.remove(i);
            }
        }
        
        for (Sprite alien : aliens) { // then the aliens' missiles
            if (alien.isVisible()) {
                ms = alien.getMissiles();
                for (int i = 0; i < ms.size(); i++) {
                    Missile m = ms.get(i);
                    if (m.isVisible())
                    {
                        if (m.isVisible()) {
                            m.move();
                        } else {
                            ms.remove(i);
                            
                        }
                    }
                }
            }
        }
        
    }
    
    /**
     * move our aliens
     */
    private void updateAliens() {
        
        if (aliens.size() <= 0 && (game_mode == "gametime" || game_mode == "starting")) {
            initAliens();
            
            return;
        }
        
        Sprite reinforce = null;
        Explosion boom = null;
        for (int i = 0; i < aliens.size(); i++) {
            
            Sprite a = aliens.get(i);
            boom = a.getBoom();
            if (boom != null)
            {
                explosions.add(boom);
            }
            if (reinforce == null)
            {
                reinforce = a.checkReinforcements();
            }
            if (a.isVisible()) {
                a.move();
            } else {
                aliens.remove(i);
                
            }
        }
        if (reinforce != null)
        {
            aliens.add(reinforce);
        }
    }
    
    /**
     * check to see if anything has hit anything
     */
    public void checkCollisions() {
        
        Rectangle r3 = spaceship.getBounds();
        
        for (Sprite alien : aliens) {
            
            Rectangle r2 = alien.getBounds();
            
            if (r3.intersects(r2) && !spaceship.isInvincibile() && alien.getType() != "powerup") {
                
                if (spaceship.die() <= 0)
                {
                    spaceship.setVisible(false);
                    alien.setVisible(false);
                    game_mode = "gameover";
                }
            }
            else if (r3.intersects(r2) && alien.getType() == "powerup")
            {
                alien.fire();
                spaceship.powerup(alien.getType());
                alien.setVisible(false);
            }
        }
        
        
        Rectangle r2;
        List<Missile> ms;
        for (Sprite alien : aliens) { // check alien missile collisions
            if (alien.isVisible()) {
                ms = alien.getMissiles();
                for (int i = 0; i < ms.size(); i++) {
                    Missile m = ms.get(i);
                    r2 = m.getBounds();
                    r3 = spaceship.getBounds();
                    for (Sprite alien2 : aliens) { // alien missiles cant shoot through blocks
                        
                        if (r2.intersects(alien2.getBounds()) && alien2.getType() == "block")
                        {
                            ms.remove(i);
                            break;
                        }
                    }
                    if ((r2.intersects(r3) && !spaceship.isInvincibile() && m.isVisible()))
                    {
                        ms.remove(i);
                        if (spaceship.die() <= 0)
                        {
                            spaceship.setVisible(false);
                            alien.setVisible(false);
                            game_mode = "gameover";
                            
                        }
                    }
                    else if (m.destroy())
                    {
                        ms.remove(i);
                    }
                }
            }
        }
        ms = spaceship.getMissiles();
        
        for (Missile m : ms) { // check player missile collisions
            Rectangle r1 = m.getBounds();
            for (Sprite alien : aliens) {
                r2 = alien.getBounds();
                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    if (alien.damage() <=0)
                    {
                        
                        if (alien.getType() == "boss" )
                        {
                            alien.explode();
                            if (game_mode !="complete")
                            {
                                spaceship.addPoints(alien.getPoints());
                                spaceship.killCount();
                            }
                            spaceship.completeMode();
                        }
                        else if (alien.getType() == "enemy")
                        {
                            alien.setVisible(false);
                            spaceship.addPoints(alien.getPoints());
                            spaceship.killCount();
                            explosions.add(new Explosion(alien.getX(),alien.getY()));
                        }
                        else if (alien.getType() == "miniboss")
                        {
                            alien.explode();
                            spaceship.addPoints(alien.getPoints());
                            spaceship.killCount();
                        }
                    }
                }
            }
            
        }
        if (game_mode == "complete")
        {
            for (Sprite alien : aliens) {
                if (alien.getType() != "boss" )
                {
                    alien.setVisible(false);
                    explosions.add(new Explosion(alien.getX(),alien.getY()));
                }
            }
        }
    }
    
    /**
     * Check our spaceship class for what to do when a button is pressed
     * or released
     */
    private class TAdapter extends KeyAdapter {
        
        @Override
        public void keyReleased(KeyEvent e) {
            if (ship_input)
            {
                spaceship.keyReleased(e);
            }
            
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            if (ship_input)
            {
                spaceship.keyPressed(e);
            }
            else
            {
                non_game_input(e);
            }
            
        }
        
    }
    
    private void non_game_input(KeyEvent e)
    {
        switch(game_mode){
            case "gameover":
                if (enter_score)
                {
                    if (e.getKeyChar() >= 32 && e.getKeyChar() <= 126 && name.length() <= 10) //letters, numbers and punctuation
                    {
                        name = name.substring(0, name.length()) + String.valueOf(e.getKeyChar());
                    }
                    else if ((e.getKeyChar() == 127 || e.getKeyChar() == 8) && name.length() > 0) // delete
                    {
                        name = name.substring(0, name.length()-1);
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_ENTER) //enter
                    {
                        enter_score = false;
                        game_mode = "highscore";
                        hm.addScore(name, spaceship.getScore());
                    }
                    
                }
                else
                {
                    game_mode = "highscore";
                }
                break;
            case "mainmenu":
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    switch (menu_option){
                        case 0: //play
                            ship_input = true;
                            spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y, difficulty);
                            Stage stage = new Stage(difficulty, spaceship);
                            break;
                        case 1: // instructions
                            game_mode = "instructions";
                            break;
                        case 2: // high scores
                            game_mode = "highscore";
                            break;
                        case 3: // achievements
                            game_mode = "achievements";
                            break;
                        case 4: // options
                            game_mode = "options";
                            break;
                        case 5: // credits
                            game_mode = "credits";
                            break;
                        case 6: // exit
                            System.exit(0);
                            break;
                             
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                    menu_option--;
                    SoundEffect.MENU_SELECTION.play();
                    if (menu_option < 0)
                    {
                        menu_option = 6;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    menu_option++;
                    SoundEffect.MENU_SELECTION.play();
                    if (menu_option > 6)
                    {
                        menu_option = 0;
                    }
                }
                break;
            default:
                game_mode = "mainmenu";
                break;
            
        }
    }
}
