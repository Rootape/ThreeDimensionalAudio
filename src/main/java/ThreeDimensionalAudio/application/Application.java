package ThreeDimensionalAudio.application;

import java.util.ArrayList;
import java.util.HashMap;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.model.Device;
import ThreeDimensionalAudio.parser.XMLParsers;
import ThreeDimensionalAudio.util.device.DeviceUtils;
import ThreeDimensionalAudio.util.playback.PlayUtils;

public class Application {
	
	public static float srFinal = 0;
	public static float frFinal = 0;
	
	public static void main(String[] args) {
		String xmlAudios = "C:\\TCC\\help\\xmlAudio.xml";
		String xmlDevices = "C:\\TCC\\help\\xmls\\xmlDev.xml";
		
		System.out.println("Parsing Audio XML");
		ArrayList<Audio> audios = XMLParsers.readAudiosConfig(xmlAudios);
		System.out.println("Parsing Devices XML");
		HashMap<String, Device> devices = XMLParsers.readDevicesConfig(xmlDevices);
		
		System.out.println("Populating Devices with Audios");
		devices = DeviceUtils.populateDevices(devices, audios);
		PlayUtils.play(devices.values());
	}
}