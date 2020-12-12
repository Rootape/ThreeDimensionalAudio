package com.tcc.model;

import javax.sound.sampled.Mixer.Info;

public class Mixer {

	private String name;
	private Info info;
	
	public Mixer(Info info) {
		super();
		this.info = info;
		this.name = info.getName();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	
	
	
}
