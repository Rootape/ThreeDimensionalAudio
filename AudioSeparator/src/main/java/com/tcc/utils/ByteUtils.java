package com.tcc.utils;

public class ByteUtils {
	
	public static void main(String[] args) {
		byte[] data = new byte[50];
		//writeBytes(data, -141, 0, 2, "");
		data = writeBytes(data, "RIFF", 0);
		data = writeBytes(data, 2084, 4, 4, "le");
		data = writeBytes(data, "WAVEfmt ", 8);
		data = writeBytes(data, 16, 16, 4, "le");
		data = writeBytes(data, 1, 20, 2, "le");
		data = writeBytes(data, 2, 22, 2, "le");
		data = writeBytes(data, 22050, 24, 4, "le");
		data = writeBytes(data, 88200, 28, 4, "le");
		data = writeBytes(data, 4, 32, 2, "le");
		data = writeBytes(data, 16, 34, 2, "le");
		data = writeBytes(data, "data", 36);
		data = writeBytes(data, 2048, 40, 4, "le");
		data = writeBytes(data, -114, 44, 2, "le");
		data = writeBytes(data, 5888, 46, 2, "le");
		
		for(int i = 0; i < data.length; i++) {
			String s = Integer.toHexString((data[i]<0?(256+(int)data[i]):data[i]));
			//System.out.print((s.length() == 2?s:"0"+s) + " ");
			if((i+1)%12 == 0) {
				//System.out.println();
			}
		}
		
 	}
	
	public static byte[] writeBytes(byte[] data, String info, int inicio) {
		char[] ca = info.toCharArray();
		int count = 0;
		for(char c : ca) {
			data[inicio+count] = (byte)(int)c;
			count++;
		}
		return data;
	}
	
	public static byte[] writeBytes(byte[] data, int info, int inicio, int size, String end) {
		
		String hex = Integer.toHexString(info);
		hex = hex.replace("ffff", "");
		
		if(hex.length() < size * 2) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < (size * 2) - hex.length(); i++) {
				sb.append("0");
			}
			sb.append(hex);
			hex = sb.toString();
		}
		
		if(end.equals("be")) {
			String[] sa = Util.splitHex(Util.bigEndian(Util.hexInt(hex)));
			
			for(String s : sa) {
				data[inicio] = (byte)Integer.parseInt(s, 16);
				inicio++;
			}
		}else {
			String[] sa = Util.splitHex(Util.littleEndian(Util.hexInt(hex)));
			
			for(String s : sa) {
				data[inicio] = (byte)Integer.parseInt(s, 16);
				inicio++;
			}
		}
		 return data;
				
	}

}
