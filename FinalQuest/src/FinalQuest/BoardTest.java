package FinalQuest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class BoardTest {

	

	/**
	 * @author SusyBW65
	 *
	 */


		private static final long serialVersionUID = 1L;


		/**
		 * Get robot to press play from the Main Menu
		 */
		@Test 
		public void testdrawMainMenu(){
			
			
		try {
		        Robot robot = new Robot();

		        // Simulate a key press
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);

		} catch (AWTException e) {
		        e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
		}
			
			
			
		}
		
		/**
		 * Check whether there is always a background in game
		 */
		@Test  (expected=Exception.class)
		public void testInitBG(){
			
			ArrayList background = new ArrayList<>();

			
			Background tmp = Stage.background();

			while (tmp != null) {
				background.add(tmp);
				tmp = Stage.background();
				assertFalse(tmp.equals(null));//check tmp is not null
			}
		}
		
		
		

		

	

}
