package com.tcc.devices;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;

public class MixerGetter implements Runnable{
	Thread t;
	public MixerGetter(){
		t = new Thread(this, "Getter for Mixer");
		System.out.println("Starting Mixer Getter");
		t.start();
	}

	public void run() {
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
	    
	    for(Info info: mixerInfo) {
	    	if(info.getDescription().equals("Direct Audio Device: DirectSound Playback") && 
					!info.getName().equals("Primary Sound Driver") &&
					!info.getName().contains("AG Audio")) {
		    	System.out.println(String.format("Name: " + info.getName()));
		    	System.out.println(String.format("Version: " + info.getVersion()));
		    	System.out.println(String.format("Vendor: " + info.getVendor()));
		    	System.out.println(String.format("Description: " + info.getDescription()));	
		    	System.out.println("\n");
	    	}
	    }
	    System.out.println("=============================");
	    System.out.println("\n");
	    System.out.println("=============================");	
	}
}

