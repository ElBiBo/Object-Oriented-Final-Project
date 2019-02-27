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
    
    public Stage(String d, SpaceShip spaceship)
    {
        level = 1;
        wave_count = 1;
        difficulty = d;
        this.spaceship = spaceship;
        
    }
    
    public static List<Sprite> sendWave(){
        aliens = new ArrayList<>();
        switch (wave_count){
            case 1:
                for (int i = 0;i<13;i++)
                {
                    aliens.add(new Alien1(B_WIDTH+100*i, 50+i*30, difficulty));
                    aliens.add(new Alien1(B_WIDTH+100*i, B_HEIGHT-50-i*30, difficulty));
                }
                wave_count++;
                break;
            case 2:
                for (int i = 0;i<13;i++)
                {
                    aliens.add(new Alien2(B_WIDTH+100*i, 50+i*30, difficulty));
                    aliens.add(new Alien2(B_WIDTH+100*i, B_HEIGHT-50-i*30, difficulty));
                }
                wave_count++;
                break;
            case 3:
                for (int i = 0;i<13;i++)
                {
                    aliens.add(new Alien3(B_WIDTH+100*i, 50, difficulty));
                    aliens.add(new Alien3(B_WIDTH+100*i, B_HEIGHT-50, difficulty));
                }
                wave_count++;
                break;
            case 4:
                for (int i = 0;i<13;i++)
                {
                    aliens.add(new Alien4(B_WIDTH+100*i, 50, difficulty));
                    aliens.add(new Alien4(B_WIDTH+100*i, B_HEIGHT-50, difficulty));
                }
                wave_count++;
                break;
            case 5:
                for (int i = 0;i<13;i++)
                {
                    aliens.add(new Alien5(B_WIDTH+100*i, 50, difficulty));
                    aliens.add(new Alien5(B_WIDTH+100*i, B_HEIGHT-50, difficulty));
                }
                wave_count++;
                break;
            case 6:
                for (int i = 0;i<13;i++)
                {
                    aliens.add(new Alien6(B_WIDTH+100*i, 50, difficulty, spaceship));
                    aliens.add(new Alien6(B_WIDTH+50+100*i, B_HEIGHT-50, difficulty, spaceship));
                }
                wave_count = 0;
                break;
                
        }
        
        return (aliens);
    }
}
