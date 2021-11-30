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
		
		String xmlDevices = "src\\main\\resources\\xmlDev.xml";
		Scanner sca = new Scanner(System.in);
		
		System.out.print("Press ENTER to start");
		String s = sca.nextLine();
		
		for(int i = 1; i < 8; i++) {
			String xmlAudios = "src\\main\\resources\\xmlAudio" + i + ".xml";
			
			//System.out.println("Parsing Audio XML");
			ArrayList<Audio> audios = XMLParsers.readAudiosConfig(xmlAudios);
			//System.out.println("Parsing Devices XML");
			HashMap<String, Device> devices = XMLParsers.readDevicesConfig(xmlDevices);
			
			//System.out.println("Populating Devices with Audios");
			devices = DeviceUtils.populateDevices(devices, audios);
			PlayUtils.play(devices.values());
			
			System.out.print("Press ENTER to continue");
			s = sca.nextLine();
		}
	}
}