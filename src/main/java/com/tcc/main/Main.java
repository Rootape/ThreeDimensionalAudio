package com.tcc.main;

import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;

import com.tcc.devices.MixerGetter;
import com.tcc.teste.AudioUtils;

//https://stackoverflow.com/questions/3297749/java-reading-manipulating-and-writing-wav-files

public class Main {
	
	public static void main(String[] args) throws Exception {
		
	    Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
	    
	    for(Info info: mixerInfo) {
	    	if(getFilter(info)) {
		    	System.out.println(String.format("Name: " + info.getName()));
		    	System.out.println(String.format("Version: " + info.getVersion()));
		    	System.out.println(String.format("Vendor: " + info.getVendor()));
		    	System.out.println(String.format("Description: " + info.getDescription()));
		    	System.out.println("\n");
		    	/*
		    	try {
			        Clip clip = AudioSystem.getClip(info);
			        clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\beep.wav")));
			        clip.start();
			    } catch (Throwable t) {
			    }*/
	    	}
	    }
	    
	    //AudioUtils.readWav();

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
