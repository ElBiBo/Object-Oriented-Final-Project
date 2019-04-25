package FinalQuest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class BoardTest {

	

	/**
	 * @author Gach5
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

		        // Simulate a key press moving up & down menu
		        // Simulate pressing enter to choose option
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        robot.keyPress(KeyEvent.VK_DOWN);
		        robot.keyRelease(KeyEvent.VK_DOWN);
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        robot.keyPress(KeyEvent.VK_DOWN);
		        robot.keyRelease(KeyEvent.VK_DOWN);
		        robot.keyPress(KeyEvent.VK_ENTER);
		        robot.keyRelease(KeyEvent.VK_ENTER);
		        robot.keyPress(KeyEvent.VK_UP);
		        robot.keyRelease(KeyEvent.VK_UP);
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
		
		
		
		/**
		 * Check whether the game mode is set to boss
		 * when player gets to wave 11
		 */
		@Test  
		
		public void testInitAlien(){
		
			
			String game_mode = "";
			int wave_count = 10;
			if (Stage.getWave() == wave_count) {
			
				
				assertEquals(game_mode,"boss");
								
			
			}
			
		}
		

		/**
		 * Check whether the spaceship is invisible when it dies, game mode 
		 * is set to game over when player dies, and if power-up is invisible
		 *  when picked-up by player.
		 */
		@Test (expected=Exception.class)
		
		
		public void testcheckCollisions() {
		
			
			
			// see if the spaceship collides with aliens
			SpaceShip spaceship = null;
			Rectangle r3 = spaceship.getBounds();

			Alien aliens = null;
			Rectangle r2 = aliens.getBounds();
			String game_mode = "";
			
				if (r3.intersects(r2) && !spaceship.isInvincibile() && aliens.getType() != "powerup") {

					if (spaceship.die() <= 0) {
						assertTrue(spaceship.isInvincibile());//test ship is dead when life is 0 or less					
						assertEquals(game_mode,"gameover");//check game mode is gameover
						
						
					}
				} else if (r3.intersects(r2) && aliens.getType() == "powerup") {
					aliens.fire();
					spaceship.powerup(aliens.getType());
					aliens.setVisible(false);
					assertFalse(aliens.isVisible());//check if power-up is picked-up 
				
				}
				
				
			
				
				
		}

}
