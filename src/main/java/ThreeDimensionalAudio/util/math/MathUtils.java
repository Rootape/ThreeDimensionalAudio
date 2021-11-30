package ThreeDimensionalAudio.util.math;

import ThreeDimensionalAudio.model.Audio;
import ThreeDimensionalAudio.model.Device;

public class MathUtils {
	
	public static double getCos(Device d, Audio a) {
		double valA = d.getDistance(a.getPos());
		double valB = d.getUserDistance();
		double valC = a.getUserDistance();
		
		return (Math.pow(valB, 2) + Math.pow(valC, 2) - Math.pow(valA, 2))/(2 * valB * valC);
	}
	
	public static double getCosNew(int[] a, int[] b, int[] c) {

        int[] ab = new int[]{0, 0, 0};
        int[] bc = new int[]{0, 0, 0};

        //System.out.println(a.length + b.length + c.length);
        
        for(int i = 0; i < a.length; i++){
            ab[i] = b[i] - a[i];
            bc[i] = c[i] - b[i];
        }

        double abbc = ab[0]*bc[0] + ab[1]*bc[1] +ab[2]*bc[2];
        double div = (Math.sqrt(Math.pow(ab[0], 2) + Math.pow(ab[1], 2) + Math.pow(ab[2], 2)) *
                Math.sqrt(Math.pow(bc[0], 2) + Math.pow(bc[1], 2) + Math.pow(bc[2], 2)));

        double cos = abbc/div;
        
        return cos;
	}
	
	public static double getSin(double cos) {
		return Math.sqrt(1 - Math.pow(cos, 2));
	}
	
	public static void main(String[] args) {
		int[] a = new int[] {1, 2, 1};
		int[] b = new int[] {2, 2, 0};
		int[] c = new int[] {3, 2, 0};
		
		System.out.println(isPlane(a, b, c));
	}
	
	public static boolean isPlane(int[] a, int[] b, int[] c) {
		
		for(int i = 0; i < a.length; i++) {
			if(a[i] == b[i] && b[i] == c[i]) {
				return true;
			}
		}
		return false;
		
	}

}
