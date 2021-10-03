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

        int[] ab = new int[]{0, 0};
        int[] bc = new int[]{0, 0};

        for(int i = 0; i < a.length; i++){
            ab[i] = b[i] - a[i];
            bc[i] = c[i] - b[i];
        }

        double abbc = ab[0]*bc[0] + ab[1]*bc[1];
        double div = (Math.sqrt(Math.pow(ab[0], 2) + Math.pow(ab[1], 2)) *
                Math.sqrt(Math.pow(bc[0], 2) + Math.pow(bc[1], 2)));

        double cos = abbc/div;
        
        return cos;
	}

}
