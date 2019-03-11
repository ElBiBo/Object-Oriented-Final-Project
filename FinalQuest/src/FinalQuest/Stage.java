package FinalQuest;

import java.util.ArrayList;
import java.util.List;

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
    private static SpaceShip spaceship;
    private static List<Sprite> aliens;
    private static List<Sprite> power_ups;
    private static int draw_list = 2;
    private static int current_draw = 2;
    private static Background draw_sprite;
    
    public Stage(String d, SpaceShip spaceship)
    {
        level = 2;
        wave_count = 0;
        difficulty = d;
        this.spaceship = spaceship;    
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
     * check what wave you currently are on
     * @return  the current wave as an int
     */
    public static int getWave()
    {
        return wave_count;
    }
    
    
    public static List<Sprite> sendWave(){
        aliens = new ArrayList<>();
        power_ups = new ArrayList<>();
        switch (level){
            case 1: // level 1's enemies
                switch (wave_count){
                    case 0: // level 1, ready!
                        wave_count++;
                        break;
                    case 1: // level 1, wave 1
                        MusicPlayer.MAIN2.play();//Start background Music
                        for (int i = 0;i<3;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+70*i, 50, difficulty));
                            aliens.add(new Alien1(B_WIDTH+70*i, B_HEIGHT-70, difficulty));
                            aliens.add(new Alien1(B_WIDTH+300+70*i, 150, difficulty));
                            aliens.add(new Alien1(B_WIDTH+300+70*i, B_HEIGHT-170, difficulty));
                            aliens.add(new Alien1(B_WIDTH+600+70*i, 250, difficulty));
                            power_ups.add(new Power_up(B_WIDTH+600*i, B_HEIGHT));
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
                            aliens.add(new Alien1(B_WIDTH+100+200*i, B_HEIGHT-50, difficulty));
                        }
                        wave_count++;
                        break;
                    case 11: //warning!
                        MusicPlayer.MAIN2.stop();
                        wave_count++;
                        break;
                    case 12:// level 1, boss
                        MusicPlayer.BOSS1.play();
                        aliens.add(new Boss1(B_WIDTH, B_HEIGHT/2-60, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        MusicPlayer.MAIN2.play();
                        break;
                }
                break;
            case 2: // level 2's enemies
                switch (wave_count){
                    case 0:
                        wave_count++;
                        break;
                    case 1: // level 2, wave 1
                        MusicPlayer.MAIN1.play();
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Asteroid(B_WIDTH+400*i, -64-i*1000, difficulty,2, 4));
                            aliens.add(new Asteroid(B_WIDTH-100+400*i, -164-i*1000, difficulty,1, 4));
                            aliens.add(new Asteroid(B_WIDTH-200+400*i, -264-i*1000, difficulty,0, 4));
                            power_ups.add(new Power_up(B_WIDTH+100*i, B_HEIGHT));
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
                        power_ups.add(new Power_up(B_WIDTH+100, B_HEIGHT-960));
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
                            power_ups.add(new Power_up(B_WIDTH+100*i, B_HEIGHT-70));
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
                        MusicPlayer.MAIN1.stop();
                        wave_count++;
                        break;
                    case 12:// level 1, boss
                        MusicPlayer.BOSS1.play();
                        aliens.add(new Boss2(B_WIDTH, B_HEIGHT/2-60, difficulty));
                        wave_count++;
                        break;
                    case 13: // move on to the next level
                        level++;
                        wave_count = 0;
                        
                        break;
                }
                break;
        }
        
        return (aliens);
    }
    
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
    
    private static Background level4BG()
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
    
    private static Background level5BG()
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
    
    private static Background level6BG()
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
    
    private static Background level7BG()
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
    
    private static Background level8BG()
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
}

