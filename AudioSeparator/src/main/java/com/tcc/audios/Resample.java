package com.tcc.audios;

import java.io.File;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import com.sun.media.sound.WaveFileReader;
import com.sun.media.sound.WaveFileWriter;

@SuppressWarnings("restriction")
public class Resample {

public static void main(String[] args) {
	
	//resample("C:\\Users\\João\\Downloads\\file_example.wav", 8000);
	
}

public static void resampleTest(String path, int sampleRate) {
    try {
        File wavFile = new File(path);
        File dstFile = new File(path);
        
        WaveFileReader reader = new WaveFileReader();
        AudioInputStream audioIn = reader.getAudioInputStream(wavFile);
        AudioFormat srcFormat = audioIn.getFormat();
        
        AudioFormat dstFormat = new AudioFormat(srcFormat.getEncoding(),
        		sampleRate,
                srcFormat.getSampleSizeInBits(),
                srcFormat.getChannels(),
                srcFormat.getFrameSize(),
                srcFormat.getFrameRate(),
                srcFormat.isBigEndian());

        AudioInputStream convertedIn = AudioSystem.getAudioInputStream(dstFormat, audioIn);

        WaveFileWriter writer = new WaveFileWriter();
        writer.write(convertedIn, Type.WAVE, dstFile);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

public static File resample(File wavFile, String name, float sampleRate, float frameRate) {
    try {
    	File endpoint = new File("C:\\TCC\\temp\\" + name + ".wav");
        WaveFileReader reader = new WaveFileReader();
        AudioInputStream audioIn = reader.getAudioInputStream(wavFile);
        AudioFormat srcFormat = audioIn.getFormat();
        
        System.out.println("SampleRate - " + srcFormat.getSampleRate());
        System.out.println("Sample Sizes - " + srcFormat.getSampleSizeInBits());
        System.out.println("Canais - " + srcFormat.getChannels());
        System.out.println("Frame size - " + srcFormat.getFrameSize());
        System.out.println("Frame Rate - " + srcFormat.getFrameRate());
        //System.out.println("Bige endian - " + srcFormat.isBigEndian());
        
        AudioFormat dstFormat = new AudioFormat(srcFormat.getEncoding(),
        		sampleRate,
                srcFormat.getSampleSizeInBits(),
                srcFormat.getChannels(),
                srcFormat.getFrameSize(),
                frameRate,
                srcFormat.isBigEndian());

        AudioInputStream convertedIn = AudioSystem.getAudioInputStream(dstFormat, audioIn);
        WaveFileWriter writer = new WaveFileWriter();
        writer.write(convertedIn, Type.WAVE, endpoint);
        return new File("C:\\TCC\\temp\\" + name + ".wav");
        
        
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

}