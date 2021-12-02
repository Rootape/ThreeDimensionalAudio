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
		
		//Caminho do XML de Dispositivos
		String xmlDevices = "src\\main\\resources\\xmlDev.xml";
		Scanner sca = new Scanner(System.in);
		
		System.out.print("Press ENTER to start");
		String s = sca.nextLine();
		
		for(int i = 1; i < 8; i++) {
			//Caminho do XML de Audio a ser lido
			String xmlAudios = "src\\main\\resources\\xmlAudio" + i + ".xml";
			
			//Inicialização da Lista de Áudios com o Parser do XML de Áudio
			ArrayList<Audio> audios = XMLParsers.readAudiosConfig(xmlAudios);
			
			//Inicialização do Mapa de Dispositivos com o Parser do XML de Dispositivos
			HashMap<String, Device> devices = XMLParsers.readDevicesConfig(xmlDevices);
			
			//Divisão do Áudio entre os Dispositivos
			devices = DeviceUtils.populateDevices(devices, audios);
			
			//Reprodução do Áudio
			PlayUtils.play(devices.values());
			
			System.out.print("Press ENTER to continue");
			s = sca.nextLine();
		}
	}
}