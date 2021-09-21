package ThreeDimensionalAudio.util.data;

public class DataUtils {
	
	public static String byteToHex(byte data) {
		String s = Integer.toHexString((data<0?(256+(int)data):data));
		
		if(s.length() == 2) {
			return s;
		}else {
			return "0" + s;
		}
	}

}
