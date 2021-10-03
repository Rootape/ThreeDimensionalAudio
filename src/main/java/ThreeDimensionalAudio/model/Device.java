package ThreeDimensionalAudio.model;

import java.util.ArrayList;

public class Device {
	
	private  String id;
	private  String name;
	private  int[] pos;
	private  ArrayList<Audio> audios = new ArrayList<>();
	
	public Device(String id, String name, int[] pos) {
		this.id = id;
		this.name = name;
		this.pos = pos;
	}
	
	public void addAudio(Audio a) {
		this.audios.add(a);
	}
	
	public double getDistance(int[] oPos) {
		return (int) Math.sqrt(Math.pow(this.pos[0] - oPos[0], 2) + Math.pow(this.pos[1] - oPos[1], 2));
	}
	
	public double getUserDistance() {
		return Math.sqrt(Math.pow(pos[0], 2) + Math.pow(pos[1], 2) + Math.pow(pos[2], 2));
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int[] getPos() {
		return this.pos;
	}
	
	public ArrayList<Audio> getAudios(){
		return this.audios;
	}
	
	public String toString() {
		return this.id;
	}
	

}
