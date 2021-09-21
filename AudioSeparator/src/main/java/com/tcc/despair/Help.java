package com.tcc.despair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFileFormat.Type;

import com.sun.media.sound.WaveFileReader;
import com.sun.media.sound.WaveFileWriter;

public class Help {
	
	public static void main(String[] args) {
		
		String fz = "C:\\TCC\\temp\\0.wav";
		String fi = "C:\\TCC\\temp\\1.wav";
		String fd = "C:\\TCC\\temp\\testeVolume.wav";
		
		Path pathz = Paths.get(fz);
		Path pathi = Paths.get(fi);
		
		byte[] dataz;
		byte[] datai;
		try {
			dataz = Files.readAllBytes(pathz);
			datai = Files.readAllBytes(pathi);
			
			int size = dataz.length;
			
			byte[] dest = new byte[size];
			
			for(int i = 0; i < 44; i++) {
				dest[i] = dataz[i];
			}
			
			for(int i = 44; i < datai.length; i++) {
				dest[i] = (byte)((dataz[i]<0?(256+(int)dataz[i]):dataz[i]) + (datai[i]<0?(256+(int)datai[i]):datai[i]));
			}
			
			try (FileOutputStream stream = new FileOutputStream(fd)) {
			    stream.write(dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public static void arg() {
		try {
	        File wavFile = new File(path);
	        File dstFile = new File(path);
	        
	        WaveFileReader reader = new WaveFileReader();
	        AudioInputStream audioIn = reader.getAudioInputStream(wavFile);
	        AudioFormat srcFormat = audioIn.getFormat();
	        
	        AudioFormat dstFormat = new AudioFormat(srcFormat.getEncoding(),
	        		sampleRate,
	                srcFormat.getSampleSizeInBits(),
	                srcFormat.getChannels(),
	                srcFormat.getFrameSize(),
	                srcFormat.getFrameRate(),
	                srcFormat.isBigEndian());

	        AudioInputStream convertedIn = AudioSystem.getAudioInputStream(dstFormat, audioIn);

	        WaveFileWriter writer = new WaveFileWriter();
	        writer.write(convertedIn, Type.WAVE, dstFile);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
