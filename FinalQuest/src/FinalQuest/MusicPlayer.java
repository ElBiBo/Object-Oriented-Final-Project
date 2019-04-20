package FinalQuest;

import javax.sound.sampled.*;
import java.io.File;
/**
 * This class handles all the music in the game.
 * Ideally we would like to have a song for each level and boss, but we don't
 * have those kind of resources. Boss songs should be listed as BOSS# while
 * stage songs are MAIN#. VICTORY is the song played at the end of each level.
 * Menu is for the game menus.
 * @author Nicholas Gacharich
 */
public enum MusicPlayer{
    
    BOSS1("src/resources/boss1.wav"),          // boss theme
    BOSS8("src/resources/boss8.wav"),          // lAR boss theme
    MAIN1("src/resources/level1.wav"),         // level 1 theme
    MAIN2("src/resources/level2.wav"),         // level 2 theme
    MAIN3("src/resources/level3.wav"),         // level 3 theme
    MAIN4("src/resources/level4.wav"),         // level 4 theme
    MAIN5("src/resources/level5.wav"),         // level 5 theme
    MAIN6("src/resources/level6.wav"),         // level 6 theme
    MAIN7("src/resources/level7.wav"),         // level 7 theme
    MAIN8("src/resources/FinalTheme.wav"),     // level 8 theme
    LOGO("src/resources/chipnese.wav"),        // our logo jingle
    INTRO("src/resources/TheAdventureBEGINS.wav"), //intro song
    ROCKET("src/resources/rocket.wav"),        // rocket sound
    MENU("src/resources/menu.wav"),            // menu music
    GAMEOVER("src/resources/GAMEOVER.wav"),    // game over music
    ENDING("src/resources/AlexBeroza_-_Strike_The_Root.wav"), // game ending music
    VICTORY("src/resources/victory.wav");      // victory theme
    // Nested class for specifying volume
    public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
    
   public static Volume volume = Volume.LOW;
   
   
   // Each sound effect has its own clip, loaded with its own sound file.
  Clip clip;
  
  AudioInputStream audioInputStream; 
  
   // Constructor to construct each element of the enum with its own sound file.
   MusicPlayer(String soundFileName) {
      try {
           // create AudioInputStream object 
        audioInputStream =  
                AudioSystem.getAudioInputStream(new File(soundFileName).getAbsoluteFile()); 
          
        // create clip reference 
        clip = AudioSystem.getClip(); 
          
        // open audioInputStream to the clip 
        clip.open(audioInputStream); 
      } catch(Exception e){
          e.printStackTrace();
      }
      
   }
   
    /**
     *  Used to ensure a song clip is at the beginning before it starts
     */
    public void newSong()
   {
       clip.setFramePosition(0); // rewind to the beginning
   }
  
    /**
     * This method is used to play any song in the game
     */
    public void play() {
         if (volume != Volume.MUTE) {

         if (clip.isRunning())

            clip.stop();   // Stop the player if it is still running
         
         new Thread(){//multi-tasking stuff
             public void run(){
                 
            clip.loop(Clip.LOOP_CONTINUOUSLY);

     
             }
             
         }.start();
         
      }
    
    }
    
    /**
     * This plays a song once instead of continuously
     */
    public void playOnce() {
         if (volume != Volume.MUTE) {

         if (clip.isRunning())

            clip.stop();   // Stop the player if it is still running
         
         new Thread(){//multi-tasking stuff
             public void run(){
                 
            clip.loop(0);

     
             }
             
         }.start();
         
      }
    
    }
    
    public void stop(){
            clip.stop();//Stop music if dead
        }
}
    
    
    
