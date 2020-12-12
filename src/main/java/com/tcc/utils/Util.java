package com.tcc.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class Util {
	
	public static String[] splitHex(String hex) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < hex.length(); i++) {
			sb.append(hex.substring(i, i+1));
			if(i != 0 && i%2!=0) {
				sb.append(";");
			}
		}
		return sb.toString().split(";");
		
	}
	
	public static LinkedHashMap<String, int[]> getChunks(byte[] data){
		
		LinkedHashMap<String, int[]> map = new LinkedHashMap<String, int[]>();
		
		for(int i = 0; i < data.length-3; i++) {
			/*
			if(Integer.toHexString((int)data[i]).equals("66") && 
					   Integer.toHexString((int)data[i + 1]).equals("6d") &&
					   Integer.toHexString((int)data[i + 2]).equals("74")) {
				int aux = i + 2;
				map.put("fmt", new int[] {i, aux});
			}*/
			if(Integer.toHexString((int)data[i]).equals("4c") && 
					   Integer.toHexString((int)data[i + 1]).equals("49") &&
					   Integer.toHexString((int)data[i + 2]).equals("53") &&
					   Integer.toHexString((int)data[i + 3]).equals("54")) {
				int aux = i + 3;
				map.put("list", new int[] {i, aux});
			}
			if(Integer.toHexString((int)data[i]).equals("64") && 
				   Integer.toHexString((int)data[i + 1]).equals("61") &&
				   Integer.toHexString((int)data[i + 2]).equals("74") &&
				   Integer.toHexString((int)data[i + 3]).equals("61")) {
				int aux = i + 3;
				map.put("data", new int[] {i, aux});
			}
			if(Integer.toHexString((int)data[i]).equals("66") && 
					   Integer.toHexString((int)data[i + 1]).equals("61") &&
					   Integer.toHexString((int)data[i + 2]).equals("63") &&
					   Integer.toHexString((int)data[i + 3]).equals("74")) {
				int aux = i + 3;
				map.put("fact", new int[] {i, aux});
			}
			if(Integer.toHexString((int)data[i]).equals("63") && 
					   Integer.toHexString((int)data[i + 1]).equals("75") &&
					   Integer.toHexString((int)data[i + 2]).equals("65")) {
				int aux = i + 2;
				map.put("cue", new int[] {i, aux});
			}
			if(Integer.toHexString((int)data[i]).equals("70") && 
					   Integer.toHexString((int)data[i + 1]).equals("6c") &&
					   Integer.toHexString((int)data[i + 2]).equals("73") &&
					   Integer.toHexString((int)data[i + 3]).equals("74")) {
				int aux = i + 3;
				map.put("plst", new int[] {i, aux});
			}
			if(Integer.toHexString((int)data[i]).equals("6c") && 
					   Integer.toHexString((int)data[i + 1]).equals("61") &&
					   Integer.toHexString((int)data[i + 2]).equals("62") &&
					   Integer.toHexString((int)data[i + 3]).equals("6c")) {
				int aux = i + 3;
				map.put("ltxt", new int[] {i, aux});
			}
			if(Integer.toHexString((int)data[i]).equals("6e") && 
					   Integer.toHexString((int)data[i + 1]).equals("6f") &&
					   Integer.toHexString((int)data[i + 2]).equals("74") &&
					   Integer.toHexString((int)data[i + 3]).equals("65")) {
				int aux = i + 3;
				map.put("note", new int[] {i, aux});
			}
			if(Integer.toHexString((int)data[i]).equals("73") && 
					   Integer.toHexString((int)data[i + 1]).equals("6d") &&
					   Integer.toHexString((int)data[i + 2]).equals("70") &&
					   Integer.toHexString((int)data[i + 3]).equals("6c")) {
				int aux = i + 3;
				map.put("smpl", new int[] {i, aux});
			}
			if(Integer.toHexString((int)data[i]).equals("69") && 
					   Integer.toHexString((int)data[i + 1]).equals("6e") &&
					   Integer.toHexString((int)data[i + 2]).equals("73") &&
					   Integer.toHexString((int)data[i + 3]).equals("74")) {
				int aux = i + 3;
				map.put("inst", new int[] {i, aux});
			}
		}
		return map;
	}
	
	public static String bigEndian(int[] values) {
		Integer[] what = Arrays.stream(values).boxed().toArray( Integer[]::new );
		Arrays.sort(what);
		StringBuilder absB = new StringBuilder();
		
		for(int i = 0; i < values.length; i ++) {
			String sAux = Integer.toHexString(what[i]);
			absB.append((sAux.length()<2?"0"+sAux:sAux));
			//absB.append(";");
		}
		
		absB.replace(absB.length()-1, absB.length()-1, "");
		return absB.toString();
	}
	
	public static String littleEndian(int[] values) {
		Integer[] what = Arrays.stream(values).boxed().toArray( Integer[]::new );
		Arrays.sort(what);
		StringBuilder absB = new StringBuilder();
		
		for(int i = values.length-1; i >= 0; i --) {
			String sAux = Integer.toHexString(what[i]);
			absB.append((sAux.length()<2?"0"+sAux:sAux));
			//absB.append(" ");
		}
		
		absB.replace(absB.length(), absB.length(), "");
		return absB.toString();
	}
	
	public static int[] strAToIntA(String[] sa) {
		int[] ia = new int[sa.length];
		for(int i = 0; i < sa.length; i++) {
			ia[i] = Integer.parseInt(sa[i], 16);
		}
		return ia;
	}
	
	public static int[] hexInt(String hex) {
		char[] cHex = hex.toCharArray();
		int[] iHex = new int[hex.length()/2];
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < cHex.length; i++) {
			sb.append(Character.toString(cHex[i]) + Character.toString(cHex[i+1]));
			sb.append(";");
			i++;
			
		}
		
		String[] sHex = sb.toString().split(";");
		
		for(int i = 0; i < sHex.length; i++) {
			iHex[i] = Integer.parseInt(sHex[i], 16);
		}
		
		return iHex;
		
	}

}
