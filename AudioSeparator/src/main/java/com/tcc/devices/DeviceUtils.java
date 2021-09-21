package com.tcc.devices;

import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;

public class DeviceUtils {
	
	public static ArrayList<Info> getDevices() {
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		ArrayList<Info> devAux = new ArrayList<>();
		
		for(Info info: mixerInfo) {
	    	if(getFilter(info)) {
	    		devAux.add(info);
	    	}
		}

		return devAux;
	}

	public static boolean getFilter(Info info) {
		
		if(System.getProperty("os.name").contains("Windows")) {
			return info.getDescription().equals("Direct Audio Device: DirectSound Playback") && 
					!info.getName().equals("Primary Sound Driver") &&
					!info.getName().contains("AG Audio");
		}
		return true;
	}
	
}
