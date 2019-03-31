package FinalQuest;

/**
 * This class makes a continuous scrolling background of stars or whatever else
 * we need
 * @author Marco Tacca
 */
public class Background extends Sprite {

    private String img;
    private int start_x;
    private String sprite_type;
    
    /**
     * Constructor, default background
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     */
    public Background(int x, int y) {
        super(x, y);
        this.sprite_type = "background";
        this.start_x = 0;
        this.img = "src/resources/space_background.png";
        initBackground();
    }
    
    /**
     * Constructor, choose a background
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param img   the image you want to use for the background
     */
    public Background(int x, int y, String img) {
        super(x, y);
        this.sprite_type = "background";
        this.start_x = 0;
        this.img = img;
        initBackground();
    }
    
    /**
     * Constructor, choose a background, adjust placement for details
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param img   the image you want to use for the background
     * @param start_x   how far back you want a detail to show up after it's been passed
     */
    public Background(int x, int y, String img, int start_x) {
        super(x, y);
        this.sprite_type = "background";
        this.img = img;
        this.start_x = start_x;
        initBackground();
    }

    /**
     * Init our background by assigning it an image and getting it's dimensions
    */
    private void initBackground() {

        loadImage(img);
        getImageDimensions();
    }

    /**
     *  Slowly scroll the background images to the left
     */
    public void move() {
        x -= 1;
        if (x < 0  - width) {
            if (start_x == 0)
            {
                x = width-1;
            }
            else
                x = start_x;
        }

        
    }
}