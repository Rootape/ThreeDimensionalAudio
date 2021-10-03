package ThreeDimensionalAudio.util.playback;

import java.io.File;
import java.util.Collection;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.model.Device;

public class PlayUtils {
	
	public static void play(Collection<Device> devices) {
		
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

		for(Device d: devices) {
		    for(Info info: mixerInfo) {
		    	if(getFilter(info)) {
		    		if(info.getName().equals(d.getName())) {
		    			for(Audio a: d.getAudios()) {
			    			try {
						        Clip clip = AudioSystem.getClip(info);
						        System.out.println("Tocando audio!!" + info.getName());
						        playSound(a.getPath(), clip);
						    } catch (Throwable t) {
						    	System.out.println("Catchou");
						    }
		    			}
		    		}
		    	}
		    }
		}	
	}
	
	public static synchronized void playSound(final String url, final Clip clip) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {
		        clip.open(AudioSystem.getAudioInputStream(new File(url)));
		        clip.start();
		        Thread.sleep( 10000 );
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
	
	
	public static boolean getFilter(Info info) {

		if(System.getProperty("os.name").contains("Windows")) {
			return info.getDescription().equals("Direct Audio Device: DirectSound Playback") && 
					!info.getName().equals("Primary Sound Driver") &&
					!info.getName().contains("AG Audio") &&
					!info.getName().contains("Driver");
		}
		return true;
	}

}
