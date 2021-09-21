package ThreeDimensionalAudio.util.device;

import java.io.File;
import java.util.ArrayList;
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
		
		for(Audio a : audios) {
			HashMap<String, Double> dists = new HashMap<>();
			for(Device d : devices.values()) {
				dists.put(d.getId(), d.getDistance(a.getPos()));
			}
			Entry<String, Double> min = null;
			for (Entry<String, Double> entry : dists.entrySet()) {
			    if (min == null || min.getValue() > entry.getValue()) {
			        min = entry;
			    }
			}
			String minDev = min.getKey();
			
			String scndMinDev = "";
			
			Entry<String, Double> scndMin = null;
			if(dists.get(minDev) != 0) {
				for (Entry<String, Double> entry : dists.entrySet()) {
				    if ((scndMin == null) || (scndMin.getValue() > entry.getValue() && entry != min)) {
				    	if(entry != min) {
				    		scndMin = entry;
				    	}
				    }
				}
				scndMinDev = scndMin.getKey();
			}
			
			if(scndMin == null) {
				String audioDevId = minDev+a.getId();
				String newPath = "C:\\TCC\\help\\temp\\"+audioDevId+".wav";
				try {
					File wavFile = new File(a.getPath());
						
					WaveFileReader reader = new WaveFileReader();
				    AudioInputStream audioIn = reader.getAudioInputStream(wavFile);
				    AudioFormat srcFormat = audioIn.getFormat();
				    AudioInputStream convertedIn = AudioSystem.getAudioInputStream(srcFormat, audioIn);
					
			        WaveFileWriter writer = new WaveFileWriter();
			        writer.write(convertedIn, Type.WAVE, new File(newPath));
			        
			        devices.get(minDev).addAudio(new Audio(audioDevId, newPath, srcFormat));
		        
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
			}else {
				String audioDevId = minDev+a.getId();
				String newPath = "C:\\TCC\\help\\tempDevices\\"+audioDevId+".wav";
				double scale = MathUtils.getCos(devices.get(minDev), a);
				Audio a1 = AudioUtils.changeVolume(audioDevId, a.getPath(), newPath, (float)scale);
				devices.get(minDev).addAudio(a1);
				
				audioDevId = scndMinDev+a.getId();
				newPath = "C:\\TCC\\help\\tempDevices\\"+audioDevId+".wav";
				Audio a2 = AudioUtils.changeVolume(audioDevId, a.getPath(), newPath, (float)scale);
				devices.get(scndMinDev).addAudio(a2);
			}
		}
		
		return devices;
	}

}
