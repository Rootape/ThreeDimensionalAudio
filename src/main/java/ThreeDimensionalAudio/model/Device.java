package ThreeDimensionalAudio.model;

import java.util.ArrayList;

public class Device {
	
	private  String id;
	private  int[] pos;
	private  ArrayList<Audio> audios = new ArrayList<>();
	
	public Device(String id, int[] pos) {
		this.id = id;
		this.pos = pos;
	}
	
	public void addAudio(Audio a) {
		this.audios.add(a);
	}
	
	public double getDistance(int[] oPos) {
		return (int) Math.sqrt(Math.pow(this.pos[0] - oPos[0], 2) + Math.pow(this.pos[1] - oPos[1], 2) + Math.pow(this.pos[2] - oPos[2], 2));
	}
	
	public double getUserDistance() {
		return Math.sqrt(Math.pow(pos[0], 2) + Math.pow(pos[1], 2) + Math.pow(pos[2], 2));
	}
	
	public String getId() {
		return this.id;
	}
	
	public ArrayList<Audio> getAudios(){
		return this.audios;
	}
	
	public String toString() {
		return this.id;
	}
	

}
