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

}
