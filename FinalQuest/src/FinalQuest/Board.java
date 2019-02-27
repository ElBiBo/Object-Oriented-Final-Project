package FinalQuest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private SpaceShip spaceship;
    private Background background1, background2;
    private List<Sprite> aliens;
    private final int B_WIDTH = 1280;
    private final int B_HEIGHT = 960;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = B_HEIGHT/2;
    private final int DELAY = 15;
    private int stage_count = 0;
    private final int END_STAGE = 12000;
    private int level = 1;
    private String game_mode = "gametime";
    private String difficulty = "normal";  //valid values: normal, hard, unforgiving
    private int wave_count = 0;
    private Stage stage; 
    
    /**
     * Constructor
     */
    public Board() {
        
        initBoard();
    }
    
    /**
     * Initialize our board
     */
    private void initBoard() {
        
        addKeyListener(new TAdapter()); //check for key input
        setFocusable(true); // pay attention to this window
        setBackground(Color.BLACK);
        Stage stage = new Stage(difficulty, spaceship);
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y, difficulty);
        background1 = new Background(0,0);
        background2 = new Background(background1.width,0);
        
        MusicPlayer.MUSIC.play();//Start background Music
        initAliens();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    /**
     * Initialize our aliens and drop them all into our board
     */
    public void initAliens() {
        
        aliens = new ArrayList<>();
        aliens =  stage.sendWave();
        
    }
    
    /**
     * Ignore the default swing paintComponent function. This is where
     * we decide what screen we are on. It will also be what we have to edit
     * to put our menus and possibly extra levels in
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        game_mode = spaceship.checkMode();
        switch (game_mode) {
            case "dead":
                drawObjects(g);
                break;
            case "gametime":
                drawObjects(g);
                break;
            case "pause":
                drawObjects(g);
                drawPause(g);
                break;
            case "gameover":
                drawGameOver(g);
                break;
            default:
                game_mode = "mainmenu";
                break;
        }
        Toolkit.getDefaultToolkit().sync();
    }
    
    /**
     * Draw all the active objects on our board
     */
    private void drawObjects(Graphics g) {
        
        g.drawImage(background1.getImage(), background1.getX(), background1.getY(), this);
        g.drawImage(background2.getImage(), background2.getX(), background2.getY(), this);
        
        if (spaceship.isVisible() && spaceship.invincibilityFlash() && !spaceship.isRespawning()) { // draw our spaceship first
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                    this);
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
        
        // game GUI. right now it just has game score, but we can
        // change this to display level and lives later
        
        Font small = new Font("Impact", Font.BOLD, 30);
        FontMetrics fm = getFontMetrics(small);
        if (difficulty == "normal")
            g.setColor(Color.white);
        else if (difficulty == "hard")
            g.setColor(Color.yellow);
        else if (difficulty == "unforgiving")
            g.setColor(Color.red);
        
        g.setFont(small);g.drawString("Score: " + spaceship.getScore(), 5, 35);
        g.setFont(small);g.drawString("Lives: " + spaceship.getLives(), 300, 35);
    }
    
    /**
     * You're dead, draw game over
     */
    private void drawGameOver(Graphics g) {
        
        String msg = "Game Over";
        MusicPlayer.MUSIC.stop();//Start background Music
        Font small = new Font("Impact", Font.BOLD, 40);
        FontMetrics fm = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
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
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }
    
    /*
    * Update all our objects and repaint
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        inGame();
        if (game_mode == "gametime" || game_mode == "dead")
        {
            updateBackground();
            updateShip();
            updateMissiles();
            updateAliens();
            
            checkCollisions();
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
        else
        {
            stage_count +=1;
        }
    }
    
    private void updateBackground() {
        background1.move();
        background2.move();
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
        
        for (Sprite alien : aliens) { // then the aliens
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
        
        if (aliens.size() <= 0) {
            initAliens();
            
            return;
        }
        else if (stage_count >= END_STAGE)
        {
            game_mode = "gameover";
            return;
        }
        
        for (int i = 0; i < aliens.size(); i++) {
            
            Sprite a = aliens.get(i);
            
            if (a.isVisible()) {
                a.move();
            } else {
                aliens.remove(i);
            }
        }
    }
    
    /**
     * check to see if anything has hit anything
     */
    public void checkCollisions() {
        
        Rectangle r3 = spaceship.getBounds();
        
        for (Sprite alien : aliens) {
            
            Rectangle r2 = alien.getBounds();
            
            if (r3.intersects(r2) && !spaceship.isInvincibile()) {
                
                if (spaceship.die() <= 0)
                {
                    spaceship.setVisible(false);
                    alien.setVisible(false);
                    game_mode = "gameover";
                }
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
                    if (r2.intersects(r3) && !spaceship.isInvincibile())
                    {
                        ms.remove(i);
                        if (spaceship.die() <= 0)
                        {
                            spaceship.setVisible(false);
                            alien.setVisible(false);
                            game_mode = "gameover";
                            
                            
                        }
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
                        spaceship.addPoints(alien.getPoints());
                        alien.setVisible(false);
                    }
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
            spaceship.keyReleased(e);
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
    }
}
