package com.tcc.teste;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioUtils {
	//https://www.devmedia.com.br/utilizando-componentes-com-drag-and-drop-no-primefaces/33884
	//http://www.ievs.ch/projects/var/upload/Documentation%20Microsoft%20Wave%20File%20Format.pdf
	//https://betterexplained.com/articles/understanding-big-and-little-endian-byte-order/
	//https://sites.google.com/site/musicgapi/technical-documents/wav-file-format
	
	private static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	private static ArrayList<String> alph = new ArrayList<String>() {
		{
			for(int i = 65; i < 91; i++) {
				add(Integer.toHexString(i));
				add(Integer.toHexString(i + 32));
			}
		}
	};
	
	public static void main(String[] args) {
		
		
		String somePathName = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\beep.wav";
		
		int totalFramesRead = 0;
		File fileIn = new File(somePathName);
		
		try {
		  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
		  int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
		  if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
			  bytesPerFrame = 1;
		  } 
		  
		  int numBytes = 1024 * bytesPerFrame; 
		  byte[] audioBytes = new byte[numBytes];
		  try {
		    int numBytesRead = 0;
		    int numFramesRead = 0;
		    
		    while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
		      
		      numFramesRead = numBytesRead / bytesPerFrame;
		      totalFramesRead += numFramesRead;
		      // Here, do something useful with the audio data that's 
		      // now in the audioBytes array...
		    }
		    for(int i=0; i<audioBytes.length; i++) {
		    	System.out.println(audioBytes[i]);
		    }
		  } catch (Exception ex) { 
		    System.out.println("Fudeu 1");
		  }
		} catch (Exception e) {
			System.out.println("Fudeu 2");
		}
		
	}

	public static void readWav() throws IOException {
		
		String f1 = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\videoplayback.wav";
		String f2 = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\beep.wav";
		String f3 = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\auxTeste.wav";
				
		Path path = Paths.get(f1);
		byte[] data = Files.readAllBytes(path);
		/*
		for(int i = 78; i < 12000; i++) {
			if(data[i] != 0) {
				System.out.println(i);
				break;
			}
		}
		System.out.println();*/
		
		Path path2 = Paths.get(f2);
		byte[] data2 = Files.readAllBytes(path2);
		/*
		System.out.println("File 1:");
		System.out.println("Channels - " + data[22]);
		System.out.println("Data chunk size - " + (data.length - 8));
		System.out.println("BPS - " + data[34]);
		System.out.println("NumSamples - " + (44 - data2.length)/data[22]);
		*/
		int channels = data[22];
		int bps = data[34];
		int numSamples = (data2.length - 44)/data[22];
		int subChunk2Size = numSamples * channels * bps/8;
		int chunkSize = 36 + subChunk2Size;
		
		int[] siiz = {data[4], data[5], data[6], data[7]};
		/*
		for(String s:bigEndian(siiz)){
			System.out.print(s + " ");
		}
		
		System.out.println(chunkSize);
		System.out.println(Integer.toHexString(chunkSize));
		int[] hc = hexChunk(chunkSize);
		
		byte[] dataAux = new byte[data.length];
		System.out.println(data.length);
		for(int i = 0; i < data.length; i++) {
			if(i < 78) {
				//System.out.println(i + " - " + Integer.toHexString(data[i]));
				if(i > 3 && i <8) {
					Integer in = hc[i-4];
					dataAux[i] = in.byteValue();
				}
				else if(i > 35 && i < 40) {
					if(i == 36) {
						Integer in = 64;
						dataAux[i] = in.byteValue();
					}
					if(i == 37) {
						Integer in = 61;
						dataAux[i] = in.byteValue();
					}
					if(i == 38) {
						Integer in = 74;
						dataAux[i] = in.byteValue();
					}
					if(i == 39) {
						Integer in = 61;
						dataAux[i] = in.byteValue();
					}
				}
				else {
					dataAux[i] = data[i];
				}
			}
			else {
				if(i < 28449467) {
					int aux = data[i] / 2;
					dataAux[i] = Integer.valueOf(aux).byteValue();
				}
				else {
					int aux = data[i];
					dataAux[i] = Integer.valueOf(aux).byteValue();
				}
			}
		}
		
		System.out.println("Escrevendo arquivo novo:");
		Files.write(Paths.get(f3), dataAux);
		System.out.println("Arquivo feito");
		System.out.println();
		
		for(int i = 0; i < 5; i++) {
			for(int j = 12*i; j < 12+12*i; j++) {
				System.out.print(Integer.toHexString(data[j]) + " ");
			}
			System.out.println();
		}
		System.out.println();
		/*
		for(int i = 36; i < 70; i++) {
			System.out.print(Integer.toHexString(data[i]));
			System.out.print((char)Integer.parseInt(Integer.toHexString(data[i]), 16) + " ");
		}
		System.out.println();
		
		
		System.out.println("Size = " + data.length);
		
		
		for(int i = 0; i < data.length; i++) {
			if(Integer.toHexString((int)data[i]).equals("66") && 
					   Integer.toHexString((int)data[i + 1]).equals("6d") &&
					   Integer.toHexString((int)data[i + 2]).equals("74")) {
				System.out.println("Fmt starts at: " + i);
				int aux = i + 2;
				System.out.println("Fmt ends at: " + aux);
			}
			if(Integer.toHexString((int)data[i]).equals("4c") && 
					   Integer.toHexString((int)data[i + 1]).equals("49") &&
					   Integer.toHexString((int)data[i + 2]).equals("53") &&
					   Integer.toHexString((int)data[i + 3]).equals("54")) {
				System.out.println("List starts at: " + i);
				int aux = i + 3;
				System.out.println("List ends at: " + aux);
			}
			if(Integer.toHexString((int)data[i]).equals("64") && 
				   Integer.toHexString((int)data[i + 1]).equals("61") &&
				   Integer.toHexString((int)data[i + 2]).equals("74") &&
				   Integer.toHexString((int)data[i + 3]).equals("61")) {
				System.out.println("Data starts at: " + i);
				int aux = i + 3;
				System.out.println("Data ends at: " + aux);
			}
			if(Integer.toHexString((int)data[i]).equals("66") && 
					   Integer.toHexString((int)data[i + 1]).equals("61") &&
					   Integer.toHexString((int)data[i + 2]).equals("63") &&
					   Integer.toHexString((int)data[i + 3]).equals("74")) {
				System.out.println("Fact starts at:");
				System.out.println(i);
			}
			if(Integer.toHexString((int)data[i]).equals("63") && 
					   Integer.toHexString((int)data[i + 1]).equals("75") &&
					   Integer.toHexString((int)data[i + 2]).equals("65")) {
				System.out.println("Cue starts at: " + i);
				int aux = i + 2;
				System.out.println("Cue ends at: " + aux);
			}
			if(Integer.toHexString((int)data[i]).equals("70") && 
					   Integer.toHexString((int)data[i + 1]).equals("6c") &&
					   Integer.toHexString((int)data[i + 2]).equals("73") &&
					   Integer.toHexString((int)data[i + 3]).equals("74")) {
				System.out.println("Plst starts at:");
				System.out.println(i);
			}
			if(Integer.toHexString((int)data[i]).equals("6c") && 
					   Integer.toHexString((int)data[i + 1]).equals("61") &&
					   Integer.toHexString((int)data[i + 2]).equals("62") &&
					   Integer.toHexString((int)data[i + 3]).equals("6c")) {
				System.out.println("Ltxt starts at:");
				System.out.println(i);
			}
			if(Integer.toHexString((int)data[i]).equals("6e") && 
					   Integer.toHexString((int)data[i + 1]).equals("6f") &&
					   Integer.toHexString((int)data[i + 2]).equals("74") &&
					   Integer.toHexString((int)data[i + 3]).equals("65")) {
				System.out.println("Note starts at:");
				System.out.println(i);
			}
			if(Integer.toHexString((int)data[i]).equals("73") && 
					   Integer.toHexString((int)data[i + 1]).equals("6d") &&
					   Integer.toHexString((int)data[i + 2]).equals("70") &&
					   Integer.toHexString((int)data[i + 3]).equals("6c")) {
				System.out.println("Smpl starts at:");
				System.out.println(i);
			}
			if(Integer.toHexString((int)data[i]).equals("69") && 
					   Integer.toHexString((int)data[i + 1]).equals("6e") &&
					   Integer.toHexString((int)data[i + 2]).equals("73") &&
					   Integer.toHexString((int)data[i + 3]).equals("74")) {
				System.out.println("Inst starts at:");
				System.out.println(i);
			}
			
		}
		
		System.out.println(data.length);
		*/
		int[] hc = hexChunk(chunkSize);
		int nSize = data.length - (69 - 36) - (data.length - 28449467);
		byte[] dataAux = new byte[nSize];
		int count = 0;
		
		for(int i = 0; i < data.length; i++) {
			//System.out.println(count);
			if(i > 3 && i <8) {
				Integer in = hc[i-4];
				dataAux[count] = in.byteValue();
				count += 1;
			}
			else if((i >= 36 && i <=69) || (i >=28449467)) {
				
			}
			else {
				if(i == 9224) {
					System.out.println(count);
					System.out.println(data[i]);
				}
				dataAux[count] = data[i];
				count += 1;
			}
				
			
			if(i < 28449467) {
				int aux = data[i] / 2;
				dataAux[i] = Integer.valueOf(aux).byteValue();
			}
			else {
				int aux = data[i];
				dataAux[i] = Integer.valueOf(aux).byteValue();
			}
		}
		
		System.out.println("Escrevendo arquivo novo:");
		Files.write(Paths.get(f3), dataAux);
		System.out.println("Arquivo feito");
		System.out.println();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 12*i; j < 12+12*i; j++) {
				System.out.print(Integer.toHexString(data[j]) + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		/*
		for(int i = 0; i < 8; i++) {
			for(int j = 12*i; j < 12+12*i; j++) {
				System.out.print(Integer.toHexString(dataAux[j]) + " ");
			}
			System.out.println();
		}
		System.out.println();*/
		/*
		File db = new File("C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\auxTeste.txt");
		BufferedWriter bw = new BufferedWriter( new FileWriter( db ) );
		
		ArrayList<String> manPls = new ArrayList<>();
		boolean boll = false;
		
		for(int i = 0; i < data.length; i++) {
			if(Integer.toHexString(data[i]).length() == 2) {
				
				if(alph.contains(Integer.toHexString(data[i])) && boll == false) {
					boll = true;
					manPls.add(Character.toString((char)Integer.parseInt(Integer.toHexString(data[i]), 16)));
				}
				else if(alph.contains(Integer.toHexString(data[i])) && boll == true) {
					manPls.add(Character.toString((char)Integer.parseInt(Integer.toHexString(data[i]), 16)));
				}
				else if(!alph.contains(Integer.toHexString(data[i])) && boll == true){
					if(manPls.size() > 1) {
						bw.write(manPls.toString());
						bw.flush();
						bw.newLine();
					}
					manPls = new ArrayList<>();
					boll = false;
					/*
					if(i > 40000) {
						break;
					}
				}
			}
		}
		//System.out.println(data.length);
		bw.close();	
		*/
	}
	
	private static String[] bigEndian(int[] values) {
		
		Integer[] what = Arrays.stream(values).boxed().toArray( Integer[]::new );
		Arrays.sort(what);
		String[] abs = new String[values.length];
		
		for(int i = 0; i < values.length; i ++) {
			abs[i] = Integer.toHexString(what[i]);
		}
		
		return abs;
		
	}
	
	private static int[] hexChunk(int chunk) {
		String hex = Integer.toHexString(chunk);
		String[] hexAux = splitStringEvery(hex, 2);
		
		ArrayList<String> jsk = new ArrayList<>();
		
		for(String s : hexAux) {
			jsk.add(s);
		}
		if(hexAux.length < 4) {
			for(int i = 0; i < (4-hexAux.length); i++) {
				jsk.add("00");
			}
		}
		
		int[] hexInt = new int[4];
		
		for(int i = 0; i < hexAux.length; i++) {
			hexInt[i] = Integer.parseInt(jsk.get(i), 16);
		}
		Arrays.sort(hexInt);
		hexInt = reverseOrder(hexInt);
		
		String fin = "";
		for(int i:hexInt) {
			if(i == 0) {
				fin = fin + "00 ";
			}else {
				fin = fin + Integer.toHexString(i).toUpperCase() + " ";
			}
		}
		return hexInt;
		
	}
	
	public static int[] reverseOrder(int[] array) {
		int[] aux = new int[array.length];
		
		for(int i = 0; i < array.length; i++) {
			aux[i] = array[array.length - i -1];
		}
		return aux;
	}
	
	public static String[] splitStringEvery(String s, int interval) {
	    int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
	    String[] result = new String[arrayLength];

	    int j = 0;
	    int lastIndex = result.length - 1;
	    for (int i = 0; i < lastIndex; i++) {
	        result[i] = s.substring(j, j + interval);
	        j += interval;
	    } //Add the last bit
	    result[lastIndex] = s.substring(j);

	    return result;
	}
}
