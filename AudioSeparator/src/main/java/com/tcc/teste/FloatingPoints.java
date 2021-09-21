package com.tcc.teste;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import com.tcc.audios.WavUtils;

public class FloatingPoints {

	public static void main(String[] args) {
		
		String f2 = "C:\\TCC\\temp\\0.wav";
		String f3 = "C:\\TCC\\temp\\testeVolume.wav";
		
		int[] audioInts = WavUtils.loadWav(f2).getData();
		
		byte[] audioBytes = new byte[audioInts.length];
		
		for(int i = 0; i < audioInts.length; i++) {
			audioBytes[i] = (byte)(audioInts[i]<0?256+audioInts[i]:audioInts[i]);
		}
		
		ShortBuffer sbuf =
			    ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
		short[] audioShorts = new short[sbuf.capacity()];
		sbuf.get(audioShorts);
		
		float[] audioFloats = new float[audioShorts.length];
		for (int i = 0; i < audioShorts.length; i++) {
		    audioFloats[i] = ((float)audioShorts[i])/0x8000;
		}
	}
	
}
