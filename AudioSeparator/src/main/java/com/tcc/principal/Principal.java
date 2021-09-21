package com.tcc.principal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.media.sound.WaveFileReader;
import com.tcc.audios.Resample;
import com.tcc.audios.WavUtils;
import com.tcc.devices.DeviceUtils;
import com.tcc.model.WavFile;

public class Principal {
	
	public static void main(String[] args) {
		
		String xmlAudios = "C:\\TCC\\xmlAudio.xml";
		String xmlDevices = "C:\\TCC\\xmlDev.xml";
		String PATH = "C:\\Users\\João\\Documents\\audiosTemp";
		
		ArrayList<Info> devices = DeviceUtils.getDevices();
		HashMap<String, AudioInputStream> audios = new HashMap<>();
		
		HashMap<String, int[]> audioPos = new HashMap<>();
		HashMap<String, int[]> devicePos = new HashMap<>();
		
		ArrayList<String> audioSrc = new ArrayList<>();
		HashMap<String, String> idSrc = new HashMap<>();
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlAudios);
			NodeList list = doc.getElementsByTagName("audio");
			
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				
				String id = node.getAttributes().getNamedItem("id").getNodeValue();
				String src = node.getAttributes().getNamedItem("src").getNodeValue();
				String x = node.getAttributes().getNamedItem("x").getNodeValue();
				String y = node.getAttributes().getNamedItem("y").getNodeValue();
				String z = node.getAttributes().getNamedItem("z").getNodeValue();
				
