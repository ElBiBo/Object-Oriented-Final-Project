/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalQuest;

import javax.sound.sampled.*;
import java.io.File;
/**
 *
 * @author Gach5
 */
public enum MusicPlayer{
    
    BOSS1("src/resources/boss1.wav"),                       // boss theme
    MAIN1("src/resources/MainBackgroundMusic.wav"),         // main theme
    MAIN2("src/resources/MainBackgroundMusic.wav"),         // main theme
    VICTORY("src/resources/victory.wav");                   // victory theme
            
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
  
    public void play() {
         if (volume != Volume.MUTE) {

         if (clip.isRunning())

            clip.stop();   // Stop the player if it is still running

         clip.setFramePosition(0); // rewind to the beginning
         
         new Thread(){//multi-tasking stuff
             public void run(){
                 
            clip.loop(Clip.LOOP_CONTINUOUSLY);

     
             }
             
         }.start();
         
      }
    
    }
    
    public void stop(){
            clip.stop();//Stop music if dead
        }
}
    
    
    
