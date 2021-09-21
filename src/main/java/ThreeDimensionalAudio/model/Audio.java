package ThreeDimensionalAudio.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;

import ThreeDimensionalAudio.util.data.DataUtils;

public class Audio {
	
	private  String id;
	private  String path;
	private  AudioFormat af;
	private  float sampleRate;
	private  float frameRate;
	private  int[] pos;
	
	
	public Audio() {
		
	}
	
	public Audio(String id, String path, AudioFormat audio) {
		this.id = id;
		this.path = path;
		this.af = audio;
		this.sampleRate = audio.getSampleRate();
		this.frameRate = audio.getFrameRate();
	}
	
	public String getId() {
		return this.id;
	}
	
	public float getSampleRate() {
		return this.sampleRate;
	}
	
	public float getFrameRate() {
		return this.frameRate;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public int[] getPos() {
		return this.pos;
	}
	
	public AudioFormat getAudioFormat() {
		return this.af;
	}
	
	public void setPos(int[] pos) {
		this.pos = pos;
	}
	
	public double getUserDistance() {
		return Math.sqrt(Math.pow(pos[0], 2) + Math.pow(pos[1], 2) + Math.pow(pos[2], 2));
	}
	
	public void printOriginalSrFr() {
		System.out.println("SampleRate = "+this.af.getSampleRate() + "\nFrameRate = "+this.af.getFrameRate()+"\n");
	}
	
	public int getDistance(int[] oPos) {
		return (int) Math.sqrt(Math.pow(this.pos[0] - oPos[0], 2) + Math.pow(this.pos[1] - oPos[1], 2) + Math.pow(this.pos[2] - oPos[2], 2));
	}
	
	public byte[] getWavAsByte() {
		Path path = Paths.get(this.path);
		byte[] data = new byte[0];
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void printWav(int c) {
		
		byte[] infos = this.getWavAsByte();
		
		for(int i = 0; i < c; i ++) {
			System.out.print("| ");
			for(int j = 12*i; j < 12*(i+1); j++) {
				System.out.print(DataUtils.byteToHex(infos[j]) + " ");
			}
			if(!(i%2==0)) {
				System.out.print("|\n");
			}
		}
		
	}
	
	public String toString() {
		return this.id;
	}

}
