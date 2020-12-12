package com.tcc.audios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import com.tcc.model.WavFile;
import com.tcc.utils.Util;

public class WavUtils {
	
	public static WavFile loadWav(String fp) {
		
		Path path = Paths.get(fp);
		
		byte[] data = new byte[0];
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int chunkSize = Integer.parseInt(Util.bigEndian(new int[] {data[4], data[5], data[6], data[7]}), 16);
		int subChunk1Size = Integer.parseInt(Util.bigEndian(new int[] {data[16], data[17], data[18], data[19]}), 16);
		int audioFormat = Integer.parseInt(Util.bigEndian(new int[] {data[20], data[21]}), 16);
		int numChannels = Integer.parseInt(Util.bigEndian(new int[] {data[22], data[23]}), 16);
		int sampleRate = Integer.parseInt(Util.bigEndian(new int[] {data[24], data[25], data[26], data[27]}), 16);
		int byteRate = Integer.parseInt(Util.bigEndian(new int[] {data[28], data[29], data[30], data[31]}), 16);
		int blockAlign = Integer.parseInt(Util.bigEndian(new int[] {data[32], data[33]}), 16);
		int bitsPerSample = Integer.parseInt(Util.bigEndian(new int[] {data[34], data[35]}), 16);
		int subChunk2Size = Integer.parseInt(Util.bigEndian(new int[] {data[40], data[41], data[42], data[43]}), 16);
		int bytesPerSample = bitsPerSample/8;
		
		LinkedHashMap<String, int[]> map = Util.getChunks(data);
		
		int dataBeg = 44;
		int dataEnd = data.length;
		
		//System.out.println("Data ia de " + dataBeg + " ate " + dataEnd);
		
		if(map.get("data")[0] != 44-8) {
			dataBeg = map.get("data")[1] + 5;
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
		
		int dataSize = dataEnd - dataBeg;
		
		//System.out.println("Data vai de " + dataBeg + " ate " + dataEnd);
		
		//System.out.println("Data Size = " + (dataSize/2));
		
		String[] hexBuffer = new String[dataSize];
		
		for(int i = dataBeg; i < dataEnd; i++) {
			String sByt = Integer.toHexString(data[i]);
			if(sByt.length() > 2) {
				sByt = sByt.substring(sByt.length()-2);
			}
			hexBuffer[i-dataBeg] = sByt;
		}
		
		for(int i = 0; i < hexBuffer.length; i++) {
			if(i%12 == 0 && i != 0) {
				break;
			}
		}
		
		int[] buffer = new int[dataSize / (bytesPerSample)];
		
		int count = 0;
		
		for(int i = 0; i < hexBuffer.length; i++) {
			int[] inByt = new int[bytesPerSample];
			for(int j = 0; j < bytesPerSample; j++) {
				inByt[j] = Integer.parseInt(hexBuffer[i + j], 16);
			}
			String hex = Util.littleEndian(inByt);
			buffer[count] = Integer.valueOf(hex, 16).shortValue();
			count++;
			i += bytesPerSample - 1;
		}
		
		byte[] header = new byte[44];
				
		return new WavFile("Ex", chunkSize, subChunk1Size, audioFormat, numChannels, sampleRate,
		 byteRate, blockAlign, bitsPerSample, subChunk2Size, buffer);
		
	}

}
