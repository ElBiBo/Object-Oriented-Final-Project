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
    private List<Alien> aliens;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 1280;
    private final int B_HEIGHT = 960;
    private final int DELAY = 15;
    private int stage_count = 0;
    private final int end_stage = 5000;
    private int level = 1;

    private final int[][] pos = { // Starting alien positions
        {238, 29}, {250, 59}, {1380, 89},
        {780, 109}, {580, 139}, {680, 239},
        {790, 259}, {760, 50}, {790, 150},
        {980, 209}, {560, 45}, {510, 70},
        {930, 159}, {590, 80}, {530, 60},
        {940, 59}, {990, 30}, {920, 200},
        {900, 259}, {660, 50}, {540, 90},
        {810, 220}, {860, 20}, {740, 180},
        {820, 128}, {490, 170}, {700, 30}
    };

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
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Initialize our aliens and drop them all into our board
     */
    public void initAliens() {
        
        aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0]+1000, p[1]*3));
        }
    }

    /**
     * Ignore the default swing paintComponent function. This is where 
     * we decide what screen we are on. It will also be what we have to edit
     * to put our menus and possibly extra levels in
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }
    
    /**
     * Draw all the active objects on our board
     */
    private void drawObjects(Graphics g) {

        
        if (spaceship.isVisible()) { // draw our spaceship first
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

        for (Alien alien : aliens) { // then the aliens
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }

        // game GUI. right now it just has aliens left, but we can 
        // change this to display game score, level and lives
        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
    }

    /**
     * You're dead, draw game over
     */
    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 40);
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

        updateShip();
        updateMissiles();
        updateAliens();

        checkCollisions();

        repaint();
    }

    /**
     * Stop our game timer if we are not currently playing
     */
    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
        else
        {
            stage_count +=1;
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
    }

    /**
     * move our aliens
     */
    private void updateAliens() {

        if (stage_count >= end_stage || aliens.size() <= 0) {

            ingame = false;
            return;
        }

        for (int i = 0; i < aliens.size(); i++) {

            Alien a = aliens.get(i);
            
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

        for (Alien alien : aliens) {
            
            Rectangle r2 = alien.getBounds();

            if (r3.intersects(r2)) {
                
                spaceship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
            }
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (Alien alien : aliens) {

                Rectangle r2 = alien.getBounds();

                if (r1.intersects(r2)) {
                    
                    m.setVisible(false);
                    alien.setVisible(false);
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