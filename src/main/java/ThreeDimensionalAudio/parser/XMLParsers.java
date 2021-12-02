package ThreeDimensionalAudio.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.model.Device;
import ThreeDimensionalAudio.util.audio.AudioUtils;

public class XMLParsers {
	
	/**
	 * Parser para o XML de Áudio.
	 * @param xmlAudios Caminho do arquivo XML
	 * @return Uma lista de áudios extraídos do XML.
	 */
	public static ArrayList<Audio> readAudiosConfig(String xmlAudios) {
		
		ArrayList<Audio> audios = new ArrayList<>();
		Audio tempAudio;
		
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
				
				tempAudio = new Audio();
				tempAudio = AudioUtils.normalizeAudio(id, src);
				tempAudio.setPos(new int[] {Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)});
				//tempAudio.setPos(new int[] {Integer.parseInt(x), Integer.parseInt(y)});
				audios.add(tempAudio);
				
				tempAudio = null;
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return audios;
		
	}
	
	/**
	 * Parser para o XML de Dispositivos.
	 * @param xmlDevices Caminho do arquivo XML
	 * @return Um mapa de dispositivos extraídos do XML.
	 */
	public static HashMap<String, Device> readDevicesConfig(String xmlDevices) {
		
		HashMap<String, Device> devices = new HashMap<>();
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlDevices);
			NodeList list = doc.getElementsByTagName("device");
			
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				
				Element element = (Element) node;
				
				Node output = element.getElementsByTagName("output").item(0);
				
				String id = output.getAttributes().getNamedItem("id").getNodeValue();
				String name = output.getAttributes().getNamedItem("name").getNodeValue();
				
				String x = output.getAttributes().getNamedItem("x").getNodeValue();
				String y = output.getAttributes().getNamedItem("y").getNodeValue();
				String z = output.getAttributes().getNamedItem("z").getNodeValue();
				
				//devices.put(device, new Device(device, new int[] {Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)}));
				devices.put(id, new Device(id, name, new int[] {Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)}));
				
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return devices;
		
	}

}
