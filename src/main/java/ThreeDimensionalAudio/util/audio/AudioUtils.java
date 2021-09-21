package ThreeDimensionalAudio.util.audio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFileFormat.Type;

import com.sun.media.sound.WaveFileReader;
import com.sun.media.sound.WaveFileWriter;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.model.Device;


@SuppressWarnings("restriction")
public class AudioUtils {
	
	public static final int BUFFER_SIZE = 10000;
	public static final int WAV_HEADER_SIZE = 44;
	
	public static AudioFormat getAudioFormat(String path) {
		File wavFile = new File(path);
		WaveFileReader reader = new WaveFileReader();
        AudioInputStream audioIn;
		try {
			audioIn = reader.getAudioInputStream(wavFile);
			AudioFormat srcFormat = audioIn.getFormat();
	        return srcFormat;
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Audio alterAudio(Audio a, float sr, float fr) {
		try {
			String newPath = "C:\\TCC\\help\\temp\\"+a.getId()+"RESAMPLED.wav";
			
			File file = new File(a.getPath());
			File dstFile = new File(newPath);
			
			WaveFileReader reader = new WaveFileReader();
	        AudioInputStream audioIn = reader.getAudioInputStream(file);
	        AudioFormat srcFormat = audioIn.getFormat();
			
			AudioFormat novo = new AudioFormat(a.getAudioFormat().getEncoding(),
	        		sr,
	        		a.getAudioFormat().getSampleSizeInBits(),
	        		a.getAudioFormat().getChannels(),
	        		a.getAudioFormat().getFrameSize(),
	                fr,
	                a.getAudioFormat().isBigEndian());
			
			AudioInputStream convertedIn = AudioSystem.getAudioInputStream(novo, audioIn);
	
	        WaveFileWriter writer = new WaveFileWriter();
	        writer.write(convertedIn, Type.WAVE, dstFile);
	        
	        Audio newAudio = new Audio(a.getId()+"RESAMPLED", newPath, novo);
	        
	        newAudio.setPos(a.getPos());
	        
	        return newAudio;
	        
		}catch(Exception e) {
			System.out.println("Uepa ratinho nho nho");
		}
		
		return null;
        
	}
	
	public static Audio alterAudio(Audio a, float sr) {
		try {
			
			String newPath = "C:\\TCC\\help\\temp\\"+a.getId()+"RESAMPLED.wav";
			
			File file = new File(a.getPath());
			File dstFile = new File(newPath);
			
			WaveFileReader reader = new WaveFileReader();
	        AudioInputStream audioIn = reader.getAudioInputStream(file);
	        AudioFormat srcFormat = audioIn.getFormat();
			
			AudioFormat novo = new AudioFormat(a.getAudioFormat().getEncoding(),
	        		sr,
	        		a.getAudioFormat().getSampleSizeInBits(),
	        		a.getAudioFormat().getChannels(),
	        		a.getAudioFormat().getFrameSize(),
	                a.getAudioFormat().getFrameRate(),
	                a.getAudioFormat().isBigEndian());
			
			AudioInputStream convertedIn = AudioSystem.getAudioInputStream(novo, audioIn);
	
	        WaveFileWriter writer = new WaveFileWriter();
	        writer.write(convertedIn, Type.WAVE, dstFile);
	        
	        Audio newAudio = new Audio(a.getId()+"RESAMPLED", newPath, novo);
	        
	        newAudio.setPos(a.getPos());
	        
	        return newAudio;
	        
		}catch(Exception e) {
			System.out.println("Uepa ratinho nho nho");
		}
		
		return null;
        
	}
	
	public static Audio normalizeAudio(String id, String path) {
		//https://stackoverflow.com/questions/15410725/java-resample-wav-soundfile-without-third-party-library
		String newPath = "C:\\TCC\\help\\temp\\"+id+".wav";
		
		try {
			File wavFile = new File(path);
			WaveFileReader reader = new WaveFileReader();
	        AudioInputStream audioIn = reader.getAudioInputStream(wavFile);
	        AudioFormat srcFormat = audioIn.getFormat();
			
			AudioFormat novo = new AudioFormat(srcFormat.getEncoding(),
	        		srcFormat.getSampleRate(),
	                srcFormat.getSampleSizeInBits(),
	                (srcFormat.getChannels()==2?srcFormat.getChannels()/2:srcFormat.getChannels()),
	                srcFormat.getFrameSize(),
	                srcFormat.getFrameRate(),
	                srcFormat.isBigEndian());
			
			AudioInputStream convertedIn = AudioSystem.getAudioInputStream(novo, audioIn);
	
	        WaveFileWriter writer = new WaveFileWriter();
	        writer.write(convertedIn, Type.WAVE, new File(newPath));
	        
	        return new Audio(id, newPath, novo);
		}catch(Exception e) {
			System.out.println("Estourou no " + id);
			throw new RuntimeException(e);
		}
	}
	
	public static Audio normalizeFinalAudio(String id, String path, float srFinal, float frFinal) {
		
		try {
			File wavFile = new File(path);
			
			WaveFileReader reader = new WaveFileReader();
	        AudioInputStream audioIn = reader.getAudioInputStream(wavFile);
	        AudioFormat srcFormat = audioIn.getFormat();
			
			AudioFormat novo = new AudioFormat(srcFormat.getEncoding(),
	        		srFinal,
	                srcFormat.getSampleSizeInBits(),
	                srcFormat.getChannels(),
	                srcFormat.getFrameSize(),
	                frFinal,
	                srcFormat.isBigEndian());
			
			AudioInputStream convertedIn = AudioSystem.getAudioInputStream(novo, audioIn);
	
	        WaveFileWriter writer = new WaveFileWriter();
	        writer.write(convertedIn, Type.WAVE, new File(path));
	        
	        return new Audio(id, path, novo);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ArrayList<Audio> normalizeSrFr(ArrayList<Audio> audios){
		
		ArrayList<Float> srs = new ArrayList<>();
		for(Audio k : audios) {
			srs.add(k.getSampleRate());
		}
		
		ArrayList<Float> frs = new ArrayList<>();
		for(Audio k : audios) {
			frs.add(k.getFrameRate());
		}
		
		Float sampleRate = Collections.max(srs);
		Float frameRate = Collections.max(frs);
		
		ArrayList<Audio> audiosNovo = new ArrayList<>();
		
		for(Audio aux : audios) {
			audiosNovo.add(alterAudio(aux, sampleRate, frameRate));
		}
		
		return audiosNovo;
		
	}
	
	public static ArrayList<Audio> normalizeSr(ArrayList<Audio> audios){
		
		ArrayList<Float> srs = new ArrayList<>();
		for(Audio k : audios) {
			srs.add(k.getSampleRate());
		}
		
		Float sampleRate = Collections.max(srs);
		
		ArrayList<Audio> audiosNovo = new ArrayList<>();
		
		for(Audio aux : audios) {;
			audiosNovo.add(alterAudio(aux, sampleRate));
		}
		
		return audiosNovo;
		
	}
	
	public static Audio changeVolume(String id, String src, String dst, float scale) {
		
		File source = new File(src);
		File destination = new File(dst);
		
	    RandomAccessFile fileIn = null;
	    RandomAccessFile fileOut = null;

	    byte[] header = new byte[WAV_HEADER_SIZE];
	    byte[] buffer = new byte[BUFFER_SIZE];

	    try {
	        fileIn = new RandomAccessFile(source, "r");
	        fileOut = new RandomAccessFile(destination, "rw");
	        
	        int numBytes = fileIn.read(header); 
	        fileOut.write(header, 0, numBytes);
	        
	        int seekDistance = 0;
	        int bytesToRead = BUFFER_SIZE;
	        long totalBytesRead = 0;

	        while(totalBytesRead < fileIn.length()) {
	            if (seekDistance + BUFFER_SIZE <= fileIn.length()) {
	                bytesToRead = BUFFER_SIZE;
	            } else {             
	                bytesToRead = (int) (fileIn.length() - totalBytesRead);
	            }

	            fileIn.seek(seekDistance);
	            int numBytesRead = fileIn.read(buffer, 0, bytesToRead);
	            totalBytesRead += numBytesRead;

	            for (int i = 0; i < numBytesRead - 1; i++) {
	                buffer[i] = (byte) (scale * ((int) buffer[i]));
	            }

	            fileOut.write(buffer, 0, numBytesRead);
	            seekDistance += numBytesRead;
	        }

	        fileOut.setLength(fileIn.length());  
	        
	        
	    } catch (FileNotFoundException e) {
	        System.err.println("File could not be found" + e.getMessage());
	    } catch (IOException e) {
	        System.err.println("IOException: " + e.getMessage());
	    } finally {
	        try {
	            fileIn.close();
	            fileOut.close();
	            
	            File wavFile = new File(dst);
						
				WaveFileReader reader = new WaveFileReader();
				AudioInputStream audioIn = reader.getAudioInputStream(wavFile);
				AudioFormat srcFormat = audioIn.getFormat();
			    
			    return new Audio(id, dst, srcFormat);
		        
			} catch (IOException e) {
	            System.err.println("IOException: " + e.getMessage());
	        } catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}       
	    }
	    return null;
	}
	
	public static void sumAudios(HashMap<String, Device> devices) {
		
		try {
			for(String k : devices.keySet()) {
				
				Device d = devices.get(k);
				
				System.out.println();
				byte[] bAux = null;
				int maxSize = 0;
				byte[] header = new byte[44];
				
				for(Audio a : d.getAudios()) {
					bAux = Files.readAllBytes(Paths.get(a.getPath()));
					System.out.println(a.getId() + " - Size = " + bAux.length);
					if(bAux.length > maxSize) {
						maxSize = bAux.length;
						for(int i = 0; i < header.length; i++) {
							header[i] = bAux[i];
						}
					}
				}
				
				byte[] data = new byte[maxSize];
				
				for(int i = 0; i < header.length; i++) {
					data[i] = header[i];
				}
					
				for(Audio a : d.getAudios()) {
					//System.out.println("Pegando audio: " + a.getPath() + " com SR = " + a.getSampleRate() + " e FR = " + a.getFrameRate());
					bAux = Files.readAllBytes(Paths.get(a.getPath()));
					for(int i = 44; i < bAux.length; i++) {
						data[i] = (byte)((data[i]<0?(256+(int)data[i]):data[i]) + (bAux[i]<0?(256+(int)bAux[i]):bAux[i]));
					}
				}
				
				String fd = "C:\\TCC\\help\\final\\" + d.getId() + ".wav";
				
				try (FileOutputStream stream = new FileOutputStream(fd)) {
				    stream.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
