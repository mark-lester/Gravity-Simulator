package force;

public class InverseSquareRoot extends Force {
	public double force(double distance, double massA, double massB, double combined_radii){
		distance=Math.max(SMALL, distance);
		return SCALE * massA * massB / Math.sqrt(distance);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		distance=Math.max(SMALL, distance);
		return 2 * SCALE * massA * massB * Math.sqrt(distance);
	}
}
