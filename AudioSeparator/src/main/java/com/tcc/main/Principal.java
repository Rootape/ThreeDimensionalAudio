package com.tcc.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.tcc.audios.WavUtils;
import com.tcc.model.WavFile;
import com.tcc.teste.TesteTres;
import com.tcc.utils.Util;

public class Principal {
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		String[] aux = new String[2];
		String fp = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\TCC\\file_example.wav";
		//String fp = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\TCC\\videoplayback.wav";
		//String fd = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\onegai.wav";
		
		WavFile w1 = WavUtils.loadWav(fp);		
		
		byte[] data = w1.getOriginal();
		
		for(int i = 0; i < data.length/12; i ++) {
			System.out.print("| ");
			for(int j = 12*i; j < 12*(i+1); j++) {
				System.out.print(Util.byteToHex(data[j]) + " ");
			}
			if(!(i%2==0)) {
				System.out.print("|\n");
			}
		}
		
		System.out.println(w1);
		
	}

}
