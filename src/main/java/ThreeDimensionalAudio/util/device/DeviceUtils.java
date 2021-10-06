package ThreeDimensionalAudio.util.device;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFileFormat.Type;

import com.sun.media.sound.WaveFileReader;
import com.sun.media.sound.WaveFileWriter;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.model.Device;
import ThreeDimensionalAudio.util.audio.AudioUtils;
import ThreeDimensionalAudio.util.math.MathUtils;

@SuppressWarnings("restriction")
public class DeviceUtils {
	
	public static HashMap<String, Device> populateDevices(HashMap<String, Device> devices, ArrayList<Audio> audios){
				
		for(Audio a: audios) {
			ArrayList<Device> closeDevs = getCloseDevices(a, devices);
			
			for(Device d: closeDevs) {
				System.out.println(d.getId());
				String newPath = "C:\\TCC\\help\\tempDevices\\"+d.getId()+a.getId()+".wav";
				int[] user = new int[] {0, 0};
				double cos = MathUtils.getCosNew(a.getPos(), user, d.getPos());
				Audio aAlter = AudioUtils.changeVolume(d.getId()+a.getId(), a.getPath(), newPath, cos);
				devices.get(d.getId()).addAudio(aAlter);
			}
			
		}
		
		return devices;
	}
	
	public static ArrayList<Device> getCloseDevices(Audio a, HashMap<String, Device> devices){
		ArrayList<String> devIds = new ArrayList<>();
		ArrayList<Double> dists = new ArrayList<>();
		
		for(Device d:devices.values()) {
			devIds.add(d.getId());
			dists.add(d.getDistance(a.getPos()));
			//System.out.println("[" + d.getPos()[0] + ", " + d.getPos()[1] + "] - [" + a.getPos()[0] + ", " + a.getPos()[1] + "]" );
			//System.out.println(d.getId() + " - " + d.getDistance(a.getPos()));
		}
		
		ArrayList<Device> closeDevs = new ArrayList<>();
		
		if(Collections.min(dists) == 0.0) {
			int indexOne = dists.indexOf(Collections.min(dists));
			closeDevs.add(devices.get(devIds.get(indexOne)));
		} else {
			int indexOne = dists.indexOf(Collections.min(dists));
			closeDevs.add(devices.get(devIds.get(indexOne)));
			printArrayList(dists);
			dists.set(indexOne, 100.0);
			printArrayList(dists);
			int indexTwo = dists.indexOf(Collections.min(dists));
			closeDevs.add(devices.get(devIds.get(indexTwo)));
			System.out.println("Index1 = " + indexOne + ", " + "Index2 = " + indexTwo);
		}
		
		return closeDevs;
		
	}
	
	public static <T> void printArrayList(ArrayList<T> array) {
		
		System.out.print("[ ");
		
		for(Object o : array) {
			System.out.print(o + " ");
		}
		
		System.out.println("]");
	}
}
