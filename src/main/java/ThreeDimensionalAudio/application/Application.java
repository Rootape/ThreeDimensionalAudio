package ThreeDimensionalAudio.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.model.Device;
import ThreeDimensionalAudio.parser.XMLParsers;
import ThreeDimensionalAudio.util.device.DeviceUtils;
import ThreeDimensionalAudio.util.playback.PlayUtils;

public class Application {
	
	public static float srFinal = 0;
	public static float frFinal = 0;
	
	public static void main(String[] args) {
		
		String xmlDevices = "C:\\TCC\\help\\xmls\\xmlDev.xml";
		
		for(int i = 1; i < 8; i++) {
			String xmlAudios = "C:\\TCC\\help\\xmls\\xmlAudio" + i + ".xml";
			
			System.out.println("Parsing Audio XML");
			ArrayList<Audio> audios = XMLParsers.readAudiosConfig(xmlAudios);
			System.out.println("Parsing Devices XML");
			HashMap<String, Device> devices = XMLParsers.readDevicesConfig(xmlDevices);
			
			System.out.println("Populating Devices with Audios");
			devices = DeviceUtils.populateDevices(devices, audios);
			PlayUtils.play(devices.values());
			
			Scanner sca = new Scanner(System.in);
			String s = sca.nextLine();
		}
	}
}