package com.tcc.teste;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.tcc.utils.Util;

public class TesteTres {
	
	public static byte[] getArr(String[] args) throws UnsupportedAudioFileException, IOException {
		String fn = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\file_example.wav";
		String fd = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\final_test.wav";
		
		Path path = Paths.get(fn);
		byte[] data = Files.readAllBytes(path);
		
		int numChannels = Integer.parseInt(Util.bigEndian(new int[] {data[22], data[23]}), 16);//22 e 23
		int sampleRate = Integer.parseInt(Util.bigEndian(new int[] {data[24], data[25], data[26], data[27]}), 16);
		int byteRate = Integer.parseInt(Util.bigEndian(new int[] {data[28], data[29], data[30], data[31]}), 16);
		int blockAlign = Integer.parseInt(Util.bigEndian(new int[] {data[32], data[33]}), 16);//22 e 23
		int bitsPerSample = Integer.parseInt(Util.bigEndian(new int[] {data[34], data[35]}), 16);
		
		byte[] header = new byte[44];
		
		LinkedHashMap<String, int[]> map = Util.getChunks(data);
		
		int dataBeg = 44;
		int dataEnd = data.length;
				
		if(map.get("data")[0] != 44) {
			dataBeg = map.get("data")[0];
		}
		
		ArrayList<Integer> intAux = new ArrayList<Integer>();
		
		if(map.size() != 1) {
			for(String s : map.keySet()) {
				if(!s.equals("data")) {
					intAux.add(map.get(s)[0]);
				}
			}
			boolean dataF = true;
			while(dataF) {
				int aux = Collections.min(intAux);
				if(aux > dataBeg) {
					dataEnd = aux;
					dataF = false;
				}
				else {
					intAux.remove(intAux.indexOf(aux));
				}
					
			}
		}
				
		byte[] buffer = new byte[dataEnd - dataBeg];
		
		for(int i = 0; i < 44; i++) {
			header[i] = (byte)(data[i]<0?256+(int)data[i]:data[i]);
		}
		
		for(int i = dataBeg; i < dataEnd; i++) {
			
			String fb = Integer.toHexString((data[i+1]<0?(256+(int)data[i+1]):data[i+1]));
			String sb = Integer.toHexString((data[i]<0?(256+(int)data[i]):data[i]));
			
			if(fb.length() != 2) {
				fb = "0" + fb;
			}
			if(sb.length() != 2) {
				sb = "0" + sb;
			}
			
			int[] le = Util.hexInt(intervalConv(fb+sb, 1));
			
			buffer[i - dataBeg] = (byte) (le[1]<0?(256+(int)le[1]):le[1]);
			buffer[i - dataBeg + 1] = (byte) (le[0]<0?(256+(int)le[0]):le[0]);
			
			i += numChannels - 1;
		}
		
		byte[] bytFinal = new byte[header.length + buffer.length];
		for(int i = 0; i < header.length; i++) {
			bytFinal[i] = header[i];
		}
		for(int i = 0; i < buffer.length; i++) {
			bytFinal[i+44] = (byte)(buffer[i]<0?256+(int)buffer[i]:buffer[i]);
		}
		
		Files.write(Paths.get(fd), bytFinal);
		//System.out.println("Arquivo feito");
		
		return bytFinal;
	}
	
	public static String intervalConv(String hex, double div) {
		
		double iHex = Integer.valueOf(hex, 16).shortValue();
		double aarg = iHex / div;
		
		String resStr = Integer.toHexString((int)aarg);
		
		if(resStr.length() > 4) {
			resStr = resStr.substring(4);
		}
		
		StringBuilder sb = new StringBuilder();
		
		if(resStr.length() < 4) {
			for(int i = 0; i < 4 - resStr.length(); i++) {
				sb.append("0");
			}
			sb.append(resStr);
			resStr = sb.toString();
		}
		
		return resStr;
	}

}
