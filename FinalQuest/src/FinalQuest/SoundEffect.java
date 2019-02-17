package FinalQuest;

import java.io.*;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
   
/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can use the static variable SoundEffect.volume to mute the sound.
 */
public enum SoundEffect {
   LASER("src/resources/Laser_Shoot3.wav"),         // laser blast
   EXPLODE("src/resources/Explosion4.wav"),         // Explosion
   POWERUP("src/resources/Powerup3.wav");           // Powerup
   
   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static Volume volume = Volume.LOW;
   
   // Each sound effect has its own clip, loaded with its own sound file.
  Clip clip;
  
  AudioInputStream audioInputStream; 
  
    
   
   // Constructor to construct each element of the enum with its own sound file.
   SoundEffect(String soundFileName) {
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
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
       
       
       if (volume != Volume.MUTE) {

         if (clip.isRunning())

            clip.stop();   // Stop the player if it is still running

         clip.setFramePosition(0); // rewind to the beginning
         
         new Thread(){//multi-tasking stuff
             public void run(){
         clip.start();     // Start playing
             }
             
         }.start();
         
      }
         
    }
   
   
}