				audioSrc.add(src);
				idSrc.put(src, id);
				audioPos.put(id, new int[] {Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)});
				
				////System.out.println("Audio:");
				////System.out.println(id + " (" + src +")" +  " - [ " + x + ", " + y + ", " + z +" ]");
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<File> files = new ArrayList<>();
		ArrayList<String> ids = new ArrayList<>();
		int[] srs = new int[audioSrc.size()];
		int i = 0;
		for(String path: audioSrc) {
			ids.add(idSrc.get(path));
			File wavFile = new File(path);
			files.add(wavFile);
			WaveFileReader reader = new WaveFileReader();
	        AudioInputStream audioIn;
			try {
				audioIn = reader.getAudioInputStream(wavFile);
				AudioFormat srcFormat = audioIn.getFormat();
				srs[i] = (int) srcFormat.getSampleRate();
				i++;
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int sr = Arrays.stream(srs).min().getAsInt();
		
		for(i = 0; i < files.size(); i++) {
			files.set(i, Resample.resample(files.get(i), Integer.toString(i), sr));
		}
		
		HashMap<String, File> idFile = new HashMap<>();
		
		for(i = 0; i < files.size(); i++) {
			idFile.put(ids.get(i), files.get(i));
		}
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlDevices);
			NodeList list = doc.getElementsByTagName("device");
			
			for (i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				
				Element element = (Element) node;
				
				//String id = node.getAttributes().getNamedItem("id").getNodeValue();
				
				Node output = element.getElementsByTagName("output").item(0);
				
				String device = output.getAttributes().getNamedItem("id").getNodeValue();
				
				String x = output.getAttributes().getNamedItem("x").getNodeValue();
				String y = output.getAttributes().getNamedItem("y").getNodeValue();
				String z = output.getAttributes().getNamedItem("z").getNodeValue();
				
				devicePos.put(device, new int[] {Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)});
				
				////System.out.println(device = " - [ " + x + ", " + y + ", " + z +" ]");
				
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//idFile // audioPos // devices // devicePos
		
		//int sr = Arrays.stream(srs).min().getAsInt();
		
		HashMap<String, HashMap<String, Double>> finalConfig = new HashMap<>();
		
		for(String s: devicePos.keySet()) {
			HashMap<String, Double> hashAux = new HashMap<>();
			finalConfig.put(s, hashAux);
		}
		
		for(String audioId : audioPos.keySet()) {
			boolean isOnTop = false;
			String devName = null;
			//Confere se alguma caixa esta em cima do audio
			for(String devId : devicePos.keySet()) {
				if(devicePos.get(devId).equals(audioPos.get(audioId))) {
					isOnTop = true;
					devName = devId;
				}
			}
			
			if(isOnTop) {
				HashMap<String, Double> audAux = finalConfig.get(devName);
				audAux.put(audioId, 1.0);
				finalConfig.put(devName, audAux);
			}else {
				HashMap<String, Integer> minAux = new HashMap<>();
				int[] aPos = audioPos.get(audioId);
				for(String devId : devicePos.keySet()) {
					int[] dPosAux = devicePos.get(devId);
					int distance = (int) Math.sqrt(Math.pow(dPosAux[0] - aPos[0], 2) + Math.pow(dPosAux[1] - aPos[1], 2) + Math.pow(dPosAux[2] - aPos[2], 2));
					minAux.put(devId, distance);
				}
				
				Entry<String, Integer> min = null;
				for (Entry<String, Integer> entry : minAux.entrySet()) {
					////System.out.println("Entry - " + entry + ", min - " + min);
				    if (min == null || min.getValue() > entry.getValue()) {
				        min = entry;
				    }
				}
				String minDev = min.getKey();
				
				Entry<String, Integer> scndmin = null;
				for (Entry<String, Integer> entry : minAux.entrySet()) {
					////System.out.println("Entry - " + entry + ", min = " + min + ". Boolean = " + (entry!= min));
				    if ((scndmin == null) || (scndmin.getValue() > entry.getValue() && entry != min)) {
				    	if(entry != min) {
				    		scndmin = entry;
				    	}
				    }
				}
				
				String scndMinDev = scndmin.getKey();
				
				//aPos
				int[] user = new int[]{0, 0, 0};
				int[] devUm = devicePos.get(minDev);
				int[] devDois = devicePos.get(scndMinDev);
				
				double c = Math.sqrt(Math.pow(aPos[0], 2) + Math.pow(aPos[1], 2) + Math.pow(aPos[2], 2));
				
				//Calculo pra Device 1
				double aUm = Math.sqrt(Math.pow(devUm[0] - aPos[0], 2) + Math.pow(devUm[1] - aPos[1], 2) + Math.pow(devUm[2] - aPos[2], 2));
				double bUm = Math.sqrt(Math.pow(devUm[0], 2) + Math.pow(devUm[1], 2) + Math.pow(devUm[2], 2));
				
				double cosUm = (Math.pow(bUm, 2) + Math.pow(c, 2) - Math.pow(aUm, 2))/(2 * bUm * c);
				
				HashMap<String, Double> audAux = finalConfig.get(minDev);
				audAux.put(audioId, cosUm);
				finalConfig.put(minDev, audAux);
				
				//Calculo pra Device 2
				double aDos = Math.sqrt(Math.pow(devDois[0] - aPos[0], 2) + Math.pow(devDois[1] - aPos[1], 2) + Math.pow(devDois[2] - aPos[2], 2));
				double bDos = Math.sqrt(Math.pow(devDois[0], 2) + Math.pow(devDois[1], 2) + Math.pow(devDois[2], 2));
				
				double cosDois = (Math.pow(bDos, 2) + Math.pow(c, 2) - Math.pow(aDos, 2))/(2 * bDos * c);
				
				audAux = finalConfig.get(scndMinDev);
				audAux.put(audioId, cosDois);
				finalConfig.put(scndMinDev, audAux);
				
			}
			
		}
		
		for(String s : finalConfig.keySet()) {
			
			//System.out.println(s);
			HashMap<String, WavFile> devAudios = new HashMap<>();
			int[] dataSizes = new int[finalConfig.get(s).keySet().size()];
			HashMap<Integer, String> sizeAudAux = new HashMap<>();
			
			i = 0;
			
			for(String id : finalConfig.get(s).keySet()) {
				//System.out.println("Audio: " + id + ", mult = " + finalConfig.get(s).get(id));
				WavFile aux = WavUtils.loadWav(idFile.get(id));
				//aux.printWav(6);
				devAudios.put(id, aux);
				System.out.println("Tamanho de " + id + " = " + aux.getData().length);
				dataSizes[i] = (aux.getData().length);
				sizeAudAux.put(aux.getData().length, id);
				i++;
			}
			
			for(int asd : dataSizes) {
				//System.out.print(asd + " - ");
			}
			
			int maxSize = Arrays.stream(dataSizes).max().getAsInt();
			//System.out.println("Maior = " + maxSize);
			
			String maiorId = sizeAudAux.get(maxSize);
			
			WavFile maior = devAudios.get(maiorId);
			maior.changeVolume(finalConfig.get(s).get(maiorId));
			
			for(String id : devAudios.keySet()) {
				if(!id.equals(maiorId)) {
					maior.sumOfFiles(devAudios.get(id), finalConfig.get(s).get(id));
				}
			}
			
			try (FileOutputStream stream = new FileOutputStream("C:\\TCC\\tempFinal\\"+s+".wav")) {
			    stream.write(maior.getOriginal());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Foi");
		
	}

}
