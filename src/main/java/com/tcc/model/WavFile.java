package com.tcc.model;

import com.tcc.utils.ByteUtils;

public class WavFile extends ByteUtils{
	
	private String name;
	
	private int chunkSize;     // data[ 4], data[ 7]
	private int subChunk1Size; // data[16], data[19]
	private int audioFormat;   // data[20], data[21]
	private int numChannels;   // data[22], data[23]
	private int sampleRate;    // data[24], data[25], data[26], data[27]
	private int byteRate;      // data[28], data[29], data[30], data[31]
	private int blockAlign;    // data[32], data[33]
	private int bitsPerSample; // data[34], data[35]
	private int subChunk2Size; // data[40], data[43]
	private int bytesPerSample;
	
	private int[] data;

	public WavFile(String name, int chunkSize, int subChunk1Size, int audioFormat, int numChannels, int sampleRate,
			int byteRate, int blockAlign, int bitsPerSample, int subChunk2Size, int[] data) {
		super();
		this.name = name;
		this.chunkSize = chunkSize;
		this.subChunk1Size = subChunk1Size;
		this.audioFormat = audioFormat;
		this.numChannels = numChannels;
		this.sampleRate = sampleRate;
		this.byteRate = byteRate;
		this.blockAlign = blockAlign;
		this.bitsPerSample = bitsPerSample;
		this.subChunk2Size = subChunk2Size;
		this.data = data;
		this.bytesPerSample = bitsPerSample/8;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getChunkSize() {
		return chunkSize;
	}
	
	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}
	
	public int getSubChunk1Size() {
		return subChunk1Size;
	}
	
	public void setSubChunk1Size(int subChunk1Size) {
		this.subChunk1Size = subChunk1Size;
	}
	
	public int getAudioFormat() {
		return audioFormat;
	}
	
	public void setAudioFormat(int audioFormat) {
		this.audioFormat = audioFormat;
	}
	
	public int getNumChannels() {
		return numChannels;
	}
	
	public void setNumChannels(int numChannels) {
		this.numChannels = numChannels;
	}
	
	public int getSampleRate() {
		return sampleRate;
	}
	
	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}
	
	public int getByteRate() {
		return byteRate;
	}
	
	public void setByteRate(int byteRate) {
		this.byteRate = byteRate;
	}
	
	public int getBlockAlign() {
		return blockAlign;
	}
	
	public void setBlockAlign(int blockAlign) {
		this.blockAlign = blockAlign;
	}
	
	public int getBitsPerSample() {
		return bitsPerSample;
	}
	
	public void setBitsPerSample(int bitsPerSample) {
		this.bitsPerSample = bitsPerSample;
	}
	
	public int getSubChunk2Size() {
		return subChunk2Size;
	}
	
	public void setSubChunk2Size(int subChunk2Size) {
		this.subChunk2Size = subChunk2Size;
	}
	
	public int[] getData() {
		return data;
	}
	
	public void setData(int[] data) {
		this.data = data;
	}
	
	public byte[] getOriginal() {
		
		System.out.println("Data Tamanho = "+data.length);
		
		byte[] buffer = new byte[44 + data.length*bytesPerSample];
		
		buffer = writeBytes(buffer, "RIFF", 0);
		buffer = writeBytes(buffer, chunkSize, 4, 4, "le");
		buffer = writeBytes(buffer, "WAVEfmt ", 8);
		buffer = writeBytes(buffer, subChunk1Size, 16, 4, "le");
		buffer = writeBytes(buffer, audioFormat, 20, 2, "le");
		buffer = writeBytes(buffer, numChannels, 22, 2, "le");
		buffer = writeBytes(buffer, sampleRate, 24, 4, "le");
		buffer = writeBytes(buffer, byteRate, 28, 4, "le");
		buffer = writeBytes(buffer, blockAlign, 32, 2, "le");
		buffer = writeBytes(buffer, bitsPerSample, 34, 2, "le");
		buffer = writeBytes(buffer, "data", 36);
		buffer = writeBytes(buffer, subChunk2Size, 40, 4, "le");
		
		int c = 44;
		for(int i : data) {
			buffer = writeBytes(buffer, i, c, bytesPerSample, "le");
			c += bytesPerSample;
		}
		
		return buffer;
	}
	
	public String toString() {
		return "BitRate: " + sampleRate * numChannels * bitsPerSample;
	}
	
}
