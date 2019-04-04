package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Found myself constantly in need of a random y position for enemy positioning.
 * So, decided to just make a class for it. This provides a random Y or X position
 * within the screen, accounting for height and avoiding overlapping
 * @author Marco Tacca
 */
public class GetPos {
    
    /**
     * Used if you are only getting a single Y and aren't worrying about
     * whether it will be overlapping something else
     * @param height    The height in pixels of the object you'll be placing
     * @return  a random y position on the screen
     */
    public static int justY(int height)
    {
        int min_y = 50;
        int max_y = 960-height;
        int y = ThreadLocalRandom.current().nextInt(min_y, max_y);
        return y;
    }
    
    /**
     * This is used if you want multiple Y's in the same x position but don't
     * want them overlapping with each other
     * @param height    height of the object you want to place in pixels
     * @param zones    how many zones you'd like (>0 and not so many zones the heights are impossible to place)
     * @param pos   which zone you want the position to be in (1<=pos<=zones)
     * @return  an int y coordinate for your object
     */
    public static int splitY(int height, int zones, int pos)
    {
        int zone_size = 910/zones;
        int min_y = zone_size*(pos-1)+50;
        int max_y = min_y-height+zone_size;
        int y = ThreadLocalRandom.current().nextInt(min_y, max_y);
        return y;
    }
    
    /**
     * Used if you are only getting a single X and aren't worrying about
     * whether it will be overlapping something else
     * @param width    The width in pixels of the object you'll be placing
     * @return  a random x position on the screen
     */
    public static int justX(int width)
    {
        int min_x = 0;
        int max_x = 1280-width;
        int x = ThreadLocalRandom.current().nextInt(min_x, max_x);
        return x;
    }
    
    /**
     * This is used if you want multiple X's in the same Y position but don't
     * want them overlapping with each other
     * @param width    width of the object you want to place in pixels
     * @param zones    how many zones you'd like (>1 and not so many zones the width are impossible to place)
     * @param pos   which zone you want the position to be in (1<=pos<=zones)
     * @return  an int x coordinate for your object
     */
    public static int splitX(int width, int zones, int pos)
    {
        int zone_size = 1280/zones;
        int min_x = zone_size*(pos-1)+50;
        int max_x = min_x-width+zone_size;
        int x = ThreadLocalRandom.current().nextInt(min_x, max_x);
        return x;
    }
}
