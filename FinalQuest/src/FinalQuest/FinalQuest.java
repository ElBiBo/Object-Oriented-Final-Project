package FinalQuest;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * The main driver for our program
 * 
 * @author Marco Tacca and Nicholas Gacharich with help from http://zetcode.com/tutorials/javagamestutorial/
 */
public class FinalQuest extends JFrame {

    /**
     * Constructor
     */
    public FinalQuest() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board()); // creates our window
        
        setResizable(false); // can't resize the window
        pack(); // set up our window
        
        setTitle("Final Quest: Journey for a Passing Grade"); //window title
        setLocationRelativeTo(null); //center's the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close program on clicking x
    }

    /**
     * Main function
     * @param args needs no parameters
     */
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            FinalQuest ex = new FinalQuest();
            ex.setVisible(true);
        });
    }
}