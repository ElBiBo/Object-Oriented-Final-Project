package FinalQuest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class organizes the game into levels. Each level has 10 waves
 * of minor enemies followed by a "boss". Once the boss is defeated, the level
 * is complete. There are 8 levels, followed by an ending. After that, the game
 * resets and the difficulty is increased.
 * @author Marco Tacca
 */
public class Stage {
    private static int level;
    private static int wave_count;
    private static final int B_WIDTH = 1280;
    private static final int B_HEIGHT = 960;
    private static String difficulty;

    /**
     * This was not technically needed as it was already static in board, but
     * I didn't know what I was doing when I started things and it's too much trouble
     * to go back and fix it.
     */
    public static SpaceShip spaceship;
    private static List<Sprite> aliens;
    private static List<Sprite> power_ups;
    private static int draw_list = 2;
    private static int current_draw = 2;
    private static Background draw_sprite;
    private static MusicPlayer song;
    
    /**
     * constructor
     * @param d Difficulty: normal, hard or unforgiving
     * @param s our spaceship object
     */
    public Stage(String d, SpaceShip s)
    {
        level = 1;
        wave_count = 1;
        difficulty = d;
        Stage.spaceship = s;
        
    }
    
    /**
     * Used to set the current music to whatever you want the player to play.
     * If music is currently playing, it stops that 
     * @param a should be one of the songs listed in the MusicPlayer class
     */
    public static void setSong(MusicPlayer a)
    {
        if (song != a)
        {
            if (song != null)
            {
                song.stop();
            }
            song = a;
            
            if (song != null)
            {
                song.newSong();
                song.play();
            }
        }
    }
    
    /**
     * Used to set the current music to whatever you want the player to play.
     * If music is currently playing, it stops that 
     * @param a should be one of the songs listed in the MusicPlayer class
     */
    public static void playOnce(MusicPlayer a)
    {
        if (song != a)
        {
            if (song != null)
            {
                song.stop();
            }
            song = a;
            
            if (song != null)
            {
                song.newSong();
                song.playOnce();
            }
        }
    }
    
    /**
     * Game starts over from the beginning
     */
    public static void restart()
    {
        level = 1;
        wave_count = 0;
    }
    
    /**
     * This class chooses the current music based on the level and location
     * in the level. Ideally there should be different music for each level and 
     * boss. We don't have that much free music to use though...
     */
    public static void setLevelSong()
    {
         //Start background Music
        switch (level){
            case 1:
                if (wave_count < 11)
                {
                    setSong(MusicPlayer.MAIN1); 
                }
                else if (wave_count == 11)
                {
                    setSong(null);
                }
                else if (wave_count == 12)
                {
                    setSong(MusicPlayer.BOSS1);
                }
                else
                {
                    setSong(MusicPlayer.MAIN2);
                }
                break;
            case 2:
                if (wave_count < 11)
                {
                    setSong(MusicPlayer.MAIN2); 
                }
                else if (wave_count == 11)
                {
                    setSong(null);
                }
                else if (wave_count == 12)
                {
                    setSong(MusicPlayer.BOSS1);
                }
                else
                {
                    setSong(MusicPlayer.MAIN3);
                }
                break;
            case 3:
                if (wave_count < 11)
                {
                    setSong(MusicPlayer.MAIN3); 
                }
                else if (wave_count == 11)
                {
                    setSong(null);
                }
                else if (wave_count == 12)
                {
                    setSong(MusicPlayer.BOSS1);
                }
                else
                {
                    setSong(MusicPlayer.MAIN4);
                }
                break;
            case 4:
                if (wave_count < 11)
                {
                    setSong(MusicPlayer.MAIN4); 
                }
                else if (wave_count == 11)
                {
                    setSong(null);
                }
                else if (wave_count == 12)
                {
                    setSong(MusicPlayer.BOSS1);
                }
                else
                {
                    setSong(MusicPlayer.MAIN5);
                }
                break;
            case 5:
                if (wave_count < 11)
                {
                    setSong(MusicPlayer.MAIN5); 
                }
                else if (wave_count == 11)
                {
                    setSong(null);
                }
                else if (wave_count == 12)
                {
                    setSong(MusicPlayer.BOSS1);
                }
                else
                {
                    setSong(MusicPlayer.MAIN6);
                }
                break;
            case 6:
                if (wave_count < 11)
                {
                    setSong(MusicPlayer.MAIN6); 
                }
                else if (wave_count == 11)
                {
                    setSong(null);
                }
                else if (wave_count == 12)
                {
                    setSong(MusicPlayer.BOSS1);
                }
                else
                {
                    setSong(MusicPlayer.MAIN7);
                }
                break;
            case 7:
                if (wave_count < 11)
                {
                    setSong(MusicPlayer.MAIN7); 
                }
                else if (wave_count == 11)
                {
                    setSong(null);
                }
                else if (wave_count == 12)
                {
                    setSong(MusicPlayer.BOSS1);
                }
                else
                {
                    setSong(MusicPlayer.MAIN8);
                }
                break;
            case 8:
                if (wave_count < 11)
                {
                    setSong(MusicPlayer.MAIN8); 
                }
                else if (wave_count == 11)
                {
                    setSong(null);
                }
                else if (wave_count == 12)
                {
                    setSong(MusicPlayer.BOSS1);
                }
                else
                {
                    setSong(MusicPlayer.MAIN8);
                }
                break;
        }
    }
    
    /**
     * Used to stop the current song, whatever it is.
     */
    public static void stopSong()
    {
        song.stop();
    }
    
    /**
     * Used to start the current song again, whatever it is
     */
    public static void startSong()
    {
        if (song != null)
        {
            song.play();
        }
        else
        {
            setLevelSong();
        }
    }
    
    /**
     * check what stage you currently are on
     * @return  the current level as an int
     */
    public static int getLevel()
    {
        return level;
    }
    
    /**
     * level select! input the level you want to go to. must be between 1 and 8
     * @param a the level we want to go to
     */
    public static void setLevel(int a)
    {
        level = a;
        if (level>8)
        {
            level = 1;
        }
        else if (level<1)
        {
            level = 8;
        }
    }
    
    
    /**
     * check what wave you currently are on
     * @return  the current wave as an int
     */
    public static int getWave()
    {
        return wave_count;
    }
    
