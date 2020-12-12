package com.tcc.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.tcc.audios.WavUtils;
import com.tcc.model.WavFile;
import com.tcc.teste.TesteTres;

public class Principal {
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		String[] aux = new String[2];
		String fp = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\file_example.wav";
		//String fd = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\onegai.wav";
		
		WavFile w1 = WavUtils.loadWav(fp);		
		
		byte[] data = w1.getOriginal();
		byte[] data2 = TesteTres.getArr(args);
		
		//System.out.println(data.length + " e " + data2.length);
		
		int count = 0;
		int count2 = 0;
		
		for(int i = 0; i < data.length; i++) {
			if(data[i] != data2[i]) {
				count++;
			}
			if(data[i] == data2[i]) {
				count2++;
			}
		}
		System.out.println(count + " " + count2);
		/*
		for(int i = 0; i < data.length; i++) {
			String s = Integer.toHexString((data[i]<0?(256+(int)data[i]):data[i]));
			System.out.print((s.length() == 2?s:"0"+s) + " ");
			if((i+1)%12 == 0) {
				System.out.println();
			}
			if(i == 44) {
				System.out.print("//");
			}
		}
		
		String fs = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\final_test.wav";
		
		Path path = Paths.get(fs);
		
		byte[] data2 = new byte[0];
		try {
			data2 = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println((data2.length - 44)/2);*/
		
	}

}
