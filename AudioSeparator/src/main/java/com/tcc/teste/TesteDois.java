package com.tcc.teste;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TesteDois {
	
	//https://stackoverflow.com/questions/16810228/how-to-remove-noise-from-wav-file-after-mixed

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		String fn = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\file_example.wav";
		String fd = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\final_test.wav";
		File f = new File(fn);
		
		Path path = Paths.get(fn);
		byte[] data = Files.readAllBytes(path);
		
		int numChannels = Integer.parseInt(arrayToStr(bigEndian(new int[] {data[22], data[23]})), 16);//22 e 23
		int sampleRate = Integer.parseInt(arrayToStr(bigEndian(new int[] {data[24], data[25], data[26], data[27]})), 16);
		int byteRate = Integer.parseInt(arrayToStr(bigEndian(new int[] {data[28], data[29], data[30], data[31]})), 16);
		int blockAlign = Integer.parseInt(arrayToStr(bigEndian(new int[] {data[32], data[33]})), 16);//22 e 23
		int bitsPerSample = Integer.parseInt(arrayToStr(bigEndian(new int[] {data[34], data[35]})), 16);
		
		System.out.println("Channels - " + numChannels);
		System.out.println("Sample Rate - " + sampleRate);
		System.out.println("Byte Rate - " + byteRate);
		System.out.println("Block ALign - " + blockAlign);
		System.out.println("BPS - " + bitsPerSample);
		
		System.out.println("Tamanho data = " + (data.length - 44 - (data.length - 1072992)));
		
		
		byte[] header = new byte[44];
		byte[] buffer = new byte[data.length - 44];
		
		for(int i = 0; i < 44; i++) {
			header[i] = (byte)(data[i]<0?256+(int)data[i]:data[i]);
		}
		
		for(int i = 44; i < data.length; i++) {
			
			double m = 0.9;
			
			if(i+1 < 1072992) {
				//buffer[i-44] = (byte)((data[i]<0?(256+(int)data[i]):data[i])*m);
				String[] sa = byteDiv(Integer.toHexString((data[i]<0?(256+(int)data[i]):data[i]))
						+Integer.toHexString((data[i+1]<0?(256+(int)data[i+1]):data[i+1])), m).split(" ");
				if(sa.length == 2) {
					buffer[i-44] = (byte)Integer.parseInt(sa[0], 16);
					buffer[i-43] = (byte)Integer.parseInt(sa[1], 16);
				}else {
					buffer[i-44] = (byte)Integer.parseInt("00", 16);
					buffer[i-43] = (byte)Integer.parseInt(sa[0], 16);
				}
				i += 1;
			}else {
				buffer[i-44] = data[i];
			}
			
			/*
			 * ideia inicial
			int[] bits = new int[blockAlign / numChannels];
			
			for(int j = 0; j < blockAlign / numChannels; j++) {
				bits[j] = (data[i+j]<0?256+(int)data[i+j]:data[i+j]);
			}
			if(i < 1072992) {
				String[] le = toHexArray(strAtoIntA(byteDiv(arrayToStr(toHexArray(bits)), 2)));
				
				if(le.length != blockAlign / numChannels) {
					StringBuilder sb = new StringBuilder();
					for(int k = 0; k < blockAlign / numChannels - le.length; k++) {
						sb.append("00;");
					}
					for(String s:le) {
						sb.append(s + ";");
					}
					
					le = sb.toString().split(";");
				}
				
				for(int j = 0; j < blockAlign / numChannels; j++) {
					int resInt = Integer.parseInt(le[j], 16);
					buffer[(i-44) + j] = (byte)(resInt<0?256+(int)resInt:resInt);
				}
			}
			else {
				for(int j = 0; j < blockAlign / numChannels; j++) {
					buffer[(i-44) + j] = (byte)(data[i+j]<0?256+(int)data[i+j]:data[i+j]);
				}
			}
			i += (blockAlign / numChannels) - 1;*/
		}
		
		byte[] bytFinal = new byte[header.length + buffer.length];
		for(int i = 0; i < header.length; i++) {
			bytFinal[i] = header[i];
		}
		for(int i = 0; i < buffer.length; i++) {
			bytFinal[i+44] = (byte)(buffer[i]<0?256+(int)buffer[i]:buffer[i]);
			//bytFinal[i+44] = buffer[i];
		}
		
		for(int i = 0; i < 5; i++) {
			for(int j = 12*i; j < 12+12*i; j++) {
				int iAux = (bytFinal[j]<0?256+(int)bytFinal[j]:bytFinal[j]);
				if(arrayContains(new int[] {0, 1, 2, 3, 8, 9, 10, 11, 12, 13, 14, 36, 37, 38, 39}, j)) {
					System.out.print(Character.toString((char)iAux)+" ");
				}else {
					System.out.print(Integer.toHexString(iAux) + " ");
				}
			}
			System.out.println();
		}
		
		System.out.println("");
		System.out.println("LIST:");
		
		int lstSiz = data.length - 1072992;
		int cntrl = 0;
		
		for(int i = 0; i < (lstSiz/12)+1; i++) {
			for(int j = 12*i; j < 12+12*i; j++) {
				int ia = j + 1072992;
				if(ia >= data.length) {
					cntrl = 1;
					break;
				}
				int iAux = (bytFinal[ia]<0?256+(int)bytFinal[ia]:bytFinal[ia]);
				System.out.print(Character.toString((char)iAux)+" ");
			}
			if(cntrl == 1) {
				break;
			}
			System.out.println();
		}
		System.out.println("");
		//System.out.println(arrayEquals(bytFinal, data));
		Files.write(Paths.get(fd), bytFinal);
		System.out.println("Arquivo feito");
		
	}
	
	private static String byteDiv(String byt, double div) {
		int bytInt = Integer.parseInt(byt, 16);
		int resInt = (int) (bytInt / div);
				
		String hexRes = Integer.toHexString(resInt);
		StringBuilder sb = new StringBuilder();
		
		if(!(hexRes.length()%2 == 0)) {
			sb.append("0");
		}

		sb.append(hexRes);
		
		String sem = sb.toString();
		StringBuilder sbd = new StringBuilder();
		
		int count = 0;
		for(char c: sem.toCharArray()) {
			sbd.append(c);
			count += 1;
			if(count == 2) {
				sbd.append(";");
				count = 0;
			}
		}

		//return sbd.toString().replace(";", " ").split(" ");
		return sbd.toString().replace(";", " ");
	}
	
	private static String[] bigEndian(int[] values) {
		Integer[] what = Arrays.stream(values).boxed().toArray( Integer[]::new );
		Arrays.sort(what);
		StringBuilder absB = new StringBuilder();
		
		for(int i = 0; i < values.length; i ++) {
			String sAux = Integer.toHexString(what[i]);
			absB.append((sAux.length()<2?"0"+sAux:sAux));
			absB.append(";");
		}
		
		absB.replace(absB.length()-1, absB.length()-1, "");
		return absB.toString().split(";");
	}
	
	private static String[] littleEndian(int[] values) {
		Integer[] what = Arrays.stream(values).boxed().toArray( Integer[]::new );
		Arrays.sort(what);
		StringBuilder absB = new StringBuilder();
		
		for(int i = values.length-1; i >= 0; i --) {
			String sAux = Integer.toHexString(what[i]);
			absB.append((sAux.length()<2?"0"+sAux:sAux));
			absB.append(" ");
		}
		
		absB.replace(absB.length(), absB.length(), "");
		return absB.toString().split(" ");
	}
	
	static String arrayToStr(String[] sa) {
		StringBuilder sb = new StringBuilder();
		for(String s:sa) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	static int[] strAtoIntA(String[] sa) {
		int[] ia = new int[sa.length];
		
 		for(int i = 0; i < sa.length; i++) {
			ia[i] = Integer.parseInt(sa[i], 16);
		}
 		return ia;
	}
	
	static boolean isSubArray(byte A[], byte B[], int n, int m) {
		int i = 0, j = 0; 
		
		while (i < n && j < m){ 
			if (A[i] == B[j]){ 
				i++; 
				j++; 
				if (j == m) { 
					return true; 
				}
			} 
			else{ 
				i = i - j + 1; 
				j = 0; 
			} 
		} 
		return false; 
	}
	
	public static boolean arrayEquals(byte[] a, byte[] b) {
		if(a.length != b.length) {
			System.out.println("Tamanho diferente");
			return false;
		}
		boolean ba = true;
		for(int i = 0; i < a.length; i++) {
			if(!(a[i] == b[i])) {
				System.out.println(i);
				System.out.println("Dif - " + a[i] + " != " + b[i]);
			}
		}
		return true;
	}
	
	public static boolean arrayContains(int[] ar, int i) {
		for(int j : ar) {
			if(j == i) {
				return true;
			}
		}
		return false;
	}
	
	public static String[] toHexArray(int[] ia) {
		String[] sa = new String[ia.length];
		for(int i = 0; i < ia.length; i++) {
			String s = Integer.toHexString(ia[i]);
			sa[i] = (s.length()<2?"0"+s:s);
		}
		return sa;
	}
	
	public static void save(byte[] fin, String myPath) {
        try {
            DataOutputStream outFile = new DataOutputStream(new FileOutputStream(myPath + "temp"));
            
            for(byte b : fin) {
            	int iAux = (b<0?256+(int)b:b);
            	outFile.writeBytes(String.valueOf(iAux));
            }
            System.out.println("Arquivo criado");
            outFile.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
