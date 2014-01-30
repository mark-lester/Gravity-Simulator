package force;


public class GravityInverseCube extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.max(combined_radii/2, distance);
		return (SCALE * massA * massB) /  (distance * distance * distance);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.max((double)1, distance);
		return (SCALE * massA * massB) / (2 * distance * distance);
	}
}
