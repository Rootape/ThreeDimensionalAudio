package com.tcc.teste;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TesteStack {
	
	public static final int BUFFER_SIZE = 1024;
	public static final int WAV_HEADER_SIZE = 73;
	
	public static void main(String[] args) {
		//String f2 = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\videoplayback.wav";
		//String f3 = "C:\\Users\\JoaoPauloAmaralCarne\\Desktop\\auxTeste.wav";
		
		String f2 = "C:\\TCC\\temp\\0.wav";
		String f3 = "C:\\TCC\\temp\\testeVolume.wav";
		
		File fi = new File(f2);
		File fo = new File(f3);
		
		changeVolume(fi, fo, 1/2);
	}

	public static void changeVolume(File source, File destination, float scale) {
	    RandomAccessFile fileIn = null;
	    RandomAccessFile fileOut = null;

	    byte[] header = new byte[WAV_HEADER_SIZE];
	    byte[] buffer = new byte[BUFFER_SIZE];

	    try {
	        fileIn = new RandomAccessFile(source, "r");
	        fileOut = new RandomAccessFile(destination, "rw");

	        // copy the header of source to destination file
	        int numBytes = fileIn.read(header); 
	        fileOut.write(header, 0, numBytes);
	        
	        //for(byte h: header) {System.out.println(Integer.toHexString(h));};

	        System.out.println(header[0]);
	        // read & write audio samples in blocks of size BUFFER_SIZE
	        int seekDistance = 0;
	        int bytesToRead = BUFFER_SIZE;
	        long totalBytesRead = 73;

	        //28449467
	        //while(totalBytesRead < fileIn.length()) {
	        while(totalBytesRead < 28449467) {
	            //if (seekDistance + BUFFER_SIZE <= fileIn.length()) {
	        	if (seekDistance + BUFFER_SIZE <= 28449467) {
	                bytesToRead = BUFFER_SIZE;
	            } else {
	                // read remaining bytes                   
	                //bytesToRead = (int) (fileIn.length() - totalBytesRead);
	            	bytesToRead = (int) (28449467 - totalBytesRead);
	            }

	            fileIn.seek(seekDistance);
	            System.out.println(bytesToRead);
	            int numBytesRead = fileIn.read(buffer, 0, bytesToRead);
	            if(numBytesRead == -1) {
	            	break;
	            }
	            if(totalBytesRead == 0) {	
	            	System.out.println(buffer[0]);
	            }
	            totalBytesRead += numBytesRead;

	            for (int i = 0; i < numBytesRead - 1; i++) {
	                // WHAT TO DO HERE?
	            	if(!(i >= 36 && i <=69) || (i >=28449467)) {
	            		buffer[i] = (byte) (scale * (buffer[i]<0?256+(int)buffer[i]:buffer[i]));
	            	}
	            }

	            System.out.println("TotalBytesRead = " + totalBytesRead);
	            System.out.println("Antes numBytesRead = " + numBytesRead);
	            
	            System.out.println("MAMA MINHA ROLA");
	            fileOut.write(buffer, 0, numBytesRead);
	            seekDistance += numBytesRead;
	            System.out.println("Depois numBytesRead = " + numBytesRead);
	        }
	        
	        for(int i = 0; i < 5; i++) {
				for(int j = 12*i; j < 12+12*i; j++) {
					System.out.print(Integer.toHexString(header[j]) + " ");
				}
				System.out.println();
			}
	        
	        for(int i = 0; i < 5; i++) {
				for(int j = 12*i; j < 12+12*i; j++) {
					System.out.print(Integer.toHexString(buffer[j]) + " ");
				}
				System.out.println();
			}

	        //fileOut.setLength(fileIn.length());
	        fileOut.setLength(28449467);
	    } catch (FileNotFoundException e) {
	        System.err.println("File could not be found" + e.getMessage());
	    } catch (IOException e) {
	        System.err.println("IOException: " + e.getMessage());
	    } finally {
	        try {
	            fileIn.close();
	            fileOut.close();
	        } catch (IOException e) {
	            System.err.println("IOException: " + e.getMessage());
	        }       
	    }
	}

}
