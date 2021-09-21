package ThreeDimensionalAudio.application;

import java.util.ArrayList;
import java.util.HashMap;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.model.Device;
import ThreeDimensionalAudio.parser.XMLParsers;
import ThreeDimensionalAudio.util.audio.AudioUtils;
import ThreeDimensionalAudio.util.device.DeviceUtils;

public class Application {
	
	public static float srFinal = 0;
	public static float frFinal = 0;
	
	public static void main(String[] args) {
		String xmlAudios = "C:\\TCC\\help\\xmlAudio.xml";
		String xmlDevices = "C:\\TCC\\help\\xmlDev.xml";
		
		System.out.println("Parsing Audio XML");
		ArrayList<Audio> audios = XMLParsers.readAudiosConfig(xmlAudios);
		System.out.println("Parsing Devices XML");
		HashMap<String, Device> devices = XMLParsers.readDevicesConfig(xmlDevices);
		
		System.out.println("Normalizing Audio files");
		audios = AudioUtils.normalizeSrFr(audios);
		System.out.println("Populating Devices with Audios");
		devices = DeviceUtils.populateDevices(devices, audios);
		System.out.println("Adding up all Audios in each Devices");
		AudioUtils.sumAudios(devices);
		//play
	}
}