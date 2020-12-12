package com.tcc.teste;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Teste {

	public static void main(String[] args) throws IOException {
		String fn = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\file_example.wav";
		Path path = Paths.get(fn);
		byte[] data = Files.readAllBytes(path);
		
		int lstSiz = data.length - 1072992;
		int cntrl = 0;
		
		for(int i = 0; i < (lstSiz/12)+1; i++) {
			for(int j = 12*i; j < 12+12*i; j++) {
				int ia = j + 1072992;
				if(ia >= data.length) {
					cntrl = 1;
					break;
				}
				System.out.print(Character.toString((char)data[ia])+" ");
			}
			if(cntrl == 1) {
				break;
			}
			System.out.println();
		}
		
		for(int i = 1072992; i < data.length; i++) {
			int ia = i - 1072992;
			if(arrayContains(new int[] {0, 1, 2, 3, 8, 9, 10, 11}, i)) {
				
			}
		}
		
		//ash(data);
	}
	
	public static boolean arrayContains(int[] ar, int i) {
		for(int j : ar) {
			if(j == i) {
				return true;
			}
		}
		return false;
	}
	
	public static void ash(byte[] data){
		
		for(int i = 0; i < data.length; i++) {
			int j = i;
			if(j+1 < data.length || j+2 < data.length || j+3 < data.length) {
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
			
		}
		
	}
}
