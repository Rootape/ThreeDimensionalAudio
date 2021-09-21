package ThreeDimensionalAudio.application;


import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFileFormat.Type;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.util.audio.AudioUtils;

import com.sun.media.sound.WaveFileReader;
import com.sun.media.sound.WaveFileWriter;

public class Testing {
	
	static String audio1 = "C:\\TCC\\help\\src\\audio1.wav";
	static String id = "test1";
	static String newPath = "C:\\TCC\\help\\temp\\"+id+".wav";
	
	static String audio2 = "C:\\TCC\\help\\src\\audio2.wav";
	static String id2 = "test2";
	static String newPath2 = "C:\\TCC\\help\\temp\\"+id+".wav";
	/*
	public static void main(String[] args) {
		final Audio a1 = AudioUtils.normalizeAudio(id, audio1);
		final Audio a2 = AudioUtils.normalizeAudio(id2, audio2);
		
		ArrayList<Audio> testAudios = new ArrayList<Audio>() {{
			add(a1);
			add(a2);
		}};
		
		AudioUtils.normalizeSr(testAudios);
		
		AudioUtils.alterAudio(a1, 0);
		
		
	}*/
	
	public static void main(String[] argv) {
	    try {
	        File wavFile = new File(audio1);
	        File dstFile = new File(newPath);
	        WaveFileReader reader = new WaveFileReader();
	        AudioInputStream audioIn = reader.getAudioInputStream(wavFile);
	        AudioFormat srcFormat = audioIn.getFormat();

	        AudioFormat dstFormat = new AudioFormat(srcFormat.getEncoding(),
	                srcFormat.getSampleRate() / 2,
	                srcFormat.getSampleSizeInBits(),
	                srcFormat.getChannels(),
	                srcFormat.getFrameSize(),
	                srcFormat.getFrameRate() / 2,
	                srcFormat.isBigEndian());

	        AudioInputStream convertedIn = AudioSystem.getAudioInputStream(dstFormat, audioIn);

	        WaveFileWriter writer = new WaveFileWriter();
	        writer.write(convertedIn, Type.WAVE, dstFile);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
