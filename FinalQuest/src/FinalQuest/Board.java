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
    private List<Sprite> aliens;
    private List<Background> background;
    private Background GUI_bar, GUI_bar_player, warning;
    private final int B_WIDTH = 1280;
    private final int B_HEIGHT = 960;
    private final int ICRAFT_X = 200;
    private final int ICRAFT_Y = B_HEIGHT/2;
    private final int DELAY = 15;
    private int stage_count = 0;
    private int level = 1;
    private String game_mode;
    private String difficulty;  
    private int wave_count = 0;
    private Stage stage;
    private int num_players;
    
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
        Stage stage = new Stage(difficulty, spaceship);
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y, difficulty);
        GUI_bar = new Background(400,0,"src/resources/GUIbar.png");
        GUI_bar_player = new Background(0,0,"src/resources/GUIbarplayer.png");
        warning = new Background(0,0,"src/resources/warning.png");
        MusicPlayer.MUSIC.play();//Start background Music
        initBG();
        initAliens();
        
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
            case "starting":
                drawStart(g);
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
            case "boss":
                drawObjects(g);
                drawWarning(g);
                break;
            default:
                game_mode = "menu";
                break;
        }
        Toolkit.getDefaultToolkit().sync();
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
        
        drawGUI(g);
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
    
    private void drawGUI(Graphics g)
    {
        g.drawImage(GUI_bar.getImage(), GUI_bar.getX(), GUI_bar.getY(),this);
        g.drawImage(GUI_bar_player.getImage(), GUI_bar_player.getX(), GUI_bar_player.getY(),this);
        
        Font small = new Font("Impact", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(small);
        g.setFont(small);
        g.setColor(Color.black);
        g.drawString("Player 1: " + spaceship.getScore(), 14, 22);
        g.drawString("Lives: " + spaceship.getLives(), 299, 22);
        g.drawString("High Score: " + spaceship.getScore(), 410, 22);
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
        g.drawString("High Score: " + spaceship.getScore(), 411, 23);
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
        else if (game_mode == "starting" || game_mode == "boss")
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
        
        Sprite reinforce = null;
        for (int i = 0; i < aliens.size(); i++) {
            
            Sprite a = aliens.get(i);
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
