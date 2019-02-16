/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalQuest;

/**
 * This class makes a continuous scrolling background of stars
 * @author Marco Tacca
 */
public class Background extends Sprite {

    
    /**
     * Constructor
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     */
    public Background(int x, int y) {
        super(x, y);

        initBackground();
    }

    /**
     * Init our alien by assigning it an image and getting it's dimensions
    */
    private void initBackground() {

        loadImage("src/resources/space_background.png");
        getImageDimensions();
    }

    /**
     *  Alien's AI
     * currently moves it to the left until it reaches the end of the screen
     * then pops it back to the right side of the screen
     * We will program some more interesting AI here
     */
    public void move() {

        if (x < 0  - width) {
            x = width;
        }

        x -= 1;
    }
}