    /**
     * Sends a wave of aliens
     * there are 8 levels, each consisting of 10 waves an a boss
     * @return  a list of aliens
     */
    public static List<Sprite> sendWave(){
        aliens = new ArrayList<>();
        power_ups = new ArrayList<>();
        int num;
        if (wave_count > 0 && wave_count < 11)
        {
            if (spaceship.powerupCheck())
            {
                int ypos = ThreadLocalRandom.current().nextInt(50, 890);
                spaceship.deployPowerup();
                aliens.add(new PowerUp(B_WIDTH+1000,ypos));
            }
        }
        
        setLevelSong(); //start level's music
        switch (level){
            case 1: // level 1's enemies
                switch (wave_count){
                    case 0: // level 1, ready!
                        wave_count++;
                        break;
                    case 1: // level 1, wave 1
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+70*i, 50, difficulty));
                            aliens.add(new Alien1(B_WIDTH+70*i, B_HEIGHT-70, difficulty));
                            aliens.add(new Alien1(B_WIDTH+300+70*i, 150, difficulty));
                            aliens.add(new Alien1(B_WIDTH+300+70*i, B_HEIGHT-170, difficulty));
                            aliens.add(new Alien1(B_WIDTH+600+70*i, 250, difficulty));
                            aliens.add(new Alien1(B_WIDTH+600+70*i, B_HEIGHT-270, difficulty));
                            aliens.add(new Alien1(B_WIDTH+900+70*i, 350, difficulty));
                            aliens.add(new Alien1(B_WIDTH+900+70*i, B_HEIGHT-370, difficulty));
                            aliens.add(new Alien1(B_WIDTH+1200+70*i, B_HEIGHT/2, difficulty));
                        }
                        wave_count++;
                        break;
                    case 2:// level 1, wave 2
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+100*i, 50+i*70, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 3:// level 1, wave 3
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+100*i, B_HEIGHT-70-i*70, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 4:// level 1, wave 4
                        for (int i = 0;i<10;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, 200+B_HEIGHT/2, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 5:// level 1, wave 5
                        for (int i = 0;i<10;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, 50, difficulty,4));
                            aliens.add(new Alien3(B_WIDTH+100*i, B_HEIGHT-70, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 6:// level 1, wave 6
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, B_HEIGHT-70, difficulty,4,420));
                        }
                        wave_count++;
                        break;
                    case 7:// level 1, wave 7
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+200*i, 30, difficulty,4,100));
                            aliens.add(new Alien3(B_WIDTH+200*i, 230, difficulty,4,100));
                            aliens.add(new Alien3(B_WIDTH+200*i, B_HEIGHT-50, difficulty,4,100));
                            aliens.add(new Alien3(B_WIDTH+200*i, B_HEIGHT-50-250, difficulty,4,100));
                        }
                        wave_count++;
                        break;
                    case 8:// level 1, wave 8
                        for (int i = 0;i<8;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+200*i, 200, difficulty,4,100));                           
                            aliens.add(new Alien3(B_WIDTH+200*i, B_HEIGHT-20-250, difficulty,4,100));
                        }
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+3000+70*i, 50, difficulty,10));
                            aliens.add(new Alien1(B_WIDTH+3000+70*i, B_HEIGHT-70, difficulty,10));
                            aliens.add(new Alien1(B_WIDTH+4000+70*i, B_HEIGHT/2-30, difficulty,10));
                        }
                        wave_count++;
                        break;
                    case 9:// level 1, wave 9
                        for (int i = 0;i<20;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, B_HEIGHT-70, difficulty,4,420));
                        }
                        for (int i = 0;i<10;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+50+200*i, B_HEIGHT/2-30, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 10:// level 1, wave 10 
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+200*i, 50, difficulty));
                            aliens.add(new Alien1(B_WIDTH+100+200*i, 150, difficulty));
                            aliens.add(new Alien1(B_WIDTH+200*i, 250, difficulty));
                            aliens.add(new Alien1(B_WIDTH+100+200*i, 350, difficulty));
                            aliens.add(new Alien1(B_WIDTH+200*i, 450, difficulty));
                            aliens.add(new Alien1(B_WIDTH+100+200*i, 550, difficulty));
                            aliens.add(new Alien1(B_WIDTH+200*i, 650, difficulty));
                            aliens.add(new Alien1(B_WIDTH+100+200*i, 750, difficulty));
                            aliens.add(new Alien1(B_WIDTH+200*i, 850, difficulty));
                        }
                        wave_count++;
                        break;
                    case 11: //warning!
                        wave_count++;
                        break;
                    case 12:// level 1, boss
                        aliens.add(new Boss1(B_WIDTH, B_HEIGHT/2-60, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        break;
                }
                break;
            case 2: // level 2's enemies
                switch (wave_count){
                    case 0:
                        wave_count++;
                        break;
                    case 1: // level 2, wave 1
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+400*i, -64-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-100+400*i, -164-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-200+400*i, -264-i*1000, difficulty,0, 4));
                            aliens.add(new Asteroid(B_WIDTH-300+400*i, -364-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-400+400*i, -464-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-500+400*i, -564-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-600+400*i, -664-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-700+400*i, -764-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-800+400*i, -164-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-900+400*i, -264-i*1000, difficulty,0, 4));
                            aliens.add(new Asteroid(B_WIDTH-1000+400*i, -364-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-1100+400*i, -464-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-1200+400*i, -564-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-1300+400*i, -664-i*1000, difficulty,3, 4));
                        }
                        wave_count++;
                        break;
                    case 2:// level 2, wave 2
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+100*i, 50+i*70, difficulty));
                            aliens.add(new Alien1(B_WIDTH+100*i, B_HEIGHT-70-i*70, difficulty));
                        }
                        aliens.add(new Alien2(B_WIDTH+300, B_HEIGHT/2-100, difficulty));
                        aliens.add(new Alien2(B_WIDTH+300, B_HEIGHT/2+100, difficulty));
                        wave_count++;
                        break;
                    case 3:// level 2, wave 3
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien4(B_WIDTH+100*i, B_HEIGHT-170, difficulty, 700));
                        }
                        aliens.add(new Alien2(B_WIDTH+700, B_HEIGHT/2-150, difficulty));
                        aliens.add(new Alien2(B_WIDTH+700, B_HEIGHT/2, difficulty));
                        aliens.add(new Alien2(B_WIDTH+700, B_HEIGHT/2+150, difficulty));
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+4000+100*i, B_HEIGHT-170, difficulty, 10));
                            aliens.add(new Alien1(B_WIDTH+4000+100*i, 170, difficulty, 10));
                        }
                        wave_count++;
                        break;
                    case 4:// level 2, wave 4
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+400*i, -64-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-100+400*i, -164-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-200+400*i, -264-i*1000, difficulty,0, 4));
                            aliens.add(new Asteroid(B_WIDTH-300+400*i, -364-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-400+400*i, -464-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-500+400*i, -564-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-600+400*i, -664-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-700+400*i, -764-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-800+400*i, -164-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-900+400*i, -264-i*1000, difficulty,0, 4));
                            aliens.add(new Asteroid(B_WIDTH-1000+400*i, -364-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-1100+400*i, -464-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-1200+400*i, -564-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-1300+400*i, -664-i*1000, difficulty,3, 4));
                        }
                        wave_count++;
                        break;
                    case 5:// level 2, wave 5
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien2(B_WIDTH+200+100*i, 50+i*70, difficulty));
                            aliens.add(new Alien2(B_WIDTH+200+100*i, B_HEIGHT-50-i*70, difficulty));
                        }
                        aliens.add(new Asteroid(B_WIDTH+100, 50, difficulty,3, 1));
                        aliens.add(new Asteroid(B_WIDTH+400, 50, difficulty,5, 2));
                        aliens.add(new Asteroid(B_WIDTH+200, B_HEIGHT-50, difficulty,4, -1));
                        aliens.add(new Asteroid(B_WIDTH+500, B_HEIGHT-150, difficulty,6, -1));
                        aliens.add(new Asteroid(B_WIDTH+3000, B_HEIGHT/2+300, difficulty,8, -1));
                        aliens.add(new Asteroid(B_WIDTH+3000, B_HEIGHT/2-300, difficulty,8, 1));
                        aliens.add(new Asteroid(B_WIDTH+3000, B_HEIGHT/2, difficulty,6, 0));
                        wave_count++;
                        break;
                    case 6:// level 2, wave 6
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien4(B_WIDTH+100*i, B_HEIGHT-70, difficulty));
                            aliens.add(new Alien4(B_WIDTH+100*i, 50, difficulty));
                        }
                        aliens.add(new Alien2(B_WIDTH+300, B_HEIGHT/2-10, difficulty));
                        aliens.add(new Alien2(B_WIDTH+600, 170, difficulty));
                        aliens.add(new Alien2(B_WIDTH+600, B_HEIGHT-170, difficulty));
                        wave_count++;
                        break;
                    case 7:// level 2, wave 7
                        for (int i = 0;i<12;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+50*i, B_HEIGHT-50-250, difficulty));
                            aliens.add(new Alien4(B_WIDTH+200+50*i, B_HEIGHT-250, difficulty,480));
                        }
                        aliens.add(new Alien2(B_WIDTH, B_HEIGHT-170, difficulty));
                        aliens.add(new Alien2(B_WIDTH, 150, difficulty));
                        wave_count++;
                        break;
                    case 8:// level 2, wave 8
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+400*i, B_HEIGHT+64+i*1000, difficulty,2, -4));
                            aliens.add(new Asteroid(B_WIDTH-100+400*i, B_HEIGHT+164-i*1000, difficulty,1, -4));
                            aliens.add(new Asteroid(B_WIDTH-200+400*i, B_HEIGHT+264+i*1000, difficulty,0, -4));
                            aliens.add(new Asteroid(B_WIDTH-300+400*i, B_HEIGHT+364+i*1000, difficulty,3, -4));
                            aliens.add(new Asteroid(B_WIDTH-400+400*i, B_HEIGHT+464+i*1000, difficulty,1, -4));
                            aliens.add(new Asteroid(B_WIDTH-500+400*i, B_HEIGHT+564+i*1000, difficulty,2, -4));
                            aliens.add(new Asteroid(B_WIDTH-600+400*i, B_HEIGHT+664+i*1000, difficulty,3, -4));
                            aliens.add(new Asteroid(B_WIDTH-700+400*i, B_HEIGHT+764+i*1000, difficulty,2, -4));
                            aliens.add(new Asteroid(B_WIDTH-800+400*i, B_HEIGHT+164+i*1000, difficulty,1, -4));
                            aliens.add(new Asteroid(B_WIDTH-900+400*i, B_HEIGHT+264+i*1000, difficulty,0, -4));
                            aliens.add(new Asteroid(B_WIDTH-1000+400*i, B_HEIGHT+364+i*1000, difficulty,3, -4));
                            aliens.add(new Asteroid(B_WIDTH-1100+400*i, B_HEIGHT+464+i*1000, difficulty,1, -4));
                            aliens.add(new Asteroid(B_WIDTH-1200+400*i, B_HEIGHT+564+i*1000, difficulty,2, -4));
                            aliens.add(new Asteroid(B_WIDTH-1300+400*i, B_HEIGHT+664+i*1000, difficulty,3, -4));
                        }
                        wave_count++;
                        break;
                    case 9://level 2, wave 9
                        aliens.add(new Asteroid(B_WIDTH, B_HEIGHT-170, difficulty,2));
                        aliens.add(new Alien2(B_WIDTH+100, B_HEIGHT-150, difficulty));
                        aliens.add(new Asteroid(B_WIDTH, 150, difficulty,2));
                        aliens.add(new Alien2(B_WIDTH+100, 170, difficulty));
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+1000+i*50, 230, difficulty,4,100));
                            aliens.add(new Alien3(B_WIDTH+1000+i*50, B_HEIGHT-230, difficulty,4,100));
                        }
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+3200, 50, difficulty,8));
                            aliens.add(new Alien1(B_WIDTH+3200, B_HEIGHT/2-20, difficulty,8));
                            aliens.add(new Alien1(B_WIDTH+3200, B_HEIGHT-70, difficulty,8));
                        
                        }
                        wave_count++;
                        break;
                    case 10://level 2, wave 10
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+i*100, B_HEIGHT-70, difficulty));
                            aliens.add(new Alien1(B_WIDTH+i*100, 50, difficulty));
                            aliens.add(new Alien1(B_WIDTH+i*100, B_HEIGHT/2-20, difficulty));
                        }
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+i*100, B_HEIGHT-130, difficulty,4,50));
                            aliens.add(new Alien3(B_WIDTH+i*100, 100, difficulty,4,50));
                            aliens.add(new Alien4(B_WIDTH+i*100, B_HEIGHT-270, difficulty,150));
                            aliens.add(new Alien4(B_WIDTH+i*100, 240, difficulty,150));
                        }
                        aliens.add(new Alien2(B_WIDTH+800, B_HEIGHT-130, difficulty));
                        aliens.add(new Alien2(B_WIDTH+800, 100, difficulty));
                        aliens.add(new Alien2(B_WIDTH+800, B_HEIGHT/2-20, difficulty));
                        aliens.add(new Asteroid(B_WIDTH+3000, 120, difficulty,6,1));
                        aliens.add(new Asteroid(B_WIDTH+3000, B_HEIGHT-140, difficulty,6,-1));
                        aliens.add(new Asteroid(B_WIDTH+1000, -2000, difficulty,4,7));
                        aliens.add(new Asteroid(B_WIDTH+1200, B_HEIGHT+2000, difficulty,4,-7));
                        aliens.add(new Asteroid(B_WIDTH+800, -2100, difficulty,4,7));
                        aliens.add(new Asteroid(B_WIDTH+1100, B_HEIGHT+2100, difficulty,4,-7));
                        aliens.add(new Asteroid(B_WIDTH+1500, -2500, difficulty,4,7));
                        aliens.add(new Asteroid(B_WIDTH+1700, B_HEIGHT+2500, difficulty,4,-7));
                        aliens.add(new Asteroid(B_WIDTH+1500, -2500, difficulty,5,6));
                        aliens.add(new Asteroid(B_WIDTH+1700, B_HEIGHT+2500, difficulty,5,-6));
                        aliens.add(new Asteroid(B_WIDTH+3100, -850, difficulty,6,2));
                        aliens.add(new Asteroid(B_WIDTH+3300, B_HEIGHT+800, difficulty,6,-2));
                        aliens.add(new Asteroid(B_WIDTH+2500, B_HEIGHT+3000, difficulty,4,-6));
                        aliens.add(new Asteroid(B_WIDTH+2500, B_HEIGHT+3500, difficulty,5,-5));
                        
                        wave_count++;
                        break;
                    case 11: //warning!
                        wave_count++;
                        break;
                    case 12:// level 2, boss
                        aliens.add(new Boss2(B_WIDTH, B_HEIGHT/2-60, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        break;
                }
                break;
            case 3: // level 3's enemies
                switch (wave_count){
                    case 0:
                        wave_count++;
                        break;
                    case 1: // level 3, wave 1
                        
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+100*i, 300, difficulty));
                            aliens.add(new Alien1(B_WIDTH+100*i, 500, difficulty));
                            aliens.add(new Alien4(B_WIDTH+1600+100*i, 600, difficulty));
                        }
                        wave_count++;
                        break;
                    case 2:// level 3, wave 2
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien4(B_WIDTH+100*i, 300, difficulty));
                            aliens.add(new Alien4(B_WIDTH+50+100*i, 660, difficulty));
                        }
                        wave_count++;
                        break;
                    case 3:// level 3, wave 3
                        for (int i = 0;i<12;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+100*i, B_HEIGHT-70, difficulty));
                            aliens.add(new Alien5(B_WIDTH+100*i, 50, difficulty));
                        }
                        wave_count++;
                        break;
                    case 4:// level 3, wave 4
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+400*i, 50, difficulty,6,3-i));
                            aliens.add(new Asteroid(B_WIDTH-100+400*i, 200, difficulty,6+2-i,2-i));
                            aliens.add(new Asteroid(B_WIDTH-200+400*i, 350, difficulty,6+2-i,2-i));
                            aliens.add(new Asteroid(B_WIDTH-300+400*i, 500, difficulty,6+2-i,2-i));
                            aliens.add(new Asteroid(B_WIDTH-300+400*i, 650, difficulty,6+2-i,2-i));
                            
                        }
                        wave_count++;
                        break;
                    case 5:// level 3, wave 5
                        for (int i = 0;i<12;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+100*i, B_HEIGHT-70, difficulty,400));
                            
                        }
                        wave_count++;
                        break;
                    case 6:// level 3, wave 6
                        for (int i = 0;i<7;i++)
                        {
                            aliens.add(new Alien6(B_WIDTH+100*i, B_HEIGHT-170, difficulty));
                            aliens.add(new Alien6(B_WIDTH+50+100*i, 150, difficulty));
                        }
                        
                        wave_count++;
                        break;
                    case 7:// level 3, wave 7
                        for (int i = 0;i<12;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+50*i, B_HEIGHT-50-250, difficulty));
                            //aliens.add(new Alien4(B_WIDTH+200+50*i, B_HEIGHT-250, difficulty,480));
                        }
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien6(B_WIDTH+600+100*i, B_HEIGHT, difficulty));
                            aliens.add(new Alien6(B_WIDTH+600+50+100*i, 0, difficulty));
                        }
                        wave_count++;
                        break;
                    case 8:// level 3, wave 8
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+400*i, B_HEIGHT+64+i*1000, difficulty,2, -4));
                            aliens.add(new Asteroid(B_WIDTH-100+400*i, B_HEIGHT+164-i*1000, difficulty,1, -4));
                            aliens.add(new Asteroid(B_WIDTH-200+400*i, B_HEIGHT+264+i*1000, difficulty,0, -4));
                            aliens.add(new Asteroid(B_WIDTH-300+400*i, B_HEIGHT+364+i*1000, difficulty,3, -4));
                            aliens.add(new Asteroid(B_WIDTH-400+400*i, B_HEIGHT+464+i*1000, difficulty,1, -4));
                            aliens.add(new Asteroid(B_WIDTH-500+400*i, B_HEIGHT+564+i*1000, difficulty,2, -4));
                            aliens.add(new Asteroid(B_WIDTH-600+400*i, B_HEIGHT+664+i*1000, difficulty,3, -4));
                            aliens.add(new Asteroid(B_WIDTH-700+400*i, B_HEIGHT+764+i*1000, difficulty,2, -4));
                            aliens.add(new Asteroid(B_WIDTH-800+400*i, B_HEIGHT+164+i*1000, difficulty,1, -4));
                            aliens.add(new Asteroid(B_WIDTH-900+400*i, B_HEIGHT+264+i*1000, difficulty,0, -4));
                            aliens.add(new Asteroid(B_WIDTH-1000+400*i, B_HEIGHT+364+i*1000, difficulty,3, -4));
                            aliens.add(new Asteroid(B_WIDTH-1100+400*i, B_HEIGHT+464+i*1000, difficulty,1, -4));
                            aliens.add(new Asteroid(B_WIDTH-1200+400*i, B_HEIGHT+564+i*1000, difficulty,2, -4));
                            aliens.add(new Asteroid(B_WIDTH-1300+400*i, B_HEIGHT+664+i*1000, difficulty,3, -4));
                        }
                        aliens.add(new Alien6(B_WIDTH+600, B_HEIGHT, difficulty));
                        aliens.add(new Alien6(B_WIDTH+600+50, 0, difficulty));
                        aliens.add(new Alien6(B_WIDTH+2600, B_HEIGHT+250, difficulty));
                        aliens.add(new Alien6(B_WIDTH+2600+50, -250, difficulty));
                        aliens.add(new Alien6(B_WIDTH+3600, B_HEIGHT+500, difficulty));
                        aliens.add(new Alien6(B_WIDTH+3600+50, -500, difficulty));
                        aliens.add(new Alien6(B_WIDTH+4600, B_HEIGHT+750, difficulty));
                        aliens.add(new Alien6(B_WIDTH+4600+50, -750, difficulty));
                        wave_count++;
                        break;
                    case 9://level 3, wave 9
                        for (int i = 0;i<12;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+100*i, B_HEIGHT/2+200, difficulty));
                            
                        }
                        aliens.add(new Alien6(B_WIDTH+1500, -250, difficulty));
                        aliens.add(new Alien6(B_WIDTH+1550, B_HEIGHT+270, difficulty));
                        wave_count++;
                        break;
                    case 10://level 3, wave 10
                        for (int i = 0;i<12;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+100*i, 50, difficulty,100));
                            aliens.add(new Alien5(B_WIDTH+100*i, 300, difficulty,80));
                            aliens.add(new Alien5(B_WIDTH+100*i, B_HEIGHT-300, difficulty,80));
                            aliens.add(new Alien5(B_WIDTH+100*i, B_HEIGHT-50, difficulty,100));
                            
                        }
                        wave_count++;
                        break;
                    case 11: //warning!
                        wave_count++;
                        break;
                    case 12:// level 3, boss
                        aliens.add(new Boss3(B_WIDTH, 300, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        break;
                }
                break;
            case 4: // level 4's enemies
                switch (wave_count){
                    case 0:
                        wave_count++;
                        break;
                    case 1: // level 4, wave 1
                        
                        for (int i = 0;i<30;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+100*i, GetPos.justY(64), difficulty));
                            
                        }
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+500*i, GetPos.justY(28), difficulty,6));
                            
                        }
                        wave_count++;
                        break;
                    case 2:// level 4, wave 2
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+150*i, GetPos.justY(28), difficulty,4));
                            aliens.add(new Alien2(B_WIDTH+50+150*i, GetPos.justY(28), difficulty,4));
                            if (i%2==0)
                            {
                                aliens.add(new Alien6(B_WIDTH+100+150*i, GetPos.justY(28), difficulty));
                            }
                        }
                        wave_count++;
                        break;
                    case 3:// level 4, wave 3
                        for (int i = 0;i<30;i++)
                        {
                            aliens.add(new Asteroid(GetPos.splitX(64,2,2), 0-i*100, difficulty,0,6));
                            aliens.add(new Asteroid(GetPos.splitX(64,2,1), 0-i*100, difficulty,0,6));
                            
                        }
                        wave_count++;
                        break;
                    case 4:// level 4, wave 4
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+400*i, -64-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-100+400*i, -164-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-200+400*i, -264-i*1000, difficulty,0, 4));
                            aliens.add(new Asteroid(B_WIDTH-300+400*i, -364-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-400+400*i, -464-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-500+400*i, -564-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-600+400*i, -664-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-700+400*i, -764-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-800+400*i, -164-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-900+400*i, -264-i*1000, difficulty,0, 4));
                            aliens.add(new Asteroid(B_WIDTH-1000+400*i, -364-i*1000, difficulty,3, 4));
                            aliens.add(new Asteroid(B_WIDTH-1100+400*i, -464-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-1200+400*i, -564-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-1300+400*i, -664-i*1000, difficulty,3, 4));
                        }
                        wave_count++;
                        break;
                    case 5:// level 4, wave 5
                        for (int i = 0;i<4;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+200+320*i, 50, difficulty,100));
                            aliens.add(new Alien5(B_WIDTH+200+320*i, B_HEIGHT-50, difficulty,100));
                            aliens.add(new Alien5(B_WIDTH+200+320*i, B_HEIGHT/2-50, difficulty,100,B_WIDTH-230,4));
                        }
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien2(B_WIDTH+3050+50*i, 300, difficulty,8));
                            aliens.add(new Alien2(B_WIDTH+3050+50*i, B_HEIGHT-300, difficulty,8));
                            aliens.add(new Alien2(B_WIDTH+6050+50*i, 300, difficulty,8));
                            aliens.add(new Alien2(B_WIDTH+6050+50*i, B_HEIGHT-300, difficulty,8));
                        }
                        wave_count++;
                        break;
                    case 6:// level 4, wave 6
                        for (int i = 0;i<30;i++)
                        {
                            aliens.add(new Asteroid(GetPos.splitX(64,2,2), B_HEIGHT+i*100, difficulty,0,-6));
                            aliens.add(new Asteroid(GetPos.splitX(64,2,1), B_HEIGHT+i*100, difficulty,0,-6));
                            
                        }
                        wave_count++;
                        break;
                    case 7:// level 4, wave 7
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+50+100*i, 200, difficulty));
                            aliens.add(new Alien3(B_WIDTH+100*i, B_HEIGHT-200, difficulty));
                        }
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+2500+i*64, GetPos.justY(64), difficulty));                            
                        }
                        wave_count++;
                        break;
                    case 8:// level 4, wave 8
                        for (int j = 0;j<6;j++)
                        {
                            
                            for (int i = 0;i<3;i++)
                            {
                                aliens.add(new Alien3(B_WIDTH+j*300+50*i, GetPos.justY(29), difficulty,2,100));
                            }
                        }
                        wave_count++;
                        break;
                    case 9://level 4, wave 9
                        for (int i = 0;i<16;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+i*64, GetPos.justY(64), difficulty));                            
                        }
                        for (int i = 0;i<4;i++)
                        {
                            aliens.add(new Alien2(B_WIDTH+256*i, GetPos.justY(64), difficulty,6));                            
                        }
                        wave_count++;
                        break;
                    case 10://level 4, wave 10
                        
                        wave_count++;
                        break;
                    case 11: //warning!
                        wave_count++;
                        break;
                    case 12:// level 4, boss
                        aliens.add(new Boss4(B_WIDTH, 300, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        break;
                }
                break;
            case 5: // level 5's enemies
                switch (wave_count){
                    case 0:
                        wave_count++;
                        break;
                    case 1: // level 5, wave 1
                        
                        aliens.add(new Alien7(B_WIDTH, 200, difficulty));
                        aliens.add(new Alien7(B_WIDTH+300, B_HEIGHT/2, difficulty));
                        aliens.add(new Alien7(B_WIDTH, B_HEIGHT-230, difficulty));
                        
                        wave_count++;
                        break;
                    case 2:// level 5, wave 2
                        for (int i = 0;i<5;i++)
                        {
                            aliens.add(new Alien8(B_WIDTH+i*40, 50+i*200, difficulty));
                        }
                        
                        wave_count++;
                        break;
                    case 3:// level 5, wave 3
                        for (int i = 0;i<7;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+100*i, B_HEIGHT-270, difficulty, 280, B_WIDTH-300,5));
                        }
                        for (int i = 0;i<5;i++)
                        {
                            aliens.add(new Alien8(B_WIDTH+1000+i*40, 50+i*200, difficulty));
                        }
                        aliens.add(new Alien7(B_WIDTH+500, 50, difficulty));
                        aliens.add(new Alien7(B_WIDTH+500, B_HEIGHT-50, difficulty));
                        wave_count++;
                        break;
                    case 4:// level 5, wave 4
                        for (int i = 0;i<5;i++)
                        {
                            aliens.add(new Alien8(B_WIDTH+i*40, 50+i*200, difficulty));
                        }
                        for (int i = 0;i<5;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+200+i*80, GetPos.splitY(26,3,1), difficulty));
                            aliens.add(new Alien1(B_WIDTH+200+i*80, GetPos.splitY(26,3,2), difficulty));
                            aliens.add(new Alien1(B_WIDTH+200+i*80, GetPos.splitY(26,3,3), difficulty));
                        }
                        wave_count++;
                        break;
                    case 5:// level 5, wave 5
                        for (int i = 0;i<4;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+200+320*i, 50, difficulty,100,B_WIDTH-230,4));
                            aliens.add(new Alien5(B_WIDTH+200+320*i, B_HEIGHT-50, difficulty,100,B_WIDTH-230,4));
                            aliens.add(new Alien5(B_WIDTH+200+320*i, B_HEIGHT/2-50, difficulty,100));                            
                        }
                        for (int i = 0;i<10;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+2500+i*80, GetPos.splitY(26,3,1), difficulty));
                            aliens.add(new Alien1(B_WIDTH+2200+i*80, GetPos.splitY(26,3,2), difficulty));
                            aliens.add(new Alien1(B_WIDTH+2500+i*80, GetPos.splitY(26,3,3), difficulty));
                        }
                        wave_count++;
                        break;
                    case 6:// level 5, wave 6
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, B_HEIGHT-70, difficulty,4,100));
                            aliens.add(new Alien3(B_WIDTH+100*i, 50, difficulty,4,100));
                        }
                        aliens.add(new Alien2(B_WIDTH+600, 170, difficulty));
                        aliens.add(new Alien2(B_WIDTH+600, B_HEIGHT-170, difficulty));
                        for (int i = 0;i<5;i++)
                        {
                            aliens.add(new Alien8(B_WIDTH+1500+i*40, 50+i*200, difficulty));
                        }
                        wave_count++;
                        break;
                    case 7:// level 5, wave 7
                        for (int i = 0;i<12;i++)
                        {
                            aliens.add(new Alien4(B_WIDTH+200+50*i, B_HEIGHT-250, difficulty,480));
                        }
                        aliens.add(new Alien6(B_WIDTH, B_HEIGHT-170, difficulty));
                        aliens.add(new Alien6(B_WIDTH, 150, difficulty));
                        wave_count++;
                        break;
                    case 8:// level 5, wave 8
                        for (int i = 0;i<10;i++)
                        {
                            aliens.add(new Alien2(B_WIDTH+300+i*180, GetPos.splitY(26,3,1), difficulty));
                            aliens.add(new Alien2(B_WIDTH+i*180, GetPos.splitY(26,3,2), difficulty));
                            aliens.add(new Alien2(B_WIDTH+300+i*180, GetPos.splitY(26,3,3), difficulty));
                        }
                        wave_count++;
                        break;
                    case 9://level 5, wave 9
                        for (int i = 0;i<20;i++)
                        {
                            for (int j = i; j>=0;j--)
                            {
                                aliens.add(new Block(B_WIDTH+i*32, j*32, "mdbrown", 16));
                            }
                            
                        }
                        for (int i = 20;i<45;i++)
                        {
                            for (int j = 22; j>i-20;j--)
                            {
                                aliens.add(new Block(B_WIDTH+i*32, j*32, "mdbrown", 16));
                            }
                            
                        }
                        for (int i = 0; i<10;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+700+i*80, GetPos.splitY(26,4,4), difficulty));
                        }
                        wave_count++;
                        break;
                    case 10://level 5, wave 10
                        for (int i = 25;i>0;i--)
                        {
                            for (int j = 0; j<i;j++)
                            {
                                aliens.add(new Block(B_WIDTH+600+i*32, B_HEIGHT-200-j*32, "mdbrown", 16));
                            }
                            
                        }
                        for (int i = 0;i<4;i++)
                        {
                            for (int j = 0; j<8;j++)
                            {
                                aliens.add(new Block(B_WIDTH+i*32, B_HEIGHT-200-j*32, "mdbrown", 16));
                            }
                            
                        }
                        
                        for (int i = 0;i<8;i++)
                        {
                            for (int j = 0; j<8;j++)
                            {
                                aliens.add(new Block(B_WIDTH+100+i*32, j*32, "mdbrown", 16));
                            }
                            
                        }
                        
                        for (int i = 0; i<7;i++)
                        {
                            aliens.add(new Alien4(B_WIDTH+800+i*80, 850, difficulty));
                        }
                        
                        for (int i = 0; i<7;i++)
                        {
                            aliens.add(new Alien2(B_WIDTH+800+i*80, GetPos.splitY(29, 6, 6), difficulty));
                        }
                        
                        wave_count++;
                        break;
                    case 11: //warning!
                        wave_count++;
                        break;
                    case 12:// level 5, boss
                        aliens.add(new Boss5(B_WIDTH, 300, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        break;
                }
                break;
            case 6: // level 6's enemies
                switch (wave_count){
                    case 0:
                        wave_count++;
                        break;
                    case 1: // level 6, wave 1
                        aliens.add(new LaserBarrier(B_WIDTH, 150, "sentry"));
                        aliens.add(new LaserBarrier(B_WIDTH, B_HEIGHT - 170, "sentry"));
                        aliens.add(new LaserBarrier(B_WIDTH+280, 150, "sentry"));
                        aliens.add(new LaserBarrier(B_WIDTH+280, B_HEIGHT - 170, "sentry"));
                        
                        for (int i = 0;i<2;i++)
                        {
                            for (int j = 0; j<11;j++)
                            {
                                aliens.add(new Block(B_WIDTH+100+i*32, j*32, "mdbrown", 16));
                                aliens.add(new Block(B_WIDTH+100+i*32, B_HEIGHT-32-j*32, "mdbrown", 16));
                            }
                            
                        }
                        aliens.add(new LaserBarrier(B_WIDTH+110, B_HEIGHT/2-20, "barrier"));
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+200+i*80, GetPos.splitY(29, 5, 3), difficulty));
                        }
                        for (int i = 0;i<15;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+880+i*80, GetPos.justY(29), difficulty));
                            
                        }
                        
                        wave_count++;
                        break;
                    case 2:// level 6, wave 2
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new LaserBarrier(B_WIDTH, 80+200*i, "barrier"));
                        }
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new LaserBarrier(B_WIDTH+300, 200+300*i, "sentry"));
                        }
                        wave_count++;
                        break;
                    case 3:// level 6, wave 3
                        num = -4;
                        for (int i = 0;i<202;i++)
                        {
                            aliens.add(new Block(B_WIDTH+100+i*32, 32+num*32, "mdbrown", 16));
                            aliens.add(new Block(B_WIDTH+100+i*32, B_HEIGHT-32-num*32, "mdbrown", 16));
                            
                            if (i%3==0)
                            {
                                if (i<45)
                                {
                                    num++;
                                }
                                else if (i <165)
                                {
                               
                                }
                                else
                                {
                                    num--;
                                }
                            }
                        }
                        
                        for (int i = 0;i<5;i++)
                        {
                            aliens.add(new LaserBarrier(B_WIDTH+580+(32*3*i), 100+(32*i), "sentry"));
                            aliens.add(new LaserBarrier(B_WIDTH+580+(32*3*i), B_HEIGHT-100-(32*i), "sentry"));
                            aliens.add(new LaserBarrier(B_WIDTH+1500+970*i, B_HEIGHT/2, "barrier"));
                        }
                        for (int i = 0; i < 21; i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+1600+i*180, GetPos.splitY(29, 7, 4), difficulty));
                        }
                        
                        wave_count++;
                        break;
                    case 4:// level 6, wave 4
                        for (int i = 0;i<25;i++)
                        {
                            for (int k = 0; k < 3; k++)
                            {
                                aliens.add(new Block(B_WIDTH+k*840, i*32, "mdbrown", 16));
                                aliens.add(new Block(B_WIDTH+420+k*840, B_HEIGHT-i*32, "mdbrown", 16));
                            }
                        }
                        aliens.add(new LaserBarrier(B_WIDTH+2110, 60, "barrier"));
                        aliens.add(new LaserBarrier(B_WIDTH+10, B_HEIGHT-92, "barrier"));
                        wave_count++;
                        break;
                    case 5:// level 6, wave 5
                        for (int i = 0;i<30;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+i*100, GetPos.justY(29), difficulty));
                        }
                        wave_count++;
                        break;
                    case 6:// level 6, wave 6
                        for (int i = 0;i<15;i++)
                        {
                            aliens.add(new Alien2(B_WIDTH+i*200, GetPos.justY(29), difficulty));
                        }
                        wave_count++;
                        break;
                    case 7:// level 6, wave 7
                        for (int i = 0; i<3 ; i++)
                        {
                            for (int k = 0; k<3 ; k++)
                            {
                                aliens.add(new Block(B_WIDTH+100+i*32, 400+k*32, "mdbrown", 16));
                            }
                        }
                        aliens.add(new LaserBarrier(B_WIDTH+80, 432, "sentry"));
                        aliens.add(new LaserBarrier(B_WIDTH+208, 432, "sentry"));
                        aliens.add(new LaserBarrier(B_WIDTH+144, 496, "sentry"));
                        aliens.add(new LaserBarrier(B_WIDTH+144, 368, "sentry"));
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+700+i*210, 100, difficulty,100,B_WIDTH-200,4));
                            aliens.add(new Alien5(B_WIDTH+700+i*210, B_HEIGHT-100, difficulty,100,B_WIDTH-200,4));
                        }
                        aliens.add(new LaserBarrier(B_WIDTH+800, B_HEIGHT/2, "barrier"));
                        wave_count++;
                        break;
                    case 8:// level 6, wave 8
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new LaserBarrier(B_WIDTH, 80+200*i, "barrier"));
                        }
                        for (int i = 0;i<10;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+300+i*100, GetPos.splitY(26, 3, 1), difficulty));
                            aliens.add(new Alien1(B_WIDTH+300+i*100, GetPos.splitY(26, 3, 2), difficulty));
                            aliens.add(new Alien1(B_WIDTH+300+i*100, GetPos.splitY(26, 3, 3), difficulty));
                        }
                        wave_count++;
                        break;
                    case 9://level 6, wave 9
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new LaserBarrier(B_WIDTH, 80+400*i, "barrier"));
                            aliens.add(new LaserBarrier(B_WIDTH+200, 280+400*i, "barrier"));
                            aliens.add(new LaserBarrier(B_WIDTH+400, 80+400*i, "barrier"));
                        }
                        aliens.add(new Alien8(B_WIDTH+3100, GetPos.splitY(26, 3, 1), difficulty));
                        aliens.add(new Alien8(B_WIDTH+3200, GetPos.splitY(26, 3, 2), difficulty));
                        aliens.add(new Alien8(B_WIDTH+3300, GetPos.splitY(26, 3, 3), difficulty));
                        aliens.add(new Alien8(B_WIDTH+3400, GetPos.splitY(26, 3, 1), difficulty));
                        aliens.add(new Alien8(B_WIDTH+3500, GetPos.splitY(26, 3, 2), difficulty));
                        aliens.add(new Alien8(B_WIDTH+3600, GetPos.splitY(26, 3, 3), difficulty));
                        wave_count++;
                        break;
                    case 10://level 6, wave 10
                        num = -4;
                        for (int i = 0;i<202;i++)
                        {
                            aliens.add(new Block(B_WIDTH+100+i*32, 32+num*32, "mdbrown", 16));
                            aliens.add(new Block(B_WIDTH+100+i*32, B_HEIGHT-32-num*32, "mdbrown", 16));
                            
                            if (i%3==0)
                            {
                                if (i<45)
                                {
                                    num++;
                                }
                                else if (i <165)
                                {
                               
                                }
                                else
                                {
                                    num--;
                                }
                            }
                        }
                        
                        for (int i = 0;i<5;i++)
                        {
                            aliens.add(new LaserBarrier(B_WIDTH+580+(32*3*i), 100+(32*i), "sentry"));
                            aliens.add(new LaserBarrier(B_WIDTH+580+(32*3*i), B_HEIGHT-100-(32*i), "sentry"));
                            aliens.add(new LaserBarrier(B_WIDTH+1500+970*i, B_HEIGHT/2, "barrier"));
                        }
                        for (int i = 0; i < 10; i++)
                        {
                            aliens.add(new Alien2(B_WIDTH+1600+i*360, GetPos.splitY(29, 7, 4), difficulty,2));
                        }
                        wave_count++;
                        break;
                    case 11: //warning!
                        wave_count++;
                        break;
                    case 12:// level 6, boss
                        aliens.add(new Boss6(B_WIDTH, 300, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        break;
                }
                break;
            case 7: // level 7's enemies
                switch (wave_count){
                    case 0:
                        wave_count++;
                        break;
                    case 1: // level 7, wave 1
                        for (int i = 0; i < 10; i++)
                        {
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 1), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 2), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 3), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 4), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 5), B_HEIGHT+200*i, difficulty));
                        }
                        wave_count++;
                        break;
                    case 2:// level 7, wave 2
                        for (int i = 0; i < 10; i++)
                        {
                            aliens.add(new Alien10(B_WIDTH+1400*i, GetPos.splitY(29, 5, (i%3)+2), difficulty));
                        }
                        for (int i = 0; i < 15; i++)
                        {
                            aliens.add(new Alien2(B_WIDTH+250*i, GetPos.splitY(29, 5, 1), difficulty));
                            aliens.add(new Alien2(B_WIDTH+250*i, GetPos.splitY(29, 5, 5), difficulty));
                        }
                        
                        wave_count++;
                        break;
                    case 3:// level 7, wave 3
                        for (int i = 0; i < 5; i++)
                        {
                            for (int j = 0;j<5;j++)
                            {
                                aliens.add(new LaserBarrier(GetPos.splitX(23,6,i%3+3), B_HEIGHT+j*6000, "deploy"));
                            }
                        }
                        wave_count++;
                        break;
                    case 4:// level 7, wave 4
                        for (int i = 0; i < 10; i++)
                        {
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 1), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 2), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 3), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 4), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 5), B_HEIGHT+200*i, difficulty));
                        }
                        wave_count++;
                        break;
                    case 5:// level 7, wave 5
                        for (int i = 0; i < 3; i++)
                        {
                            aliens.add(new Alien10(B_WIDTH, 150+320*i, difficulty));
                        }
                        for (int i = 0; i < 3; i++)
                        {
                            aliens.add(new LaserBarrier(GetPos.splitX(23,6,i%2+5), B_HEIGHT+1300, "deploy"));
                        }
                        wave_count++;
                        break;
                    case 6:// level 7, wave 6
                        for (int i = 0; i < 10; i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+i*400, GetPos.splitY(29,5,1), difficulty));
                            aliens.add(new Alien1(B_WIDTH+i*400, GetPos.splitY(29,5,2), difficulty));
                            aliens.add(new Alien1(B_WIDTH+i*400, GetPos.splitY(29,5,3), difficulty));
                            aliens.add(new Alien1(B_WIDTH+i*400, GetPos.splitY(29,5,4), difficulty));
                            aliens.add(new Alien1(B_WIDTH+i*400, GetPos.splitY(29,5,5), difficulty));
                            aliens.add(new Alien2(B_WIDTH+200+i*400, GetPos.splitY(29,3,1), difficulty));
                            aliens.add(new Alien2(B_WIDTH+200+i*400, GetPos.splitY(29,3,2), difficulty));
                            aliens.add(new Alien2(B_WIDTH+200+i*400, GetPos.splitY(29,3,3), difficulty));
                        }
                        wave_count++;
                        break;
                    case 7:// level 7, wave 7
                        for (int i = 0; i < 10; i++)
                        {
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 1), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 2), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 3), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 4), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 5), B_HEIGHT+200*i, difficulty));
                        }
                        wave_count++;
                        break;
                    case 8:// level 7, wave 8
                        for (int i = 0;i<16;i++)
                        {
                            aliens.add(new Alien5(B_WIDTH+79*i, 250, difficulty,200,B_WIDTH-220,4));
                            
                        }
                        aliens.add(new Alien10(B_WIDTH+2300, 150,difficulty));
                        aliens.add(new Alien10(B_WIDTH+2300, B_HEIGHT-150, difficulty));
                        wave_count++;
                        break;
                    case 9://level 7, wave 9
                        for (int i = 0;i<8;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, 300, difficulty));
                            
                        }
                        for (int i = 0;i<8;i++)
                        {
                            aliens.add(new Alien4(B_WIDTH+800+100*i, 300, difficulty));
                        }
                        for (int i = 0;i<4;i++)
                        {
                             aliens.add(new LaserBarrier(B_WIDTH+400*i, 120, "barrier"));
                             aliens.add(new LaserBarrier(B_WIDTH+400*i, B_HEIGHT-120, "barrier"));
                        }
                        aliens.add(new LaserBarrier(B_WIDTH+1200, 340, "barrier"));
                        aliens.add(new LaserBarrier(B_WIDTH+1200, B_HEIGHT-340, "barrier"));
                        wave_count++;
                        break;
                    case 10://level 7, wave 10
                        for (int i = 0; i < 10; i++)
                        {
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 1), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 2), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 3), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 4), B_HEIGHT+200*i, difficulty));
                            aliens.add(new Alien9(GetPos.splitX(10, 5, 5), B_HEIGHT+200*i, difficulty));
                        }
                        wave_count++;
                        break;
                    case 11: //warning!
                        wave_count++;
                        break;
                    case 12:// level 7, boss
                        aliens.add(new Boss7(B_WIDTH, 300, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        break;
                }
                break;
            case 8: // level 8's enemies
                switch (wave_count){
                    case 0:
                        wave_count++;
                        break;
                    case 1: // level 8, wave 1
                        num = 0;
                        for (int i = 0; i<10;i++)
                        {
                            num += GetPos.splitX(1,6,1); 
                            aliens.add(new UTentacle(B_WIDTH+num, 0));
                            num += GetPos.splitX(1,6,1); 
                            aliens.add(new Tentacle(B_WIDTH+num, B_HEIGHT-360));
                        }
                        for (int i = 0;i<40;i++)
                        {
                            aliens.add(new Alien11(B_WIDTH+1000+i*250, GetPos.splitY(29, 5, 3), difficulty));
                        }
                        wave_count++;
                        break;
                    case 2:// level 8, wave 2
                        for (int i = 0; i< 5;i++)
                        {
                            aliens.add(new Alien11(B_WIDTH+i*300, GetPos.splitY(16, 5, 5), difficulty));
                            aliens.add(new Alien11(B_WIDTH+i*300, GetPos.splitY(16, 5, 4), difficulty));
                            aliens.add(new Alien11(B_WIDTH+i*300, GetPos.splitY(16, 5, 3), difficulty));
                            aliens.add(new Alien11(B_WIDTH+i*300, GetPos.splitY(16, 5, 2), difficulty));
                            aliens.add(new Alien11(B_WIDTH+i*300, GetPos.splitY(16, 5, 1), difficulty));
                            aliens.add(new Alien12(B_WIDTH+150+i*300, GetPos.splitY(48, 2, 1), difficulty));
                            aliens.add(new Alien12(B_WIDTH+150+i*300, GetPos.splitY(48, 2, 2), difficulty));
                            
                        }
                        wave_count++;
                        break;
                    case 3:// level 8, wave 3
                        for (int i = 0; i < 5; i++)
                        {
                            for (int j = 0;j<2;j++)
                            {
                                aliens.add(new LaserBarrier(GetPos.splitX(23,6,i%3+3), B_HEIGHT+j*6000, "deploy"));
                            }
                        }
                        for (int i = 0; i < 6; i++)
                        {
                            aliens.add(new Alien11(B_WIDTH+150+i*300, GetPos.splitY(48, 2, 1), difficulty));
                            aliens.add(new Alien11(B_WIDTH+150+i*300, GetPos.splitY(48, 2, 2), difficulty));
                            aliens.add(new Alien12(B_WIDTH+1150+i*300, GetPos.splitY(48, 2, 1), difficulty));
                            aliens.add(new Alien12(B_WIDTH+1150+i*300, GetPos.splitY(48, 2, 2), difficulty));
                            aliens.add(new Alien13(B_WIDTH+2150+i*300, GetPos.splitY(48, 2, 1), difficulty));
                            aliens.add(new Alien13(B_WIDTH+2150+i*300, GetPos.splitY(48, 2, 2), difficulty));
                        }
                        wave_count++;
                        break;
                    case 4:// level 8, wave 4
                        num = 0;
                        for (int i = 0; i<8;i++)
                        {
                            num += GetPos.splitX(1,6,1); 
                            aliens.add(new UTentacle(B_WIDTH+num, 0));
                            num += GetPos.splitX(1,6,1); 
                            aliens.add(new Tentacle(B_WIDTH+num, B_HEIGHT-360));
                        }
                        for (int i = 0;i<26;i++)
                        {
                            aliens.add(new Alien12(B_WIDTH+1500+i*450, GetPos.splitY(48, 5, 3), difficulty));
                        }
                        wave_count++;
                        break;
                    case 5:// level 8, wave 5
                        for (int i = 0;i < 4;i++)
                        {
                            aliens.add(new Alien14(B_WIDTH+i*600, GetPos.splitY(48,5,(i%3)+2), difficulty));
                        }
                        wave_count++;
                        break;
                    case 6:// level 8, wave 6
                        num = 500;
                        for (int i=0;i<4;i++)
                        {
                            aliens.add(new Tentacle(num, B_HEIGHT-200+GetPos.splitY(1,100,1)));
                            num += GetPos.splitX(1,8,2);
                        }
                        for (int i = 0; i < 15; i++)
                        {
                            aliens.add(new Asteroid(GetPos.splitX(64,4,1), 0-64-i*250, difficulty,0,6));
                            aliens.add(new Asteroid(GetPos.splitX(64,4,2), 0-64-i*250, difficulty,0,6));
                            aliens.add(new Asteroid(GetPos.splitX(64,4,3), 0-64-i*250, difficulty,0,6));
                            aliens.add(new Asteroid(GetPos.splitX(64,4,4), 0-64-i*250, difficulty,0,6));
                           
                        }
                        for (int i = 0;i<20;i++)
                        {
                            aliens.add(new Alien11(B_WIDTH+2500+i*50, GetPos.justY(16), difficulty));
                        }
                        wave_count++;
                        break;
                    case 7:// level 8, wave 7
                        num = 0;
                        for (int i = 0; i<7;i++)
                        {
                            num += GetPos.splitX(1,6,1); 
                            aliens.add(new UTentacle(B_WIDTH+num, 0));
                            num += GetPos.splitX(1,6,1); 
                            aliens.add(new Tentacle(B_WIDTH+num, B_HEIGHT-360));
                        }
                        for (int i = 0;i<30;i++)
                        {
                            aliens.add(new Alien13(B_WIDTH+1500+i*300, GetPos.splitY(29, 5, 3), difficulty));
                        }
                        wave_count++;
                        break;
                    case 8:// level 8, wave 8
                        for (int i = 0; i < 5; i++)
                        {
                            for (int j = 0;j<2;j++)
                            {
                                aliens.add(new LaserBarrier(GetPos.splitX(23,6,i%3+3), B_HEIGHT+j*6000, "deploy"));
                            }
                        }
                        aliens.add(new Alien14(B_WIDTH, GetPos.splitY(48,5,3), difficulty));
                        aliens.add(new Alien14(B_WIDTH+1000, GetPos.splitY(48,5,3), difficulty));
                        wave_count++;
                        break;
                    case 9://level 8, wave 9
                        num = -4;
                        for (int i = 0;i<202;i++)
                        {
                            aliens.add(new Block(B_WIDTH+100+i*32, 32+num*32, "dkblue", 16));
                            aliens.add(new Block(B_WIDTH+100+i*32, B_HEIGHT-32-num*32, "dkblue", 16));
                            
                            if (i%3==0)
                            {
                                if (i<45)
                                {
                                    num++;
                                }
                                else if (i <165)
                                {
                               
                                }
                                else
                                {
                                    num--;
                                }
                            }
                        }
                        for (int i = 0;i<5;i++)
                        {
                            aliens.add(new LaserBarrier(B_WIDTH+580+(32*3*i), 100+(32*i), "sentry"));
                            aliens.add(new LaserBarrier(B_WIDTH+580+(32*3*i), B_HEIGHT-100-(32*i), "sentry"));
                        }
                        for (int i = 0;i<9;i++)
                        {
                            aliens.add(new LaserBarrier(B_WIDTH+1500+485*i, B_HEIGHT/2, "barrier"));
                        }
                        
                        for (int i = 0; i < 3; i++)
                        {
                            aliens.add(new Alien14(B_WIDTH+1600+i*1420, GetPos.splitY(48, 7, 4), difficulty));
                        }
                        
                        wave_count++;
                        break;
                    case 10://level 8, wave 10
                        for (int i = 0; i < 8; i++)
                        {
                            aliens.add(new Alien11(B_WIDTH+150+i*300, GetPos.splitY(48, 2, 1), difficulty));
                            aliens.add(new Alien11(B_WIDTH+150+i*300, GetPos.splitY(48, 2, 2), difficulty));
                            aliens.add(new Alien12(B_WIDTH+1150+i*300, GetPos.splitY(48, 2, 1), difficulty));
                            aliens.add(new Alien12(B_WIDTH+1150+i*300, GetPos.splitY(48, 2, 2), difficulty));
                            aliens.add(new Alien13(B_WIDTH+2150+i*300, GetPos.splitY(48, 2, 1), difficulty));
                            aliens.add(new Alien13(B_WIDTH+2150+i*300, GetPos.splitY(48, 2, 2), difficulty));
                        }
                        wave_count++;
                        break;
                    case 11: //warning!
                        wave_count++;
                        break;
                    case 12:// level 8, boss
                        aliens.add(new Boss8(B_WIDTH+200, 300, difficulty));
                        wave_count++;
                        break;
                    case 13: // ending!
                        level = 1;
                        wave_count = 0;
                        spaceship.endingMode();
                        break;
                }
                break;
        }
        
        return (aliens);
    }
    
    /**
     * Sends background sprites for use in drawing a pretty background
     * @return  a single background from the list of images currently kept track of, or null once the list is complete
     */
    public static Background background()
    {
        pickBackground();
        if (current_draw < 0)
        {
            current_draw = draw_list;
            return null;
        }
        else
        {           
            return draw_sprite;
        }
    }
    
    /**
     * Mostly used for cutscenes to set a specific background
     * @param level the level's background you desire
     */
    public static void setBackground(int level)
    {
        switch (level){
            case 1:
                draw_sprite = level1BG();
                break;
            case 2:
                draw_sprite = level2BG();
                break;
            case 3:
                draw_sprite = level3BG();
                break;
            case 4:
                draw_sprite = level4BG();
                break;
            case 5:
                draw_sprite = level5BG();
                break;
            case 6:
                draw_sprite = level6BG();
                break;
            case 7:
                draw_sprite = level7BG();
                break;
            case 8:
                draw_sprite = level8BG();
                break;
            default:
                draw_sprite = level1BG();
                break;               
        }
    }
    
    private static void pickBackground()
    {
        switch (level){
            case 1:
                draw_sprite = level1BG();
                break;
            case 2:
                draw_sprite = level2BG();
                break;
            case 3:
                draw_sprite = level3BG();
                break;
            case 4:
                draw_sprite = level4BG();
                break;
            case 5:
                draw_sprite = level5BG();
                break;
            case 6:
                draw_sprite = level6BG();
                break;
            case 7:
                draw_sprite = level7BG();
                break;
            case 8:
                draw_sprite = level8BG();
                break;
            default:
                draw_sprite = level1BG();
                break;               
        }
    }

    private static Background level1BG()
    {
        draw_list = 2;
        switch (current_draw){
            case 2:
                current_draw--;
                return new Background(0,0);
            case 1:
                current_draw--;
                return new Background(1920,0);
            default:
                current_draw--;
                break;
        }
            
        return null;
    }
    
    private static Background level2BG()
    {
        draw_list = 2;
        switch (current_draw){
            case 2:
                current_draw--;
                return new Background(0,0,"src/resources/bg_lvl2.png");
            case 1:
                current_draw--;
                return new Background(2880,0,"src/resources/bg_lvl2.png");
            default:
                current_draw--;
                break;
        }
            
        return null;
    }
    
    private static Background level3BG()
    {
        draw_list = 2;
        switch (current_draw){
            case 2:
                current_draw--;
                return new Background(0,0,"src/resources/bg_lvl3.png");
            case 1:
                current_draw--;
                return new Background(2880,0,"src/resources/bg_lvl3.png");
            default:
                current_draw--;
                break;
        }
            
        return null;
    }
    
    private static Background level4BG()
    {
        draw_list = 2;
        switch (current_draw){
            case 2:
                current_draw--;
                return new Background(0,0,"src/resources/bg_lvl4.jpg");
            case 1:
                current_draw--;
                return new Background(1280,0,"src/resources/bg_lvl4.jpg");
            default:
                current_draw--;
                break;
        }
            
        return null;
    }
    
    private static Background level5BG()
    {
        draw_list = 2;
        switch (current_draw){
            case 2:
                current_draw--;
                return new Background(0,0,"src/resources/bg_lvl5.png");
            case 1:
                current_draw--;
                return new Background(2880,0,"src/resources/bg_lvl5.png");
            default:
                current_draw--;
                break;
        }
            
        return null;
    }
    
    private static Background level6BG()
    {
        draw_list = 2;
        switch (current_draw){
            case 2:
                current_draw--;
                return new Background(0,0,"src/resources/bg_lvl6.png");
            case 1:
                current_draw--;
                return new Background(2851,0,"src/resources/bg_lvl6.png");
            default:
                current_draw--;
                break;
        }
            
        return null;
    }
    
    private static Background level7BG()
    {
        draw_list = 2;
        switch (current_draw){
            case 2:
                current_draw--;
                return new Background(0,0,"src/resources/bg_lvl7.png");
            case 1:
                current_draw--;
                return new Background(1914,0,"src/resources/bg_lvl7.png");
            default:
                current_draw--;
                break;
        }
            
        return null;
    }
    
    private static Background level8BG()
    {
        draw_list = 2;
        switch (current_draw){
            case 2:
                current_draw--;
                return new Background(0,0,"src/resources/bg_lvl8.png");
            case 1:
                current_draw--;
                return new Background(2000,0,"src/resources/bg_lvl8.png");
            default:
                current_draw--;
                break;
        }
            
        return null;
    }
}

