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
        switch (level){
            case 1:
                switch (wave_count){
                    case 1:
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
                    case 2:
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+100*i, 50+i*70, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 3:
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien1(B_WIDTH+100*i, B_HEIGHT-70-i*70, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 4:
                        for (int i = 0;i<10;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, 200+B_HEIGHT/2, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 5:
                        for (int i = 0;i<10;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, 50, difficulty,4));
                            aliens.add(new Alien3(B_WIDTH+100*i, B_HEIGHT-70, difficulty,4));
                        }
                        wave_count++;
                        break;
                    case 6:
                        for (int i = 0;i<13;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+100*i, B_HEIGHT-70, difficulty,4,420));
                        }
                        wave_count++;
                        break;
                    case 7:
                        for (int i = 0;i<6;i++)
                        {
                            aliens.add(new Alien3(B_WIDTH+200*i, 30, difficulty,4,100));
                            aliens.add(new Alien3(B_WIDTH+200*i, 230, difficulty,4,100));
                            aliens.add(new Alien3(B_WIDTH+200*i, B_HEIGHT-50, difficulty,4,100));
                            aliens.add(new Alien3(B_WIDTH+200*i, B_HEIGHT-50-250, difficulty,4,100));
                        }
                        wave_count++;
                        break;
                    case 8:
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
                    case 9:
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
                    case 10:
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
                    case 11:
                        aliens.add(new Boss1(B_WIDTH, B_HEIGHT/2-60, difficulty));
                        wave_count = 0;
                        break;
                }
                break;
        }
        
        return (aliens);
    }
}
