import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



/**
 *
 * @author kyle
 * this class will play WAV sounds from a folder link
 * @credit: StackOverFlow user @author WChargin
 */
public class SoundPlayer {
	public SoundPlayer(){
	}
	/*
	 * general play sound class
	 */
	public void playSound(File Sound){
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			
			//Thread.sleep(clip.getMicrosecondLength()/1000);
		}catch(Exception e){
			
		}
	}
	/*
	 * play crow sound
	 */
	public void playCrow(){
		File crow = new File("C:/Users/kyle/CSC162/Math Game/src/sound/crow-1.wav");
		playSound(crow);
	}
	/*
	 * play laser sound
	 */
	public void playLaser(){
		File laser = new File("C:/Users/kyle/CSC162/Math Game/src/sound/laser.wav");
		playSound(laser);
	}
	public void playDing(){
		File ding = new File("C:/Users/kyle/CSC162/Math Game/src/sound/Ding.wav");
		playSound(ding);
	}
	public void playTheme(){
		File theme = new File("C:/Users/kyle/CSC162/Math Game/src/sound/Theme.wav");
		playSound(theme);
	}
}
