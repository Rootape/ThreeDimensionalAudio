package com.tcc.teste;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TesteStackReborn {
	
	public static final int BUFFER_SIZE = 1024;
	public static final int WAV_HEADER_SIZE = 44;
	
	public static void main(String[] args) {
		
		String fz = "C:\\TCC\\temp\\0.wav";
		String fi = "C:\\TCC\\temp\\1.wav";
		String fd = "C:\\TCC\\temp\\testeVolume.wav";
		
		File f0 = new File(fz);
		File f1 = new File(fi);
		File f2 = new File(fd);
		
		sumFiles(f0, f1, f2, 1);
	}

	public static void sumFiles(File source1, File source2, File destination, float scale) {
		
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    AudioInputStream ais;
		    ais = AudioSystem.getAudioInputStream(source1);
		    int read;
		    byte[] buffer = new byte[(int)ais.getFrameLength()];
		    while ((read = ais.read(buffer)) != -1) {
		        baos.write(buffer, 0, read);
		    }
		    baos.flush();
		    byte[] bufferA = baos.toByteArray();

		    baos = new ByteArrayOutputStream();
		    ais = AudioSystem.getAudioInputStream(source2);
		    buffer = new byte[(int)ais.getFrameLength()];
		    while ((read = ais.read(buffer)) != -1) {
		        baos.write(buffer, 0, read);
		    }
		    baos.flush();
		    byte[] bufferB = baos.toByteArray();
			
		    byte[] array = new byte[bufferA.length];

		    for (int i=0; i<bufferA.length; i+=2) {
		    	if(i >= bufferA.length || i >= bufferB.length) {
		    		break;
		    	}
		        short buf1A = bufferA[i+1];
		        short buf2A = bufferA[i];
		        buf1A = (short) ((buf1A & 0xff) << 8);
		        buf2A = (short) (buf2A & 0xff);

		        short buf1B = bufferB[i+1];
		        short buf2B = bufferB[i];
		        buf1B = (short) ((buf1B & 0xff) << 8);
		        buf2B = (short) (buf2B & 0xff);

		        short buf1C = (short) (buf1A + buf1B);
		        short buf2C = (short) (buf2A + buf2B);

		        short res = (short) (buf1C + buf2C);

		        array[i] = (byte) res;
		        array[i+1] = (byte) (res >> 8);
		    }
		    
		    RandomAccessFile fileIn = new RandomAccessFile(source1, "r");
		    RandomAccessFile fileOut = new RandomAccessFile(destination, "rw");
		    
		    byte[] header = new byte[WAV_HEADER_SIZE];
		    int numBytes = fileIn.read(header); 
	        fileOut.write(header, 0, numBytes);
	        fileOut.write(array, 0, 44);
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
