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
import java.util.concurrent.ThreadLocalRandom;
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
    public static SpaceShip spaceship;
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
    private String game_mode = "logo";
    private String difficulty;
    private Stage stage;
    private int num_players;
    private String name = "";
    private boolean enter_score = false;
    private boolean ship_input;
    private boolean cut_scene = false;
    private HighscoreManager hm = new HighscoreManager();
    private int menu_option = 0;
    private int step = 0;
    private int count = 0;
    private int speed = 0;
    private int num1,num2,num3, game_level;
    private int options;
    private int cursor = 0;
    private String spread_fire_mode;
    public static AchievementManager am;
    private boolean[] achievements;
    private static int wait;
    
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
        timer = new Timer(DELAY, this);
        timer.start();
        am = new AchievementManager();
        achievements = am.getAchievements();
        game_level = 1;
        spread_fire_mode = "off";
        wait = 0;
        background = new ArrayList<>();
        aliens = new ArrayList<>();
        
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
        wait--;
        if (!cut_scene && game_mode != "starting" && spaceship.checkMode() == "starting")
        {
            initAliens();
            initBG();
        }
        if (ship_input)
        {
            game_mode = spaceship.checkMode();
        }
        
        switch (game_mode) {
            case "logo":
                ship_input = false;
                cut_scene = true;
                CSLogo(g);
                break;
            case "intro":
                ship_input = false;
                cut_scene = true;
                CSIntro(g);
                break;
            case "level1":
                ship_input = false;
                cut_scene = true;
                CSLevel1(g);
                break;
            case "level2":
                ship_input = false;
                cut_scene = true;
                CSLevel2(g);
                break;
            case "level3":
                ship_input = false;
                cut_scene = true;
                CSLevel3(g);
                break;
            case "level4":
                ship_input = false;
                cut_scene = true;
                CSLevel4(g);
                break;
            case "level5":
                ship_input = false;
                cut_scene = true;
                CSLevel5(g);
                break;
            case "level6":
                ship_input = false;
                cut_scene = true;
                CSLevel6(g);
                break;
            case "level7":
                ship_input = false;
                cut_scene = true;
                CSLevel7(g);
                break;
            case "level8":
                ship_input = false;
                cut_scene = true;
                CSLevel8(g);
                break;
            case "ending":
                ship_input = false;
                cut_scene = true;
                CSEnding(g);
                break;
            case "dead":
                drawObjects(g);
                cut_scene = false;
                ship_input = true;
                break;
            case "starting":
                drawStart(g);
                cut_scene = false;
                ship_input = true;
                break;
            case "gametime":
                drawObjects(g);
                cut_scene = false;
                ship_input = true;
                break;
            case "level":
                drawLevelComplete(g);
                ship_input = true;
                cut_scene = false;
                break;
            case "pause":
                drawObjects(g);
                drawPause(g);
                ship_input = true;
                cut_scene = false;
                break;
            case "gameover":
                drawGameOver(g);
                ship_input = false;
                cut_scene = false;
                break;
            case "highscore":
                drawHighscore(g);
                ship_input = false;
                cut_scene = false;
                break;
            case "mainmenu":
                drawMainMenu(g);
                ship_input = false;
                cut_scene = false;
                break;
            case "boss":
                drawObjects(g);
                drawWarning(g);
                ship_input = true;
                cut_scene = false;
                break;
            case "complete": case "flyoff":
                drawObjects(g);
                ship_input = true;
                cut_scene = false;
                break;
            case "credits":
                drawCredits(g);
                ship_input = false;
                cut_scene = false;
                break;
            case "instructions":
                drawInstructions(g);
                ship_input = false;
                cut_scene = false;
                break;
            case "achievements":
                drawAchievements(g);
                ship_input = false;
                cut_scene = false;
                break;
            case "options":
                drawOptions(g);
                ship_input = false;
                cut_scene = false;
                break;
            default:
                game_mode = "mainmenu";
                ship_input = false;
                cut_scene = false;
                break;
        }
        for (int i=0;i<16;i++)
        {
            if (am.popupAchievement(g, i))
            {
                break;
            }
        }
    }
    
    /**
     * At some times we want the player to wait before they get the chance to
     * hit a button so they don't accidentally skip something they should see
     * (like cutscenes, for example) This function adds a little wait time
     */
    public static void waitTime()
    {
        if (wait<0)
        {    
            wait = 30;
        }
        else
        {
            wait += 30;
        }
        
    }
    
    /**
     * Animation scene for our logo
     * @param g the board we draw to
     */
    public void CSLogo(Graphics g)
    {
        switch(step)
        {
            case 0:
                if (count >= 255)
                {
                    count = 255;
                    step++;
                }
                else
                {
                    count += 15;
                    if (count > 255)
                    {
                        count = 255;
                    }
                }
                g.setColor(new Color(count, count,count));
                g.fillRect(0, 0, 1280, 960);
                g.setColor(Color.black);
                g.fillOval(355, 235, 500, 500);
                g.setColor(new Color(count, 0,0));
                g.fillOval(350, 230, 500, 500);
                break;
            case 1:
                count = 0;
                step++;
                g.setColor(Color.white);
                g.fillRect(0, 0, 1280, 960);
                g.setColor(Color.black);
                g.fillOval(355, 235, 500, 500);
                g.setColor(Color.red);
                g.fillOval(350, 230, 500, 500);
                break;
            case 2:
                g.setColor(Color.white);
                g.fillRect(0, 0, 1280, 960);
                g.setColor(Color.black);
                g.fillOval(355, 235, 500, 500);
                g.setColor(Color.red);
                g.fillOval(350, 230, 500, 500);
                count++;
                if (count > 60)
                {
                    count = 700;
                    step++;
                    speed = 50;
                    Stage.playOnce(MusicPlayer.ROCKET);
                }
                break;
            case 3:
                g.setColor(Color.white);
                g.fillRect(0, 0, 1280, 960);
                g.setColor(Color.black);
                g.fillOval(355, 235, 500, 500);
                g.drawImage(spaceship.getImage(), count, B_HEIGHT/2,this);
                g.setColor(Color.red);
                g.fillOval(350, 230, 500, 500);
                
                count+=speed/4;
                speed--;
                if (speed <= 0)
                {
                    step++;
                }
                break;
            case 4:
                g.setColor(Color.white);
                g.fillRect(0, 0, 1280, 960);
                g.setColor(Color.black);
                g.fillOval(355, 235, 500, 500);
                g.setColor(Color.red);
                g.fillOval(350, 230, 500, 500);
                g.drawImage(spaceship.getImage(), count+36, B_HEIGHT/2, -36, 39, null);
                count+=speed/4;
                speed--;
                if (count < -50)
                {
                    step++;
                    count = 0;
                }
                break;
            case 5:
                g.setColor(Color.white);
                g.fillRect(0, 0, 1280, 960);
                g.setColor(Color.black);
                g.fillOval(355, 235, 500, 500);
                g.setColor(Color.red);
                g.fillOval(350, 230, 500, 500);
                count++;
                if (count <90)
                {
                    
                }
                else if (count <255+90)
                {
                    count+=4;
                    Stage.playOnce(MusicPlayer.LOGO);
                    g.setColor(new Color(0,0,0,count-90));
                    String msg = "AKAMARU GAMES";
                    Font small = new Font("Impact", Font.BOLD, 120);
                    FontMetrics fm = getFontMetrics(small);
                    g.setFont(small);
                    g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 7*3);
                }
                else if (count < 450)
                {
                    g.setColor(new Color(0,0,0,255));
                    String msg = "AKAMARU GAMES";
                    Font small = new Font("Impact", Font.BOLD, 120);
                    FontMetrics fm = getFontMetrics(small);
                    g.setFont(small);
                    g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 7*3);
                }
                else if (count < 705)
                {
                    count +=4;
                    g.setColor(new Color(0,0,0,255));
                    String msg = "AKAMARU GAMES";
                    Font small = new Font("Impact", Font.BOLD, 120);
                    FontMetrics fm = getFontMetrics(small);
                    g.setFont(small);
                    g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 7*3);
                    g.setColor(new Color(0,0,0,count-450));
                    g.fillRect(0, 0, 1280, 960);
                }
                else{
                    g.setColor(new Color(0,0,0,255));
                    g.fillRect(0, 0, 1280, 960);
                    count = 0;
                    step++;
                }
                break;
            default:
                step = 0;
                count = 0;
                game_mode = "intro";
        }
    }
    
    /**
     * Our intro cut scene at the beginning of the game
     * @param g the board we draw to
     */
    public void CSIntro(Graphics g)
    {
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        Sprite a1 = new Alien1(0,0,"normal");
        Sprite a2 = new Alien3(0,0,"normal");
        Sprite b1 = new Boss1(0,0,"normal");
        
        Stage.playOnce(MusicPlayer.INTRO);
        String[] msg1 = {"The year is 20XX...",
            "   You have been studying hard for your post graduate degree ",
            "at the local space academy. Everything was going well until ",
            "they attacked...", "Aliens from outer space."," "," "," "};
        String[] msg2 = {"   The enemy forces rained down death and destruction upon",
            "the Earth. The worst part is that in all the noise and confusion,",
            "it is absolutely impossible for you to concentrate on your studies.",
            "Your grades are slipping and it is looking like you may fail the",
            "semester. You have only one hope: extra credit.  "," "," "," "};
        String[] msg3 = {
            "   Given your terrible grades, even extra credit is a long shot, ",
            "so you go to the professor and say that you will single-handedly ",
            "drive off the alien invasion for an 'A' in the class. The professor ",
            "takes a look at your grades and says that is a mathematical ",
            "impossibility. The best he can give you is a 'B'. ", " ", " ", " "};
        String[] msg4={
            "   \"Deal!\" you exclaim! You didn't expect he would be so kind ",
            "and happily comandeer the last remaining battle-ready ",
            "spacecraft on Earth before the professor can change his mind. ",
            "The only thing standing between you and a passing grade is an ",
            "entire fleet of aliens hell-bent on wiping out all life in ",
            "the galaxy... ",
            "That grade is as good as yours! ", " ", " "};
        
        
        int adjust;
        int d = 400;
        if (num2 > 800-d)
        {
            num2--;
        }
        if (step == 2 || step == 3)
        {
            if (num3%15 == 0)
            {
                int ex = ThreadLocalRandom.current().nextInt(B_WIDTH/2-100, B_WIDTH/2+100);
                int ey = ThreadLocalRandom.current().nextInt(850, 900);
                explosions.add(new Explosion(ex,ey));
            }
            updateMissiles();
            drawExplosions(g);
        }
        num3++;
        if (num3/8 %8 < 4)
        {
            adjust = (num3/8 % 4)*3;
        }
        else
        {
            adjust = (4-(num3/8%4))*3;
        }
        g.drawImage(a1.getImage(), num2+d, 800+adjust,this);
        g.drawImage(a1.getImage(), num2+100+d, 850+adjust,this);
        g.drawImage(a1.getImage(), num2+200+d, 900+adjust,this);
        g.drawImage(a2.getImage(), 1100-d - num2, 900+adjust,-23,28,null);
        g.drawImage(a2.getImage(), 1100-d - num2+100, 850+adjust,-23,28,null);
        g.drawImage(a2.getImage(), 1100-d - num2+200, 800+adjust,-23,28,null);
        
        adjust = num3-3070;
        if (adjust > 150)
        {
            if (adjust/8 %8 < 4)
            {
                adjust = 150+(adjust/8 % 4)*3;
            }
            else
            {
                adjust = 150+(4-(adjust/8%4))*3;
            }
        }
        if (step == 4)
        {
            g.drawImage(spaceship.getImage(), B_WIDTH/2, 960-adjust,this);
        }
        
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = 1500;
                num3 = 0;
            case 1:
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            case 2:
                if (scrollingText(g, msg2, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            case 3:
                if (scrollingText(g, msg3, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            case 4:
                if (scrollingText(g, msg4, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            default:
                step = 0;
                count = 0;
                game_mode = "mainmenu";
                num1 = -1;
                num2 = -1;
                num3 = -1;
        }
        
    }
    
    /**
     * Cut scene before the first level starts
     * @param g the board we draw to
     */
    public void CSLevel1(Graphics g)
    {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg1 = {"   You've escaped earth's atmosphere and the artificial gravity",
            "in your ship gently pulls you down into your seat. The aliens ",
            "have sent forces throughout our solar system to probe for life. ",
            "You must bring them death. ",
            "Extra credit demands this."};
        
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = 300;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(0,0));
                background.add(new Background(1920,0));
                break;
            case 1:
                for (Background bg : background)
                {
                    bg.move();
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            default:
                step = 0;
                count = 0;
                num1 = -1;
                num2 = -1;
                num3 = -1;
                ship_input = true;
                cut_scene = false;
                spaceship.startingMode();
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
            
        }
        
    }
    
    /**
     * Cut scene before the second level starts
     * @param g the board we draw to
     */
    public void CSLevel2(Graphics g)
    {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg1 = {"   With the first alien commander's ship now flaming space",
            "debris, you continue your way out of the solar system and into",
            "deep space. Asteriods are everywhere out here, threatening to ",
            "smash your tiny ship to pieces.",
            "But this is where the mothership for Earth's invasion lies.",
            "Destroy it and that should cut off their attack for now."};
        
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = -1;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(0,0,"src/resources/bg_lvl2.png"));
                background.add(new Background(2880,0,"src/resources/bg_lvl2.png"));
                break;
            case 1:
                for (Background bg : background)
                {
                    bg.move();
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            default:
                step = 0;
                count = 0;
                num1 = -1;
                num2 = -1;
                num3 = -1;
                ship_input = true;
                cut_scene = false;
                spaceship.startingMode();
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
            
        }
    }
    
    /**
     * Cut scene before the third level starts
     * @param g the board we draw to
     */
    public void CSLevel3(Graphics g)
    {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg1 = {"   The alien mothership is destroyed, and the attack on Earth",
            "has been disrupted... at least temporarily. Left to their own",
            "devices, the aliens will regroup and attack the earth with a",
            "new mothership.",
            "A new attack will not allow you peace to study.",
            "A new attack will ruin your extra credit. To battle!"};
        
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = -1;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(0,0,"src/resources/bg_lvl3.png"));
                background.add(new Background(2880,0,"src/resources/bg_lvl3.png"));
                break;
            case 1:
                for (Background bg : background)
                {
                    bg.move();
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            default:
                step = 0;
                count = 0;
                num1 = -1;
                num2 = -1;
                num3 = -1;
                ship_input = true;
                cut_scene = false;
                spaceship.startingMode();
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
            
        }
    }
    
    /**
     * Cut scene before the fourth level starts
     * @param g the board we draw to
     */
    public void CSLevel4(Graphics g)
    {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg1 = {"   The aliens are quickly ramping up their response to you.",
            "You've destroyed yet another one of their warships and countless",
            "fighters. You are about halfway to their home planet and the final",
            "source of their invasion. They will not allow you to get there,",
            "but you didn't ask for their permission. ",
            "You will wipe out their entire race!"};
        
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = -1;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(0,0,"src/resources/bg_lvl4.png"));
                background.add(new Background(1280,0,"src/resources/bg_lvl4.png"));
                break;
            case 1:
                for (Background bg : background)
                {
                    bg.move();
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            default:
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                step = 0;
                count = 0;
                num1 = -1;
                num2 = -1;
                num3 = -1;
                ship_input = true;
                cut_scene = false;
                spaceship.startingMode();
                
        }
    }
    
    /**
     * Cut scene before the fifth level starts
     * @param g the board we draw to
     */
    public void CSLevel5(Graphics g)
    {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg1 = {"   Violent swirls of pink and purple space gas flow all around",
            "as yet another warship is reduced to scrap thanks to you. Every",
            "victory you get that much closer to the alien homeworld. They are",
            "growing more and more desperate with your approach. ",
            "On your screen you see a protective structure up ahead and ",
            "fearless kamikaze warriors flying straight at you with seeking",
            "missiled...",
            "Things are heating up!"};
        
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = -1;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(0,0,"src/resources/bg_lvl5.png"));
                background.add(new Background(2880,0,"src/resources/bg_lvl5.png"));
                break;
            case 1:
                for (Background bg : background)
                {
                    bg.move();
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            default:
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                step = 0;
                count = 0;
                num1 = -1;
                num2 = -1;
                num3 = -1;
                ship_input = true;
                cut_scene = false;
                spaceship.startingMode();
                
        }
    }
    
    /**
     * Cut scene before the sixth level starts
     * @param g the board we draw to
     */
    public void CSLevel6(Graphics g)
    {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg1 = {"   Swirving around ships that expertly attempt to crash into",
            "you, you manage to destroy yet another warship. And with that",
            "victory you enter the star system of the alien homeworld.",
            "    They have prepared for your arrival, errecting laser barriers",
            "and sentry cannons in an effort to take you down. Extra credit",
            "is within your grasp. You can't allow a little planetary defense",
            "system stop you now!"};
        
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = -1;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(0,0,"src/resources/bg_lvl6.png"));
                background.add(new Background(2851,0,"src/resources/bg_lvl6.png"));
                break;
            case 1:
                for (Background bg : background)
                {
                    bg.move();
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                    }
                }
                break;
            default:
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                step = 0;
                count = 0;
                num1 = -1;
                num2 = -1;
                num3 = -1;
                ship_input = true;
                cut_scene = false;
                spaceship.startingMode();
                
        }
    }
    
    /**
     * Cut scene before the seventh level starts
     * @param g the board we draw to
     */
    public void CSLevel7(Graphics g)
    {
        int adjust;
        Stage.setSong(MusicPlayer.MENU);
        String[] msg1 = {"   Taking down the enemy spacecraft carrier with ease, you",
            "finally arrive at the alien homeworld. Their planetary defenses",
            "were useless against your skills and you descend into the planet's",
            "atmosphere. As you do, your ships sensors immediately begin to",
            "wail at you. It seems they have launched ground missiles at you!",
            "There's no way you fought through that many aliens just to get",
            "shot down now!"};
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = -1;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(-200,0,"src/resources/bg_lvl6.png"));
                background.add(new Background(900,B_HEIGHT/2+100,"src/resources/hjm-big_gas_planet.png"));
                
                break;
            case 1:
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                if (num2 < 800)
                {
                    num2++;
                }
                num3++;
                if (num3/8 %8 < 4)
                {
                    adjust = (num3/8 % 4)*3;
                }
                else
                {
                    adjust = (4-(num3/8%4))*3;
                }
                if (num2 < 940)
                {
                    g.drawImage(spaceship.getImage(), num2, 600+adjust,this);
                }
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        num2 +=3;
                        if (num2 > 1000)
                        {
                            step++;
                            num1 = -1;
                        }
                    }
                }
                break;
            default:
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                step = 0;
                count = 0;
                num1 = -1;
                num2 = -1;
                num3 = -1;
                ship_input = true;
                cut_scene = false;
                spaceship.startingMode();
                
        }
    }
    
    /**
     * Cut scene before the eighth level starts
     * @param g the board we draw to
     */
    public void CSLevel8(Graphics g)
    {
        int adjust;
        Stage.setSong(MusicPlayer.MENU);
        String[] msg1 = {"   The planet's defenses are nothing to you. The only thing left",
            "to do is to fly into the heart of the planet and destroy the head ",
            "alien. You decend from the clouds into a massive cave and your ",
            "sensors detect something big living down there...",
            "The aliens have run out of war machines and have begun ",
            "attacking without ships. The creatures are giant flying monsters",
            "that deserve to be destroyed!", 
            "You can practically TASTE that 'B'!"};
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = -1;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(0,0,"src/resources/bg_lvl7.png"));
                background.add(new Background(1914,0,"src/resources/bg_lvl7.png"));
                
                break;
            case 1:
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                num2+=2;
                if (num2 < 500)
                {
                    num3++;
                }
                
                if (num3/8 %8 < 4)
                {
                    adjust = (num3/8 % 4)*3;
                }
                else
                {
                    adjust = (4-(num3/8%4))*3;
                }
                if (num2 < 500)
                {
                    g.drawImage(spaceship.getImage(), num2, 600+adjust,this);
                }
                else
                {
                    num2++;
                    g.drawImage(spaceship.getImage(), num2, 600+num2-500,this);
                }
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    if (count > 180)
                    {
                        num2 +=3;
                        if (num2 > 1000)
                        {
                            step++;
                            num1 = -1;
                        }
                    }
                }
                break;
            default:
                for (Background bg : background)
                {
                    g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
                }
                step = 0;
                count = 0;
                num1 = -1;
                num2 = -1;
                num3 = -1;
                ship_input = true;
                cut_scene = false;
                spaceship.startingMode();
                
        }
    }
    
    /**
     * Our With the game completed, this is the ending cut scene
     * @param g the board we draw to
     */
    public void CSEnding(Graphics g)
    {
        for (Background bg : background)
        {
            g.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
        }
        
        Stage.playOnce(MusicPlayer.ENDING);
        String[] msg1 = {"   As the leader of the alien forces falls before your might,",
            "the planet begins to self destruct. You are unsure why anyone",
            "would design a planet to self destruct, but rather than nitpick ",
            "the absurdity, you decide to get out of there before you end up ",
            "dead. "};
        String[] msg2 = {"   You make it off the planet just as it goes critical and ",
            "explodes into a shower of space dust. You've defeated the alien",
            "invaders and more than earned your 'B' for the semester. ",
            "All that's left now is a long trip back home where you can cash",
            "in your reward! "," "};
        String[] msg3 = {
            "   When you finally make it back to Earth, you land back at the",
            "space academy and tell the professor what you have done and that  ",
            "the aliens will no longer be a problem.",
            "The professor smiles and says, 'You've worked pretty hard for this",
            "grade. Why couldn't you work this hard during your class?' ", " ", " ", " "};
        String[] msg4={
            "   You don't really have an answer for that, but you ask him for",
            "confirmation that your grade would be adjusted for your work.",
            "He says, 'Sure. You've certainly earned this B!'  ",
            " ",
            "",
            "",
            ""};
        String[] msg5={
            "   You celebrate your hard won victory and head back home. ",
            "However, your victory is short lived because you still have ",
            "three other classes to pass and you are doing just as badly",
            "in each of those. Guess you will be needing to drive off a few ",
            "more alien invasions for you to pass this semester...",
            "",
            "", " ", " "};
        
        String[] credit = { " ", " ", "#", "Programmers", "!", "Marco Tacca", "Nicholas Gacharich",
            " ", " ", " ", "#", "Story (or lack thereof)", "!", "Marco Tacca",
            " ", " ", " ", "#", "Game Art", "!", "OpenGameArt.org",
            " ", " ", " ", "#", "Music", "!", "Strike The Root by Alex Ft: Snowflake © copyright 2011",
            "Licensed under a Creative Commons Attribution Share-Alike  (3.0) license. ",
            "http://dig.ccmixter.org/files/AlexBeroza/31622 ",
            " ","Matthew Pablo","www.matthewpablo.com", 
            " ","Jump to Win by Neocrey",
            " ","Mr Poly",
            " ", " ", " ", "#", "Sound FX", "!", "bfxr.org",
            " ", " ", " ", "#", "Game Testing", "!", "Akamaru Gamers Club",
            " ", " ", " ", "#", "Special Thanks", "!"," ", "Dr. David Jaramillo", " ", "and YOU!",
            " ", " ", " ", "%", "No animals were harmed in the making of this game.",
            "The programmers got some carpal tunnel and countless sprites were destroyed",
            "but the animals are perfectly fine.",
            " ", "%", "All characters and events depicted in this game are entirely fictitious.", 
            "Any similarity to actual events or persons, living or dead, is purely coincidental.", " "," ",
            "#", "© 2019 Akamaru Games. All rights reserved.",
            " ", " ", " "," ", " "," "," ", "#", "The End", " " , " ", " "};
        
        int adjust;
        int ex, ey, ac;
        switch(step)
        {
            case 0:
                num1 = -1;
                step++;
                num2 = 300;
                num3 = -1;
                background = new ArrayList<>();
                background.add(new Background(0,0,"src/resources/bg_lvl8.png"));
                background.add(new Background(2000,0,"src/resources/bg_lvl8.png"));
                break;
            case 1:
                for (Background bg : background)
                {
                    for (int i = 0; i<15;i++)
                    {
                        bg.move();
                    }
                    bg.shake();
                }
                num3++;
                if (num3/8 %8 < 4)
                {
                    adjust = (num3/8 % 4)*3;
                }
                else
                {
                    adjust = (4-(num3/8%4))*3;
                }
                g.drawImage(spaceship.getImage(), num2, 500+adjust,this);
                for (int i = 0; i<num2/50;i++)
                {
                    ex = ThreadLocalRandom.current().nextInt(0, num2-40);
                    ey = ThreadLocalRandom.current().nextInt(0, 960);
                    explosions.add(new Explosion(ex,ey));
                }
                updateMissiles();
                drawExplosions(g);
                if (scrollingText(g, msg1, 40, 50, 100))
                {
                    for (int i = 0; i<num2/50 && i <1280/40;i++)
                    {
                        ex = ThreadLocalRandom.current().nextInt(0, num2-40);
                        ey = ThreadLocalRandom.current().nextInt(0, 960);
                        explosions.add(new Explosion(ex,ey));
                    }
                    num2 +=5;
                    if (num2 > 1280)
                    {
                        num3++;
                        int c = num3-836;
                        if (c>254)
                        {
                            g.setColor(new Color(255,255,255,255));
                        }
                        else
                        {
                            g.setColor(new Color(255,255,255,c));
                        }
                        g.fillRect(0, 0, 1280, 960);
                        if (c > 400)
                        {
                            step++;
                            num2 = -1;
                            num1 = -1;
                            num3 = -1;
                            count = 0;
                        }
                    }
                }
                
                break;
            case 2:
                num1 = -1;
                step++;
                num2 = 255;
                num3 = -1;
                background = new ArrayList<>();
                explosions = new ArrayList<>();
                background.add(new Background(-200,0,"src/resources/bg_lvl6.png"));
                background.add(new Background(900,B_HEIGHT/2,"src/resources/hjm-big_gas_planet.png"));
                g.setColor(new Color(255,255,255,num2));
                g.fillRect(0, 0, 1280, 960);
                break;
            case 3:
                if (num2>0)
                {
                    num2-=5;
                    g.setColor(new Color(255,255,255,num2));
                    g.fillRect(0, 0, 1280, 960);
                }
                else
                {
                    if (num3 % 3 == 0)
                    {
                        ex = ThreadLocalRandom.current().nextInt(900, 970);
                        ey = ThreadLocalRandom.current().nextInt(B_HEIGHT/2+10, B_HEIGHT/2+90);
                        explosions.add(new Explosion(ex,ey));
                    }
                    ac = 0;
                    for (Background bg : background)
                    {
                        if (ac == 1)
                        {
                            bg.shake();
                        }
                        ac++;
                    }
                    updateMissiles();
                    drawExplosions(g);
                    num3++;
                    if (num3/8 %8 < 4)
                    {
                        adjust = (num3/8 % 4)*3;
                    }
                    else
                    {
                        adjust = (4-(num3/8%4))*3;
                    }
                    g.drawImage(spaceship.getImage(), 920-num3, 500+adjust,-36, 39, null);
                }
                if (num3 > 200)
                {
                    num1 = -1;
                    num2 = -1;
                    num3 = -1;
                    step++;
                }
                break;
            case 4:
                num3++;
                if (num3/8 %8 < 4)
                {
                    adjust = (num3/8 % 4)*3;
                }
                else
                {
                    adjust = (4-(num3/8%4))*3;
                }
                g.drawImage(spaceship.getImage(), 720-num3, 500+adjust,-36, 39, null);
                ac = 0;
                for (Background bg : background)
                {
                    if (ac == 1)
                    {
                        bg.shake();
                    }
                    ac++;
                }
                updateMissiles();
                drawExplosions(g);
                g.setColor(Color.white);
                adjust = 15*num3;
                g.fillOval(955-adjust, (B_HEIGHT/2+56)-(adjust/2), adjust*2, adjust);
                if (num3>50)
                {
                    num1 = -1;
                    num2 = -1;
                    num3 = 0;
                    step++;
                    background = new ArrayList<>();
                    background.add(new Background(-200,0,"src/resources/bg_lvl6.png"));
                    
                }
                break;
            case 5:
                if (num3/8 %8 < 4)
                {
                    adjust = (num3/8 % 4)*3;
                }
                else
                {
                    adjust = (4-(num3/8%4))*3;
                }
                g.drawImage(spaceship.getImage(), 650-num3, 500+adjust,-36, 39, null);
                num2++;
                if (num2 >= 10)
                {
                    
                    adjust = (num2-10)*5;
                    
                    if (num2 >= 50)
                    {
                        adjust = (255+50-num2)*2;
                        num3++;
                        
                    }
                    if (adjust >255)
                    {
                        adjust = 255;
                    }
                    else if (adjust <0)
                    {
                        adjust = 0;
                        step++;
                    }
                    g.setColor(new Color (255,255,255,adjust));
                    g.fillRect(0, 0, 1280, 960);
                    
                }
                
                g.setColor(Color.white);
                adjust = 765-15*num3;
                g.fillOval(955-adjust, (B_HEIGHT/2+56)-(adjust/2), adjust*2, adjust);
                if (step==6)
                {
                    num3=0;
                    num2 = 0;
                }
                break;
            case 6:
                num3++;
                if (num3/8 %8 < 4)
                {
                    adjust = (num3/8 % 4)*3;
                }
                else
                {
                    adjust = (4-(num3/8%4))*3;
                }
                g.drawImage(spaceship.getImage(), 394-num2, 500+adjust,-36, 39, null);
                if (scrollingText(g, msg2, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        num2 +=10;
                        if (num2 > 500)
                        {
                            step++;
                            num1 = -1;
                            num2 = 0;
                            num3 = 0;
                            count = 0;
                            background = new ArrayList<>();
                            background.add(new Background(0,0,"src/resources/menu_image.png"));
                        }
                        
                    }
                }
                break;
            case 7:
                num3++;
                num2++;
                if (num3/8 %8 < 4)
                {
                    adjust = (num3/8 % 4)*3;
                }
                else
                {
                    adjust = (4-(num3/8%4))*3;
                }
                if (1300-num2 > B_WIDTH/2)
                {
                    g.drawImage(spaceship.getImage(), 1300-num2, 500+adjust,-36, 39, null);
                }
                else if (num2-660 < 300)
                {
                    g.drawImage(spaceship.getImage(), B_WIDTH/2, 500+(num2-660),-36, 39, null);
                }
                else if (scrollingText(g, msg3, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                        count = 0;
                    }
                }
                break;
            case 8:
                if (scrollingText(g, msg4, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = -1;
                        count = 0;
                    }
                }
                break;
            case 9:
                if (scrollingText(g, msg5, 40, 50, 100))
                {
                    if (count > 120)
                    {
                        step++;
                        num1 = 0;
                        count = 0;
                    }
                }
                break;
            case 10:
                g.drawImage(menu.getImage(), 0, 0, this);
                
                if (creditRoll(g,credit))
                {
                    step = 0;
                    count = 0;
                    game_mode = "level1";
                    num1 = -1;
                    num2 = -1;
                    num3 = -1;
                    explosions = new ArrayList<>();
                    Stage.restart();
                }
                break;
            default:
                step = 0;
                count = 0;
                game_mode = "level1";
                num1 = -1;
                num2 = -1;
                num3 = -1;
                explosions = new ArrayList<>();
                Stage.restart();
        }
        
    }
    
    public boolean creditRoll(Graphics g, String[] msg){
        boolean done = false;
        int adjust = 40;
        int y = 650;
        int fade;
        Font size = new Font("Impact", Font.BOLD, adjust);
        FontMetrics fm = getFontMetrics(size);
        g.setFont(size);
        num1--;
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
                case "%":
                    adjust = 20;
                    size = new Font("Impact", Font.BOLD, adjust);
                    fm = getFontMetrics(size);
                    g.setFont(size);
                    break;
                case " ":
                    y += 100;
                    break;
                default:
                    if (msg1 == "The End" && y+num1+50 < 350)
                    {
                        g.setColor(Color.gray);
                        g.drawString("The End?", (B_WIDTH - fm.stringWidth(msg1)) / 2, 350);
                        g.setColor(Color.white);
                        g.drawString("The End?", (B_WIDTH - fm.stringWidth(msg1)) / 2 + 2, 350);
                    }
                    else if (y+num1 > 550)
                    {
                        fade = 255-(y+num1-(550));
                        if (fade >255)
                        {
                            fade = 255;
                        }
                        else if (fade <0)
                        {
                            fade =0;
                        }
                        g.setColor(new Color(153,153,153,fade));
                        g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2, y+num1+50);
                        g.setColor(new Color(255,255,255,fade));
                        g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2 + 2, y+2+num1+50);
                    }
                    else if (y+num1 < 100)
                    {
                        fade = y+num1;
                        if (fade >255)
                        {
                            fade = 255;
                        }
                        else if (fade <0)
                        {
                            fade =0;
                        }
                        g.setColor(new Color(153,153,153,fade));
                        g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2, y+num1+50);
                        g.setColor(new Color(255,255,255,fade));
                        g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2 + 2, y+2+num1+50);
                    }
                    else
                    {
                        g.setColor(Color.gray);
                        g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2, y+num1+50);
                        g.setColor(Color.white);
                        g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2 + 2, y+2+num1+50);
                        
                    }
                    y+=adjust;
                    break;
            }
        }
        return done;
    }
    
    /**
     * Used to make text appear during cut scenes.
     * This makes use of Board's count and num1 variables to keep track of things.
     * They should be initiated to 0 and -1 respectively prior to using this function
     * @param g     The graphics board we're drawing to
     * @param msg   the message to be displayed. It should be an array of strings with each string representing a line
     * @param size  desired font size
     * @param x     starting x position
     * @param y     starting y position
     * @return      returns true when the text is fully displayed, otherwise false.
     */
    public boolean scrollingText(Graphics g, String[] msg, int size, int x, int y){
        Font fontsize = new Font("Impact", Font.BOLD, size);
        FontMetrics fm = getFontMetrics(fontsize);
        g.setFont(fontsize);
        size = size*2;
        for (int i = 0; i<=num1;i++)
        {
            g.setColor(new Color(153,153,153));
            g.drawString(msg[i], x, y+i*size);
            g.setColor(new Color(255,255,255));
            g.drawString(msg[i], x+2, y+i*size+2);
        }
        
        count+= 2;
        if (count > 255)
        {
            count = 255;
        }
        if (num1+1<msg.length)
        {
            g.setColor(new Color(153,153,153,count));
            g.drawString(msg[num1+1], x, y+(num1+1)*size);
            g.setColor(new Color(255,255,255,count));
            g.drawString(msg[num1+1], x+2, y+(num1+1)*size+2);
        }
        if (count >= 255)
        {
            count = 0;
            if (num1+1 < msg.length)
            {
                num1++;
            }
        }
        return num1+1 >= msg.length;
    }
    
    /**
     * Setter for the game mode
     * @param m a string representing the current game mode
     */
    public void setGameMode(String m)
    {
        game_mode = m;
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
    
    /**
     * used to draw in any explosions that happen in the game
     * @param g the board we're drawing to
     */
    private void drawExplosions(Graphics g)
    {
        for (Explosion e : explosions){
            if (e.isVisible())
            {
                g.drawImage(e.getImage(), e.getX(), e.getY(), this);
            }
        }
    }
    
    /**
     * When the boss appears, a warning is drawn on the screen
     * @param g  the board we're drawing to
     */
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
    
    /**
     * Draw a level complete screen whenever we beat a level
     * @param g  the board we're drawing to
     */
    private void drawLevelComplete(Graphics g)
    {
        String msg = "Level complete!";
        Font small = new Font("Impact", Font.BOLD, 40);
        FontMetrics fm = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
        
        
        small = new Font("Impact", Font.BOLD, 30);
        fm = getFontMetrics(small);
        g.setFont(small);
        msg = "Aliens destroyed: "+spaceship.getKills()+"    Accuracy: "+spaceship.getAccuracy()+"%";
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2+40);
        
        drawGUI(g);
    }
    
    /**
     * Draw the GUI to the screen to display points, lives and the high score
     * @param g  the board we're drawing to
     */
    private void drawGUI(Graphics g)
    {
        int high_score = hm.getScores().get(0).getScore();
        if (spaceship.getScore() > high_score)
        {
            high_score = spaceship.getScore();
            am.addAchievement(11);
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
        msg = new String[] {"%", "© 2019 Akamaru Games. All rights reserved."};
        drawCenteredText(g,msg,190);
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
        String[] msg = {"#", "Options"};
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        int y = B_HEIGHT/7;
        int x = 540;
        drawCenteredText(g,msg,y);
        
        y = 200;
        int spacing = 50;
        msg = new String[] {"Difficulty: ", "Sound: ", "Music: ", "Start Level: ", "Spread fire: ", "Back"};
        
        Font small = new Font("Impact", Font.BOLD, 30);
        FontMetrics fm = getFontMetrics(small);
        g.setFont(small);
        if (options <0)
        {
            options = 5;
        }
        if (options > 5)
        {
            options = 0;
        }
        String sound, music, level, spread_fire;
        if (SoundEffect.volume == SoundEffect.Volume.MUTE)
        {
            sound = "Off";
        }
        else
        {
            sound = "On";
        }
        if (MusicPlayer.volume == MusicPlayer.Volume.MUTE)
        {
            music = "Off";
        }
        else
        {
            music = "On";
        }
        int skip_level = 1;
        achievements = am.getAchievements();
        for (int i = 0; i<7;i++)
        {
            if (!achievements[i])
            {
                break;
            }
            else
            {
                skip_level++;
            }
        }
        if (game_level > skip_level)
        {
            game_level = 1;
        }
        if (game_level < 1)
        {
            game_level = skip_level;
        }
        level = Integer.toString(game_level);
        
        if (!achievements[12])
        {
            spread_fire_mode = "off";
        }
        spread_fire = spread_fire_mode;
        for (int i = 0;i < msg.length;i++)
        {
            switch (i)
            {
                case 0:
                    msg[i] += difficulty;
                    break;
                case 1:
                    msg[i] += sound;
                    break;
                case 2:
                    msg[i] += music;
                    break;
                case 3:
                    msg[i] += level;
                    break;
                case 4:
                    msg[i] += spread_fire;
                    break;
            }
            
            if (i==3)
            {
                y+=60;
                drawCenteredText(g,new String[] {"Cheats","!", "(must be unlocked to use) "},y+2+i*spacing);
                y+=90;
            }
            g.setColor(Color.gray);
            g.drawString(msg[i], x+2, y+2+i*spacing);
            if (options == i)
            {
                g.setColor(Color.blue);
            }
            else
            {
                g.setColor(Color.white);
            }
            g.drawString(msg[i], x, y+i*spacing);
        }
        if (options <3)
        {
            y-=150;
        }
        g.drawImage(arrow.getImage(), x-40, y+options*spacing-25, null);
        
        
    }
    
    
    /**
     * Any achievements the player has acquired are displayed here
     */
    private void drawAchievements(Graphics g) {
        Stage.setSong(MusicPlayer.MENU);
        String[] msg = {"#", "Achievements"};
        String[] unknown = {"???"};
        g.drawImage(menu.getImage(), menu.getX(), menu.getY(),this);
        int y = B_HEIGHT/7;
        drawCenteredText(g,msg,y);
        Achievement a;
        int x = 50;
        y = B_HEIGHT/7*2;
        achievements = am.getAchievements();
        a = new Achievement(x, y,1);
        g.setColor(Color.red);
        g.fillRect(x-8+150*(cursor%8), y-8+150*(cursor/8),150,150);
        for (int i = 0;i<achievements.length;i++)
        {
            if (achievements[i])
            {
                a.getPlace(x+150*(i%8), y+150*(i/8),i);
                g.drawImage(a.getImage(), a.getX(), a.getY(),this);
            }
            else
            {
                g.setColor(Color.gray);
                g.fillRect(x+150*(i%8), y+150*(i/8), 134, 134);
            }
        }
        if (achievements[cursor])
        {
            drawCenteredText(g,a.getPlace(0, 0, cursor),B_HEIGHT/7*5);
        }
        else
        {
            drawCenteredText(g,unknown,B_HEIGHT/7*5);
        }
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
                case "%":
                    adjust = 20;
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
        
        
        //first check to see if the spaceship collides with anything
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
                am.addAchievement(14);
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
                    spaceship.hitCount();
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
            else if (cut_scene)
            {
                if (wait <=0)
                {
                    step = 500;
                }
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
                else if (wait <= 0)
                {
                    game_mode = "highscore";
                }
                break;
            case "mainmenu":
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    SoundEffect.SELECTION.play();
                    switch (menu_option){
                        case 0: //play
                            ship_input = true;
                            spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y, difficulty);
                            spaceship.fireMode(spread_fire_mode);
                            stage = new Stage(difficulty, spaceship);
                            stage.setLevel(game_level);
                            spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y, difficulty);
                            spaceship.fireMode(spread_fire_mode);
                            stage.spaceship = spaceship;
                            
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
            case "achievements":
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    SoundEffect.SELECTION.play();
                    game_mode = "mainmenu";
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                    SoundEffect.MENU_SELECTION.play();
                    cursor -= 8;
                    if (cursor <0)
                    {
                        cursor += 8;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    SoundEffect.MENU_SELECTION.play();
                    cursor += 8;
                    if (cursor > 15)
                    {
                        cursor -= 8;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    SoundEffect.MENU_SELECTION.play();
                    cursor += 1;
                    if (cursor > 15)
                    {
                        cursor = 15;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    SoundEffect.MENU_SELECTION.play();
                    cursor -= 1;
                    if (cursor <0)
                    {
                        cursor = 0;
                    }
                }
                break;
            case "options":
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    SoundEffect.SELECTION.play();
                    game_mode = "mainmenu";
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                    SoundEffect.MENU_SELECTION.play();
                    options--;
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    SoundEffect.MENU_SELECTION.play();
                    options++;
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    switch(options)
                    {
                        case 0:
                            switch (difficulty)
                            {
                                case "normal":
                                    difficulty = "hard";
                                    break;
                                case "hard":
                                    difficulty = "unforgiving";
                                    break;
                                default:
                                    difficulty = "normal";
                                    break;
                            }
                            break;
                        case 1:
                            if (SoundEffect.volume == SoundEffect.Volume.MUTE)
                            {
                                SoundEffect.volume = SoundEffect.Volume.HIGH;
                            }
                            else
                            {
                                SoundEffect.volume = SoundEffect.Volume.MUTE;
                            }
                            break;
                        case 2:
                            if (MusicPlayer.volume == MusicPlayer.Volume.MUTE)
                            {
                                MusicPlayer.volume = MusicPlayer.Volume.HIGH;
                            }
                            else
                            {
                                MusicPlayer.volume = MusicPlayer.Volume.MUTE;
                            }
                            Stage.stopSong();
                            Stage.startSong();
                            break;
                        case 3:
                            game_level++;
                            break;
                        case 4:
                            if (spread_fire_mode == "off")
                            {
                                spread_fire_mode = "on";
                                
                            }
                            else
                            {
                                spread_fire_mode = "off";
                            }
                            break;
                            
                    }
                    SoundEffect.MENU_SELECTION.play();
                    
                    
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    switch(options)
                    {
                        case 0:
                            switch (difficulty)
                            {
                                case "normal":
                                    difficulty = "unforgiving";
                                    break;
                                case "hard":
                                    difficulty = "normal";
                                    break;
                                default:
                                    difficulty = "hard";
                                    break;
                            }
                            break;
                        case 1:
                            if (SoundEffect.volume == SoundEffect.Volume.MUTE)
                            {
                                SoundEffect.volume = SoundEffect.Volume.HIGH;
                            }
                            else
                            {
                                SoundEffect.volume = SoundEffect.Volume.MUTE;
                            }
                            break;
                        case 2:
                            if (MusicPlayer.volume == MusicPlayer.Volume.MUTE)
                            {
                                MusicPlayer.volume = MusicPlayer.Volume.HIGH;
                            }
                            else
                            {
                                MusicPlayer.volume = MusicPlayer.Volume.MUTE;
                            }
                            Stage.stopSong();
                            Stage.startSong();
                            break;
                        case 3:
                            game_level--;
                            break;
                        case 4:
                            if (spread_fire_mode == "off")
                            {
                                spread_fire_mode = "on";
                                
                            }
                            else
                            {
                                spread_fire_mode = "off";
                            }
                            break;
                    }
                    SoundEffect.MENU_SELECTION.play();
                    
                }
                break;
            default:
                SoundEffect.SELECTION.play();
                game_mode = "mainmenu";
                break;
                
        }
    }
}
